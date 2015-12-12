package org.javers.organization.structure.ui;

import org.javers.organization.structure.domain.Employee;
import org.javers.organization.structure.domain.Hierarchy;
import org.javers.organization.structure.domain.HierarchyService;
import org.javers.organization.structure.domain.Person;
import org.javers.organization.structure.domain.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Controller {

    private PersonService personService;

    private HierarchyService hierarchyService;
    private MainView mainView;

    @Autowired
    public Controller(PersonService personService, HierarchyService hierarchyService) {
        this.personService = personService;
        this.hierarchyService = hierarchyService;
    }

    public void updatePerson(Person person) {
        personService.update(person);
    }

    public List<Hierarchy> getHierarchyList() {
        return hierarchyService.findAll();
    }

    public void updateHierarchy(Hierarchy selected) {
        hierarchyService.update(selected);
    }

    public void employeeSelected(Employee e) {
        if (e != null) {
            Person person = personService.findPerson(e.getLogin());
            mainView.selectPersonOnForm(person);
        } else {
            mainView.clearForm();
        }

    }

    public void injectView(MainView mainView) {
        this.mainView = mainView;
    }
}
