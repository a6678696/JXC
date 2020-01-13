package com.ledao.util;

/**
 * 字符串工具类
 *
 * @author LeDao
 * @company
 * @create 2020-01-07 22:42
 */
public class StringUtil {

    /**
     * 判断是否是空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str.trim())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否不是空
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        if ((str != null) && !"".equals(str.trim())) {
            return true;
        } else {
            return false;
        }
    }

    public static String formatCode(String code) {
        int length = code.length();
        Integer num = Integer.parseInt(code.substring(length-4,length))+1;
        StringBuffer codeNum = new StringBuffer(num+"");
        int codeLength = codeNum.length();
        for (int i = 4; i > codeLength; i--) {
            codeNum.insert(0, "0");
        }
        return codeNum.toString();
    }

    public static void main(String[] args) {
        System.out.println(formatCode("JH201712010002"));
    }
}
