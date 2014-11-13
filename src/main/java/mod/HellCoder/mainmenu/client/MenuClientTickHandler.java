package mod.HellCoder.mainmenu.client;
 
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mod.HellCoder.HellCoderCore.Utils.FCLog;
import mod.HellCoder.mainmenu.MenuFC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;

@SideOnly(Side.CLIENT)
public class MenuClientTickHandler
{
   @SubscribeEvent
   public void onTick(ClientTickEvent event)
   {
	   
       GuiScreen guiscreen = Minecraft.getMinecraft().currentScreen;
 
       if (((guiscreen instanceof GuiMainMenu)))
       {
	             FCLog.info("Patched GuiMainMenu.class");
         Minecraft.getMinecraft().displayGuiScreen(new MenuFC());
       }
       
       if (((guiscreen instanceof GuiIngameMenu)))
       { 
            	 FCLog.info("Patched GuiIngameMenu.class");
         Minecraft.getMinecraft().displayGuiScreen(new mod.HellCoder.mainmenu.InGameMenu.GuiIngameMenu());
       }
   }
}
