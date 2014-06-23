package mod.HellCoder.things.core;

import mod.HellCoder.HellCoderCore.Utils.ModVersionChecker;
import mod.HellCoder.things.FriendsCraft2mod;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends CommonProxy
{

	@Override
	public void init(FMLInitializationEvent event)
    {
		FMLCommonHandler.instance().bus().register(new FriendsCraftTicker());
        
        FMLCommonHandler.instance().bus().register(this);
        
		if(FriendsCraft2mod.instance.allowUpdateCheck){
		FriendsCraft2mod.instance.versionChecker = new ModVersionChecker(FriendsCraft2mod.instance.metadata.name, FriendsCraft2mod.instance.metadata.version, FriendsCraft2mod.instance.versionURL, FriendsCraft2mod.instance.mcfTopic);
		FriendsCraft2mod.instance.versionChecker.checkVersionWithLogging();
		
		}
    }
}
