package mod.HellCoder.things.Blocks.machine.fermenter;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyStorage;
import cofh.api.tileentity.IEnergyInfo;
import cofh.core.block.TileCoFHBase;
import cofh.core.util.fluid.FluidTankAdv;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mod.HellCoder.HellCoderCore.Utils.Utils;
import mod.HellCoder.things.fluid.Tank;
import mod.HellCoder.things.fluid.TankManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

public class TileEntityFermenter extends TileCoFHBase implements ISidedInventory, IFluidHandler, IEnergyHandler
{
	public int heat = 0;
	int maxheat = 300;
	
	private static final int[] slotsTop = new int[] {0};
    private static final int[] slotsBottom = new int[] {1};
    private static final int[] slotsSides = new int[] {0,1};
    
    public Tank tank = new Tank("main", 20000, this);
    private TankManager<Tank> tankManager = new TankManager<Tank>();
    
    private static int maxCapacity = 10000;
    public EnergyStorage rfStored = new EnergyStorage(10000);

    int cookTime = 0;
    final int cookTimeDone = 100;
    final float ticksPerTemp = 0.03f;
    final int updateInterval = 4;
    private int updateCounter = Utils.rng.nextInt();
    
    private int redstoneSignal = 0;
       
    private String displayName = "";
    
    private ItemStack[] fermenterItemStacks = new ItemStack[2]; // 0=input, 1=output
    
    public TileEntityFermenter(){
    	super();
    	tankManager.add(tank);

    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        return true;
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from) {
        return this.rfStored.getMaxEnergyStored();
    }

    @Override
    public int getEnergyStored(ForgeDirection from) {
        return this.rfStored.getEnergyStored();
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
        return 0;
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
        return this.rfStored.receiveEnergy(maxReceive, simulate);
    }


	public IEnergyStorage getEnergyStorage() {
	   return this.rfStored;
	}
    
    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return this.fermenterItemStacks.length;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int par1)
    {
        return this.fermenterItemStacks[par1];
    }
    
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (this.fermenterItemStacks[par1] != null)
        {
            ItemStack itemstack;

            if (this.fermenterItemStacks[par1].stackSize <= par2)
            {
                itemstack = this.fermenterItemStacks[par1];
                this.fermenterItemStacks[par1] = null;
                return itemstack;
            }
            else
            {
                itemstack = this.fermenterItemStacks[par1].splitStack(par2);

                if (this.fermenterItemStacks[par1].stackSize == 0)
                {
                    this.fermenterItemStacks[par1] = null;
                }

                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack getStackInSlotOnClosing(int par1)
    {
        if (this.fermenterItemStacks[par1] != null)
        {
            ItemStack itemstack = this.fermenterItemStacks[par1];
            this.fermenterItemStacks[par1] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
    	   this.fermenterItemStacks[par1] = par2ItemStack;

           if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
           {
               par2ItemStack.stackSize = this.getInventoryStackLimit();
           }
    }

    /**
     * Returns the name of the inventory
     */
    public String getInventoryName()
    {
        return this.hasCustomInventoryName() ? this.displayName : "Fermenter";
    }

    /**
     * Returns if the inventory is named
     */
    public boolean hasCustomInventoryName()
    {
        return this.displayName != null && this.displayName.length() > 0;
    }

    public void func_145951_a(String p_145951_1_)
    {
        this.displayName = p_145951_1_;
    }

    @Override
	public Packet getDescriptionPacket()
	{
    	NBTTagCompound nbtData = new NBTTagCompound();
        this.writeToNBT(nbtData);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbtData);

	}
	    

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
    	NBTTagCompound nbt = pkt.func_148857_g();
    	this.readFromNBT(nbt);
    }
    
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items", 10);
        this.fermenterItemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
        	 NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.fermenterItemStacks.length)
            {
                this.fermenterItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        this.heat = par1NBTTagCompound.getShort("Heat");
        this.cookTime = par1NBTTagCompound.getShort("CookTime");
        this.rfStored.readFromNBT(par1NBTTagCompound);
        tankManager.readFromNBT(par1NBTTagCompound);
    }
    
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("Heat", (short) this.heat);
        par1NBTTagCompound.setShort("CookTime", (short)this.cookTime);
        this.rfStored.writeToNBT(par1NBTTagCompound);
        tankManager.writeToNBT(par1NBTTagCompound);
        
        NBTTagList nbttaglist = new NBTTagList();
        
        for (int i = 0; i < this.fermenterItemStacks.length; ++i){
            if (this.fermenterItemStacks[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.fermenterItemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        par1NBTTagCompound.setTag("Items", nbttaglist);
    }
	
    @Override
	public int getInventoryStackLimit()
    {
        return 64;
    }
    /** just to be sure! */
    @Override 
    public boolean canUpdate()
    {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public int getCookProgressScaled(int par1){
        return (cookTime * par1) / cookTimeDone;
    }
    
    @SideOnly(Side.CLIENT)
	public int getHeat(int par1){    	
	   return (heat * par1) / maxheat;
	}
    
    @SideOnly(Side.CLIENT)
    public int getEnergy(int par1){   	
		return ((this.rfStored.getEnergyStored() * par1) / this.rfStored.getMaxEnergyStored());		
    }

    public void updateEntity()
    {

		if (this.worldObj.isRemote){
			return;
		}

		boolean flagStateChange = false;

		redstoneSignal = getWorld().getBlockPowerInput(this.xCoord,	this.yCoord, this.zCoord);

		updateCounter++;
		if (updateCounter % updateInterval == 0) {
			
			
			boolean flag1 = false;

			float oldpressure = heat;
			if(heat < maxheat){
				updatePressure();
			}
			if(heat != oldpressure){flagStateChange = true;}

			int oldCookTime = cookTime;
			if (redstoneSignal == 0) {

				if (canSmelt()) {

					cookTime += (ticksPerTemp * heat);
					if (cookTime >= cookTimeDone) {
						this.smeltItem();
						
						flag1 = true;
						cookTime = 0;
					}
				} else {
					cookTime = 0;
				}
			} else {

			}

			if(oldCookTime != cookTime){
				flagStateChange = true;
			}

			if (flag1 ) {
				this.markDirty();
			}
			if(flagStateChange){
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
		}
    }
    
	private void updatePressure(){
		
		if(rfStored.getEnergyStored() < 0){
			rfStored.setEnergyStored(0);
		}
		
		if(rfStored.getEnergyStored() >= 200){
		rfStored.extractEnergy(200, false);
		heat = heat + 2;
		}
		
		if(heat < 0){
			heat = 0;
        }
		if(heat >= (maxheat + 1)){
			heat = maxheat;
        }
	}

	 private boolean canSmelt()
	    {
	        if (this.fermenterItemStacks[0] == null)
	        {
	            return false;
	        }
	        else
	        {   
	        	int NeedHeat = (int)FermenterRecipes.init().getHeatUse(this.fermenterItemStacks[0]);
	        	if (heat >= NeedHeat){
	        		FluidStack fluidstack = FermenterRecipes.init().getSmeltingResult(this.fermenterItemStacks[0]);
	                if (fluidstack == null) {
	            	    return false;
	                }
	                if((this.tank.getCapacity() - this.tank.getFluidAmount()) < NeedHeat){
	                	return false;
	                }
	              return true;
	        	}else{
	        		return false;
	        	}
	        }
	    }

    public void smeltItem()
    {
        if (this.canSmelt())
        {
        	FluidStack fluidstack = FermenterRecipes.init().getSmeltingResult(this.fermenterItemStacks[0]);

        	this.tank.fill(fluidstack, true);

            --this.fermenterItemStacks[0].stackSize;

            if (this.fermenterItemStacks[0].stackSize <= 0)
            {
                this.fermenterItemStacks[0] = null;
            }
        }
    }

    @Override 
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
    	return getWorld().getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }

    public void openInventory() {}

    public void closeInventory() {}
    

    public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
    {
        return ( par1 == 0 );
    }

    public int[] getAccessibleSlotsFromSide(int par1)
    {
        return par1 == 0 ? slotsBottom : (par1 == 1 ? slotsTop : slotsSides);
    }

    public boolean canInsertItem(int slot, ItemStack item, int side)
    {
        return this.isItemValidForSlot(slot, item);
    }

    public boolean canExtractItem(int slot, ItemStack item, int side)
    {
    	if(slot == 1){
    		return true;
    	}else {
    		return false;
    	}
    }

	public World getWorld() {
		return this.worldObj;
	}

    public int getFillLevel(){
    	return tank.getFluidAmount();
    }
    
    public int getMaxFill(){
    	return tank.getCapacity();
    }
    public Fluid getCurrentFluid(){
    	if(tank.getFluid() == null)return null;
    	return tank.getFluid().getFluid();
    }
	
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
    return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		FluidStack fluid = tank.drain(maxDrain, doDrain);
		return fluid;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return false;
	}
	
	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {		
		return new FluidTankInfo[]{this.tank.getInfo()};
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public String getName() {
		return this.displayName;
	}

	@Override
	public int getType() {
		return 0;
	}
}