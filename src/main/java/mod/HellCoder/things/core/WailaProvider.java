package mod.HellCoder.things.core;

import java.util.List;

import cpw.mods.fml.common.Optional.InterfaceList;
import cpw.mods.fml.common.Optional.Method;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import mod.HellCoder.things.Blocks.machine.rollingmachine.TileEntityRM;
import net.minecraft.item.ItemStack;

@cpw.mods.fml.common.Optional.InterfaceList({@cpw.mods.fml.common.Optional.Interface(iface = "mcp.mobius.waila.api.IWailaDataProvider", modid = "Waila")})
public class WailaProvider implements IWailaDataProvider {

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return null;
	}
 
	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return currenttip;
	}
 
	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		if(accessor.getTileEntity() instanceof TileEntityRM)
			currenttip.add("Pressure: " + String.valueOf( ((TileEntityRM)accessor.getTileEntity()).mjStored));
		return currenttip;
	}
 
	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return currenttip;
	}
 
	public static void callbackRegister(IWailaRegistrar registrar){
	}
	
}
