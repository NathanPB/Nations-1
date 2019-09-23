package com.arckenver.nations.object;

import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Hashtable;

public class NationSpawn {
    private String name;
    private Location<World> location;
    private Hashtable<String, Boolean> flags;

    public NationSpawn(String name, Location<World> location, Hashtable<String, Boolean> flags) {
        this.name = name;
        this.location = location;
        this.flags = flags;
    }

    public NationSpawn(String name, Location<World> location) {
        this(name, location, new Hashtable<>());
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

    public Hashtable<String, Boolean> getFlags() {
        return flags;
    }

    public boolean getFlag(String flag) {
        return this.flags.get(flag);
    }

    public void setFlag(String flag, boolean b) {
        this.flags.put(flag, b);
    }
}
