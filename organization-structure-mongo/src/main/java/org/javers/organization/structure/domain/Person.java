package org.javers.organization.structure.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author bartosz walacik
 */
@Document
public class Person {

    @Id
    private int id;
    private String firstName;
    private String lastName;
    private Sex sex;
    private Integer salary;
    private Position position;
    @Version
    private Long version;

    public Person() {
    }

    public Person(int id, String firstName, String lastName, Sex sex) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
    }

    public Person(int id, String firstName, String lastName, Sex sex, Integer salary, Position position) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.salary = salary;
        this.position = position;
    }

    public Person assignPosition(Position position, int salary) {
        this.position = position;
        this.salary = salary;
        return this;
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

    public Employee toEmployee() {
        return new Employee(firstName+"."+lastName, id);
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }
}
