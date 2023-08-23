package net.george.autumnity.util;

import net.minecraft.util.Identifier;

//TODO：Remove to Special Library Mod!!
@SuppressWarnings("unused")
public class BuiltinUtil {
    private final String modId;

    public BuiltinUtil(String modId) {
        this.modId = modId;
    }

    public static final int MAX_BUILD_HEIGHT = 318;
    public static final int MIN_BUILD_HEIGHT = 0;

    public Identifier getIdentifier(String name) {
        return new Identifier(this.modId, name);
    }
}
