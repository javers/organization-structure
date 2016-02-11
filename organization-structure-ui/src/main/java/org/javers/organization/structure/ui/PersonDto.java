package org.javers.organization.structure.ui;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PersonDto {
    private int id;
    private String firstName;
    private String lastName;
    private String sex;
    private Integer salary;
    private String position;

    @JsonCreator
    public PersonDto(@JsonProperty("id") Integer id,
                     @JsonProperty("firstName") String firstName,
                     @JsonProperty("lastName") String lastName,
                     @JsonProperty("sex") String sex,
                     @JsonProperty("salary") Integer salary,
                     @JsonProperty("position") String position) {
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

    public String getSex() {
        return sex;
    }

    public Integer getSalary() {
        return salary;
    }

    public String getPosition() {
        return position;
    }
}
