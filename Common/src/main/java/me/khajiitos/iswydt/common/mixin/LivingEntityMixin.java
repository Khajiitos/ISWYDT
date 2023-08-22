package me.khajiitos.iswydt.common.mixin;


import me.khajiitos.iswydt.common.util.DamageUtils;
import me.khajiitos.iswydt.common.util.FluidUtils;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/damagesource/DamageSource;is(Lnet/minecraft/tags/TagKey;)Z", ordinal = 7), method = "hurt")
    public boolean shouldNotKnockback(DamageSource damageSource, TagKey<DamageType> p_270890_) {
        return damageSource.is(DamageTypeTags.IS_EXPLOSION) || damageSource.is(DamageTypeTags.IS_FIRE) || damageSource.is(DamageTypeTags.IS_FALL) || damageSource.is(DamageTypeTags.IS_DROWNING);
    }

    @ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z", ordinal = 1), index = 0, method = "aiStep")
    public DamageSource aiStep(DamageSource oldDamageSource) {
        LivingEntity livingEntity = (LivingEntity)(Object)this;

        LivingEntity fluidPlacer = FluidUtils.getPlacerOfTouchingFluid(livingEntity, FluidTags.WATER);

        if (fluidPlacer != null) {
            return new DamageSource(DamageUtils.getDamageTypeHolder(DamageTypes.DROWN, livingEntity.level.registryAccess()), null, fluidPlacer);
        }

        return oldDamageSource;
    }
}