package mod.HellCoder.things.handler;

import mod.HellCoder.things.TileEntity.TileRM;
import mod.HellCoder.things.container.ContainerRecon;
import mod.HellCoder.things.gui.GuiRM;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class RMGuiHandler implements IGuiHandler {

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (!world.blockExists(x, y, z))
			return null;
		
		TileEntity tile = world.getTileEntity(x, y, z);
		
		switch (ID) {
		case 0:
			if (!(tile instanceof TileRM))
				return null;
			return new GuiRM(player.inventory, world, (TileRM) tile);

		default:
			return null;
		}
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,	int x, int y, int z) {
		if (!world.blockExists(x, y, z))
			return null;
		
		TileEntity tile = world.getTileEntity(x, y, z);
		
		switch (ID) {
		case 0:
			if (!(tile instanceof TileRM))
				return null;
			return new ContainerRecon(player.inventory, (TileRM) tile);
			
		default:
			return null;
		}

	}

}
