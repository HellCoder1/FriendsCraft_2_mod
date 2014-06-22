package mod.HellCoder.things.lib;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import mod.HellCoder.things.FriendsCraft2mod;
import mod.HellCoder.things.Blocks.DigaBlock;
import mod.HellCoder.things.Blocks.DigaOre;
import mod.HellCoder.things.Blocks.RMBlock;
import mod.HellCoder.things.Items.ItemBlockRM;
import mod.HellCoder.things.Items.ItemBlockReconstructor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fluids.BlockFluidClassic;

public class RegBlocks {
	public static Block blockLiquidMetal;

	public static Block digaore;
	public static Block digablock;
	public static Block digaoreglow;

	public static Block rollingmachine;

	public static void init() {

		digablock = registerBlock(new DigaBlock(Material.iron)
				.setBlockName("digablock")
				.setBlockTextureName("friendscraft:digablock")
				.setStepSound(Block.soundTypeMetal).setResistance(1500.0F)
				.setHardness(50.0F));
		digaore = registerBlock(new DigaOre(true).setBlockName("digaore")
				.setBlockTextureName("friendscraft:digaore"));
		
        rollingmachine = registerBlock(new RMBlock(), ItemBlockRM.class).setBlockName("Rolling Machine");
}

	public static Block registerBlock(Block block) {
		GameRegistry.registerBlock(block, block.getUnlocalizedName().replace("tile.", ""));

		return block;
	}

	public static Block registerBlock(Block block,
			Class<? extends ItemBlock> itemBlockClass) {
		GameRegistry.registerBlock(block, itemBlockClass, block.getUnlocalizedName().replace("tile.", ""));

		return block;
	}

	public static Block registerBlock(Block block,
		Class<? extends ItemBlock> itemBlockClass,Object... constructorArgs) {
		GameRegistry.registerBlock(block, itemBlockClass, block.getUnlocalizedName().replace("tile.", ""), constructorArgs);

		return block;
	}
}
