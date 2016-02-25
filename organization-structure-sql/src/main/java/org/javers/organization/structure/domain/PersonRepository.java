package org.javers.organization.structure.domain;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.repository.CrudRepository;

@JaversSpringDataAuditable
public interface PersonRepository extends CrudRepository<Person, Integer> {

}
