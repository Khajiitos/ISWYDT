package me.khajiitos.iswydt.fabric.mixin;

import me.khajiitos.iswydt.common.ISeeWhatYouDidThere;
import me.khajiitos.iswydt.common.action.PlaceAnvilRecord;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public abstract class BlockItemMixin {

    @Shadow public abstract Block getBlock();

    @Inject(at = @At("TAIL"), method = "place")
    public void onPlace(BlockPlaceContext blockPlaceContext, CallbackInfoReturnable<InteractionResult> cir) {
        if (this.getBlock() instanceof AnvilBlock) {
            ISeeWhatYouDidThere.hazardousActions.add(new PlaceAnvilRecord(blockPlaceContext.getPlayer(), blockPlaceContext.getLevel(), blockPlaceContext.getClickedPos()));
        }
    }
}
