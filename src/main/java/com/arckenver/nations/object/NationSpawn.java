package com.arckenver.nations.object;

import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.HashSet;

public class NationSpawn {
    private String name;
    private Location<World> location;
    private HashSet<String> flags;

    public NationSpawn(String name, Location<World> location, HashSet<String> flags) {
        this.name = name;
        this.location = location;
        this.flags = flags;
    }

    public NationSpawn(String name, Location<World> location) {
        this(name, location, NationSpawn.getDefaultFlags());
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location<World> getLocation() {
        return location;
    }

    public void setLocation(Location<World> location) {
        this.location = location;
    }

    public HashSet<String> getFlags() {
        return flags;
    }

    public boolean hasFlag(String flag) {
        return flags.contains(flag);
    }

    public void addTag(String tag) {
        flags.add(tag);
    }

    public void removeTag(String tag) {
        flags.remove(tag);
    }

    public void setFlag(String flag, boolean b) {
        if(b) {
            addTag(flag);
        } else {
            removeTag(flag);
        }
    }

    private static HashSet<String> getDefaultFlags() {
        HashSet<String> flags = new HashSet<>();
        flags.add("public");
        return flags;
    }
}
