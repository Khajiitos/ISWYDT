package me.khajiitos.iswydt.common.action;

import me.khajiitos.iswydt.common.config.Config;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;

public class CauseAnvilToFallRecord extends HazardousActionRecord {
    private final FallingBlockEntity fallingBlock;

    public CauseAnvilToFallRecord(LivingEntity causedBy, FallingBlockEntity fallingBlock) {
        super(causedBy, Config.cfg.placeAnvilRememberTicks);
        this.fallingBlock = fallingBlock;
    }

    public FallingBlockEntity getFallingBlock() {
        return fallingBlock;
    }
}
