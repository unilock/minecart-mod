package com.alc.moreminecarts.blocks.rail_crossings;

import com.alc.moreminecarts.MMConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class WoodenRailCrossing extends RailCrossing {

    public WoodenRailCrossing(Properties builder) {
        super(builder);
    }

    @Override
    public float getRailMaxSpeed(BlockState state, Level world, BlockPos pos, AbstractMinecart cart) {
        return MMConstants.WOODEN_MAX_SPEED;
    }

//    @Override
//    public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
//        if (face == Direction.UP || face == Direction.DOWN) return false;
//        return true;
//    }
}
