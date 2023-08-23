package net.george.autumnity.block.custom;

import net.george.autumnity.effect.AutumnityEffects;
import net.george.autumnity.event.AutumnityEvents;
import net.george.autumnity.item.AutumnityFoodComponents;
import net.george.autumnity.item.AutumnityItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class CookedTurkeyBlock extends TurkeyBlock{
    public CookedTurkeyBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void restoreHunger(World world, PlayerEntity player) {
        player.getHungerManager().add(AutumnityFoodComponents.COOKED_TURKEY.getHunger(),
                AutumnityFoodComponents.COOKED_TURKEY.getSaturationModifier());

        int i = AutumnityFoodComponents.COOKED_TURKEY.getHunger();
        int j = i == 1 ? i : (int) (i * 0.5F);

        if (player.hasStatusEffect(AutumnityEffects.FOUL_TASTE)) {
            player.getHungerManager().add(j, 0.0F);
            AutumnityEvents.OnPlayerEaten.updateFoulTaste(player);
        }
    }

    @Override
    public Item getLegItem() {
        return AutumnityItems.COOKED_TURKEY_PIECE;
    }
}
