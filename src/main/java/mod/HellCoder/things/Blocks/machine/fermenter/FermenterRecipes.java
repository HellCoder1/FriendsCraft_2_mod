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

public class FermenterRecipes
{
    private static final FermenterRecipes recipeBase = new FermenterRecipes();
    /** The list of smelting results. */
    private Map recipeList = new HashMap();
	private Map pressureList= new HashMap();

    /**
     * Used to call methods addSmelting and getSmeltingResult.
     */
    public static FermenterRecipes init()
    {
        return recipeBase;
    }

    private FermenterRecipes()
    {
        this.regBlock(Blocks.iron_block, new ItemStack(RegItems.ironPlate), 250);
        this.reg(Items.iron_ingot, new ItemStack(RegItems.smallIronPlate), 150);
        this.reg(Items.leather, new ItemStack(RegItems.Insulator), 100);


    }

    public void regBlock(Block p_151393_1_, ItemStack p_151393_2_, int pressure)
    {
        this.reg(Item.getItemFromBlock(p_151393_1_), p_151393_2_, pressure);
    }

    public void reg(Item item, ItemStack stack, int pressure)
    {
        this.addList(new ItemStack(item, 1, 32767), stack, pressure);
    }

    public void addList(ItemStack stack, ItemStack stack2, int pressure)
    {
        this.recipeList.put(stack, stack2);
        this.pressureList.put(stack, Float.valueOf(pressure));
    }

    /**
     * Returns the smelting result of an item.
     */
    public ItemStack getSmeltingResult(ItemStack p_151395_1_)
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

        return (ItemStack)entry.getValue();
    }
    
    public float getPressureUse(ItemStack par1)
    {
    	
        Iterator iterator = this.pressureList.entrySet().iterator();
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