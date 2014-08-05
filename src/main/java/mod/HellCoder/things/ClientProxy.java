package mod.HellCoder.things;

import cofh.render.IconRegistry;
import cofh.util.StringHelper;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fluids.Fluid;
import mod.HellCoder.mainmenu.client.MenuClientTickHandler;
import mod.HellCoder.things.Blocks.render.BlockCustomDigaOreRender;
import mod.HellCoder.things.Blocks.render.BlockHermeticPipeRender;
import mod.HellCoder.things.Blocks.render.DigaBlockRender;
import mod.HellCoder.things.Blocks.render.TestBlock3DRender;
import mod.HellCoder.things.TileEntity.TileEntityTest3DBlock;
import mod.HellCoder.things.fluid.FCFluids;
import mod.HellCoder.things.lib.RegBlocks;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;

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
		   registerBlockRenderer(new BlockHermeticPipeRender());
	  }
	
	  @Override
	  public void registerRenderThings() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTest3DBlock.class, new TestBlock3DRender());
		GameRegistry.registerTileEntity(TileEntityTest3DBlock.class, "3Dblock");
      }
	
	  public void registerBlockRenderer(ISimpleBlockRenderingHandler renderer)
	  {
	    RenderingRegistry.registerBlockHandler(renderer);
	  }
	  
	  @Override
	    public void initRenderingAndTextures()
	    {

	    }
	  
		@Override
		@SubscribeEvent
		public void registerIcons(TextureStitchEvent.Pre event) {

			if (event.map.getTextureType() == 0) {
				registerFluidIcons(FCFluids.methane, event.map);
			}
		}

		@Override
		@SubscribeEvent
		public void initializeIcons(TextureStitchEvent.Post event) {

			setFluidIcons(FCFluids.methane);
		}
		
		public static void registerFluidIcons(Fluid fluid, IIconRegister ir) {

			String name = StringHelper.titleCase(fluid.getName());
			IconRegistry.addIcon("Fluid" + name, "friendscraft:fluid/" + name + "_Still", ir);
			IconRegistry.addIcon("Fluid" + name + 1, "friendscraft:fluid/" + name + "_Flow", ir);
		}

		public static void setFluidIcons(Fluid fluid) {

			String name = StringHelper.titleCase(fluid.getName());
			fluid.setIcons(IconRegistry.getIcon("Fluid" + name), IconRegistry.getIcon("Fluid" + name, 1));
		}
}
