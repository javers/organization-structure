package org.javers.organization.structure.domain;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * @author bartosz walacik
 */
@Entity
@Table
public class Hierarchy implements Serializable{

    @Id
    private String name;

    @OneToMany(mappedBy = "hierarchy", fetch = FetchType.EAGER)
    private List<HierarchyEmployees> hierarchyEmployees;


    public Hierarchy() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<HierarchyEmployees> getHierarchyEmployees() {
        return hierarchyEmployees;
    }

    public void setHierarchyEmployees(List<HierarchyEmployees> hierarchyEmployees) {
        this.hierarchyEmployees = hierarchyEmployees;
    }
}
