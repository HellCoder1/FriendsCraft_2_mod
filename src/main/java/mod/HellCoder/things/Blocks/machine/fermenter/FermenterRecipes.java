package mod.HellCoder.things.Blocks.machine.fermenter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import mod.HellCoder.things.lib.RegItems;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class FermenterRecipes
{
    private static final FermenterRecipes recipeBase = new FermenterRecipes();
    /** The list of smelting results. */
    private Map recipeList = new HashMap();
	private Map heatList= new HashMap();

    /**
     * Used to call methods addSmelting and getSmeltingResult.
     */
    public static FermenterRecipes init()
    {
        return recipeBase;
    }

    private FermenterRecipes()
    {
//        this.regBlock(Blocks.iron_block, new ItemStack(RegItems.ironPlate), 250);
//        this.reg(Items.iron_ingot, new ItemStack(RegItems.smallIronPlate), 150);
    	
    	//need 400 pieces of items for full fluid buffer (60000/150 = 400)
        this.reg(RegItems.rawOrganicMaterial, new FluidStack(FluidRegistry.getFluid("methane"), 300), 100);


    }

    public void reg(Item item, FluidStack stack, int heat)
    {
        this.addList(new ItemStack(item, 1, 32767), stack, heat);
    }

    public void addList(ItemStack stack, FluidStack stack2, int heat)
    {
        this.recipeList.put(stack, stack2);
        this.heatList.put(stack, Float.valueOf(heat));
    }

    /**
     * Returns the smelting result of an item.
     */
    public FluidStack getSmeltingResult(ItemStack p_151395_1_)
    {
        Iterator iterator = this.recipeList.entrySet().iterator();
        Entry entry;

        do
        {
            if (!iterator.hasNext())
            {
                return null;
            }

            entry = (Entry)iterator.next();
        }
        while (!this.func_151397_a(p_151395_1_, (ItemStack)entry.getKey()));

        return (FluidStack)entry.getValue();
    }
    
    public float getHeatUse(ItemStack par1)
    {
    	
        Iterator iterator = this.heatList.entrySet().iterator();
        Entry entry;

        do
        {
            if (!iterator.hasNext())
            {
                return 0.1F;
            }

            entry = (Entry)iterator.next();
        }
        while (!this.func_151397_a(par1, (ItemStack)entry.getKey()));

        return ((Float)entry.getValue()).floatValue();
    }
    
    public float getSmeltingExp(ItemStack item)
    {
        return -1; //-1 will default to the old lookups.
    }

    private boolean func_151397_a(ItemStack p_151397_1_, ItemStack p_151397_2_)
    {
        return p_151397_2_.getItem() == p_151397_1_.getItem() && (p_151397_2_.getItemDamage() == 32767 || p_151397_2_.getItemDamage() == p_151397_1_.getItemDamage());
    }

    public Map getSmeltingList()
    {
        return this.recipeList;
    }
}