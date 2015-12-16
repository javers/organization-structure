package org.javers.organization.structure.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HierarchyService {

    private HierarchyRepository hierarchyRepository;

    @Autowired
    public HierarchyService(HierarchyRepository hierarchyRepository) {
        this.hierarchyRepository = hierarchyRepository;
    }

    public List<Hierarchy> findAll() {
        return hierarchyRepository.findAll();
    }

    public void update(Hierarchy selected) {
        //TODO
        Hierarchy found = hierarchyRepository.findOne(selected.getHierarchyName());

        hierarchyRepository.save(selected);
    }

    public Hierarchy find(String left) {
        return hierarchyRepository.findOne(left);
    }
}
