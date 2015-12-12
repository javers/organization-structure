package org.javers.organization.structure.domain;

import org.javers.organization.structure.infrastructure.UserContext;
import org.javers.spring.annotation.JaversAuditable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonService {

    private PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @JaversAuditable
    public void update(Person person) {
        //TODO check

        personRepository.save(person);
    }

    public Person findPerson(String login) {
        return personRepository.findOne(login);
    }
}
