package me.khajiitos.iswydt.common.mixin;

import me.khajiitos.iswydt.common.ISeeWhatYouDidThere;
import me.khajiitos.iswydt.common.action.IgniteCreeperRecord;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Creeper.class)
public class CreeperMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/Creeper;ignite()V"), method = "mobInteract")
    public void onIgnite(Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {
        ISeeWhatYouDidThere.hazardousActions.add(new IgniteCreeperRecord(player, (Creeper)(Object)this));
    }
}
