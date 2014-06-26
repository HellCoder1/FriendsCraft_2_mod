package mod.HellCoder.things.rollingmachine;

import buildcraft.api.power.PowerHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerRM extends Container
{
    private TileEntityRM tile;
    private int lastCookTime;
    private int energyStore;
    

    public ContainerRM(InventoryPlayer par1InventoryPlayer, TileEntityRM par2TileEntityIronOven)
    {
        this.tile = par2TileEntityIronOven;
        this.addSlotToContainer(new Slot(par2TileEntityIronOven, 0, 43, 33));
        this.addSlotToContainer(new Slot(par2TileEntityIronOven, 1, 116, 33)); 
        
        int i;

        for (i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, i, 8 + i * 18, 142));
        }
    }

	public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.tile.cookTime);
        
        PowerHandler.PowerReceiver provider = this.tile.getPowerReceiver(null);
        if (provider != null)
        	par1ICrafting.sendProgressBarUpdate(this, 1, (int)provider.getEnergyStored());

    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
        PowerHandler.PowerReceiver provider = this.tile.getPowerReceiver(null);

        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);

            if (provider != null) {
                icrafting.sendProgressBarUpdate(this, 1, (int)provider.getEnergyStored());
            }
            if (this.lastCookTime != this.tile.cookTime)
            {
                icrafting.sendProgressBarUpdate(this, 0, this.tile.cookTime);
            }
        }

        this.lastCookTime = this.tile.cookTime;
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0)
        {
            this.tile.cookTime = par2;
        }
        if (par1 == 1)
        {
        	this.tile.pressure = par2;
        }


    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.tile.isUseableByPlayer(par1EntityPlayer);
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slotIndex)
    {
    	// copied from ContainerFurnace
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (slotIndex == 1) {
            	// the output slot, send to player inventory
                if (!this.mergeItemStack(itemstack1, 2, inventorySlots.size(), true)) {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (slotIndex != 1 && slotIndex != 0) {
            	// player inventory, send to input slot or to toolbar
                if (RMRecipes.smelting().getSmeltingResult(itemstack1) != null) {
                	// stick it in the input slot
                    if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                        return null;
                    }
                } else if (slotIndex >= 2 && slotIndex < (inventorySlots.size() - 9)) {
                	// main inventory, send to toolbar
                    if (!this.mergeItemStack(itemstack1, inventorySlots.size() - 9, inventorySlots.size(), false)) {
                        return null;
                    }
                } else if (slotIndex >= (inventorySlots.size() - 9) && slotIndex < inventorySlots.size() && !this.mergeItemStack(itemstack1, 2, (inventorySlots.size() - 9), false)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack1, 2, inventorySlots.size(), false)) {
                return null;
            }

            if (itemstack1.stackSize == 0) {
                slot.putStack((ItemStack)null);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
        }

        return itemstack;
    }
}