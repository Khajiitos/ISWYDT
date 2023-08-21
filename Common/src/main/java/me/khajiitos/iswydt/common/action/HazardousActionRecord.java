package me.khajiitos.iswydt.common.action;

import net.minecraft.world.entity.LivingEntity;

public abstract class HazardousActionRecord {
    private final LivingEntity causedBy;
    private int expiresIn;

    public boolean tickToRemove() {
        return expiresIn-- <= 0;
    }

    public HazardousActionRecord(LivingEntity causedBy, int expiresIn) {
        this.causedBy = causedBy;
        this.expiresIn = expiresIn;
    }

    public LivingEntity getCausedBy() {
        return causedBy;
    }
}
