package me.khajiitos.iswydt.fabric;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.khajiitos.iswydt.common.config.ClothConfigCheck;
import me.khajiitos.iswydt.common.config.ClothConfigScreenMaker;

public class ModMenuApiImpl implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (ClothConfigCheck.isInstalled()) {
            return ClothConfigScreenMaker::create;
        }
        return ModMenuApi.super.getModConfigScreenFactory();
    }
}
