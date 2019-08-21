package org.javers.organization.structure.view;

import org.javers.organization.structure.domain.Employee;
import org.javers.organization.structure.domain.Person;
import org.javers.organization.structure.domain.Position;
import org.javers.organization.structure.domain.Sex;
import org.javers.organization.structure.ui.EmployeeDto;
import org.javers.organization.structure.ui.PersonDto;

public class DomainModelToViewConverter {

    public static EmployeeDto employeeToEmployeeDto(Employee employee) {
        return new EmployeeDto(employee.getDomainName());
    }

    public static PersonDto personToPersonDto(Person person) {
        return new PersonDto(person.getId(),
                person.getFirstName(),
                person.getLastName(),
                person.getSex().toString(),
                person.getSalary(),
                person.getPosition().toString());
    }

    public static Person personDtoToPerson(PersonDto personDto) {
        return new Person(personDto.getId(),
                personDto.getFirstName(),
                personDto.getLastName(),
                Sex.valueOf(personDto.getSex()),
                personDto.getSalary(),
                Position.valueOf(personDto.getPosition()));
    }
}
