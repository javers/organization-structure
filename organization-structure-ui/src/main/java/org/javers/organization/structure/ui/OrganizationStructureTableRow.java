package org.javers.organization.structure.ui;

public class OrganizationStructureTableRow {

    private final EmployeeDto employee;
    private final EmployeeDto boss;

    public OrganizationStructureTableRow(EmployeeDto employee, EmployeeDto boss) {
        this.employee = employee;
        this.boss = boss;
    }

    public EmployeeDto getEmployee() {
        return employee;
    }

    public EmployeeDto getBoss() {
        return boss;
    }
}
