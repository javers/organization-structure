package org.javers.spring.admin;

import org.javers.common.collections.Lists;
import org.javers.common.reflection.ReflectionUtil;
import org.javers.core.Javers;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.repository.jql.QueryBuilder;
import org.javers.spring.admin.model.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/javers-admin/api")
public class MainController {

    public MainController() {
        System.out.println("eee");
    }

    @Autowired
    private Javers javers;

    @RequestMapping("/entities")
    public List<Entity> entities() {
        return Lists.asList(
                new Entity("org.javers.organization.structure.domain.Employee", 10, 200),
                new Entity("org.javers.organization.structure.domain.Hierarchy", 2, 10),
                new Entity("org.javers.organization.structure.domain.Person", 12, 100)
        );
    }

    @RequestMapping("/entities/{entityName}/")
    public String latestSnapshots(@PathVariable String entityName) {
        List<CdoSnapshot> snapshots = javers.findSnapshots(
                QueryBuilder.byClass(ReflectionUtil.classForName(entityName)).build());
        return javers.getJsonConverter().toJson(snapshots);
    }
}
