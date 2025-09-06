package com.leandroperez.taf.utils.keyboardutil;

import com.leandroperez.taf.utils.keyboardutil.constants.SpecialKey;

/**
 * @author Leandro Henrique Perez
 */

public class KeyMapper {

    public static String getKeyValue(SpecialKey key) {
        if (key == null) return "";
        switch (key) {
            // Add more keys as needed
            case NEW_LINE:
                return "\n";
            default:
                return "";
        }
    }


}

