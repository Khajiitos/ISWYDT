package me.khajiitos.iswydt.common.mixin;

import me.khajiitos.iswydt.common.ISeeWhatYouDidThere;
import me.khajiitos.iswydt.common.action.PushActionRecord;
import me.khajiitos.iswydt.common.config.Config;
import me.khajiitos.iswydt.common.util.DamageUtils;
import me.khajiitos.iswydt.common.util.FluidUtils;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {
    @Shadow private Level level;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;push(DDD)V", ordinal = 0), method = "push(Lnet/minecraft/world/entity/Entity;)V")
    public void push(Entity other, CallbackInfo ci) {
        Entity thisEntity = (Entity)(Object)this;

        if (!this.level.isClientSide && thisEntity instanceof LivingEntity living && other instanceof LivingEntity otherLiving) {
            ISeeWhatYouDidThere.hazardousActions.removeIf(action -> action instanceof PushActionRecord pushAction && pushAction.getOther() == otherLiving);
            ISeeWhatYouDidThere.addHazardousActionServer(new PushActionRecord(living, otherLiving, Config.cfg.pushRememberTicks));
        }
    }

    @ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"), method = "lavaHurt")
    public DamageSource hurt(DamageSource oldDamageSource) {
        Entity entity = (Entity)(Object)this;

        LivingEntity fluidPlacer = FluidUtils.getPlacerOfTouchingFluid(entity, FluidTags.LAVA);

        if (fluidPlacer != null) {
            return new DamageSource(DamageUtils.getDamageTypeHolder(DamageTypes.LAVA, entity.level().registryAccess()), null, fluidPlacer);
        }

        return oldDamageSource;
    }
}
