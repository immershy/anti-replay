package com.cmcc.arp.handler;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zmcc on 17/4/1.
 */
public class ServletErrorHandler implements ErrorHandler {
    public void handleError(Exception e, Object... param) {
        try {
            HttpServletResponse resp = (HttpServletResponse) param[0];
            PrintWriter writer = resp.getWriter();

            resp.setStatus(402);
            Map<String, String> map = new HashMap<String, String>(2);
            map.put("error", "replay_error");
            map.put("error_desc", "repeated request");
            writer.write(JSONObject.toJSONString(map));
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }
}
