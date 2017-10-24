package org.javers.spring.admin.model;

public class Entity {
    private final String id;
    private final String name;
    private final int count;
    private final int snapshots;


    public Entity(String id, int count, int snapshots) {
        this.id = id;
        String[] split = id.split("\\.");
        this.name = split[split.length - 1];
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

    public String getId() {
        return id;
    }
}
