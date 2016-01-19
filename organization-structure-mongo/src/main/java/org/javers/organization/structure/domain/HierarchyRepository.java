package org.javers.organization.structure.domain;

import org.javers.organization.structure.domain.Hierarchy;
import org.springframework.data.repository.CrudRepository;

public interface HierarchyRepository extends CrudRepository<Hierarchy, String> {
}
