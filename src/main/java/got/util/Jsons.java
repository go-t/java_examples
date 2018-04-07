package got.util;

import com.alibaba.fastjson.JSON;

public class Jsons {
    public static String toJson(Object obj) {
        return JSON.toJSONString(obj);
    }
}