package me.khajiitos.iswydt.common.mixin;

import me.khajiitos.iswydt.common.ISeeWhatYouDidThere;
import me.khajiitos.iswydt.common.action.HazardousActionRecord;
import me.khajiitos.iswydt.common.action.IgniteCreeperRecord;
import me.khajiitos.iswydt.common.util.DamageUtils;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Level.class)
public abstract class LevelMixin {
    @Shadow
    public abstract Explosion explode(Entity $$0, DamageSource $$1, ExplosionDamageCalculator $$2, double $$3, double $$4, double $$5, float $$6, boolean $$7, Level.ExplosionInteraction $$8);

    @Inject(at = @At("HEAD"), method = "explode(Lnet/minecraft/world/entity/Entity;DDDFLnet/minecraft/world/level/Level$ExplosionInteraction;)Lnet/minecraft/world/level/Explosion;", cancellable = true)
    public void onExplode(Entity entity, double $$1, double $$2, double $$3, float $$4, Level.ExplosionInteraction $$5, CallbackInfoReturnable<Explosion> cir) {
        if (entity instanceof Creeper) {
            for (HazardousActionRecord hazardousActionRecord : ISeeWhatYouDidThere.hazardousActions) {
                if (hazardousActionRecord instanceof IgniteCreeperRecord igniteCreeperRecord) {
                    if (igniteCreeperRecord.getIgnitedCreeper() == entity) {
                        cir.setReturnValue(explode(entity, new DamageSource(DamageUtils.getDamageTypeHolder(DamageTypes.EXPLOSION, entity.level().registryAccess()), entity, igniteCreeperRecord.getCausedBy()), null, $$1, $$2, $$3, $$4, false, $$5));
                        break;
                    }
                }
            }
        }
    }
}
