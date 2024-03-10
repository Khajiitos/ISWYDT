package me.khajiitos.iswydt.fabric.mixin;

import me.khajiitos.iswydt.common.ISeeWhatYouDidThere;
import me.khajiitos.iswydt.common.action.PlaceFluidRecord;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BucketItem.class)
public class BucketItemMixin {

    @Shadow @Final private Fluid content;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/BucketItem;playEmptySound(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;)V", ordinal = 0), method = "emptyContents")
    public void onEmptyOnWaterloggable(Player player, Level level, BlockPos blockPos, BlockHitResult blockHitResult, CallbackInfoReturnable<Boolean> cir) {
        if (!level.isClientSide) {
            ISeeWhatYouDidThere.addHazardousActionServer(new PlaceFluidRecord(player, this.content, level, blockPos));
        }
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/BucketItem;playEmptySound(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;)V", ordinal = 1), method = "emptyContents")
    public void onEmpty(Player player, Level level, BlockPos blockPos, BlockHitResult blockHitResult, CallbackInfoReturnable<Boolean> cir) {
        if (!level.isClientSide) {
            ISeeWhatYouDidThere.addHazardousActionServer(new PlaceFluidRecord(player, this.content, level, blockPos));
        }
    }
}
