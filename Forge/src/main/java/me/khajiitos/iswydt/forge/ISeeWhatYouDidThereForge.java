package me.khajiitos.iswydt.forge;

import me.khajiitos.iswydt.common.ISeeWhatYouDidThere;
import me.khajiitos.iswydt.common.config.ClothConfigCheck;
import me.khajiitos.iswydt.common.config.ClothConfigScreenMaker;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;

@Mod(ISeeWhatYouDidThere.MOD_ID)
public class ISeeWhatYouDidThereForge {
    public ISeeWhatYouDidThereForge() {
        ISeeWhatYouDidThere.init();
        MinecraftForge.EVENT_BUS.register(new EventListeners());

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            if (ClothConfigCheck.isInstalled()) {
                ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory(ClothConfigScreenMaker::create));
            }
        });
    }
}