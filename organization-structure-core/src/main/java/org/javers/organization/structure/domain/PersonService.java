package org.javers.organization.structure.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonService {

    private PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person update(Person person) {
        return personRepository.save(person);
    }

    public Person findPerson(String login) {
        return personRepository.findOne(login);
    }
}
