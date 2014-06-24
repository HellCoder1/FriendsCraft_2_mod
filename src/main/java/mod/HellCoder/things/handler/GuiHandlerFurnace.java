package mod.HellCoder.things.handler;

import mod.HellCoder.things.rollingmachine.ContainerRM;
import mod.HellCoder.things.rollingmachine.GuiRM;
import mod.HellCoder.things.rollingmachine.TileEntityRM;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandlerFurnace implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tile_entity = world.getTileEntity(x, y, z);
		switch(id)
		{
		case 0: return new ContainerRM(player.inventory, (TileEntityRM) tile_entity);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tile_entity = world.getTileEntity(x, y, z);
		switch(id)
		{
		case 0: return new GuiRM(player.inventory, (TileEntityRM) tile_entity);
		}
		return null;
	}
}
