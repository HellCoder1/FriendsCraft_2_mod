package mod.HellCoder.things;

import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import mod.HellCoder.things.Blocks.Block3D;
import mod.HellCoder.things.Blocks.Block3DModelItem;
import mod.HellCoder.things.Blocks.render.BlockCustomDigaOreRender;
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
	  }
	
	  public void registerBlockRenderer(ISimpleBlockRenderingHandler renderer)
	  {
	    RenderingRegistry.registerBlockHandler(renderer);
	  }
	  
	  @Override
	    public void initRenderingAndTextures()
	    {
		  Block3D.aludel = RenderingRegistry.getNextAvailableRenderId();
		  
		  MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RegBlocks.aludel), new Block3DModelItem());
	    }
}
