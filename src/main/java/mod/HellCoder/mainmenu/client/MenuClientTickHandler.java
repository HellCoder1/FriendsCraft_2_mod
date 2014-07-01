package mod.HellCoder.mainmenu.client;
 
import cpw.mods.fml.client.GuiModList;
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
 private GuiScreen mainMenu;
 private static boolean isRegistered = false;
	
   public String getLabel()
   {
     return null;
   }
 
   @SubscribeEvent
   public void onTick(ClientTickEvent event)
   {
	   
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
         Minecraft.getMinecraft().displayGuiScreen(new mod.HellCoder.mainmenu.ingamemenu.GuiIngameMenu());
       }
       if (((guiscreen instanceof GuiModList)) && (guiscreen.getClass() != minecraftMenu.getClass()))
       { 
            	 FCLog.info("Patched GuiModList.class");
         Minecraft.getMinecraft().displayGuiScreen(new mod.HellCoder.mainmenu.GuiModList(mainMenu));
       } 
   }

 public static boolean isRegistered()
 {
     return isRegistered;
 }
}
