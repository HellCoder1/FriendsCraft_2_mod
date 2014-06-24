package mod.HellCoder.things.Blocks;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mod.HellCoder.things.FriendsCraft2mod;
import mod.HellCoder.things.core.fx.UtilsFX;
import mod.HellCoder.things.lib.RegBlocks;
import mod.HellCoder.things.lib.RegItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class DigaOre extends Block{
	 public IIcon[] icon = new IIcon[2];

	  public DigaOre()
	  {
	    super(Material.rock);
		setHardness(50.0F);
		setResistance(1500.0F);
		setStepSound(Block.soundTypeMetal);
		setHarvestLevel("pickaxe", 3);
	    setCreativeTab(FriendsCraft2mod.tabsFC);
	    setTickRandomly(true);
	  }

	  @SideOnly(Side.CLIENT)
	  public void registerBlockIcons(IIconRegister ir)
	  {
		  this.icon[0] = ir.registerIcon("friendscraft:DigaOreFrame");
		  this.icon[1] = ir.registerIcon("friendscraft:DigaOre");
	  }

	  @SideOnly(Side.CLIENT)
	  public IIcon getIcon(int par1, int par2) {
	    return this.icon[0];
	  }

	  public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata)
	  {
	    return true;
	  }

	  public int damageDropped(int par1)
	  {
	    return par1;
	  }

	  @SideOnly(Side.CLIENT)
	  public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer)
	  {
	    int md = worldObj.getBlockMetadata(target.blockX, target.blockY, target.blockZ);
	    UtilsFX.infusedStoneSparkle(worldObj, target.blockX, target.blockY, target.blockZ, md);
	    return super.addHitEffects(worldObj, target, effectRenderer);
	  }

	  public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer)
	  {
	    return super.addDestroyEffects(world, x, y, z, meta, effectRenderer);
	  }

	  public void setBlockBoundsBasedOnState(IBlockAccess par1iBlockAccess, int par2, int par3, int par4)
	  {
	    setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	    super.setBlockBoundsBasedOnState(par1iBlockAccess, par2, par3, par4);
	  }

	  public void addCollisionBoxesToList(World world, int i, int j, int k, AxisAlignedBB axisalignedbb, List arraylist, Entity par7Entity)
	  {
	    setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	    super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, par7Entity);
	  }

	  public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int md, int fortune)
	  {
	    ArrayList ret = new ArrayList();
	    ret.add(new ItemStack(RegBlocks.digaore, 1, 0));
	    return ret;
	  }

	  public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side)
	  {
	    return true;
	  }

	  public boolean renderAsNormalBlock()
	  {
	    return false;
	  }

	  public int getRenderType()
	  {
	    return RegBlocks.blockCustomDigaOreRI;
	  }
}
