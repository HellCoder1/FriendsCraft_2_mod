package mod.HellCoder.things.Blocks;

import cofh.api.core.IInitializer;
import cofh.lib.util.helpers.ItemHelper;
import cofh.lib.util.helpers.StringHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.List;

import mod.HellCoder.things.FriendsCraft2mod;
import mod.HellCoder.things.lib.RegItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class BlockOre extends Block{

	public BlockOre() {

		super(Material.rock);
		setHardness(3.0F);
		setResistance(5.0F);
		setStepSound(soundTypeStone);
		setCreativeTab(FriendsCraft2mod.tabsFC);
		setBlockName("friendscraft.ore");

		setHarvestLevel("pickaxe", 2);
		setHarvestLevel("pickaxe", 1, 0);
		setHarvestLevel("pickaxe", 1, 1);
		setHarvestLevel("pickaxe", 3, 6);
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {

		for (int i = 0; i < NAMES.length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {

		return LIGHT[world.getBlockMetadata(x, y, z)];
	}

	@Override
	public int damageDropped(int i) {

		return i;
	}

	@Override
	public IIcon getIcon(int side, int metadata) {

		return TEXTURES[metadata];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir) {

		for (int i = 0; i < NAMES.length; i++) {
			TEXTURES[i] = ir.registerIcon("friendscraft:ore/" + StringHelper.titleCase(NAMES[i]));
		}
	}

	/* IInitializer */
	public boolean init() {

		GameRegistry.registerBlock(this, ItemBlockOre.class, "Ore");

		oreCopper = new ItemStack(this, 1, 0);

		ItemHelper.registerWithHandlers("oreCopper", oreCopper);
		
		FurnaceRecipes.smelting().func_151394_a(oreCopper, RegItems.copperIngot, 0.6F);

		return true;
	}

	public static final String[] NAMES = { "copper"};
	public static final IIcon[] TEXTURES = new IIcon[NAMES.length];
	public static final int[] LIGHT = { 0, 0, 4, 0, 0, 4, 8 };
	public static final int[] RARITY = { 0, 0, 0, 0, 0, 1, 2 };

	public static ItemStack oreCopper;

}

