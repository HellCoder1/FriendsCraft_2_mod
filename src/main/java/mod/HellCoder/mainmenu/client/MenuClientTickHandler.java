package mod.HellCoder.mainmenu.client;
 
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;

import java.io.PrintStream;import java.util.EnumSet;

import mod.HellCoder.HellCoderCore.Utils.FCLog;
import mod.HellCoder.mainmenu.MenuBaseConfig;
import mod.HellCoder.mainmenu.MenuBaseFC;
import mod.HellCoder.mainmenu.MenuCommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import paulscode.sound.SoundSystem;
 
public class MenuClientTickHandler
{
	
 private Minecraft mcClient;
 private static boolean isRegistered = false;
	
   public String getLabel()
   {
     return null;
   }
 
   @SubscribeEvent
   public void onTick(ClientTickEvent event)
   {
	   if (Minecraft.getMinecraft().isIntegratedServerRunning())
	     {
	       MenuBaseConfig.ticks += 1;
	 
	       if (Minecraft.getMinecraft().thePlayer != null)
	       {
	         MenuBaseConfig.playerPosX = Minecraft.getMinecraft().thePlayer.posX;
	         MenuBaseConfig.playerPosY = Minecraft.getMinecraft().thePlayer.posY;
	         MenuBaseConfig.playerPosZ = Minecraft.getMinecraft().thePlayer.posZ;
	       }
	     }
	   
       GuiScreen guiscreen = Minecraft.getMinecraft().currentScreen;
       MenuBaseFC minecraftMenu = new MenuBaseFC();
 
       if (((guiscreen instanceof GuiMainMenu)) && (guiscreen.getClass() != minecraftMenu.getClass()))
       {
	             FCLog.info("Patched GuiMainMenu.class");
         Minecraft.getMinecraft().displayGuiScreen(new MenuBaseFC());
       }
               if (((guiscreen instanceof GuiIngameMenu)) && (guiscreen.getClass() != minecraftMenu.getClass()))
       { 
            	 FCLog.info("Patched GuiIngameMenu.class");
         Minecraft.getMinecraft().displayGuiScreen(new mod.HellCoder.mainmenu.InGameMenu.GuiIngameMenu());
       } 
   }

 public static boolean isRegistered()
 {
     return isRegistered;
 }
}
