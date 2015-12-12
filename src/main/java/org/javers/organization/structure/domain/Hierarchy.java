package org.javers.organization.structure.domain;


import org.springframework.data.annotation.Id;

/**
 * @author bartosz walacik
 */
public class Hierarchy {

    @Id
    private String hierarchyName;

    private Employee root;

    public Hierarchy(Employee root, String hierarchyName) {
        this.root = root;
        this.hierarchyName = hierarchyName;
    }

    public String getHierarchyName() {
        return hierarchyName;
    }

    public void setHierarchyName(String hierarchyName) {
        this.hierarchyName = hierarchyName;
    }

    public Employee getRoot() {
        return root;
    }

    public void setRoot(Employee root) {
        this.root = root;
    }
}
