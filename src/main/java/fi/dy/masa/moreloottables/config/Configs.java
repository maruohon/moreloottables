package fi.dy.masa.moreloottables.config;

import java.io.File;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import fi.dy.masa.moreloottables.Reference;

public class Configs
{
    public static boolean enableDragon;
    public static boolean enableHusk;
    public static boolean enableWither;

    public static File configurationFile;
    public static Configuration config;
    
    public static final String CATEGORY_GENERIC = "Generic";

    @SubscribeEvent
    public void onConfigChangedEvent(OnConfigChangedEvent event)
    {
        if (Reference.MOD_ID.equals(event.getModID()) == true)
        {
            loadConfigs(config);
        }
    }

    public static void loadConfigsFromFile(File configFile)
    {
        configurationFile = configFile;
        config = new Configuration(configFile, null, false);
        config.load();

        loadConfigs(config);
    }

    public static void loadConfigs(Configuration conf)
    {
        Property prop;

        prop = conf.get(CATEGORY_GENERIC, "enableDragon", true).setRequiresMcRestart(false);
        prop.setComment("Enable a Loot Table for the Ender Dragon.");
        enableDragon = prop.getBoolean();

        prop = conf.get(CATEGORY_GENERIC, "enableHusk", true).setRequiresMcRestart(false);
        prop.setComment("Enable a separate Loot Table for the Husk variant of zombies.");
        enableHusk = prop.getBoolean();

        prop = conf.get(CATEGORY_GENERIC, "enableWither", true).setRequiresMcRestart(false);
        prop.setComment("Enable a Loot Table for the Wither.");
        enableWither = prop.getBoolean();

        if (conf.hasChanged() == true)
        {
            conf.save();
        }
    }
}
