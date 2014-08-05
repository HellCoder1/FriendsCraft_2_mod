package mod.HellCoder.things;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.event.TextureStitchEvent.Post;
import net.minecraftforge.client.event.TextureStitchEvent.Pre;

public class CommonProxy {

	protected void registerTickHandler() 
	{}
	
	public int particleCount(int base) {
	    return 0;
	  }

	public void registerDisplayInformation()
	  {
	  }

	public void initRenderingAndTextures() {
		
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void registerIcons(TextureStitchEvent.Pre event) {

	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void initializeIcons(TextureStitchEvent.Post event) {

	}

	public void registerRenderThings() {
		
	}
}
