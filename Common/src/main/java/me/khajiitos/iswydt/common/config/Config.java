package me.khajiitos.iswydt.common.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import me.khajiitos.iswydt.common.ISeeWhatYouDidThere;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {
    public static final File file = new File("config/iswydt.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static final Values DEFAULT = new Values();
    public static final Values cfg = new Values();

    public static class Values {
        public int fireRememberTicks = 200;
        public int fluidRememberTicks = 200;
        public int pushRememberTicks = 80;
        public int placeAnvilRememberTicks = 300;
        public int igniteCreeperRememberTicks = 150;
    }

    public static void load() {
        if (file.exists()) {
            try (FileReader fileReader = new FileReader(file)) {
                JsonObject object = GSON.fromJson(fileReader, JsonObject.class);

                if (object.has("fireRememberTicks")) {
                    cfg.fireRememberTicks = object.get("fireRememberTicks").getAsInt();
                }

                if (object.has("fluidRememberTicks")) {
                    cfg.fluidRememberTicks = object.get("fluidRememberTicks").getAsInt();
                }

                if (object.has("pushRememberTicks")) {
                    cfg.pushRememberTicks = object.get("pushRememberTicks").getAsInt();
                }

                if (object.has("placeAnvilRememberTicks")) {
                    cfg.placeAnvilRememberTicks = object.get("placeAnvilRememberTicks").getAsInt();
                }

                if (object.has("igniteCreeperRememberTicks")) {
                    cfg.igniteCreeperRememberTicks = object.get("igniteCreeperRememberTicks").getAsInt();
                }
            } catch (JsonSyntaxException e) {
                ISeeWhatYouDidThere.LOGGER.error("Config is not valid JSON");
            } catch (IOException e) {
                ISeeWhatYouDidThere.LOGGER.error("Failed to load config", e);
            }
        } else if (file.getParentFile().isDirectory() || file.getParentFile().mkdirs()) {
            save();
        } else {
            ISeeWhatYouDidThere.LOGGER.error("Failed to load or create config");
        }
    }

    public static void save() {
        try (FileWriter fileWriter = new FileWriter(file)) {
            JsonObject object = new JsonObject();
            object.addProperty("fireRememberTicks", cfg.fireRememberTicks);
            object.addProperty("fluidRememberTicks", cfg.fluidRememberTicks);
            object.addProperty("pushRememberTicks", cfg.pushRememberTicks);
            object.addProperty("placeAnvilRememberTicks", cfg.placeAnvilRememberTicks);
            object.addProperty("igniteCreeperRememberTicks", cfg.igniteCreeperRememberTicks);

            fileWriter.write(GSON.toJson(object));
        } catch (IOException e) {
            ISeeWhatYouDidThere.LOGGER.error("Failed to save config", e);
        }
    }
}
