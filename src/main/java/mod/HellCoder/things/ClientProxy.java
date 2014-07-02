package mod.HellCoder.things;

import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import mod.HellCoder.mainmenu.client.MenuClientTickHandler;
import mod.HellCoder.things.Blocks.render.BlockCustomDigaOreRender;
import mod.HellCoder.things.Blocks.render.BlockMethanePipeRender;
import mod.HellCoder.things.Blocks.render.DigaBlockRender;
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
		FMLCommonHandler.instance().bus().register(new MenuClientTickHandler());
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
		   
		  RegBlocks.DigaBlockRI = RenderingRegistry.getNextAvailableRenderId();
		   registerBlockRenderer(new DigaBlockRender());
		    
//		   RenderingRegistry.registerBlockHandler(RegBlocks.tubeRenderID, new BlockMethanePipeRender());
		   
		   RegBlocks.tubeRenderID = RenderingRegistry.getNextAvailableRenderId();
		   registerBlockRenderer(new BlockMethanePipeRender());
	  }
	
	  public void registerBlockRenderer(ISimpleBlockRenderingHandler renderer)
	  {
	    RenderingRegistry.registerBlockHandler(renderer);
	  }
	  
	  @Override
	    public void initRenderingAndTextures()
	    {

	    }
}
