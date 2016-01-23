package org.javers.organization.structure.domain;

import com.google.common.collect.Lists;
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
        return Lists.newArrayList(hierarchyRepository.findAll());
    }

    public Hierarchy update(Hierarchy selected) {
        return hierarchyRepository.save(selected);
    }

    public Hierarchy find(String left) {
        return hierarchyRepository.findOne(left);
    }
}
