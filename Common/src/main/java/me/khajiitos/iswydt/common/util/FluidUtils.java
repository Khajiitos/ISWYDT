package me.khajiitos.iswydt.common.util;

import com.mojang.datafixers.util.Pair;
import me.khajiitos.iswydt.common.ISeeWhatYouDidThere;
import me.khajiitos.iswydt.common.action.HazardousActionRecord;
import me.khajiitos.iswydt.common.action.PlaceFluidRecord;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FluidUtils {

    public static Pair<BlockPos, FluidState> getSourceOrThis(Level level, BlockPos blockPos, FluidState fluidState) {
        FluidState currentFluidState = fluidState;
        BlockPos.MutableBlockPos currentBlockPos = new BlockPos.MutableBlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ());

        int lastAddX = 0, lastAddY = 0, lastAddZ = 0;
        do {
            if (currentFluidState.isSource()) {
                return Pair.of(currentBlockPos, currentFluidState);
            }
            Vec3 flow = currentFluidState.getFlow(level, currentBlockPos);

            int addX = (int)-Math.round(flow.x);
            int addY = (int)-Math.round(flow.y);
            int addZ = (int)-Math.round(flow.z);

            if (addX == 0 && addY == 0 && addZ == 0) {
                return Pair.of(blockPos, fluidState);
            }

            // Prevents two fluids flowing against each other -> <-
            if (lastAddX == -addX && lastAddY == -addY && lastAddZ == -addZ) {
                return Pair.of(blockPos, fluidState);
            }

            lastAddX = addX;
            lastAddY = addY;
            lastAddZ = addZ;

            currentBlockPos.move(addX, addY, addZ);

            currentFluidState = level.getFluidState(currentBlockPos);
        } while (!currentFluidState.isEmpty());

        return Pair.of(blockPos, fluidState);
    }

    public static List<BlockPos> getTouchingBlockPositions(AABB box) {
        List<BlockPos> list = new ArrayList<>();
        for (int x = (int)Math.floor(box.minX); x <= (int)Math.floor(box.maxX); x++) {
            for (int y = (int)Math.floor(box.minY); y <= (int)Math.floor(box.maxY); y++) {
                for (int z = (int)Math.floor(box.minZ); z <= (int)Math.floor(box.maxZ); z++) {
                    list.add(new BlockPos(x, y, z));
                }
            }
        }
        return list;
    }

    public static List<Pair<BlockPos, FluidState>> getTouchingFluids(Entity entity) {
        return getTouchingBlockPositions(entity.getBoundingBox()).stream()
                .map(blockPos -> Pair.of(blockPos, entity.level().getFluidState(blockPos)))
                .filter(pair -> !pair.getSecond().isEmpty())
                .toList();
    }

    public static List<Pair<BlockPos, FluidState>> getSourcesOfTouchingFluids(Entity entity) {
        return getTouchingFluids(entity).stream().map(pair -> getSourceOrThis(entity.level(), pair.getFirst(), pair.getSecond())).collect(Collectors.toList());
    }

    public static LivingEntity getPlacerOfTouchingFluid(Entity entity, TagKey<Fluid> fluid) {
        List<Pair<BlockPos, FluidState>> touchingLiquids = FluidUtils.getSourcesOfTouchingFluids(entity);

        for (Pair<BlockPos, FluidState> pair : touchingLiquids) {
            BlockPos blockPos = pair.getFirst();
            FluidState fluidState = pair.getSecond();
            if (fluidState.is(fluid)) {
                for (HazardousActionRecord actionRecord : ISeeWhatYouDidThere.hazardousActions) {
                    if (actionRecord instanceof PlaceFluidRecord fluidRecord) {
                        if (fluidRecord.getLevel() == entity.level() &&
                                fluidRecord.getBlockPos().equals(blockPos) && fluidRecord.getFluid().is(fluid)) {
                            return fluidRecord.getCausedBy();
                        }
                    }
                }
            }
        }

        return null;
    }
}
