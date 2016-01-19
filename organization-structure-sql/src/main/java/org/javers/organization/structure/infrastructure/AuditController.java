package org.javers.organization.structure.infrastructure;


import org.javers.core.Javers;
import org.javers.core.diff.Change;
import org.javers.core.diff.Diff;
import org.javers.core.json.JsonConverter;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.organization.structure.domain.Hierarchy;
import org.javers.organization.structure.domain.HierarchyRepository;
import org.javers.organization.structure.domain.Person;
import org.javers.repository.jql.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/audit")
public class AuditController {

    private final Javers javers;
    private final HierarchyRepository hierarchyRepository;

    @Autowired
    public AuditController(Javers javers, HierarchyRepository hierarchyRepository) {
        this.javers = javers;
        this.hierarchyRepository = hierarchyRepository;
    }

    @RequestMapping("/person")
    public String getPersonChanges(@RequestParam Optional<String> param) {
        QueryBuilder jqlQuery = QueryBuilder.byClass(Person.class);

        jqlQuery = param.isPresent() ? jqlQuery.andProperty(param.get()) : jqlQuery;

        List<Change> changes = javers.findChanges(jqlQuery.build());

        changes.sort((o1, o2) -> -1 * o1.getCommitMetadata().get().getCommitDate().compareTo(o2.getCommitMetadata().get().getCommitDate()));

        JsonConverter jsonConverter = javers.getJsonConverter();

        return jsonConverter.toJson(changes);
    }

    @RequestMapping("/person/{login}")
    public String getPersonChanges(@PathVariable String login, @RequestParam Optional<String> param) {
        QueryBuilder jqlQuery = QueryBuilder.byInstanceId(login, Person.class);

        jqlQuery = param.isPresent() ? jqlQuery.andProperty(param.get()) : jqlQuery;

        List<Change> changes = javers.findChanges(jqlQuery.build());

        changes.sort((o1, o2) -> -1 * o1.getCommitMetadata().get().getCommitDate().compareTo(o2.getCommitMetadata().get().getCommitDate()));

        JsonConverter jsonConverter = javers.getJsonConverter();

        return jsonConverter.toJson(changes);
    }

    @RequestMapping("/person/snapshots")
    public String getPersonSnapshots(@RequestParam Optional<String> param) {
        QueryBuilder jqlQuery = QueryBuilder.byClass(Person.class);

        jqlQuery = param.isPresent() ? jqlQuery.andProperty(param.get()) : jqlQuery;

        List<CdoSnapshot> changes = javers.findSnapshots(jqlQuery.build());

        changes.sort((o1, o2) -> -1 * o1.getCommitMetadata().getCommitDate().compareTo(o2.getCommitMetadata().getCommitDate()));

        JsonConverter jsonConverter = javers.getJsonConverter();

        return jsonConverter.toJson(changes);
    }

    @RequestMapping("/person/{login}/snapshots")
    public String getPersonSnapshots(@PathVariable String login, @RequestParam Optional<String> param) {
        QueryBuilder jqlQuery = QueryBuilder.byInstanceId(login, Person.class);

        jqlQuery = param.isPresent() ? jqlQuery.andProperty(param.get()) : jqlQuery;

        List<CdoSnapshot> changes = javers.findSnapshots(jqlQuery.build());

        changes.sort((o1, o2) -> -1 * o1.getCommitMetadata().getCommitDate().compareTo(o2.getCommitMetadata().getCommitDate()));

        JsonConverter jsonConverter = javers.getJsonConverter();

        return jsonConverter.toJson(changes);
    }

    @RequestMapping("/hierarchy/{left}/diff/{right}")
    public String getPersonSnapshots(@PathVariable String left, @PathVariable String right) {
        Hierarchy l = hierarchyRepository.findOne(left);
        Hierarchy p = hierarchyRepository.findOne(right);


        Diff diff = javers.compare(l, p);


//        TODO
//        List<Change> changes = diff.getChanges(input ->
//                (input instanceof NewObject
//                        && input.getAffectedGlobalId().getCdoClass().getClientsClass() != Hierarchy.class));


        JsonConverter jsonConverter = javers.getJsonConverter();

        return jsonConverter.toJson(diff.getChanges());
    }
}
