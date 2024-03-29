package net.george.autumnity.mixin;

import net.minecraft.util.SignType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SignType.class)
public interface SignTypeAccessor {
    @Invoker("<init>")
    static SignType createSignType(String name) {
        throw new AssertionError();
    }

    @Invoker("register")
    static SignType register(SignType type) {
        throw new AssertionError();
    }
}