package me.khajiitos.iswydt.forge;

import me.khajiitos.iswydt.common.ISeeWhatYouDidThere;
import me.khajiitos.iswydt.common.action.PlaceFluidRecord;
import me.khajiitos.iswydt.common.action.StartFireActionRecord;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventListeners {

    @SubscribeEvent
    public void onTick(TickEvent.ServerTickEvent e) {
        if (e.phase == TickEvent.Phase.START) {
            ISeeWhatYouDidThere.tick();
        }
    }

    @SubscribeEvent
    public void onPlace(BlockEvent.EntityPlaceEvent e) {
        if (e.getEntity() instanceof LivingEntity living && e.getPlacedBlock().getBlock() instanceof BaseFireBlock) {
            ISeeWhatYouDidThere.hazardousActions.add(new StartFireActionRecord(living, living.level(), e.getPos()));
        }
    }

    @SubscribeEvent
    public void onServerStopped(ServerStoppedEvent e) {
        ISeeWhatYouDidThere.hazardousActions.clear();
    }
}
