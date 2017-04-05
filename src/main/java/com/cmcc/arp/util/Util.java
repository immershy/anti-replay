package com.cmcc.arp.util;

import java.util.Map;

/**
 * Created by zmcc on 17/4/1.
 */
public class Util {

    public static String concat(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                sb.append(entry.getKey() + entry.getValue());
            }
        }
        return sb.toString();
    }
}
