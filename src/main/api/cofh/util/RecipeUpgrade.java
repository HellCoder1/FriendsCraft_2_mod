package cofh.util;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class RecipeUpgrade extends ShapedOreRecipe {

	int upgradeSlot = 4;

	public RecipeUpgrade(ItemStack result, Object[] recipe) {

		super(result, recipe);
	}

	public RecipeUpgrade(int upgradeSlot, ItemStack result, Object[] recipe) {

		super(result, recipe);
		this.upgradeSlot = upgradeSlot;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting craftMatrix) {

		if (craftMatrix.getStackInSlot(upgradeSlot) == null || craftMatrix.getStackInSlot(upgradeSlot).stackTagCompound == null) {
			return super.getCraftingResult(craftMatrix);
		}
		return ItemHelper.copyTag(getRecipeOutput().copy(), craftMatrix.getStackInSlot(upgradeSlot));
	}

}
