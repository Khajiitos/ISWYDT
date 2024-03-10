package me.khajiitos.iswydt.common;

import me.khajiitos.iswydt.common.action.HazardousActionRecord;
import me.khajiitos.iswydt.common.config.Config;
import net.minecraft.server.level.ServerLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ISeeWhatYouDidThere {
    public static final String MOD_ID = "iswydt";
    public static final Logger LOGGER = LoggerFactory.getLogger("ISWYDT");
    public static final List<HazardousActionRecord> hazardousActions = new CopyOnWriteArrayList<>();

    public static void addHazardousActionServer(HazardousActionRecord hazardousActionRecord) {
        if (hazardousActionRecord.getCausedBy().level() instanceof ServerLevel) {
            hazardousActions.add(hazardousActionRecord);
        }
    }

    public static void init() {
        Config.load();
    }

    public static void tick() {
        hazardousActions.removeIf(HazardousActionRecord::tickToRemove);
    }
}
