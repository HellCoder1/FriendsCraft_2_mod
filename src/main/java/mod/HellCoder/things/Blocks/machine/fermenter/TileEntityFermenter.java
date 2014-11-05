package mod.HellCoder.things.Blocks.machine.fermenter;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mod.HellCoder.HellCoderCore.Utils.Utils;
import net.minecraft.entity.player.EntityPlayer;
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

public class TileEntityFermenter extends TileEntity implements ISidedInventory, IFluidHandler, IEnergyHandler
{

	public int heat = 0;
	final float cookingPressure = 100f;
	int maxheat = 300;
	public int useHeatPerUse = 0;

	static final float powerConst = 1.4f;
	static final float lossConst = 0.00005f ;
	static final float coolrate = 0.25f;
	
	private static final int[] slotsTop = new int[] {0};
    private static final int[] slotsBottom = new int[] {1};
    private static final int[] slotsSides = new int[] {0,1};
    
    public static FluidTank tank = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME * 20);
    
    private static int maxCapacity = 10000;
    private static int POWER_USAGE = 25;
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

    }

    @Override
    public World getWorldObj() {
        return super.getWorldObj();
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
        return this.hasCustomInventoryName() ? this.displayName : "Rolling Machine";
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
    	super.getDescriptionPacket();
        NBTTagCompound access = new NBTTagCompound();
        access.setInteger("methane",tank.getFluidAmount());
        access.setShort("Heat", (short)this.heat);
        access.setShort("CookTime", (short)this.cookTime);

        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, access);
	}
	    

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
    	super.onDataPacket(net, pkt);
    	NBTTagCompound access = pkt.func_148857_g();
    	this.tank.setFluid(new FluidStack(FluidRegistry.getFluid("methane"),access.getInteger("methane")));
    	this.heat = access.getShort("Heat");
    	this.cookTime = access.getShort("CookTime");
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
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
        
        if (par1NBTTagCompound.hasKey("methane"))
        {
        	this.tank.setFluid(new FluidStack(FluidRegistry.getFluid("methane"),par1NBTTagCompound.getShort("methane")));
        }

        if (par1NBTTagCompound.hasKey("CustomName"))
        {
            this.displayName = par1NBTTagCompound.getString("CustomName");
        }
    }
    
    /**
     * Writes a tile entity to NBT.
     */
	@Override public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("Heat", (short) this.heat);
        par1NBTTagCompound.setShort("CookTime", (short)this.cookTime);
        par1NBTTagCompound.setShort("methane", (short) this.tank.getFluidAmount());
        this.rfStored.writeToNBT(par1NBTTagCompound);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.fermenterItemStacks.length; ++i)
        {
            if (this.fermenterItemStacks[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.fermenterItemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        par1NBTTagCompound.setTag("Items", nbttaglist);

        if (this.hasCustomInventoryName())
        {
            par1NBTTagCompound.setString("CustomName", this.displayName);
        }
    }

    @Override
	public int getInventoryStackLimit()
    {
        return 64;
    }
    /** just to be sure! */
    @Override public boolean canUpdate()
    {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public int getCookProgressScaled(int par1)
    {
        return (cookTime * par1) / cookTimeDone;
    }
    
    @SideOnly(Side.CLIENT)
	public int getHeat(int par1){
    	
		return (heat * par1) / maxheat;
	}
    
    @SideOnly(Side.CLIENT)
    public int getFluidScale(int par1){
    	
    	return (tank.getFluidAmount() * par1) / 20000;
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
	        	useHeatPerUse = (int)FermenterRecipes.init().getPressureUse(this.fermenterItemStacks[0]);
	        	if (heat >= useHeatPerUse){
	        		FluidStack fluidstack = FermenterRecipes.init().getSmeltingResult(this.fermenterItemStacks[0]);
	              if (fluidstack == null) {return false;}
	              if (this.fermenterItemStacks[1] == null) return true;
	              if (!this.tank.equals(fluidstack)) return false;
	              int result = this.tank.getFluidAmount() + fluidstack.amount;
	              return (result <= FluidContainerRegistry.BUCKET_VOLUME);
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

//            if (this.tank.getFluidAmount() == 0)
//            {
//                fluidstack.copy();
//            }
//            else if (this.tank.equals(fluidstack))
//            {
//                this.tank.fill(fluidstack, true);
//            }
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
		
		if (resource == null) {
            return 0;
    }

    FluidStack resourceCopy = resource.copy();
    int totalUsed = 0;
    
    totalUsed += tank.fill(resourceCopy, doFill);
    
    return totalUsed;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		
	if (resource == null)
            return null;
    if (!resource.isFluidEqual(tank.getFluid()))
            return null;
    return drain(from, resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return tank.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return true;
	}

	private FluidTankInfo[] tankInfo = new FluidTankInfo[1];
	
	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {		
		tankInfo[0] = tank.getInfo();
        return tankInfo;
	}
}