package me.khajiitos.iswydt.common.action;

import me.khajiitos.iswydt.common.config.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;

public class PlaceFluidRecord extends HazardousActionRecord {
    private final Level level;
    private final BlockPos blockPos;
    private final Fluid fluid;

    public PlaceFluidRecord(LivingEntity causedBy, Fluid fluid, Level level, BlockPos blockPos) {
        super(causedBy, Config.cfg.fluidRememberTicks);
        this.fluid = fluid;
        this.level = level;
        this.blockPos = blockPos;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

    public Level getLevel() {
        return level;
    }

    public Fluid getFluid() {
        return fluid;
    }
}