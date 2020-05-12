package com.aftership.sdk.lib;

import java.util.UUID;

public final class StrUtil {

    public static final String EMPTY = "";

    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * Determine whether the string is empty or not.
     * @param cs CharSequence
     * @return true/false
     */
    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String uuid4(){
        return UUID.randomUUID().toString();
    }


}
