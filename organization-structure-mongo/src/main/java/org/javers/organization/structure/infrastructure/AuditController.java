package org.javers.organization.structure.infrastructure;


import org.javers.core.Changes;
import org.javers.core.Javers;
import org.javers.core.diff.Change;
import org.javers.core.diff.Diff;
import org.javers.core.json.JsonConverter;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.organization.structure.domain.*;
import org.javers.repository.jql.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/audit")
public class AuditController {

    private final Javers javers;
    private final HierarchyRepository hierarchyRepository;
    private final PersonRepository personRepository;

    @Autowired
    public AuditController(Javers javers, HierarchyRepository hierarchyRepository, PersonRepository personRepository) {
        this.javers = javers;
        this.hierarchyRepository = hierarchyRepository;
        this.personRepository = personRepository;
    }

    @RequestMapping("/test")
    public void updateFrodo() {
        Person frodo = personRepository.findById(0).get();

        frodo.setSalary(frodo.getSalary() + 100);
        personRepository.save(frodo);
    }

    @RequestMapping("/persons")
    public String getPersonChanges() {
        QueryBuilder jqlQuery = QueryBuilder.byClass(Person.class)
                .withNewObjectChanges();

        Changes changes = javers.findChanges(jqlQuery.build());

        return "<pre>" + changes.prettyPrint() + "</pre>";
    }

    @RequestMapping("/person/{id}")
    public String getPersonChanges(@PathVariable Integer id) {
        QueryBuilder jqlQuery = QueryBuilder.byInstanceId(id, Person.class)
                .withNewObjectChanges();

        Changes changes = javers.findChanges(jqlQuery.build());

        return "<pre>" + changes.prettyPrint() + "</pre>";
    }

    @RequestMapping("/person/snapshots")
    public String getPersonSnapshots() {
        QueryBuilder jqlQuery = QueryBuilder.byClass(Person.class);

        List<CdoSnapshot> changes = new ArrayList(javers.findSnapshots(jqlQuery.build()));

        changes.sort((o1, o2) -> -1 * o1.getCommitMetadata().getCommitDate().compareTo(o2.getCommitMetadata().getCommitDate()));

        JsonConverter jsonConverter = javers.getJsonConverter();

        return jsonConverter.toJson(changes);
    }

    @RequestMapping("/person/{login}/snapshots")
    public String getPersonSnapshots(@PathVariable String login) {
        QueryBuilder jqlQuery = QueryBuilder.byInstanceId(login, Person.class);

        List<CdoSnapshot> changes = javers.findSnapshots(jqlQuery.build());

        changes.sort((o1, o2) -> -1 * o1.getCommitMetadata().getCommitDate().compareTo(o2.getCommitMetadata().getCommitDate()));

        JsonConverter jsonConverter = javers.getJsonConverter();

        return jsonConverter.toJson(changes);
    }

    @RequestMapping("/hierarchy/{left}/diff/{right}")
    public String getPersonSnapshots(@PathVariable String left, @PathVariable String right) {
        Hierarchy l = hierarchyRepository.findById(left).get();
        Hierarchy p = hierarchyRepository.findById(right).get();


        Diff diff = javers.compare(l, p);


//        TODO
//        List<Change> changes = diff.getChanges(input ->
//                (input instanceof NewObject
//                        && input.getAffectedGlobalId().getCdoClass().getClientsClass() != Hierarchy.class));


        JsonConverter jsonConverter = javers.getJsonConverter();

        return jsonConverter.toJson(diff.getChanges());
    }
}
