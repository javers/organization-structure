package org.javers.spring.admin;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping(value = "/javers-admin")
public class StaticController {

    @RequestMapping("/**")
    @ResponseBody
    public void getAll(HttpServletResponse response, HttpServletRequest request) throws IOException {
        String path = request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE)
                .toString()
                .replace("/javers-admin/", "");


        InputStream res = getStreamFromJar("/" + path);

        String ext = path.split("\\.")[1];

        if ("html".equalsIgnoreCase(ext)) {
            response.setContentType("text/html");
        } else if ("css".equalsIgnoreCase(ext)) {
            response.setContentType("text/css");
        } else if ("js".equalsIgnoreCase(ext)) {
            response.setContentType("application/javascript");
        } else {
            response.setContentType("image/"+ext);
        }

        IOUtils.copy(res, response.getOutputStream());
    }

    private InputStream getStreamFromJar(String path) {
        return this.getClass().getClassLoader().getResourceAsStream("javers-admin-frontend" + path);
    }
}
