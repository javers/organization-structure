package org.javers.organization.structure.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
public class HierarchyEmployees implements Serializable {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private Hierarchy hierarchy;

    @ManyToOne
    private Employee boss;

    @ManyToOne
    private Employee subordinate;

    public Hierarchy getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(Hierarchy hierarchy) {
        this.hierarchy = hierarchy;
    }

    public Employee getBoss() {
        return boss;
    }

    public void setBoss(Employee boss) {
        this.boss = boss;
    }

    public Employee getSubordinate() {
        return subordinate;
    }

    public void setSubordinate(Employee subordinate) {
        this.subordinate = subordinate;
    }
}
