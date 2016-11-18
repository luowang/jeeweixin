package com.core.util;

/**
 * @author: wang.luo
 * @date 2016/11/16 14:52
 */
public class StringUtils {

    /**
     * similar to JavaScript's String.prototype.join
     *
     * @param strs
     * @param seperator
     * @return
     */
    public static String join(String [] strs, String seperator) {
        String rv = "";

        for (int i = 0; i < strs.length; i++) {
            rv += (i == 0) ? strs[i] : seperator + strs[i];
        }

        return rv;
    }
}
