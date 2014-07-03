package mod.HellCoder.things.Blocks;

import cofh.util.ItemHelper;
import cofh.util.StringHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.List;

import mod.HellCoder.things.FriendsCraft2mod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;


public class BlockMetals extends Block {

	public BlockMetals() {

		super(Material.iron);
		setHardness(5.0F);
		setResistance(10.0F);
		setStepSound(soundTypeMetal);
		setCreativeTab(FriendsCraft2mod.tabsFC);
		setBlockName("friendscraft.metals");
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
	public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side) {

		return world.getBlockMetadata(x, y, z) == 10 ? 15 : 0;
	}

	@Override
	public float getBlockHardness(World world, int x, int y, int z) {

		return HARDNESS[world.getBlockMetadata(x, y, z)];
	}

	@Override
	public float getExplosionResistance(Entity entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {

		return RESISTANCE[world.getBlockMetadata(x, y, z)];
	}

	@Override
	public int damageDropped(int i) {

		return i;
	}

	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {

		return false;
	}

	@Override
	public boolean isBeaconBase(IBlockAccess worldObj, int x, int y, int z, int beaconX, int beaconY, int beaconZ) {

		return true;
	}

	@Override
	public IIcon getIcon(int side, int metadata) {

		return TEXTURES[metadata];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir) {

		for (int i = 0; i < NAMES.length; i++) {
			TEXTURES[i] = ir.registerIcon("friendscraft:metals/" + StringHelper.titleCase(NAMES[i]));
		}
	}

	/* IInitializer */
	public boolean init() {

		GameRegistry.registerBlock(this, ItemBlockMetals.class, "metals");

		blockCopper = new ItemStack(this, 1, 0);

		ItemHelper.registerWithHandlers("blockCopper", blockCopper);

		ItemHelper.addStorageRecipe(blockCopper, "ingotCopper");
		
		return true;
	}

	public static final String[] NAMES = { "copper" };
	public static final IIcon[] TEXTURES = new IIcon[NAMES.length];
	public static final int[] LIGHT = { 0, 0, 4, 0, 0, 4, 8, 0, 0, 0, 7, 15, 4 };
	public static final float[] HARDNESS = { 5, 5, 5, 4, 10, 5, 30, 4, 20, 5, 5, 5, 40 };
	public static final float[] RESISTANCE = { 6, 6, 6, 12, 6, 6, 120, 6, 12, 6, 9, 9, 120 };
	public static final int[] RARITY = { 0, 0, 0, 0, 0, 1, 2, 0, 0, 0, 1, 1, 2 };

	public static ItemStack blockCopper;

}