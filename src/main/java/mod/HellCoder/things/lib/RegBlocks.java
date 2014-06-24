package mod.HellCoder.things.lib;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import mod.HellCoder.things.FriendsCraft2mod;
import mod.HellCoder.things.Blocks.DigaOre;
import mod.HellCoder.things.Blocks.DigaBlock;
import mod.HellCoder.things.Blocks.ItemBlockRM;
import mod.HellCoder.things.Blocks.RMBlock;
import mod.HellCoder.things.Blocks.render.LightItemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fluids.BlockFluidClassic;

public class RegBlocks {

	public static Block digaore;
	public static Block digablock;

	public static Block rollingmachine;
	
	public static int blockCustomDigaOreRI = -1;
	public static int DigaBlockRI = -1;

	public static void init() {
		
        rollingmachine = registerBlock(new RMBlock(), ItemBlockRM.class).setBlockName("rollingmachine");

        /**
         * Light Blocks
         */
        digaore = new DigaOre().setBlockName("digaore");      
        GameRegistry.registerBlock(digaore, LightItemBlock.class, "digaore");
        
        digablock = new DigaBlock().setBlockName("digablock");
        GameRegistry.registerBlock(digablock, LightItemBlock.class, "digablock");
        
    }

	public static Block registerBlock(Block block) {
		GameRegistry.registerBlock(block, block.getUnlocalizedName().replace("tile.", ""));

		return block;
	}

	public static Block registerBlock(Block block, Class<? extends ItemBlock> itemBlockClass) {
		GameRegistry.registerBlock(block, itemBlockClass, block.getUnlocalizedName().replace("tile.", ""));

		return block;
	}

	public static Block registerBlock(Block block,
		Class<? extends ItemBlock> itemBlockClass,Object... constructorArgs) {
		GameRegistry.registerBlock(block, itemBlockClass, block.getUnlocalizedName().replace("tile.", ""), constructorArgs);

		return block;
	}
}
