package com.peng.lottery.app.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author EwinLive
 */
public final class StringUtil {

    /**
     * 默认的空值
     */
    public static final String EMPTY = "";

    /**
     * 检查字符串是否为空
     */
    public static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        } else if (str.length() == 0) {
            return true;
        } else if ("null".equals(str) || "NULL".equals(str)) {
            return true;
        } else {
            return false;
        }
    }

    public static String format(String str) {
        if (isEmpty(str)) {
            return "";
        }
        return str;
    }

    public static String format(Object o) {
        if (o == null) {
            return "";
        }
        return o.toString();
    }

    /**
     * 检查字符串是否为空
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 截取并保留标志位之前的字符串
     */
    public static String substringBefore(String str, String expr) {
        if (isEmpty(str) || expr == null) {
            return str;
        }
        if (expr.length() == 0) {
            return EMPTY;
        }
        int pos = str.indexOf(expr);
        if (pos == -1) {
            return str;
        }
        return str.substring(0, pos);
    }

    /**
     * 截取并保留标志位之后的字符串
     */
    public static String substringAfter(String str, String expr) {
        if (isEmpty(str)) {
            return str;
        }
        if (expr == null) {
            return EMPTY;
        }
        int pos = str.indexOf(expr);
        if (pos == -1) {
            return EMPTY;
        }
        return str.substring(pos + expr.length());
    }

    /**
     * 截取并保留最后一个标志位之前的字符串
     */
    public static String substringBeforeLast(String str, String expr) {
        if (isEmpty(str) || isEmpty(expr)) {
            return str;
        }
        int pos = str.lastIndexOf(expr);
        if (pos == -1) {
            return str;
        }
        return str.substring(0, pos);
    }

    /**
     * 截取并保留最后一个标志位之后的字符串
     */
    public static String substringAfterLast(String str, String expr) {
        if (isEmpty(str)) {
            return str;
        }
        if (isEmpty(expr)) {
            return EMPTY;
        }
        int pos = str.lastIndexOf(expr);
        if (pos == -1 || pos == (str.length() - expr.length())) {
            return EMPTY;
        }
        return str.substring(pos + expr.length());
    }

    /**
     * 把字符串按分隔符转换为数组
     */
    public static String[] stringToArray(String string, String expr) {
        return string.split(expr);
    }

    /**
     * 去除字符串中的空格   中间" "替换到"_"。
     */
    public static String noSpace(String str) {
        str = str.trim();
        str = str.replace(" ", "_");
        return str;
    }

    /**
     * 数字编号转换为汉字编号 （千位数以内）
     */
    public static String getChinese(String number) {
        String chs = "";
        String src = number;
        for (int i = 0; i < src.length(); i++) {
            switch (src.charAt(i)) {
                case '0':
                    chs = chs + "零";
                    break;
                case '1':
                    chs = chs + "一";
                    break;
                case '2':
                    chs = chs + "二";
                    break;
                case '3':
                    chs = chs + "三";
                    break;
                case '4':
                    chs = chs + "四";
                    break;
                case '5':
                    chs = chs + "五";
                    break;
                case '6':
                    chs = chs + "六";
                    break;
                case '7':
                    chs = chs + "七";
                    break;
                case '8':
                    chs = chs + "八";
                    break;
                case '9':
                    chs = chs + "九";
                    break;
            }
            switch (src.length() - 1 - i) {
                case 1: // 十
                    String num = chs.charAt(chs.length() - 1) + "";
                    if ("零".equals(num)) {

                    } else {
                        chs = chs + "十";
                    }
                    break;
                case 2: // 百
                    chs = chs + "百";
                    break;
            }
        }
        if (chs.length() > 0 && chs.lastIndexOf("零") == chs.length() - 1) {
            chs = chs.substring(0, chs.length() - 1);
        }
        if (chs.length() == 2 && "一".equals(chs.substring(0, 1))) {
            chs = chs.substring(1);
        }
        if (chs.length() == 3 && "一十".equals(chs.substring(0, 2))) {
            chs = chs.substring(1);
        }
        if (chs.length() == 3 && "零".equals(chs.substring(2))) {
            chs = chs.substring(0, 2);
        }
        return chs;
    }

    /**
     * 返回整数
     */
    public static int StrToInt(Object arg) {
        if (isInteger(arg + "")) {
            return Integer.parseInt(arg.toString());
        }
        return 0;
    }

    /**
     * 返回小数
     */
    public static double StrToDouble(Object arg) {
        if (isDouble(arg + "")) {
            return Double.parseDouble(arg.toString());
        }
        return 0.0;
    }

    /**
     * 判断是否为Integer类型
     */
    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断是否为Double类型
     */
    public static boolean isDouble(String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 处理需要留有空白的Obj到String。
     */
    public static String transString(Object obj, int type) {
        String str = "　　";
        if (type == 2) {
            str = "　　　　　　";
        }
        if (obj != null) {
            String tempStr = String.valueOf(obj);
            if (!"null".equals(tempStr) && !"".equals(tempStr)) {
                str = tempStr;
            }
        }
        return str;
    }

    /**
     * Obj转换String是，处理null、“null”。
     */
    public static String transString(Object obj) {
        String str = "";
        if (obj != null) {
            if (obj.getClass() == Date.class) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    str = sdf.format(obj);
                } catch (Exception e) {

                }
            } else {
                String tempStr = String.valueOf(obj);
                if (!"null".equals(tempStr)) {
                    str = tempStr;
                }
            }
        }
        return str;
    }

    public static String byte2hex(byte[] b) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
                'B', 'C', 'D', 'E', 'F'};
        char[] out = new char[b.length * 2];
        for (int i = 0; i < b.length; i++) {
            byte c = b[i];
            out[i * 2] = Digit[(c >>> 4) & 0X0F];
            out[i * 2 + 1] = Digit[c & 0X0F];
        }

        return new String(out);
    }

    public static byte[] hex2byte(String s) {
        byte[] src = s.toLowerCase().getBytes();
        byte[] ret = new byte[src.length / 2];
        for (int i = 0; i < src.length; i += 2) {
            byte hi = src[i];
            byte low = src[i + 1];
            hi = (byte) ((hi >= 'a' && hi <= 'f') ? 0x0a + (hi - 'a')
                    : hi - '0');
            low = (byte) ((low >= 'a' && low <= 'f') ? 0x0a + (low - 'a')
                    : low - '0');
            ret[i / 2] = (byte) (hi << 4 | low);
        }
        return ret;
    }

}
