package com.cmcc.arp.handler;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.arp.exception.InvalidSignException;
import com.cmcc.arp.exception.RepeatedRequestException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zmcc on 17/4/1.
 */
public class ServletErrorHandler implements ErrorHandler {

    public static final String INVALID_SIGN = "invalid_sign";

    public static final String INVALID_SIGN_DESC = "invalid sign";

    public static final String REPLAY_ERROR = "replay_error";

    public static final String REPLAY_ERROR_DESC = "repeated request";

    public static final int ERROR_CODE = 402;

    public void handleError(Exception e, Object... param) {
        try {
            HttpServletResponse resp = (HttpServletResponse) param[0];
            PrintWriter writer = resp.getWriter();

            resp.setStatus(ERROR_CODE);
            Map<String, String> map = new HashMap<String, String>();

            if (e instanceof InvalidSignException) {
                map.put("error", INVALID_SIGN);
                map.put("error_desc", INVALID_SIGN_DESC);
            }
            if (e instanceof RepeatedRequestException) {
                map.put("error", REPLAY_ERROR);
                map.put("error_desc", REPLAY_ERROR_DESC);
            }
            writer.write(JSONObject.toJSONString(map));
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }
}
