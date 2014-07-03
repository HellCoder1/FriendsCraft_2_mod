package mod.HellCoder.things.rollingmachine;

import buildcraft.api.mj.MjBattery;
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
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityRM extends TileEntity implements ISidedInventory
{

	public int pressure = 0;
	final float cookingPressure = 100f;
	int pressuremax = 250;
	public int usePressurePerUse = 0;

	static final float powerConst = 1.4f;
	static final float lossConst = 0.00005f ;
	static final float coolrate = 0.25f;
	
	private static final int[] slotsTop = new int[] {0};
    private static final int[] slotsBottom = new int[] {1};
    private static final int[] slotsSides = new int[] {0,1};
    
    private static int maxCapacity = 500;
    private static int POWER_USAGE = 25;
    @MjBattery (maxReceivedPerCycle = 10, maxCapacity = 500) 
    double mjStored = 0;

    int cookTime = 0;
    final int cookTimeDone = 100;
    final float ticksPerTemp = 0.03f;
    final int updateInterval = 4;
    private int updateCounter = Utils.rng.nextInt();
    
    private int redstoneSignal = 0;
       
    private String displayName = "";
    private ItemStack[] furnaceItemStacks = new ItemStack[2]; // 0=input, 1=output
    
    public TileEntityRM(){

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
        this.mjStored = p_145839_1_.getShort("Energy");

        if (p_145839_1_.hasKey("CustomName"))
        {
            this.displayName = p_145839_1_.getString("CustomName");
        }
    }
    
    /**
     * Writes a tile entity to NBT.
     */
	@Override public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("Pressure", (short) this.pressure);
        par1NBTTagCompound.setShort("CookTime", (short)this.cookTime);
        par1NBTTagCompound.setShort("Energy", (short) this.mjStored);
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
    
    @SideOnly(Side.CLIENT)
	public int getPressure(int par1){
    	
		return (pressure * par1) / pressuremax;
	}
    
    @SideOnly(Side.CLIENT)
    public int getEnergy(int par1){
    	
		return (int) ((this.mjStored * par1) / maxCapacity);
		
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
			boolean wasCooking = (pressure > cookingPressure);

			float oldpressure = pressure;
			if(pressure < pressuremax){
				updatePressure();
			}
			if(pressure != oldpressure){flagStateChange = true;}

			int oldCookTime = cookTime;
			if (redstoneSignal == 0) {

				if (canSmelt()) {

					cookTime += (ticksPerTemp * pressure);
					if (cookTime >= cookTimeDone) {
//                        if(pressure >= usePressurePerUse){
						this.smeltItem();
						
						flag1 = true;
						cookTime = 0;
//                        }
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
		}
    }

	public void UsePressure(){		
        pressure = pressure - (int)RMRecipes.init().getPressureUse(this.furnaceItemStacks[0]);
	}
    
	private void updatePressure(){
		
		if((int)mjStored < 0){
			mjStored = 0;
		}
		
		if((int)mjStored > 20){
		mjStored = mjStored - 20;
		pressure = pressure + 1;
		}
		
		if(pressure < 0){
			pressure = 0;
        }
		if(pressure >= (pressuremax + 1)){
			pressure = pressuremax;
        }
	}

	 private boolean canSmelt()
	    {
	        if (this.furnaceItemStacks[0] == null)
	        {
	            return false;
	        }
	        else
	        {   
	        	usePressurePerUse = (int)RMRecipes.init().getPressureUse(this.furnaceItemStacks[0]);
	        	if (pressure >= usePressurePerUse){
	              ItemStack itemstack = RMRecipes.init().getSmeltingResult(this.furnaceItemStacks[0]);
	              if (itemstack == null) {return false;}
	              if (this.furnaceItemStacks[1] == null) return true;
	              if (!this.furnaceItemStacks[1].isItemEqual(itemstack)) return false;
	              int result = furnaceItemStacks[1].stackSize + itemstack.stackSize;
	              return (result <= getInventoryStackLimit() && result <= itemstack.getMaxStackSize());
	        	}else{
	        		return false;
	        	}
	        }
	    }

    public void smeltItem()
    {
        if (this.canSmelt())
        {
            ItemStack itemstack = RMRecipes.init().getSmeltingResult(this.furnaceItemStacks[0]);
            UsePressure();
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

	public World getWorld() {
		return this.worldObj;
	}
}