package mod.HellCoder.things.Blocks;

import mod.HellCoder.HellCoderCore.Utils.BCInteract;
import mod.HellCoder.HellCoderCore.Utils.Utils;
import mod.HellCoder.things.FriendsCraft2mod;
import mod.HellCoder.things.TileEntity.TileRM;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import buildcraft.api.core.Position;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class RMBlock extends BlockContainer {

	IIcon textureTop;
	IIcon textureFront;
	IIcon textureSide;
	IIcon textureBotom;
	
	public RMBlock() {
		super(Material.iron);
		setHardness(10F);
		setResistance(10F);
		setStepSound(soundTypeMetal);
		setCreativeTab(FriendsCraft2mod.tabsFC);
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack stack) {
		super.onBlockPlacedBy(world, i, j, k, entityliving, stack);

		ForgeDirection orientation = Utils.get2dOrientation(new Position(entityliving.posX, entityliving.posY, entityliving.posZ), new Position(i, j, k));

		world.setBlockMetadataWithNotify(i, j, k, orientation.getOpposite().ordinal(),1);
	}

	@Override
	public IIcon getIcon(int i, int j) {
		// If no metadata is set, then this is an icon.
		if (j == 0 && i == 3)
			return textureFront;

		if (i == j && i>1) // Front can't be top or bottom.
			return textureFront;

		switch (i) {
		case 1:
			return textureTop;
		default:
			return textureSide;
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileRM();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegistry)
	{
	    textureFront = iconRegistry.registerIcon("friendscraft:RollingMachine_face");
        textureSide = iconRegistry.registerIcon("friendscraft:RollingMachine_top");
        textureTop = iconRegistry.registerIcon("friendscraft:RollingMachine_top");
	}
	
	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int side, float par7, float par8, float par9) {
		TileRM tile = (TileRM) world.getTileEntity(i, j, k);
		
		if (player.isSneaking())
			return false;
		
		if (FriendsCraft2mod.instance.doPipeInteract) {
			if (BCInteract.isHoldingPipe(player)) {
				return false;
			}
		}
		
		if (tile != null) {
			return tile.onBlockActivated(player, ForgeDirection.getOrientation(side));
		}
		
		return false;
	}

}
