package me.khajiitos.iswydt.common.action;

import me.khajiitos.iswydt.common.config.Config;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;

public class IgniteCreeperRecord extends HazardousActionRecord {
    private final Creeper ignitedCreeper;

    public IgniteCreeperRecord(LivingEntity causedBy, Creeper ignitedCreeper) {
        super(causedBy, Config.cfg.placeAnvilRememberTicks);
        this.ignitedCreeper = ignitedCreeper;
    }

    public Creeper getIgnitedCreeper() {
        return ignitedCreeper;
    }
}
