package me.khajiitos.iswydt.common.mixin;

import me.khajiitos.iswydt.common.ISeeWhatYouDidThere;
import me.khajiitos.iswydt.common.action.HazardousActionRecord;
import me.khajiitos.iswydt.common.action.StartFireActionRecord;
import me.khajiitos.iswydt.common.util.DamageUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BaseFireBlock.class)
public class BaseFireBlockMixin {

    @Shadow @Final private float fireDamage;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"), method = "entityInside", cancellable = true)
    public void hurt(BlockState blockState, Level level, BlockPos blockPos, Entity entity, CallbackInfo ci) {
        LivingEntity causedBy = null;
        for (HazardousActionRecord action : ISeeWhatYouDidThere.hazardousActions) {
            if (action instanceof StartFireActionRecord hotAction) {
                if (hotAction.getBlockPos().equals(blockPos) && hotAction.getLevel() == level) {
                    causedBy = hotAction.getCausedBy();
                    break;
                }
            }
        }
        if (causedBy != null) {
            try {
                entity.hurt(new DamageSource(DamageUtils.getDamageTypeHolder(DamageTypes.IN_FIRE, level.registryAccess()), null, causedBy), this.fireDamage);
                ci.cancel();
            } catch (IllegalStateException ignored) {}
        }
    }
}
