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
    
    public static boolean digaOreworldgen;

    public static void init(File configFile)
    {
        // Create the configuration object from the given configuration file
        if (configuration == null)
        {
            configuration = new Configuration(configFile);
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

    public void loadConfiguration()
    {
    	digaOreworldgen = configuration.getBoolean("configValue", Configuration.CATEGORY_GENERAL, false, "Add diga ore to WorldGen?");

        if (configuration.hasChanged())
        {
            configuration.save();
        }
    }
}
