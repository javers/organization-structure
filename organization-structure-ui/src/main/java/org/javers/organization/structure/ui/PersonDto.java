package org.javers.organization.structure.ui;

public class PersonDto {
    private int id;
    private String firstName;
    private String lastName;
    private String sex;
    private Integer salary;
    private String position;

    public PersonDto(int id, String firstName, String lastName, String sex, Integer salary, String position) {
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
