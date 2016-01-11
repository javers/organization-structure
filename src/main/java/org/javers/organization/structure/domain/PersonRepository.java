package org.javers.organization.structure.domain;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.mongodb.repository.MongoRepository;

@JaversSpringDataAuditable
public interface PersonRepository extends MongoRepository<Person, String> {

}
