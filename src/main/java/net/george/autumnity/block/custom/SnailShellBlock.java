package net.george.autumnity.block.custom;

import net.george.autumnity.block.properties.SnailShellOrientation;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public class SnailShellBlock extends HorizontalFacingBlock {
    public static final EnumProperty<SnailShellOrientation> ORIENTATION =
            EnumProperty.of("orientation", SnailShellOrientation.class);

    public SnailShellBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getPlayerLookDirection();
        if (direction.getAxis() == Direction.Axis.Y) {
            return this.getDefaultState().with(ORIENTATION, direction == Direction.UP ? SnailShellOrientation.DOWN : SnailShellOrientation.UP).with(FACING, ctx.getPlayerFacing().getOpposite());
        } else {
            return this.getDefaultState().with(ORIENTATION, SnailShellOrientation.HORIZONTAL).with(FACING, direction.getOpposite());
        }
    }

    protected static Direction getFacing(BlockState state) {
        return switch (state.get(ORIENTATION)) {
            case UP -> Direction.UP;
            case DOWN -> Direction.DOWN;
            default -> state.get(FACING);
        };
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, ORIENTATION);
    }
}
