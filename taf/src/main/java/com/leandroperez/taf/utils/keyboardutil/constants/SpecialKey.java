package com.leandroperez.taf.utils.keyboardutil.constants;

/**
 * @author Leandro Henrique Perez
 */

public enum SpecialKey {
    ENTER,
    SEARCH,
    NEW_LINE;
    // Add more keys as needed


    public static SpecialKey fromString(String value) {
        if (value == null) return null;
        try {
            return SpecialKey.valueOf(value.replace("<", "").replace(">", "").toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public boolean isNewLine() {
        return this == NEW_LINE;
    }

}
