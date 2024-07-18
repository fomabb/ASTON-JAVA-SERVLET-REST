package org.iase24.nikolay.kirilyuk.util;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HttpResponseUtil {

    private final Gson gson;

    public HttpResponseUtil() {
        this.gson = new Gson();
    }

    public void httpResponse(HttpServletResponse resp, Object object) throws IOException {
        String json = gson.toJson(object);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }
}
