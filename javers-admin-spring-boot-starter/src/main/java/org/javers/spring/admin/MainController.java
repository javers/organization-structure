package org.javers.spring.admin;

import org.javers.core.Javers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/javers")
public class MainController {

    public MainController() {
        System.out.println("eee");
    }

    @Autowired
    private Javers javers;

    @RequestMapping("/zonk")
    public String zonk() {
        return "Hello World! my javers instance is " + javers;
    }
}
