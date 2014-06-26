package mod.HellCoder.things.rollingmachine;

import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import buildcraft.api.power.PowerHandler.Type;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mod.HellCoder.HellCoderCore.Utils.Utils;
import mod.HellCoder.things.FriendsCraft2mod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityRM extends TileEntity implements  IPowerReceptor, ISidedInventory
{
	static final float maxPowerUsage = 8;
	static final float minPowerUsage = 2;

	int pressure = 0;
	int pressuremax = 500;

	PowerHandler powerHandler;
	private PowerHandler powerHandlerDummy;

	static final float powerConst = 1.4f;
	static final float lossConst = 0.00005f ;
	static final float coolrate = 0.25f;
	
	private static final int[] slotsTop = new int[] {0};
    private static final int[] slotsBottom = new int[] {1};
    private static final int[] slotsSides = new int[] {0,1};
    
    /** The number of ticks that the current item has been cooking for */
    int energy = 100;
    int maxenergy = 500;
    int cookTime = 0;
    final int cookTimeDone = 100;
    final float cookingPressure = 150f;
    final float ticksPerTemp = 0.03f;
    final int updateInterval = 4;
    private int updateCounter = Utils.rng.nextInt();
    
    private int redstoneSignal = 0;
    
    
    private String displayName = "";
    /**
     * The ItemStacks that hold the items currently being used in the furnace
     */
    private ItemStack[] furnaceItemStacks = new ItemStack[2]; // 0=input, 1=output
    
    public TileEntityRM(){
    	powerHandler = new PowerHandler(this, Type.MACHINE);
    	powerHandler.configure(minPowerUsage, maxPowerUsage, minPowerUsage, maxPowerUsage*20);
    	powerHandler.configurePowerPerdition(FriendsCraft2mod.DEFAULT_PERDITION_DRAIN, FriendsCraft2mod.DEFAULT_PERDITION_INTERVAL);
    	powerHandlerDummy = new PowerHandler(this, Type.MACHINE);
    	powerHandlerDummy.configure(0, 0, 0, 0);
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return this.furnaceItemStacks.length;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int par1)
    {
        return this.furnaceItemStacks[par1];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (this.furnaceItemStacks[par1] != null)
        {
            ItemStack itemstack;

            if (this.furnaceItemStacks[par1].stackSize <= par2)
            {
                itemstack = this.furnaceItemStacks[par1];
                this.furnaceItemStacks[par1] = null;
                return itemstack;
            }
            else
            {
                itemstack = this.furnaceItemStacks[par1].splitStack(par2);

                if (this.furnaceItemStacks[par1].stackSize == 0)
                {
                    this.furnaceItemStacks[par1] = null;
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
        if (this.furnaceItemStacks[par1] != null)
        {
            ItemStack itemstack = this.furnaceItemStacks[par1];
            this.furnaceItemStacks[par1] = null;
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
    	   this.furnaceItemStacks[par1] = par2ItemStack;

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

    public void readFromNBT(NBTTagCompound p_145839_1_)
    {
        super.readFromNBT(p_145839_1_);
        NBTTagList nbttaglist = p_145839_1_.getTagList("Items", 10);
        this.furnaceItemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
        	 NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.furnaceItemStacks.length)
            {
                this.furnaceItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        this.pressure = p_145839_1_.getShort("Pressure");
        this.cookTime = p_145839_1_.getShort("CookTime");
        this.powerHandler.setEnergy(p_145839_1_.getFloat("EnergyBuffer"));

        if (p_145839_1_.hasKey("CustomName"))
        {
            this.displayName = p_145839_1_.getString("CustomName");
        }
    }
    
    public void setEnergy(int par1){
    	
     energy = par1;
     
    }
    
    /**
     * Writes a tile entity to NBT.
     */
	@Override public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setFloat("Pressure", this.pressure);
        par1NBTTagCompound.setShort("CookTime", (short)this.cookTime);
        par1NBTTagCompound.setFloat("EnergyBuffer", (float) this.powerHandler.getEnergyStored());
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.furnaceItemStacks.length; ++i)
        {
            if (this.furnaceItemStacks[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.furnaceItemStacks[i].writeToNBT(nbttagcompound1);
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


    public void updateEntity()
    {
		// BuildCraft boilerplate
		if (this.worldObj.isRemote){
			// client world, do nothing (need to do everything server side to keep everything synchronized
			return;
		}
		// end BuildCraft boilerplate

		boolean flagStateChange = false;

		redstoneSignal = getWorld().getBlockPowerInput(this.xCoord,	this.yCoord, this.zCoord);


		powerHandler.update();

		updateCounter++;
		if (updateCounter % updateInterval == 0) {


			boolean flag1 = false;
			boolean wasCooking = (pressure > cookingPressure);


			float energy = 0;
			if(redstoneSignal == 0){
				energy = (float) powerHandler.useEnergy(0, maxPowerUsage*updateInterval, true);
			}
			float oldHeat = pressure;
			updateHeat(updateInterval,energy);
			if(pressure != oldHeat){flagStateChange = true;}

			int oldCookTime = cookTime;
			if (redstoneSignal == 0) {

				// not disabled by redstone signal
				if (canSmelt() && pressure > cookingPressure) {
					// smeltable item in input and temperature is above
					// threshold
					cookTime += (int) (ticksPerTemp * pressure);
					if (cookTime >= cookTimeDone) {
						// done cooking
						this.smeltItem(); // decrement the input and increment
											// the output
						flag1 = true; // flag for inventory update
						cookTime = 0;
					}
				} else {
					cookTime = 0;
				}
			} else {

			}
			boolean isCooking = (pressure > cookingPressure);

			if(oldCookTime != cookTime){
				flagStateChange = true;
			}

			if(wasCooking != isCooking){
				// change between glowing and non-glowing states
				RMblock.updateFurnaceBlockState(isCooking, worldObj, xCoord, yCoord, zCoord);
			}
			if (flag1 ) {
				this.markDirty();
			}
			if(flagStateChange){
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}

			// end of update interval
		}
    }

	public float getPressure(){
		return pressure;
	}
	
    @SideOnly(Side.CLIENT)
    public int getEnergyProgress(int par1)
    {
        return (int) ((pressure * par1) / pressuremax);
    }

	public float getEnergyStore(){
		return (float) powerHandler.getEnergyStored();
	}

	public float getEnergyStoreMaximum(){
		return (float) powerHandler.getMaxEnergyStored();
	}
    
	private void updateHeat(int numTicks, float amountOfEnergy ){
		// current plus gain - loss
		pressure =  (int) (pressure + ((powerConst * amountOfEnergy)/numTicks) - (lossConst * (pressure * pressure)) - coolrate);
		if(pressure < 0){
			pressure = 0;
		}
	//	powerbuffer = 0;

	}

	 private boolean canSmelt()
	    {
	        if (this.furnaceItemStacks[0] == null)
	        {
	            return false;
	        }
	        else
	        {
	            ItemStack itemstack = RMRecipes.smelting().getSmeltingResult(this.furnaceItemStacks[0]);
	            if (itemstack == null) {return false;}
	            if (this.furnaceItemStacks[1] == null) return true;
	            if (!this.furnaceItemStacks[1].isItemEqual(itemstack)) return false;
	            int result = furnaceItemStacks[1].stackSize + itemstack.stackSize;
	            return (result <= getInventoryStackLimit() && result <= itemstack.getMaxStackSize());
	        }
	    }

    public void smeltItem()
    {
        if (this.canSmelt())
        {
            ItemStack itemstack = RMRecipes.smelting().getSmeltingResult(this.furnaceItemStacks[0]);

            if (this.furnaceItemStacks[1] == null)
            {
                this.furnaceItemStacks[1] = itemstack.copy();
            }
            else if (this.furnaceItemStacks[1].isItemEqual(itemstack))
            {
                furnaceItemStacks[1].stackSize += itemstack.stackSize;
            }

            --this.furnaceItemStacks[0].stackSize;

            if (this.furnaceItemStacks[0].stackSize <= 0)
            {
                this.furnaceItemStacks[0] = null;
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
        return ( par1 == 0 ); // items in input slot only
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

	@Override
	public PowerReceiver getPowerReceiver(ForgeDirection arg0) {
		if(redstoneSignal > 0){
			return powerHandlerDummy.getPowerReceiver();
		}
		return powerHandler.getPowerReceiver();
	}

	@Override
	public void doWork(PowerHandler workProvider) {
		// TODO ������������� ��������� �������� ������
		
	}

	@Override
	public World getWorld() {
		return this.worldObj;
	}
}