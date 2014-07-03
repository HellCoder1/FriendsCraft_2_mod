package mod.HellCoder.things.handler;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import mod.HellCoder.things.FriendsCraft2mod;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigurationHandler
{
    public static Configuration configuration;
    
    public static boolean copperOreworldgen;

    public static void init(File configFile)
    {
        // Create the configuration object from the given configuration file
        if (configuration == null)
        {
            configuration = new Configuration(configFile);
            loadConfiguration();
        }
    }

    private static void loadConfiguration()
    {
    	copperOreworldgen = configuration.getBoolean("Copper Ore world gen", Configuration.CATEGORY_GENERAL, false, "Add Copper Ore to world gen?");

        if (configuration.hasChanged())
        {
            configuration.save();
        }
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.modID.equalsIgnoreCase(FriendsCraft2mod.MOD_ID))
        {
            loadConfiguration();
        }
    }
}
