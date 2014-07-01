package mod.HellCoder.things.lib;

import mod.HellCoder.things.core.MainHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class Recipes {

	public static void init() {

		/**
		 * Tools
		 */

		GameRegistry.addRecipe(new ItemStack(RegItems.NagibatorSword),
						new Object[] { " Z ", "CZC", " C ",
								Character.valueOf('Z'), RegItems.diga,
								Character.valueOf('C'), Blocks.obsidian, });
		

		/**
		 * Items
		 */
		GameRegistry.addRecipe(new ItemStack(MainHelper.get("Upgrade")), "iii", "iti", "iii", 
				('i'), Blocks.cobblestone, ('t'), Blocks.torch);
		
		GameRegistry.addRecipe(new ItemStack(RegItems.diga), new Object[] {
			"AZA", "ZBZ", "AZA", Character.valueOf('Z'),
			RegBlocks.digablock, Character.valueOf('A'), Blocks.obsidian,
			Character.valueOf('B'), Blocks.diamond_block });
		
		GameRegistry.addRecipe(new ItemStack(RegItems.FCHelmet), new Object[] {
			"ZZZ", "Z Z", Character.valueOf('Z'), RegItems.diga});
		GameRegistry.addRecipe(new ItemStack(RegItems.FCChest), new Object[] {
			"Z Z", "ZZZ", "ZZZ", Character.valueOf('Z'), RegItems.diga});
		GameRegistry.addRecipe(new ItemStack(RegItems.FCLegs), new Object[] {
			"ZZZ", "Z Z", "Z Z", Character.valueOf('Z'), RegItems.diga});
		GameRegistry.addRecipe(new ItemStack(RegItems.FCBoots), new Object[] {
			"Z Z", "Z Z", Character.valueOf('Z'), RegItems.diga});
		
		GameRegistry.addRecipe(new ItemStack(RegItems.Rollers), new Object[] {
			" A ", "ZGZ", " A ", Character.valueOf('Z'), Items.stick, 
			Character.valueOf('G'), new ItemStack(Items.redstone, 3),
			Character.valueOf('A'), RegItems.IronRedstoneGear});
		GameRegistry.addRecipe(new ItemStack(RegItems.Press), new Object[] {
			"AAZ", "  G", "AAZ", Character.valueOf('Z'), Items.iron_ingot, 
			Character.valueOf('G'), new ItemStack(Items.stick, 2),
			Character.valueOf('A'), Blocks.stone});

		/**
		 * Blocks
		 */
		GameRegistry.addRecipe(new ItemStack(RegBlocks.digablock),
				new Object[] { "ZZZ", "ZZZ", "ZZZ", Character.valueOf('Z'), RegItems.digaingot, });

		/**
		 * Furnace
		 */
		GameRegistry.addSmelting(RegBlocks.digaore, new ItemStack(RegItems.digaingot), 0.1F);
	}

}
