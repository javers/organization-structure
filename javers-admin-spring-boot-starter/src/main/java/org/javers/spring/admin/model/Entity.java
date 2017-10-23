package org.javers.spring.admin.model;

public class Entity {
    private final String name;
    private final int count;
    private final int snapshots;

    public Entity(String name, int count, int snapshots) {
        this.name = name;
        this.count = count;
        this.snapshots = snapshots;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public int getSnapshots() {
        return snapshots;
    }
}
