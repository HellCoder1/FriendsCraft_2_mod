package mod.HellCoder.things.Blocks;

import java.util.ArrayList;

import mod.HellCoder.things.FriendsCraft2mod;
import mod.HellCoder.things.TileEntity.TileEntityMethanePipe;
import mod.HellCoder.things.core.IMethaneTransporter;
import mod.HellCoder.things.lib.RegBlocks;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class blockpipe extends BlockContainer {
	
	public IIcon sideIcon;
    public int pass = 0;
    
    @Override
    public boolean canRenderInPass(int x) {
    	pass = x;
    	return x <= 1;
    }
    
	public blockpipe() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityMethanePipe();
	}

    public boolean isOpaqueCube()
    {
        return false;
    }
    
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    public int getRenderType()
    {
        return RegBlocks.tubeRenderID;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int i, int j, int k)
    {
		float baseMin = 4.0F/16.0F;
		float baseMax = 12.0F/16.0F;
		float ringMin = 4.0F/16.0F;
		float ringMax = 12.0F/16.0F;
		float px = 1.0F/16.0F;
		float minX = baseMin;
		float maxX = baseMax;
		float minY = baseMin;
		float maxY = baseMax;
		float minZ = baseMin;
		float maxZ = baseMax;
		ArrayList<ForgeDirection> myDirections = new ArrayList<ForgeDirection>();
		for (ForgeDirection direction : ForgeDirection.values()) {
			if (world.getTileEntity(i+direction.offsetX, j+direction.offsetY, k+direction.offsetZ) != null) {
				TileEntity tile = world.getTileEntity(i+direction.offsetX, j+direction.offsetY, k+direction.offsetZ);
				if (tile instanceof IMethaneTransporter) {
					IMethaneTransporter target = (IMethaneTransporter) tile;
					if (target.doesConnect(direction.getOpposite())) {
						myDirections.add(direction);
						if (direction.offsetX == 1) {
							maxX = 1.0F;
						}
						if (direction.offsetY == 1) {
							maxY = 1.0F;
						}
						if (direction.offsetZ == 1) {
							maxZ = 1.0F;
						}
						if (direction.offsetX == -1) {
							minX = 0.0F;
						}
						if (direction.offsetY == -1) {
							minY = 0.0F;
						}
						if (direction.offsetZ == -1) {
							minZ = 0.0F;
						}
					}
				}
				else if (tile instanceof IFluidHandler && FriendsCraft2mod.steamRegistered) {
					IFluidHandler target = (IFluidHandler) tile;
					if (target.canDrain(direction.getOpposite(), FluidRegistry.getFluid("steam")) || target.canFill(direction.getOpposite(), FluidRegistry.getFluid("steam"))) {
						myDirections.add(direction);
						if (direction.offsetX == 1) {
							maxX = 1.0F-2*px;
						}
						if (direction.offsetY == 1) {
							maxY = 1.0F-2*px;
						}
						if (direction.offsetZ == 1) {
							maxZ = 1.0F-2*px;
						}
						if (direction.offsetX == -1) {
							minX = 0.0F+2*px;
						}
						if (direction.offsetY == -1) {
							minY = 0.0F+2*px;
						}
						if (direction.offsetZ == -1) {
							minZ = 0.0F+2*px;
						}
					}
				}
			}
		}
		if (myDirections.size() == 2) {
			ForgeDirection direction = myDirections.get(0).getOpposite();
			
			if (direction.offsetX == 1) {
				maxX = 1.0F;
			}
			if (direction.offsetY == 1) {
				maxY = 1.0F;
			}
			if (direction.offsetZ == 1) {
				maxZ = 1.0F;
			}
			if (direction.offsetX == -1) {
				minX = 0.0F;
			}
			if (direction.offsetY == -1) {
				minY = 0.0F;
			}
			if (direction.offsetZ == -1) {
				minZ = 0.0F;
			}
		}
		setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
    {
		float baseMin = 4.0F/16.0F;
		float baseMax = 12.0F/16.0F;
		float ringMin = 4.0F/16.0F;
		float ringMax = 12.0F/16.0F;
		float px = 1.0F/16.0F;
		float minX = baseMin;
		float maxX = baseMax;
		float minY = baseMin;
		float maxY = baseMax;
		float minZ = baseMin;
		float maxZ = baseMax;
		ArrayList<ForgeDirection> myDirections = new ArrayList<ForgeDirection>();
		for (ForgeDirection direction : ForgeDirection.values()) {
			if (world.getTileEntity(i+direction.offsetX, j+direction.offsetY, k+direction.offsetZ) != null) {
				TileEntity tile = world.getTileEntity(i+direction.offsetX, j+direction.offsetY, k+direction.offsetZ);
				if (tile instanceof IMethaneTransporter) {
					IMethaneTransporter target = (IMethaneTransporter) tile;
					if (target.doesConnect(direction.getOpposite())) {
						myDirections.add(direction);
						if (direction.offsetX == 1) {
							maxX = 1.0F;
						}
						if (direction.offsetY == 1) {
							maxY = 1.0F;
						}
						if (direction.offsetZ == 1) {
							maxZ = 1.0F;
						}
						if (direction.offsetX == -1) {
							minX = 0.0F;
						}
						if (direction.offsetY == -1) {
							minY = 0.0F;
						}
						if (direction.offsetZ == -1) {
							minZ = 0.0F;
						}
					}
				}
				else if (tile instanceof IFluidHandler && FriendsCraft2mod.steamRegistered) {
					IFluidHandler target = (IFluidHandler) tile;
					if (target.canDrain(direction.getOpposite(), FluidRegistry.getFluid("steam")) || target.canFill(direction.getOpposite(), FluidRegistry.getFluid("steam"))) {
						myDirections.add(direction);
						if (direction.offsetX == 1) {
							maxX = 1.0F-2*px;
						}
						if (direction.offsetY == 1) {
							maxY = 1.0F-2*px;
						}
						if (direction.offsetZ == 1) {
							maxZ = 1.0F-2*px;
						}
						if (direction.offsetX == -1) {
							minX = 0.0F+2*px;
						}
						if (direction.offsetY == -1) {
							minY = 0.0F+2*px;
						}
						if (direction.offsetZ == -1) {
							minZ = 0.0F+2*px;
						}
					}
				}
			}
		}
		if (myDirections.size() == 2) {
			ForgeDirection direction = myDirections.get(0).getOpposite();
			if (direction.offsetX == 1) {
				maxX = 1.0F;
			}
			if (direction.offsetY == 1) {
				maxY = 1.0F;
			}
			if (direction.offsetZ == 1) {
				maxZ = 1.0F;
			}
			if (direction.offsetX == -1) {
				minX = 0.0F;
			}
			if (direction.offsetY == -1) {
				minY = 0.0F;
			}
			if (direction.offsetZ == -1) {
				minZ = 0.0F;
			}
		}
        return AxisAlignedBB.getBoundingBox(i+minX, j+minY, k+minZ, i+maxX, j+maxY, k+maxZ);
    }

    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1)
    {
        this.blockIcon = par1.registerIcon("friendscraft:blockBrass");
        this.sideIcon = par1.registerIcon("friendscraft:blockBrass" + "_pipe");

    }
}
