package org.javers.organization.structure.domain;


import org.springframework.data.annotation.Id;

import java.util.ArrayList;

/**
 * @author bartosz walacik
 */
public class Employee {

    @Id
    private String domainName;
    private Employee boss;
    private int personId;

    public Employee() {
    }

    public Employee(String domainName, int personId) {
        this.domainName = domainName;
        this.personId = personId;
    }

    public Employee(String domainName, int personId, Employee boss) {
        this.domainName = domainName;
        this.personId = personId;
        this.boss = boss;
    }

    public int getPersonId() {
        return personId;
    }

    public void addSubordinate(Employee subordinate) {
        subordinate.boss = this;
    }

    public void addSubordinates(ArrayList<Employee> employees) {
        employees.forEach(e -> e.boss = this);
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public Employee getBoss() {
        return boss;
    }

    public void setBoss(Employee boss) {
        this.boss = boss;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }
}
