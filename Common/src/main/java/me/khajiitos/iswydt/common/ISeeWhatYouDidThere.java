package me.khajiitos.iswydt.common;

import me.khajiitos.iswydt.common.action.HazardousActionRecord;
import me.khajiitos.iswydt.common.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ISeeWhatYouDidThere {
    public static final String MOD_ID = "iswydt";
    public static final Logger LOGGER = LoggerFactory.getLogger("ISWYDT");
    public static List<HazardousActionRecord> hazardousActions = new ArrayList<>();

    public static void init() {
        Config.load();
    }

    public static void tick() {
        hazardousActions.removeIf(HazardousActionRecord::tickToRemove);
    }
}
