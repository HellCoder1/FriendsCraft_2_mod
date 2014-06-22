package mod.HellCoder.things.TileEntity;

import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import mod.HellCoder.HellCoderCore.Utils.BCInteract;
import mod.HellCoder.HellCoderCore.Utils.BasicInventory;
import mod.HellCoder.HellCoderCore.Utils.Utils;
import mod.HellCoder.things.FriendsCraft2mod;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileRM extends TileEntity implements IPowerReceptor, IInventory {
	public PowerHandler powerHandler;
	private final BasicInventory inv;
	public static final int MAX_ENERGY = 1500;

	public TileRM(){
		powerHandler = new PowerHandler(this, PowerHandler.Type.MACHINE);
		initPowerProvider();
		
		inv = new BasicInventory(1,"Processing",1);
	}

	private void initPowerProvider() {
		powerHandler.configure((FriendsCraft2mod.energyPerPoint * 2), (FriendsCraft2mod.energyPerPoint * 20), FriendsCraft2mod.energyPerPoint, MAX_ENERGY);
		powerHandler.configurePowerPerdition(1, 1);
	}

	@Override
	public PowerReceiver getPowerReceiver(ForgeDirection side) {
		return powerHandler.getPowerReceiver();
	}

	@Override
	public void updateEntity() {
		getPowerReceiver(null).update();
	}


    @Override
	public void doWork(PowerHandler workProvider) {
		if (getStackInSlot(0) == null)
			return;
			
		if (!getStackInSlot(0).isItemDamaged() || !getStackInSlot(0).getItem().isRepairable()) {
			ejectItem();
			return;
		}
		double powerUsed = powerHandler.useEnergy(FriendsCraft2mod.energyPerPoint, (FriendsCraft2mod.energyPerPoint * 10), true);
		if (powerUsed < (double) FriendsCraft2mod.energyPerPoint){
			return;		
		}
		int iterations = (int)(powerUsed / FriendsCraft2mod.energyPerPoint);
		int iteration = 0;
		while (getStackInSlot(0).isItemDamaged() && iteration < iterations) {
			getStackInSlot(0).damageItem(-1, new GremlinEntity(this.worldObj));
			iteration++;
		}
	}

	@Override
	public World getWorld() {
		return worldObj;
	}

	private void ejectItem() {
		if (FriendsCraft2mod.instance.doPipeInteract) {
			if (BCInteract.addToPipe(worldObj, xCoord, yCoord, zCoord, ForgeDirection.UNKNOWN, getStackInSlot(0)) > 0) {
				decrStackSize(0, 1);
				return;
			}
		}

		if (Utils.addToRandomInventory(worldObj, xCoord, yCoord, zCoord, getStackInSlot(0)) > 0) {
			decrStackSize(0, 1);
			return;
		}
		
		float f = this.worldObj.rand.nextFloat() * 0.8F + 0.1F;
		float f1 = this.worldObj.rand.nextFloat() * 0.8F + 0.1F;
		float f2 = this.worldObj.rand.nextFloat() * 0.8F + 0.1F;

		EntityItem entityitem = new EntityItem(this.worldObj, xCoord + f, yCoord + f1 + 0.5F, zCoord + f2, getStackInSlot(0));

		float f3 = 0.05F;
		entityitem.motionX = (float) this.worldObj.rand.nextGaussian() * f3;
		entityitem.motionY = (float) this.worldObj.rand.nextGaussian() * f3 + 1.0F;
		entityitem.motionZ = (float) this.worldObj.rand.nextGaussian() * f3;
		this.worldObj.spawnEntityInWorld(entityitem);
		
		decrStackSize(0, 1);
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public int getSizeInventory() {
		return inv.getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return inv.getStackInSlot(slot);
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		return inv.decrStackSize(slot, amount);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		return inv.getStackInSlotOnClosing(slot);
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		inv.setInventorySlotContents(slot, itemstack);
	}

	@Override
	public String getInventoryName() {
		return "Reconstructor";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return itemstack.getItem().isDamageable();
	}

	public boolean onBlockActivated(EntityPlayer player, ForgeDirection orientation) {
		if (!worldObj.isRemote) {
			player.openGui(FriendsCraft2mod.instance, 0, worldObj, xCoord, yCoord, zCoord);
		}
		return true;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);
		inv.readFromNBT(data);
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);
		inv.writeToNBT(data);
	}
}
