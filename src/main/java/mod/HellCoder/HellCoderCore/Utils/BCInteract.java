package mod.HellCoder.HellCoderCore.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import buildcraft.api.core.Position;
import buildcraft.api.transport.IPipeTile;
import buildcraft.api.transport.IPipeTile.PipeType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BCInteract
{
	public static final Random RANDOM = new Random();
	
	public static int addToPipe(World worldObj, int xCoord, int yCoord, int zCoord, ForgeDirection from, ItemStack stackInSlot) {
		return addToRandomPipeAround(worldObj, xCoord, yCoord, zCoord, from, stackInSlot);
	}

	public static boolean isHoldingPipe(EntityPlayer player) {
        return player.getCurrentEquippedItem() != null && (player.getCurrentEquippedItem().getItem() instanceof IItemPipe);
	}
	
	public static int addToRandomPipeAround(World world, int x, int y, int z, ForgeDirection from, ItemStack stack) {
		List<IPipeTile> possiblePipes = new ArrayList<IPipeTile>();
		List<ForgeDirection> pipeDirections = new ArrayList<ForgeDirection>();

		for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
			if (from.getOpposite() == side) {
				continue;
			}

			Position pos = new Position(x, y, z, side);

			pos.moveForwards(1.0);

			TileEntity tile = world.getTileEntity((int) pos.x, (int) pos.y, (int) pos.z);

			if (tile instanceof IPipeTile) {
				IPipeTile pipe = (IPipeTile) tile;
				if (pipe.getPipeType() != PipeType.ITEM) {
					continue;
				}
				if (!pipe.isPipeConnected(side.getOpposite())) {
					continue;
				}

				possiblePipes.add(pipe);
				pipeDirections.add(side.getOpposite());
			}
		}

		if (possiblePipes.size() > 0) {
			int choice = RANDOM.nextInt(possiblePipes.size());

			IPipeTile pipeEntry = possiblePipes.get(choice);

			return pipeEntry.injectItem(stack, true, pipeDirections.get(choice));
		}
		return 0;
	}

	public static TileEntity getTile(World world, Position pos, ForgeDirection step) {
		Position tmp = new Position(pos);
		tmp.orientation = step;
		tmp.moveForwards(1.0);

		return world.getTileEntity((int) tmp.x, (int) tmp.y, (int) tmp.z);
	}
}
