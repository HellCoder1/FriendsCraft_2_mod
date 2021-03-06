package mod.HellCoder.HellCoderCore.Utils;

import mod.HellCoder.HellCoderCore.Utils.InventoryIterator.IInvSlot;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Utils {
	public static final Random rng = new Random();
	private static final List<ForgeDirection> directions = new ArrayList<ForgeDirection>(Arrays.asList(ForgeDirection.VALID_DIRECTIONS));

	public static final boolean isServerWorld(World world) {

		return !world.isRemote;
	}
	
	public static boolean dropItemStackIntoWorld(ItemStack stack, World world, double x, double y, double z) {

		return dropItemStackIntoWorld(stack, world, x, y, z, false);
	}

	public static boolean dropItemStackIntoWorldWithVelocity(ItemStack stack, World world, double x, double y, double z) {

		return dropItemStackIntoWorld(stack, world, x, y, z, true);
	}

	public static boolean dropItemStackIntoWorld(ItemStack stack, World world, double x, double y, double z, boolean velocity) {

		if (stack == null) {
			return false;
		}
		float x2 = 0.5F;
		float y2 = 0.0F;
		float z2 = 0.5F;

		if (velocity) {
			x2 = world.rand.nextFloat() * 0.8F + 0.1F;
			y2 = world.rand.nextFloat() * 0.8F + 0.1F;
			z2 = world.rand.nextFloat() * 0.8F + 0.1F;
		}
		EntityItem entity = new EntityItem(world, x + x2, y + y2, z + z2, stack.copy());

		if (velocity) {
			entity.motionX = (float) world.rand.nextGaussian() * 0.05F;
			entity.motionY = (float) world.rand.nextGaussian() * 0.05F + 0.2F;
			entity.motionZ = (float) world.rand.nextGaussian() * 0.05F;
		} else {
			entity.motionY = -0.05F;
			entity.motionX = 0;
			entity.motionZ = 0;
		}
		world.spawnEntityInWorld(entity);

		return true;
	}

	private static ItemStack add(IInventory inventory, ItemStack stack, ForgeDirection side, boolean simulate) {
		ItemStack added = stack.copy();
		added.stackSize = inject(inventory, stack, side, simulate);
		return added;
	}

	private static int inject(IInventory inventory, ItemStack stack, ForgeDirection side, boolean simulate) {
		List<IInvSlot> filledSlots = new ArrayList<IInvSlot>(inventory.getSizeInventory());
		List<IInvSlot> emptySlots = new ArrayList<IInvSlot>(inventory.getSizeInventory());
		for (IInvSlot slot : InventoryIterator.getIterable(inventory, side)) {
			if (slot.canPutStackInSlot(stack)) {
				if (slot.getStackInSlot() == null) {
					emptySlots.add(slot);
				} else {
					filledSlots.add(slot);
				}
			}
		}

		int injected = 0;
		injected = tryPut(inventory, stack, filledSlots, injected, simulate);
		injected = tryPut(inventory, stack, emptySlots, injected, simulate);

		inventory.markDirty();
		return injected;
	}

	private static int tryPut(IInventory inventory, ItemStack stack, List<IInvSlot> slots, int injected, boolean simulate) {
		if (injected >= stack.stackSize) {
			return injected;
		}
		for (IInvSlot slot : slots) {
			ItemStack stackInSlot = slot.getStackInSlot();
			if (stackInSlot == null || StackHelper.instance().canStacksMerge(stackInSlot, stack)) {
				int used = addToSlot(inventory, slot, stack, injected, simulate);
				if (used > 0) {
					injected += used;
					if (injected >= stack.stackSize) {
						return injected;
					}
				}
			}
		}
		return injected;
	}

	private static int addToSlot(IInventory inventory, IInvSlot slot, ItemStack stack, int injected, boolean simulate) {
		int available = stack.stackSize - injected;
		int max = Math.min(stack.getMaxStackSize(), inventory.getInventoryStackLimit());

		ItemStack stackInSlot = slot.getStackInSlot();
		if (stackInSlot == null) {
			int wanted = Math.min(available, max);
			if (!simulate) {
				stackInSlot = stack.copy();
				stackInSlot.stackSize = wanted;
				slot.setStackInSlot(stackInSlot);
			}
			return wanted;
		}
		if (!StackHelper.instance().canStacksMerge(stack, stackInSlot)) {
			return 0;
		}
		int wanted = max - stackInSlot.stackSize;
		if (wanted <= 0) {
			return 0;
		}
		if (wanted > available) {
			wanted = available;
		}
		if (!simulate) {
			stackInSlot.stackSize += wanted;
			slot.setStackInSlot(stackInSlot);
		}
		return wanted;
	}
}
