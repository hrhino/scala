package scala.runtime;

import java.lang.invoke.*;
import java.util.Arrays;

public final class SwitchMap {
    @SuppressWarnings("unchecked")
    public static CallSite bootstrap(
        MethodHandles.Lookup lookup,
        String invokedName,
        MethodType invokedType,
        int offset,
        MethodHandle ...staticConstants
    ) throws Throwable {
        Class<? extends Enum<?>> enumClass = (Class) staticConstants[0].type().returnType();
        Enum<? extends Enum<?>>[] runtimeConstants = enumClass.getEnumConstants();
        int[] ordinals = new int[runtimeConstants.length];
        Arrays.fill(ordinals, -1);
        for (int i = 0; i < staticConstants.length; i++) {
            Enum<? extends Enum<?>> value = (Enum) staticConstants[i].invoke();
            ordinals[value.ordinal()] = i + offset;
        }
        return new ConstantCallSite(MethodHandles.constant(int[].class, ordinals));
    }
}
