package me.khajiitos.iswydt.fabric.mixin;

import me.khajiitos.iswydt.common.ISeeWhatYouDidThere;
import me.khajiitos.iswydt.common.action.StartFireActionRecord;
import net.minecraft.advancements.critereon.ItemUsedOnLocationTrigger;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemUsedOnLocationTrigger.class)
public class ItemUsedOnLocationTriggerMixin {

    @Inject(at = @At("TAIL"), method = "trigger")
    public void trigger(ServerPlayer player, BlockPos blockPos, ItemStack itemStack, CallbackInfo ci) {
        Level level = player.level();
        BlockState blockState = level.getBlockState(blockPos);

        if (blockState.getBlock() instanceof BaseFireBlock) {
            ISeeWhatYouDidThere.addHazardousActionServer(new StartFireActionRecord(player, level, blockPos));
        }
    }
}
