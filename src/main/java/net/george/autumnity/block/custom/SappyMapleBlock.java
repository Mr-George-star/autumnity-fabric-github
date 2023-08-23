package net.george.autumnity.block.custom;

import net.george.autumnity.item.AutumnityItems;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

/**
 * @author Mr.George
 * @since v1.0.0.build.1
 */
@SuppressWarnings("deprecation")
public class SappyMapleBlock extends PillarBlock {
    private final Block saplessBlock;

    public SappyMapleBlock(Block saplessBlock, Settings settings) {
        super(settings);
        this.saplessBlock = saplessBlock;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        Item item = stack.getItem();
        if (stack.getItem() == Items.GLASS_BOTTLE) {
            stack.decrement(1);
            world.playSound(player, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
            if (stack.isEmpty()) {
                player.setStackInHand(hand, new ItemStack(AutumnityItems.SAP_BOTTLE));
            } else if (!player.getInventory().insertStack(new ItemStack(AutumnityItems.SAP_BOTTLE))) {
                player.dropItem(new ItemStack(AutumnityItems.SAP_BOTTLE), false);
            }

            world.emitGameEvent(player, GameEvent.FLUID_PICKUP, pos);
            if (!world.isClient) {
                player.incrementStat(Stats.USED.getOrCreateStat(item));
            }

            world.setBlockState(pos, transferBlockState(state, this.saplessBlock.getDefaultState()));
            return TypedActionResult.success(world.isClient).getResult();
        }
        return super.onUse(state, world, pos, player,hand, hit);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static BlockState transferBlockState(BlockState initial, BlockState after) {
        BlockState block = after;
        for (Property property : initial.getProperties()) {
            if (after.contains(property) && initial.get(property) != null) {
                block = block.with(property, initial.get(property));
            }
        }
        return block;
    }
}
