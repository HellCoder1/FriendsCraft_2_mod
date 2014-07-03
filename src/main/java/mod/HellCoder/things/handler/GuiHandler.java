package mod.HellCoder.things.handler;

import mod.HellCoder.things.Blocks.machine.fermenter.ContainerFermenter;
import mod.HellCoder.things.Blocks.machine.fermenter.GuiFermenter;
import mod.HellCoder.things.Blocks.machine.fermenter.TileEntityFermenter;
import mod.HellCoder.things.Blocks.machine.rollingmachine.ContainerRM;
import mod.HellCoder.things.Blocks.machine.rollingmachine.GuiRM;
import mod.HellCoder.things.Blocks.machine.rollingmachine.TileEntityRM;
import mod.HellCoder.things.gui.client.GuiFactory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tile_entity = world.getTileEntity(x, y, z);
		switch(id)
		{
		case 0: return new ContainerRM(player.inventory, (TileEntityRM) tile_entity);
		case 1: return new ContainerFermenter(player.inventory, (TileEntityFermenter) tile_entity);
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
		case 1: return new GuiFermenter(player.inventory, (TileEntityFermenter) tile_entity);
		}
		return null;
	}
}
