package mod.HellCoder.things.core;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mod.HellCoder.things.FriendsCraft2mod;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;


public class FriendsCraftTicker
{
    private Minecraft     mcClient;
    public static boolean isRegistered     = false;
    
    public FriendsCraftTicker()
    {
        if (!isRegistered)
        {
            mcClient = FMLClientHandler.instance().getClient();
            FMLCommonHandler.instance().bus().register(this);
            isRegistered = true;
        }
    }
    
    @SubscribeEvent
    public void onTick(ClientTickEvent event)
    {
        if (!event.phase.equals(Phase.START))
        {
            boolean keepTicking = !(mcClient != null && mcClient.thePlayer != null && mcClient.theWorld != null);
            
            if (!keepTicking && isRegistered)
            {
                    if (!FriendsCraft2mod.instance.versionChecker.isCurrentVersion())
                        for (String msg : FriendsCraft2mod.instance.versionChecker.getInGameMessage())
                            mcClient.thePlayer.addChatMessage(new ChatComponentText(msg));
 
                FMLCommonHandler.instance().bus().unregister(this);
                isRegistered = false;
            }
        }
    }
    
    public static boolean isRegistered()
    {
        return isRegistered;
    }

}
