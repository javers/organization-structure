package org.javers.organization.structure.domain;

import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * @author bartosz walacik
 */
public class Hierarchy {

    @Id
    private String name;

    private List<Employee> employees;

    public Hierarchy(String name, List<Employee> employees) {
        this.name = name;
        this.employees = employees;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }
}