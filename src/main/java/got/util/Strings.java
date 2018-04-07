package got.util;

import java.util.Locale;

import com.alibaba.fastjson.JSON;

/**
 * Created by zhusheng on 2018-02-08.
 */

public class Strings {

    private static final char[] HEX_DIGITS = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F'
    };

    public static String trimTail(String str, int n) {
        if (n >= str.length()){
            return "";
        }
        return str.substring(0, str.length() - n);
    }

    public static String trimTail(String str, String tail) {
        if (tail.length() < str.length() &&
                tail.equals(str.substring(str.length() - tail.length()))) {
            return str.substring(0, str.length() - tail.length());
        }
        return str;
    }

    public static String format(String fmt, Object... args) {
        return String.format(Locale.getDefault(), fmt, args);
    }

    public static String join(String sep, String ...v) {
        StringBuilder sb = new StringBuilder();
        for (String s : v) {
            if (sb.length() > 0) {
                sb.append(sep);
            }
            sb.append(s==null?"":s);
        }
        return sb.toString();
    }

    public static String join(String sep, Object ...v) {
        StringBuilder sb = new StringBuilder();
        for (Object o : v) {
            if (sb.length() > 0) {
                sb.append(sep);
            }
            sb.append(o==null?"<nil>":o.toString());
        }
        return sb.toString();
    }

    public static String joinArray(String sep, byte[] data) {
        return joinArray(sep, data, -1);
    }

    public static String joinArray(String sep, byte[] data, int limit) {
        if (data == null) {
            return "<null>";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("byte[");
        for (int i = 0; i < data.length && i != limit; i++) {
            if (i>0) {
                sb.append(sep);
            }
            byte b = data[i];
            sb.append(HEX_DIGITS[(b>>4)&0xF]);
            sb.append(HEX_DIGITS[b&0xF]);
        }
        if (limit >= 0 && limit < data.length) {
            sb.append(", ...");
        }
        sb.append(']');
        return sb.toString();
    }

    public static String joinArray(String sep, int[] data, int limit) {
        return joinArray(sep, data, 0, data.length, limit);
    }

    public static String joinArray(String sep, int[] data, int offset, int len, int limit) {
        if (data == null) {
            return "<null>";
        }
        if (len < 0 || len > (data.length - offset)) {
            len = data.length - offset;
        }
        if (limit <0 || limit > len) {
            limit = len;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("byte[");
        for (int i = 0; i < limit; i++) {
            if (i>0) {
                sb.append(sep);
            }
            sb.append(data[i + offset]);
        }
        if (limit < len) {
            sb.append(", ...");
        }
        sb.append(']');
        return sb.toString();
    }

    public static String toString(Object o) {
        return JSON.toJSONString(o);
    }

    public static int toInt(CharSequence str){
        return Integer.parseInt(str.toString());
    }

    public static int[] toInts(CharSequence str, String sep) {
        String[] parts = str.toString().split(sep);
        int[] array = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            array[i] = Integer.parseInt(parts[i]);
        }
        return array;
    }

    public static String toStr(int value) {
        return String.valueOf(value);
    }
}
