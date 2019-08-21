package org.javers.organization.structure.ui;

public class EmployeeDto {
    private String domainName;

    public EmployeeDto() {
    }

    public EmployeeDto(String domainName) {
        this.domainName = domainName;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }
}
