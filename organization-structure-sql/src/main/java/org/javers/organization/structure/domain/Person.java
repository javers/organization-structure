package org.javers.organization.structure.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
public class Person {

    @Id
    private int id;
    private String firstName;
    private String lastName;
    private Sex sex;
    private Integer salary;
    private Position position;

    @OneToOne(mappedBy = "person")
    Employee employee;

    public Person() {
    }

    public Person(int id, String firstName, String lastName, Sex sex, Integer salary, Position position) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.salary = salary;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Sex getSex() {
        return sex;
    }

    public Integer getSalary() {
        return salary;
    }

    public Position getPosition() {
        return position;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }
}

