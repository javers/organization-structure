package org.javers.spring.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

@Controller
@RequestMapping(value = "/javers-admin")
public class StaticController {

    @RequestMapping("/**")
    @ResponseBody
    public String getAll(HttpServletRequest request) throws IOException {
        String path = request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE)
                .toString()
                .replace("/javers-admin/", "");

        InputStream res = this.getClass().getClassLoader().getResourceAsStream("javers-admin-frontend/" + path);
        return convertStreamToString(res);
    }

    static String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
