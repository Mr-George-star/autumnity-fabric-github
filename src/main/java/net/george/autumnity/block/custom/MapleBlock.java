package net.george.autumnity.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author Mr.George
 * @since v1.0.0.build.1
 */
@SuppressWarnings("deprecation")
public class MapleBlock extends PillarBlock {
    private final Block stripped;
    private final Block sappy;

    public MapleBlock(Block strippedBlock, Block sappyBlock, Settings settings) {
        super(settings);
        this.stripped = strippedBlock;
        this.sappy = sappyBlock;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (hand == Hand.MAIN_HAND && player.getMainHandStack().getItem() instanceof AxeItem) {
            world.removeBlock(pos, false);
            if (world.getRandom().nextInt(2) == 0) {
                world.setBlockState(pos, this.stripped.getDefaultState());
            } else {
                world.setBlockState(pos, this.sappy.getDefaultState());
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }
}
