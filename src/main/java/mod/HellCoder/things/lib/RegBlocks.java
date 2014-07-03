package mod.HellCoder.things.lib;

import cofh.fluid.BlockFluidCoFHBase;
import cofh.util.ItemHelper;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import mod.HellCoder.things.FriendsCraft2mod;
import mod.HellCoder.things.Blocks.BlockMetals;
import mod.HellCoder.things.Blocks.BlockOre;
import mod.HellCoder.things.Blocks.DigaOre;
import mod.HellCoder.things.Blocks.DigaBlock;
import mod.HellCoder.things.Blocks.blockpipe;
import mod.HellCoder.things.Blocks.machine.fermenter.FermenterBlock;
import mod.HellCoder.things.Blocks.machine.rollingmachine.RMblock;
import mod.HellCoder.things.Blocks.render.LightItemBlock;
import mod.HellCoder.things.fluid.TESTmethaneBlockFluid;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.BlockFluidClassic;

public class RegBlocks {

	public static Block digaore;
	public static Block digablock;

	public static Block rollingmachine;
	public static Block fermenter;
	
	public static Block HermeticPipe;
	
	public static int blockCustomDigaOreRI = -1;
	public static int DigaBlockRI = -1;
    public static int tubeRenderID;
    
    public static BlockFluidCoFHBase blockFluidMethane;

    public static BlockOre blockOre;
    public static BlockMetals blockMetal;
    
	public static void init() {

		/*
		 * Machines
		 */
		
        rollingmachine = new RMblock(false).setBlockName("rollingmachine").setCreativeTab(FriendsCraft2mod.tabsFC);
        fermenter = new FermenterBlock().setBlockName("Fermenter").setCreativeTab(FriendsCraft2mod.tabsFC); 
        
        GameRegistry.registerBlock(rollingmachine, "rollingmachine");
        GameRegistry.registerBlock(fermenter, "Fermenter");
		
		/*
		 * Pipes
		 */
		HermeticPipe = new blockpipe().setCreativeTab(FriendsCraft2mod.tabsFC).setBlockName("HermeticPipe").setHardness(2.5F).setResistance(5.0F);
		GameRegistry.registerBlock(HermeticPipe, "HermeticPipe");
		
		/*
		 * Fluids
		 */
//		blockFluidMethane = new TESTmethaneBlockFluid();
//		blockFluidMethane.preInit();
		

        /*
         * Light Blocks
         */
        digaore = new DigaOre().setBlockName("digaore");      
        GameRegistry.registerBlock(digaore, LightItemBlock.class, "digaore");
        
        digablock = new DigaBlock().setBlockName("digablock");
        GameRegistry.registerBlock(digablock, LightItemBlock.class, "digablock");
        
        /*
         * Metals
         */
        blockMetal = new BlockMetals();
        blockMetal.init();
        
        /*
         * Ores
         */
        blockOre = new BlockOre();
        blockOre.init();
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
