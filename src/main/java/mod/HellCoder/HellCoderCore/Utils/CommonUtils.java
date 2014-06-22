package mod.HellCoder.HellCoderCore.Utils;

import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;


public final class CommonUtils
{
 
    public static String stringArrayToString(String[] sa)
    {
        return stringArrayToString(sa, "#");
    }
    
    public static String stringArrayToString(String[] sa, String separator)
    {
        String ret = "";
        for (String s : sa)
            ret += separator + " " + s;
        
        return ret.replaceFirst(separator + " ", "");
    }
    
    public static String[] loadTextFromURL(URL url, Logger logger)
    {
        return loadTextFromURL(url, logger, new String[] { "" }, 0);
    }
    
    public static String[] loadTextFromURL(URL url, Logger logger, int timeoutMS)
    {
        return loadTextFromURL(url, logger, new String[] { "" }, timeoutMS);
    }
    
    public static String[] loadTextFromURL(URL url, Logger logger, String defaultValue)
    {
        return loadTextFromURL(url, logger, new String[] { defaultValue }, 0);
    }
    
    public static String[] loadTextFromURL(URL url, Logger logger, String defaultValue, int timeoutMS)
    {
        return loadTextFromURL(url, logger, new String[] { defaultValue }, timeoutMS);
    }
    
    public static String[] loadTextFromURL(URL url, Logger logger, String[] defaultValue)
    {
        return loadTextFromURL(url, logger, defaultValue, 0);
    }
    
    public static String[] loadTextFromURL(URL url, Logger logger, String[] defaultValue, int timeoutMS)
    {
        List<String> arraylist = new ArrayList<String>();
        Scanner scanner = null;
        try
        {
            URLConnection uc = url.openConnection();
            uc.setReadTimeout(timeoutMS);
            uc.setConnectTimeout(timeoutMS);
            scanner = new Scanner(uc.getInputStream(), "UTF-8");
        }
        catch (Throwable e)
        {
            logger.log(Level.WARN, String.format("Error retrieving remote string value at URL %s! Defaulting to %s", url.toString(), stringArrayToString(defaultValue)));
            return defaultValue;
        }
        
        while (scanner.hasNextLine())
        {
            arraylist.add(scanner.nextLine());
        }
        scanner.close();
        return arraylist.toArray(new String[arraylist.size()]);
    }
    
    public static String getMinecraftDir()
    {
        try
        {
            return Minecraft.getMinecraft().mcDataDir.getAbsolutePath();
        }
        catch (NoClassDefFoundError e)
        {
            return MinecraftServer.getServer().getFile("").getAbsolutePath();
        }
    }
    
    public static String getConfigDir()
    {
        File configDir = new File(getMinecraftDir(), "config");
        return configDir.getAbsolutePath();
    }

}