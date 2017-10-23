package org.javers.spring.admin;

import org.javers.common.collections.Lists;
import org.javers.core.Javers;
import org.javers.spring.admin.model.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/javers-admin-api")
public class MainController {

    public MainController() {
        System.out.println("eee");
    }

    @Autowired
    private Javers javers;

    @RequestMapping("/entities")
    public List<Entity> entities() {
        return Lists.asList(
                new Entity("org.my.company.Person", 10, 200),
                new Entity("org.my.company.Hierarchy", 1, 5),
                new Entity("org.my.company.Route", 12, 50),
                new Entity("org.my.company.Speaker", 10, 50),
                new Entity("org.my.company.Attendee", 10, 50),
                new Entity("org.my.company.Conference", 10, 50)
        );
    }
}
