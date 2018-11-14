/*
 * Scala (https://www.scala-lang.org)
 *
 * Copyright EPFL and Lightbend, Inc.
 *
 * Licensed under Apache License 2.0
 * (http://www.apache.org/licenses/LICENSE-2.0).
 *
 * See the NOTICE file distributed with this work for
 * additional information regarding copyright ownership.
 */

package scala
package reflect
package internal

trait Typers {
  val global: SymbolTable
  import global._

  type Typer >: Null <: TreeTyper
  trait TreeTyper {
    // formerly typedInPatternMode
    def typedAlternative(tree: Alternative, mode: Mode, pt: Type): Tree
    def typedStar(tree: Star, mode: Mode, pt: Type): Tree

    // formerly typedTypTree
    def typedTypeTree(tree: TypeTree, mode: Mode, pt: Type): Tree
    def typedAppliedTypeTree(tree: AppliedTypeTree, mode: Mode, pt: Type): Tree
    def typedTypeBoundsTree(tree: TypeBoundsTree, mode: Mode, pt: Type): Tree
    def typedSingletonTypeTree(tree: SingletonTypeTree, mode: Mode, pt: Type): Tree
    def typedSelectFromTypeTree(tree: SelectFromTypeTree, mode: Mode, pt: Type): Tree
    def typedCompoundTypeTree(tree: CompoundTypeTree, mode: Mode, pt: Type): Tree
    def typedExistentialTypeTree(tree: ExistentialTypeTree, mode: Mode, pt: Type): Tree

    // formerly typedMemberDef
    def typedValDef(tree: ValDef): Tree
    def typedDefDef(tree: DefDef): Tree
    def typedClassDef(tree: ClassDef): Tree
    def typedModuleDef(tree: ModuleDef): Tree
    def typedTypeDef(tree: TypeDef): Tree
    def typedPackageDef(tree: PackageDef): Tree

    // formerly typedOutsidePatternMode
    def typedBlock(tree: Block, mode: Mode, pt: Type): Tree
    def typedIf(tree: If, mode: Mode, pt: Type): Tree
    def typedTypeApply(tree: TypeApply, mode: Mode, pt: Type): Tree
    def typedFunction(tree: Function, mode: Mode, pt: Type): Tree
    def typedMatch(tree: Match, mode: Mode, pt: Type): Tree
    def typedAssign(tree: Assign, mode: Mode, pt: Type): Tree
    def typedNamedArg(tree: NamedArg, mode: Mode, pt: Type): Tree
    def typedSuper(tree: Super, mode: Mode, pt: Type): Tree
    def typedAnnotated(tree: Annotated, mode: Mode, pt: Type): Tree
    def typedReturn(tree: Return, mode: Mode, pt: Type): Tree
    def typedNew(tree: New, mode: Mode, pt: Type): Tree
    def typedTry(tree: Try, mode: Mode, pt: Type): Tree
    def typedThrow(tree: Throw, mode: Mode, pt: Type): Tree
    def typedArrayValue(tree: ArrayValue, mode: Mode, pt: Type): Tree
    def typedApplyDynamic(tree: ApplyDynamic, mode: Mode, pt: Type): Tree
    def typedReferenceToBoxed(tree: ReferenceToBoxed, mode: Mode, pt: Type): Tree

    //def typedDocDef
    def typedLabelDef(tree: LabelDef): Tree

    // formerly typedInAnyMode
    def typedIdent(tree: Ident, mode: Mode, pt: Type): Tree
    def typedBind(tree: Bind, mode: Mode, pt: Type): Tree
    def typedApply(tree: Apply, mode: Mode, pt: Type): Tree
    def typedSelect(tree: Select, mode: Mode, pt: Type): Tree
    def typedLiteral(tree: Literal, mode: Mode, pt: Type): Tree
    def typedTyped(tree: Typed, mode: Mode, pt: Type): Tree
    def typedThis(tree: This, mode: Mode, pt: Type): Tree
    //def typedUnApply
  }

}

/*
      // Trees only allowed during pattern mode.
      def typedInPatternMode(tree: Tree): Tree = tree match {
        case tree: Alternative => typedAlternative(tree)
        case tree: Star        => typedStar(tree)
        case _                 => abort(s"unexpected tree in pattern mode: ${tree.getClass}\n$tree")
      }

      @inline def typedTypTree(tree: TypTree): Tree = tree match {
        case tree: TypeTree                     => typedTypeTree(tree)
        case tree: AppliedTypeTree              => typedAppliedTypeTree(tree)
        case tree: TypeBoundsTree               => typedTypeBoundsTree(tree)
        case tree: SingletonTypeTree            => typedSingletonTypeTree(tree)
        case tree: SelectFromTypeTree           => typedSelect(tree, typedType(tree.qualifier, mode), tree.name)
        case tree: CompoundTypeTree             => typedCompoundTypeTree(tree)
        case tree: ExistentialTypeTree          => typedExistentialTypeTree(tree)
        case tree: TypeTreeWithDeferredRefCheck => tree // TODO: retype the wrapped tree? TTWDRC would have to change to hold the wrapped tree (not a closure)
        case _                                  => abort(s"unexpected type-representing tree: ${tree.getClass}\n$tree")
      }

      @inline def typedMemberDef(tree: MemberDef): Tree = tree match {
        case tree: ValDef     => typedValDef(tree)
        case tree: DefDef     => defDefTyper(tree).typedDefDef(tree)
        case tree: ClassDef   => newTyper(context.makeNewScope(tree, sym)).typedClassDef(tree)
        case tree: ModuleDef  => newTyper(context.makeNewScope(tree, sym.moduleClass)).typedModuleDef(tree)
        case tree: TypeDef    => typedTypeDef(tree)
        case tree: PackageDef => typedPackageDef(tree)
        case _                => abort(s"unexpected member def: ${tree.getClass}\n$tree")
      }

      // Trees not allowed during pattern mode.
      def typedOutsidePatternMode(tree: Tree): Tree = tree match {
        case tree: Block            => typerWithLocalContext(context.makeNewScope(tree, context.owner))(_.typedBlock(tree, mode, pt))
        case tree: If               => typedIf(tree)
        case tree: TypeApply        => insertStabilizer(typedTypeApply(tree), tree)
        case tree: Function         => typedFunction(tree)
        case tree: Match            => typedVirtualizedMatch(tree)
        case tree: New              => typedNew(tree)
        case tree: Assign           => typedAssign(tree.lhs, tree.rhs)
        case tree: NamedArg         => typedAssign(tree.lhs, tree.rhs)
        case tree: Super            => typedSuper(tree)
        case tree: Annotated        => typedAnnotated(tree)
        case tree: Return           => typedReturn(tree)
        case tree: Try              => typedTry(tree)
        case tree: Throw            => typedThrow(tree)
        case tree: ArrayValue       => typedArrayValue(tree)
        case tree: ApplyDynamic     => typedApplyDynamic(tree)
        case tree: ReferenceToBoxed => typedReferenceToBoxed(tree)
        case tree: LabelDef         => labelTyper(tree).typedLabelDef(tree)
        case tree: DocDef           => typedDocDef(tree, mode, pt)
        case _                      => abort(s"unexpected tree: ${tree.getClass}\n$tree")
      }

      // Trees allowed in or out of pattern mode.
      @inline def typedInAnyMode(tree: Tree): Tree = tree match {
        case tree: Ident   => typedIdentOrWildcard(tree)
        case tree: Bind    => typedBind(tree)
        case tree: Apply   => insertStabilizer(typedApply(tree), tree)
        case tree: Select  => insertStabilizer(typedSelectOrSuperCall(tree), tree)
        case tree: Literal => typedLiteral(tree)
        case tree: Typed   => typedTyped(tree)
        case tree: This    => typedThis(tree)  // scala/bug#6104
        case tree: UnApply => abort(s"unexpected UnApply $tree") // turns out UnApply never reaches here
        case _             =>
          if (mode.inPatternMode)
            typedInPatternMode(tree)
          else
            typedOutsidePatternMode(tree)
      }

      // begin typed1
      tree match {
        case tree: TypTree   => typedTypTree(tree)
        case tree: MemberDef => typedMemberDef(tree)
        case _               => typedInAnyMode(tree)
      }
    }

 */