package mod.HellCoder.things;

import mod.HellCoder.things.Blocks.BlockCustomDigaOreRender;
import mod.HellCoder.things.lib.RegBlocks;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;

public class ClientProxy extends CommonProxy
{

	@Override
	protected void registerTickHandler()
    {
		FMLCommonHandler.instance().bus().register(new FriendsCraftTicker());
    }
	
	@Override
	 public int particleCount(int base)
	  {
	    if (FMLClientHandler.instance().getClient().gameSettings.particleSetting == 2) return 0;
	    return FMLClientHandler.instance().getClient().gameSettings.particleSetting == 1 ? base * 1 : base * 2;
	  }
	@Override
	  public void registerDisplayInformation()
	  {
		  RegBlocks.blockCustomDigaOreRI = RenderingRegistry.getNextAvailableRenderId();
		    registerBlockRenderer(new BlockCustomDigaOreRender());
	  }
	
	  public void registerBlockRenderer(ISimpleBlockRenderingHandler renderer)
	  {
	    RenderingRegistry.registerBlockHandler(renderer);
	  }
}
