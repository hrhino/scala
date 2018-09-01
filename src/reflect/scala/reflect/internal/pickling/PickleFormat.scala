package scala
package reflect
package internal
package pickling

/** This object provides constants for pickling attributes.
 *
 *  If you extend the format, be sure to increase the
 *  version minor number.
 *
 *  @author Martin Odersky
 *  @version 1.0
 */
object PickleFormat {

/***************************************************
 * Symbol table attribute format:
 *   Symtab         = nentries_Nat {Entry}
 *   Entry          = 1 TERMNAME len_Nat NameInfo
 *                  | 2 TYPENAME len_Nat NameInfo
 *                  | 3 NONEsym len_Nat
 *                  | 4 TYPEsym len_Nat SymbolInfo
 *                  | 5 ALIASsym len_Nat SymbolInfo
 *                  | 6 CLASSsym len_Nat SymbolInfo [thistype_Ref]
 *                  | 7 MODULEsym len_Nat SymbolInfo
 *                  | 8 VALsym len_Nat [defaultGetter_Ref /* no longer needed*/] SymbolInfo [alias_Ref]
 *                  | 9 EXTref len_Nat name_Ref [owner_Ref]
 *                  | 10 EXTMODCLASSref len_Nat name_Ref [owner_Ref]
 *                  | 11 NOtpe len_Nat
 *                  | 12 NOPREFIXtpe len_Nat
 *                  | 13 THIStpe len_Nat sym_Ref
 *                  | 14 SINGLEtpe len_Nat type_Ref sym_Ref
 *                  | 15 CONSTANTtpe len_Nat constant_Ref
 *                  | 16 TYPEREFtpe len_Nat type_Ref sym_Ref {targ_Ref}
 *                  | 17 TYPEBOUNDStpe len_Nat tpe_Ref tpe_Ref
 *                  | 18 REFINEDtpe len_Nat classsym_Ref {tpe_Ref}
 *                  | 19 CLASSINFOtpe len_Nat classsym_Ref {tpe_Ref}
 *                  | 20 METHODtpe len_Nat tpe_Ref {sym_Ref}
 *                  | 21 POLYTtpe len_Nat tpe_Ref {sym_Ref}
 *                  | 22 IMPLICITMETHODtpe len_Nat tpe_Ref {sym_Ref} /* no longer needed */
 *                  | 52 SUPERtpe len_Nat tpe_Ref tpe_Ref
 *                  | 24 LITERALunit len_Nat
 *                  | 25 LITERALboolean len_Nat value_Long
 *                  | 26 LITERALbyte len_Nat value_Long
 *                  | 27 LITERALshort len_Nat value_Long
 *                  | 28 LITERALchar len_Nat value_Long
 *                  | 29 LITERALint len_Nat value_Long
 *                  | 30 LITERALlong len_Nat value_Long
 *                  | 31 LITERALfloat len_Nat value_Long
 *                  | 32 LITERALdouble len_Nat value_Long
 *                  | 33 LITERALstring len_Nat name_Ref
 *                  | 34 LITERALnull len_Nat
 *                  | 35 LITERALclass len_Nat tpe_Ref
 *                  | 36 LITERALenum len_Nat sym_Ref
 *                  | 40 SYMANNOT len_Nat sym_Ref AnnotInfoBody
 *                  | 41 CHILDREN len_Nat sym_Ref {sym_Ref}
 *                  | 42 ANNOTATEDtpe len_Nat [sym_Ref /* no longer needed */] tpe_Ref {annotinfo_Ref}
 *                  | 43 ANNOTINFO len_Nat AnnotInfoBody
 *                  | 44 ANNOTARGARRAY len_Nat {constAnnotArg_Ref}
 *                  | 47 DEBRUIJNINDEXtpe len_Nat level_Nat index_Nat /* no longer needed */
 *                  | 48 EXISTENTIALtpe len_Nat type_Ref {symbol_Ref}
 *                  | 49 TREE len_Nat 1 EMPTYtree
 *                  | 49 TREE len_Nat 2 PACKAGEtree type_Ref sym_Ref mods_Ref name_Ref {tree_Ref}
 *                  | 49 TREE len_Nat 3 CLASStree type_Ref sym_Ref mods_Ref name_Ref tree_Ref {tree_Ref}
 *                  | 49 TREE len_Nat 4 MODULEtree type_Ref sym_Ref mods_Ref name_Ref tree_Ref
 *                  | 49 TREE len_Nat 5 VALDEFtree type_Ref sym_Ref mods_Ref name_Ref tree_Ref tree_Ref
 *                  | 49 TREE len_Nat 6 DEFDEFtree type_Ref sym_Ref mods_Ref name_Ref numtparams_Nat {tree_Ref} numparamss_Nat {numparams_Nat {tree_Ref}} tree_Ref tree_Ref
 *                  | 49 TREE len_Nat 7 TYPEDEFtree type_Ref sym_Ref mods_Ref name_Ref tree_Ref {tree_Ref}
 *                  | 49 TREE len_Nat 8 LABELtree type_Ref sym_Ref tree_Ref {tree_Ref}
 *                  | 49 TREE len_Nat 9 IMPORTtree type_Ref sym_Ref tree_Ref {name_Ref name_Ref}
 *                  | 49 TREE len_Nat 11 DOCDEFtree type_Ref sym_Ref string_Ref tree_Ref
 *                  | 49 TREE len_Nat 12 TEMPLATEtree type_Ref sym_Ref numparents_Nat {tree_Ref} tree_Ref {tree_Ref}
 *                  | 49 TREE len_Nat 13 BLOCKtree type_Ref tree_Ref {tree_Ref}
 *                  | 49 TREE len_Nat 14 CASEtree type_Ref tree_Ref tree_Ref tree_Ref
 *                  | 49 TREE len_Nat 15 SEQUENCEtree type_Ref {tree_Ref}
 *                  | 49 TREE len_Nat 16 ALTERNATIVEtree type_Ref {tree_Ref}
 *                  | 49 TREE len_Nat 17 STARtree type_Ref {tree_Ref}
 *                  | 49 TREE len_Nat 18 BINDtree type_Ref sym_Ref name_Ref tree_Ref
 *                  | 49 TREE len_Nat 19 UNAPPLYtree type_Ref tree_Ref {tree_Ref}
 *                  | 49 TREE len_Nat 20 ARRAYVALUEtree type_Ref tree_Ref {tree_Ref}
 *                  | 49 TREE len_Nat 21 FUNCTIONtree type_Ref sym_Ref tree_Ref {tree_Ref}
 *                  | 49 TREE len_Nat 22 ASSIGNtree type_Ref tree_Ref tree_Ref
 *                  | 49 TREE len_Nat 23 IFtree type_Ref tree_Ref tree_Ref tree_Ref
 *                  | 49 TREE len_Nat 24 MATCHtree type_Ref tree_Ref {tree_Ref}
 *                  | 49 TREE len_Nat 25 RETURNtree type_Ref sym_Ref tree_Ref
 *                  | 49 TREE len_Nat 26 TREtree type_Ref tree_Ref tree_Ref {tree_Ref}
 *                  | 49 TREE len_Nat 27 THROWtree type_Ref tree_Ref
 *                  | 49 TREE len_Nat 28 NEWtree type_Ref tree_Ref
 *                  | 49 TREE len_Nat 29 TYPEDtree type_Ref tree_Ref tree_Ref
 *                  | 49 TREE len_Nat 30 TYPEAPPLYtree type_Ref tree_Ref {tree_Ref}
 *                  | 49 TREE len_Nat 31 APPLYtree type_Ref tree_Ref {tree_Ref}
 *                  | 49 TREE len_Nat 32 APPLYDYNAMICtree type_Ref sym_Ref tree_Ref {tree_Ref}
 *                  | 49 TREE len_Nat 33 SUPERtree type_Ref sym_Ref tree_Ref name_Ref
 *                  | 49 TREE len_Nat 34 THIStree type_Ref sym_Ref  name_Ref
 *                  | 49 TREE len_Nat 35 SELECTtree type_Ref sym_Ref tree_Ref name_Ref
 *                  | 49 TREE len_Nat 36 IDENTtree type_Ref sym_Ref name_Ref
 *                  | 49 TREE len_Nat 37 LITERALtree type_Ref constant_Ref
 *                  | 49 TREE len_Nat 38 TYPEtree type_Ref
 *                  | 49 TREE len_Nat 39 ANNOTATEDtree type_Ref tree_Ref tree_Ref
 *                  | 49 TREE len_Nat 40 SINGLETONTYPEtree type_Ref tree_Ref
 *                  | 49 TREE len_Nat 41 SELECTFROMTYPEtree type_Ref tree_Ref name_Ref
 *                  | 49 TREE len_Nat 42 COMPOUNDTYPEtree type_Ref tree_Ref
 *                  | 49 TREE len_Nat 43 APPLIEDTYPEtree type_Ref tree_Ref {tree_Ref}
 *                  | 49 TREE len_Nat 44 TYPEBOUNDStree type_Ref tree_Ref tree_Ref
 *                  | 49 TREE len_Nat 45 EXISTENTIALTYPEtree type_Ref tree_Ref {tree_Ref}
 *                  | 50 MODIFIERS len_Nat flags_Long privateWithin_Ref
 *   SymbolInfo     = name_Ref owner_Ref flags_LongNat [privateWithin_Ref] info_Ref
 *   NameInfo       = <character sequence of length len_Nat in Utf8 format>
 *   NumInfo        = <len_Nat-byte signed number in big endian format>
 *   Ref            = Nat
 *   AnnotInfoBody  = info_Ref {annotArg_Ref} {name_Ref constAnnotArg_Ref}
 *   AnnotArg       = Tree | Constant
 *   ConstAnnotArg  = Constant | AnnotInfo | AnnotArgArray
 *
 *   len is remaining length after `len`.
 */
  val MajorVersion = 5
  val MinorVersion = 0

  final val TERMname = 1
  final val TYPEname = 2
  final val NONEsym = 3
  final val TYPEsym = 4
  final val ALIASsym = 5
  final val CLASSsym = 6
  final val MODULEsym = 7
  final val VALsym = 8
  final val EXTref = 9
  final val EXTMODCLASSref = 10
  final val NOtpe = 11
  final val NOPREFIXtpe = 12
  final val THIStpe = 13
  final val SINGLEtpe = 14
  final val CONSTANTtpe = 15
  final val TYPEREFtpe = 16
  final val TYPEBOUNDStpe = 17
  final val REFINEDtpe = 18
  final val CLASSINFOtpe = 19
  final val METHODtpe = 20
  final val POLYtpe = 21
  final val IMPLICITMETHODtpe = 22    // no longer generated

  final val LITERAL = 23   // base line for literals
  final val LITERALunit = 24
  final val LITERALboolean = 25
  final val LITERALbyte = 26
  final val LITERALshort = 27
  final val LITERALchar = 28
  final val LITERALint = 29
  final val LITERALlong = 30
  final val LITERALfloat = 31
  final val LITERALdouble = 32
  final val LITERALstring = 33
  final val LITERALnull = 34
  final val LITERALclass = 35
  final val LITERALenum = 36
  final val SYMANNOT = 40
  final val CHILDREN = 41
  final val ANNOTATEDtpe = 42
  final val ANNOTINFO = 43
  final val ANNOTARGARRAY = 44

  final val SUPERtpe = 46
  final val DEBRUIJNINDEXtpe = 47   // no longer generated
  final val EXISTENTIALtpe = 48

  final val TREE = 49      // prefix code that means a tree is coming
  import scala.reflect.internal.{TreeTags => TT} // copying these here for compatibility
    final val EMPTYtree = TT.EMPTYtree
    final val PACKAGEtree = TT.PACKAGEtree
    final val CLASStree = TT.CLASStree
    final val MODULEtree = TT.MODULEtree
    final val VALDEFtree = TT.VALDEFtree
    final val DEFDEFtree = TT.DEFDEFtree
    final val TYPEDEFtree = TT.TYPEDEFtree
    final val LABELtree = TT.LABELtree
    final val IMPORTtree = TT.IMPORTtree
    final val DOCDEFtree = TT.DOCDEFtree
    final val TEMPLATEtree = TT.TEMPLATEtree
    final val BLOCKtree = TT.BLOCKtree
    final val CASEtree = TT.CASEtree
    // This node type has been removed.
    // final val SEQUENCEtree = 15
    final val ALTERNATIVEtree = TT.ALTERNATIVEtree
    final val STARtree = TT.STARtree
    final val BINDtree = TT.BINDtree
    final val UNAPPLYtree = TT.UNAPPLYtree
    final val ARRAYVALUEtree = TT.ARRAYVALUEtree
    final val FUNCTIONtree = TT.FUNCTIONtree
    final val ASSIGNtree = TT.ASSIGNtree
    final val IFtree = TT.IFtree
    final val MATCHtree = TT.MATCHtree
    final val RETURNtree = TT.RETURNtree
    final val TREtree = TT.TREtree
    final val THROWtree = TT.THROWtree
    final val NEWtree = TT.NEWtree
    final val TYPEDtree = TT.TYPEDtree
    final val TYPEAPPLYtree = TT.TYPEAPPLYtree
    final val APPLYtree = TT.APPLYtree
    final val APPLYDYNAMICtree = TT.APPLYDYNAMICtree
    final val SUPERtree = TT.SUPERtree
    final val THIStree = TT.THIStree
    final val SELECTtree = TT.SELECTtree
    final val IDENTtree = TT.IDENTtree
    final val LITERALtree = TT.LITERALtree
    final val TYPEtree = TT.TYPEtree
    final val ANNOTATEDtree = TT.ANNOTATEDtree
    final val SINGLETONTYPEtree = TT.SINGLETONTYPEtree
    final val SELECTFROMTYPEtree = TT.SELECTFROMTYPEtree
    final val COMPOUNDTYPEtree = TT.COMPOUNDTYPEtree
    final val APPLIEDTYPEtree = TT.APPLIEDTYPEtree
    final val TYPEBOUNDStree = TT.TYPEBOUNDStree
    final val EXISTENTIALTYPEtree = TT.EXISTENTIALTYPEtree

  final val MODIFIERS = 50

  final val firstSymTag = NONEsym
  final val lastSymTag = VALsym
  final val lastExtSymTag = EXTMODCLASSref


  //The following two are no longer accurate, because ANNOTATEDtpe,
  //SUPERtpe, ... are not in the same range as the other types
  //final val firstTypeTag = NOtpe
  //final val lastTypeTag = POLYtpe
}
