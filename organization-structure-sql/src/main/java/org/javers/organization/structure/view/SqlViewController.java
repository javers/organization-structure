package org.javers.organization.structure.view;

import org.javers.organization.structure.domain.HierarchyEmployees;
import org.javers.organization.structure.domain.HierarchyService;
import org.javers.organization.structure.domain.PersonService;
import org.javers.organization.structure.ui.OrganizationStructureTableRow;
import org.javers.organization.structure.ui.PersonDto;
import org.javers.organization.structure.ui.ViewController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.javers.organization.structure.view.DomainModelToViewConverter.employeeToEmployeeDto;
import static org.javers.organization.structure.view.DomainModelToViewConverter.personToPersonDto;
import static org.javers.organization.structure.view.DomainModelToViewConverter.personDtoToPerson;

@Component
public class SqlViewController extends ViewController {

    @Autowired
    private HierarchyService hierarchyService;

    @Autowired
    private PersonService personService;

    @Override
    public List<String> findHierarchies() {
        return hierarchyService.findAll();
    }

    @Override
    public List<OrganizationStructureTableRow> findHierarchyByName(@PathVariable String name) {
        List<HierarchyEmployees> employees = hierarchyService.getHierarchyEmployees(name);

        return employees.stream()
                .map(hierarchyEmployees -> new OrganizationStructureTableRow(employeeToEmployeeDto(hierarchyEmployees.getBoss()),
                                                                             employeeToEmployeeDto(hierarchyEmployees.getSubordinate())))
                .collect(toList());
    }

    @Override
    public Map<Integer, String> findAllPersons() {
        return personService.findAllPersons();
    }

    @Override
    public void updatePerson(@RequestBody PersonDto person) {
        personService.update(personDtoToPerson(person));
    }

    @Override
    public void savePerson(@RequestBody PersonDto person) {
        personService.save(personDtoToPerson(person));
    }

    @Override
    public PersonDto personDetails(@PathVariable int id) {
        return personToPersonDto(personService.findPerson(id));
    }

    @Override
    public List<String> findEmplyees() {
        return personService.findAllEmployees();
    }
}
