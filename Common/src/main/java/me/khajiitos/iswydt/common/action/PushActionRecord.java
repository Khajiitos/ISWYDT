package me.khajiitos.iswydt.common.action;

import net.minecraft.world.entity.LivingEntity;

public class PushActionRecord extends HazardousActionRecord {
    private final LivingEntity other;

    public PushActionRecord(LivingEntity causedBy, LivingEntity other, int expiresIn) {
        super(causedBy, expiresIn);
        this.other = other;
    }

    public LivingEntity getOther() {
        return other;
    }
}
