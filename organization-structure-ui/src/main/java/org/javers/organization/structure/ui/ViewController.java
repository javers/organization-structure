package org.javers.organization.structure.ui;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/view")
public abstract class ViewController {

    @RequestMapping("/hierarchy")
    public abstract List<String> findHierarchies();

    @RequestMapping("/hierarchy/{name}")
    public abstract List<OrganizationStructureTableRow> findHierarchyByName(@PathVariable String name);

    @RequestMapping(value = "person", method = RequestMethod.GET)
    public abstract Map<Integer, String> findAllPersons();

    @RequestMapping(value = "person", method = RequestMethod.PUT)
    public abstract void updatePerson(@RequestBody PersonDto person);

    @RequestMapping(value = "person", method = RequestMethod.POST)
    public abstract void savePerson(@RequestBody PersonDto person);

    @RequestMapping("person/{id}")
    public abstract PersonDto personDetails(@PathVariable int id);

    @RequestMapping("employee")
    public abstract List<String> findEmplyees();
}
