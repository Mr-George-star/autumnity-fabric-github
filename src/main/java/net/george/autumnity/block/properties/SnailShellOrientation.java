package net.george.autumnity.block.properties;

import net.minecraft.util.StringIdentifiable;

public enum SnailShellOrientation implements StringIdentifiable {
    UP("up"),
    DOWN("down"),
    HORIZONTAL("horizontal");

    private final String name;

    SnailShellOrientation(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
