package me.khajiitos.iswydt.fabric.mixin;

import me.khajiitos.iswydt.common.ISeeWhatYouDidThere;
import me.khajiitos.iswydt.common.action.StartFireActionRecord;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.FireChargeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(FireChargeItem.class)
public class FireChargeItemMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;gameEvent(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/level/gameevent/GameEvent;Lnet/minecraft/core/BlockPos;)V"), method = "useOn", locals = LocalCapture.CAPTURE_FAILHARD)
    public void onUse(UseOnContext useOnContext, CallbackInfoReturnable<InteractionResult> cir, Level level, BlockPos blockPos, BlockState blockState, boolean bl) {
        if (level instanceof ServerLevel) {
            ISeeWhatYouDidThere.hazardousActions.add(new StartFireActionRecord(useOnContext.getPlayer(), level, blockPos));
        }
    }
}
