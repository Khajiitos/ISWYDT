package me.khajiitos.iswydt.common.mixin;

import me.khajiitos.iswydt.common.ISeeWhatYouDidThere;
import me.khajiitos.iswydt.common.action.CauseAnvilToFallRecord;
import me.khajiitos.iswydt.common.action.HazardousActionRecord;
import me.khajiitos.iswydt.common.util.DamageUtils;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FallingBlockEntity.class)
public class FallingBlockEntityMixin {

    @Shadow private BlockState blockState;

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Fallable;getFallDamageSource(Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/world/damagesource/DamageSource;"), method = "causeFallDamage")
    public DamageSource causeFallDamageSource(Fallable instance, Entity entity) {
        if (this.blockState.getBlock() instanceof AnvilBlock) {
            FallingBlockEntity fallingBlockEntity = (FallingBlockEntity)(Object)this;
            for (HazardousActionRecord hazardousActionRecord : ISeeWhatYouDidThere.hazardousActions) {
                if (hazardousActionRecord instanceof CauseAnvilToFallRecord causeAnvilToFallRecord) {
                    if (causeAnvilToFallRecord.getFallingBlock() == fallingBlockEntity) {
                        return new DamageSource(DamageUtils.getDamageTypeHolder(DamageTypes.FALLING_ANVIL, entity.level().registryAccess()), entity, causeAnvilToFallRecord.getCausedBy());
                    }
                }
            }
        }
        return instance.getFallDamageSource(entity);
    }
}
