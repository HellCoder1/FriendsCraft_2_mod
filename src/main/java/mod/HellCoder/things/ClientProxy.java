package mod.HellCoder.things;

import cpw.mods.fml.common.FMLCommonHandler;

public class ClientProxy extends CommonProxy
{

	@Override
	protected void registerTickHandler()
    {
		FMLCommonHandler.instance().bus().register(new FriendsCraftTicker());
    }
}
