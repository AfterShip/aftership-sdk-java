package com.aftership.sdk.lib;


public final class Define {
    public static final String VERSION = "2.0.0";

    public static final int[] SUCCESSFUL_CODE_RANGE;

    static {
        SUCCESSFUL_CODE_RANGE = new int[100];
        for (int i = 0; i < 100; i++) {
            SUCCESSFUL_CODE_RANGE[i] = 200 + i;
        }
    }


}
