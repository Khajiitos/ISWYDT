package me.khajiitos.iswydt.common.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ClothConfigScreenMaker {

    public static Screen create(Minecraft minecraft, Screen parent) {
        return create(parent);
    }

    public static Screen create(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.literal("I See What You Did There!"))
                .setSavingRunnable(Config::save);

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory category = builder.getOrCreateCategory(Component.literal("General"));

        category.addEntry(
                entryBuilder.startIntField(Component.literal("Fire Remember Ticks"), Config.cfg.fireRememberTicks)
                        .setMin(0)
                        .setDefaultValue(Config.DEFAULT.fireRememberTicks)
                        .setSaveConsumer(integer -> Config.cfg.fireRememberTicks = integer)
                        .build()
        );

        category.addEntry(
                entryBuilder.startIntField(Component.literal("Fluid Remember Ticks"), Config.cfg.fluidRememberTicks)
                        .setMin(0)
                        .setDefaultValue(Config.DEFAULT.fluidRememberTicks)
                        .setSaveConsumer(integer -> Config.cfg.fluidRememberTicks = integer)
                        .build()
        );

        category.addEntry(
                entryBuilder.startIntField(Component.literal("Push Remember Ticks"), Config.cfg.pushRememberTicks)
                        .setMin(0)
                        .setDefaultValue(Config.DEFAULT.pushRememberTicks)
                        .setSaveConsumer(integer -> Config.cfg.pushRememberTicks = integer)
                        .build()
        );

        return builder.build();
    }
}