package me.khajiitos.iswydt.common.mixin;

import me.khajiitos.iswydt.common.ISeeWhatYouDidThere;
import me.khajiitos.iswydt.common.action.CauseAnvilToFallRecord;
import me.khajiitos.iswydt.common.action.HazardousActionRecord;
import me.khajiitos.iswydt.common.action.PlaceAnvilRecord;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(FallingBlock.class)
public class FallingBlockMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/FallingBlock;falling(Lnet/minecraft/world/entity/item/FallingBlockEntity;)V"), method = "tick", locals = LocalCapture.CAPTURE_FAILHARD)
    public void onTickFall(BlockState blockState, ServerLevel serverLevel, BlockPos pos, RandomSource randomSource, CallbackInfo ci, FallingBlockEntity fallingBlockEntity) {
        if ((FallingBlock)(Object)this instanceof AnvilBlock) {
            for (HazardousActionRecord hazardousActionRecord : ISeeWhatYouDidThere.hazardousActions) {
                if (hazardousActionRecord instanceof PlaceAnvilRecord placeAnvilRecord) {
                    if (placeAnvilRecord.getLevel() == serverLevel && placeAnvilRecord.getBlockPos().equals(pos)) {
                        ISeeWhatYouDidThere.hazardousActions.add(new CauseAnvilToFallRecord(placeAnvilRecord.getCausedBy(), fallingBlockEntity));
                        break;
                    }
                }
            }
        }
    }
}
