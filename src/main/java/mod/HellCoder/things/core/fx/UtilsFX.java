package mod.HellCoder.things.core.fx;

import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.ReflectionHelper;
import mod.HellCoder.things.FriendsCraft2mod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class UtilsFX {

	  static Map<String, ResourceLocation> boundTextures = new HashMap();
	
	  public static void infusedStoneSparkle(World world, int x, int y, int z, int md) {
		    if (!world.isRemote) return;
		    int color = 0;
		    switch (md) { case 1:
		      color = 4; break;
		    }
		    for (int a = 0; a < FriendsCraft2mod.proxy.particleCount(6); a++) {
		      FXSparkle fx = new FXSparkle(world, x + world.rand.nextFloat(), y + world.rand.nextFloat(), z + world.rand.nextFloat(), 3.75F, color == -1 ? world.rand.nextInt(5) : color, 3 + world.rand.nextInt(3));

		      fx.setGravity(0.01F);
		      FMLClientHandler.instance().getClient().effectRenderer.addEffect(fx);
		    }
		  }
	  
	  public static void bindTexture(String texture)
	  {
	    ResourceLocation rl = null;
	    if (boundTextures.containsKey(texture))
	      rl = (ResourceLocation)boundTextures.get(texture);
	    else {
	      rl = new ResourceLocation("friendscraft", texture);
	    }
	    Minecraft.getMinecraft().renderEngine.bindTexture(rl);
	  }
	  
	  public static ResourceLocation getParticleTexture()
	  {
	    try
	    {
	      return (ResourceLocation)ReflectionHelper.getPrivateValue(EffectRenderer.class, null, new String[] { "particleTextures", "b", "field_110737_b" }); } catch (Exception e) {
	    }
	    return null;
	  }
	
}
