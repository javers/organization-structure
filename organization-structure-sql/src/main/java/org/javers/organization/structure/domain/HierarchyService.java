package org.javers.organization.structure.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@Component
public class HierarchyService {

    private HierarchyRepository hierarchyRepository;

    @Autowired
    public HierarchyService(HierarchyRepository hierarchyRepository) {
        this.hierarchyRepository = hierarchyRepository;
    }

    public List<String> findAll() {
        return StreamSupport.stream(hierarchyRepository.findAll().spliterator(), false)
                .map(hierarchy -> hierarchy.getName()).collect(toList());
    }

    public List<HierarchyEmployees> getHierarchyEmployees(String name) {
        return hierarchyRepository.findById(name)
                .map(it -> it.getHierarchyEmployees()).orElse(Collections.emptyList());
    }
}
