package me.khajiitos.iswydt.common.mixin;

import me.khajiitos.iswydt.common.ISeeWhatYouDidThere;
import me.khajiitos.iswydt.common.action.HazardousActionRecord;
import me.khajiitos.iswydt.common.action.PushActionRecord;
import me.khajiitos.iswydt.common.util.DamageUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(Block.class)
public class BlockMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;causeFallDamage(FFLnet/minecraft/world/damagesource/DamageSource;)Z"), method = "fallOn", cancellable = true)
    public void causeFallDamage(Level level, BlockState blockState, BlockPos blockPos, Entity entity, float damage, CallbackInfo ci) {
        Optional<LivingEntity> causedBy = ISeeWhatYouDidThere.hazardousActions.stream().filter(action -> action instanceof PushActionRecord pushAction && pushAction.getOther() == entity).map(HazardousActionRecord::getCausedBy).findFirst();

        if (causedBy.isPresent()) {
            try {
                entity.causeFallDamage(damage, 1.0f, new DamageSource(DamageUtils.getDamageTypeHolder(DamageTypes.FALL, level.registryAccess()), null, causedBy.get()));
                ci.cancel();
            } catch (IllegalStateException ignored) {}
        }

    }
}
