package me.khajiitos.iswydt.common.action;

import me.khajiitos.iswydt.common.config.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class StartFireActionRecord extends HazardousActionRecord {
    private final Level level;
    private final BlockPos blockPos;

    public StartFireActionRecord(LivingEntity causedBy, Level level, BlockPos blockPos) {
        super(causedBy, Config.cfg.fireRememberTicks);
        this.level = level;
        this.blockPos = blockPos;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

    public Level getLevel() {
        return level;
    }
}
