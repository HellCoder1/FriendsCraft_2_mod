package mod.HellCoder.things.Blocks;

import mod.HellCoder.things.FriendsCraft2mod;
import mod.HellCoder.things.TileEntity.TileEntityTest3DBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class Test3DBlock extends BlockContainer{

	public Test3DBlock(Material materail) {
        super(materail);
        this.setCreativeTab(FriendsCraft2mod.tabsFC);
        this.setBlockBounds(0.4F, 0.0F, 0.4F, 0.6F, 3.0F, 0.6F);
}

//Make sure you set this as your TileEntity class relevant for the block!
//@Override
//public TileEntity createNewTileEntity(World world) {
//        return new TileEntityTrafficLightEntity();
//}

//You don't want the normal render type, or it wont render properly.
@Override
public int getRenderType() {
        return -1;
}

//It's not an opaque cube, so you need this.
@Override
public boolean isOpaqueCube() {
        return false;
}

//It's not a normal block, so you need this too.
public boolean renderAsNormalBlock() {
        return false;
}

//This is the icon to use for showing the block in your hand.
public void registerIcons(IIconRegister icon) {
        this.blockIcon = icon.registerIcon("friendscraft:Test3D");
}

@Override
public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
	return new TileEntityTest3DBlock();
}
}
