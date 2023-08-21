package me.khajiitos.iswydt.common.mixin;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.EnderMan;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnderMan.class)
public class EnderManMixin {

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/damagesource/DamageSource;getEntity()Lnet/minecraft/world/entity/Entity;"), method = "hurt")
    public Entity getEntity(DamageSource damageSource) {
        // The Enderman will teleport away from any damage that didn't come from a living entity
        // Now that water damage can come from a player, we'd still like them to teleport away from it

        if (damageSource.is(DamageTypeTags.IS_DROWNING)) {
            return null;
        }

        return damageSource.getEntity();
    }
}
