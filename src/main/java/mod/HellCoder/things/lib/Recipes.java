package mod.HellCoder.things.lib;

import mod.HellCoder.things.core.MainHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;
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
			Character.valueOf('A'), Items.redstone,
			Character.valueOf('Z'), Blocks.stone,
			Character.valueOf('G'), RegItems.IronRedstoneGear});
		GameRegistry.addRecipe(new ItemStack(RegItems.Press), new Object[] {
			"AAZ", "  G", "AAZ", Character.valueOf('Z'), Items.iron_ingot, 
			Character.valueOf('G'), Blocks.piston,
			Character.valueOf('A'), Blocks.stone});
		
		GameRegistry.addRecipe(new ItemStack(RegItems.rawOrganicMaterial), new Object[] {
			"ZZZ", "ZQZ", "ZZZ", Character.valueOf('Z'), RegItems.compressedPlants,
			Character.valueOf('Q'), Items.water_bucket});
		GameRegistry.addRecipe(new ItemStack(RegItems.smallTank), new Object[] {
			" Z ", "Z Z", " Z ", Character.valueOf('Z'), Blocks.glass});
		GameRegistry.addRecipe(new ItemStack(RegItems.termalDispenser), new Object[] {
			" A ", "GZG", " Z ", Character.valueOf('Z'), RegItems.copperIngot, 
			Character.valueOf('G'), Blocks.iron_bars,
			Character.valueOf('A'), Items.redstone});
		GameRegistry.addRecipe(new ItemStack(RegItems.CoolantBlock), new Object[] {
			"ZZZ", "ZGZ", "ABA", Character.valueOf('Z'), Items.snowball, 
			Character.valueOf('G'), RegItems.IronRedstoneGear,
			Character.valueOf('B'), Blocks.chest,
			Character.valueOf('A'), RegItems.smallIronPlate});
		GameRegistry.addRecipe(new ItemStack(RegItems.pressureRegulator), new Object[] {
			" A ", "ZGZ", "BZB", Character.valueOf('A'), Blocks.lever, 
			Character.valueOf('G'), RegItems.IronRedstoneGear,
			Character.valueOf('Z'), RegBlocks.HermeticPipe,
			Character.valueOf('B'), RegItems.smallIronPlate});
		GameRegistry.addRecipe(new ItemStack(RegItems.pressureTank), new Object[] {
			" A ", "GZG", " A ", Character.valueOf('Z'), RegBlocks.HermeticPipe, 
			Character.valueOf('G'), RegItems.smallIronPlate,
			Character.valueOf('A'), Blocks.glass});
		GameRegistry.addRecipe(new ItemStack(RegBlocks.HermeticPipe), new Object[] {
			"   ", "GZG", " A ", Character.valueOf('Z'), Blocks.glass, 
			Character.valueOf('G'), Items.iron_ingot,
			Character.valueOf('A'), RegItems.Insulator});
		GameRegistry.addRecipe(new ItemStack(RegItems.scythe), new Object[] {
			" A ", " Z ", " Z ", Character.valueOf('A'), RegItems.scytheBlade, 
			Character.valueOf('Z'), Items.stick});
		GameRegistry.addRecipe(new ItemStack(RegItems.GoldGear), new Object[] {
			" Z ", "Z Z", " Z ", Character.valueOf('Z'), Items.gold_ingot});
		GameRegistry.addRecipe(new ItemStack(RegItems.IronGear), new Object[] {
			" Z ", "Z Z", " Z ", Character.valueOf('Z'), Items.iron_ingot});
		GameRegistry.addRecipe(new ItemStack(RegItems.GoldRedstoneGear), new Object[] {
			" Z ", "ZAZ", " Z ", Character.valueOf('A'), RegItems.GoldGear,
			Character.valueOf('Z'), Items.redstone});
		GameRegistry.addRecipe(new ItemStack(RegItems.IronRedstoneGear), new Object[] {
			" Z ", "ZAZ", " Z ", Character.valueOf('A'), RegItems.IronGear,
			Character.valueOf('Z'), Items.redstone});
		GameRegistry.addRecipe(new ItemStack(RegItems.Hammer), new Object[] {
			" B ", " A ", " A ", Character.valueOf('A'), Items.stick,
			Character.valueOf('B'), Items.iron_ingot});
//		GameRegistry.addShapedRecipe(new ItemStack(RegItems.smallIronPlate), new Object[] {
//			"BA", Character.valueOf('A'), Items.iron_ingot,
//			Character.valueOf('B'), RegItems.Hammer});
		
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
