package org.javers.organization.structure.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface HierarchyRepository extends MongoRepository<Hierarchy, String> {

}
