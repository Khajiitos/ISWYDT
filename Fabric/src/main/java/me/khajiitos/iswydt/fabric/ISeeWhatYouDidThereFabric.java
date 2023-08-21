package me.khajiitos.iswydt.fabric;

import me.khajiitos.iswydt.common.ISeeWhatYouDidThere;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class ISeeWhatYouDidThereFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ISeeWhatYouDidThere.init();
        ServerTickEvents.START_SERVER_TICK.register(server -> ISeeWhatYouDidThere.tick());
        ServerLifecycleEvents.SERVER_STOPPED.register((server -> ISeeWhatYouDidThere.hazardousActions.clear()));
    }
}
