package mod.HellCoder.things.Blocks.render;

import java.util.ArrayList;

import mod.HellCoder.things.FriendsCraft2mod;
import mod.HellCoder.things.core.IMethaneTransporter;
import mod.HellCoder.things.lib.RegBlocks;
import mod.HellCoder.things.Blocks.blockpipe;
import mod.HellCoder.things.TileEntity.TileEntityMethanePipe;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidHandler;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockMethanePipeRender implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId,
			RenderBlocks renderer) {
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		float x = 0;
		float y = 0;
		float z = 0;
		IIcon icon = block.getBlockTextureFromSide(0);
		float baseMin = 5.0F/16.0F;
		float baseMax = 11.0F/16.0F;
		float ringMin = 4.0F/16.0F;
		float ringMax = 12.0F/16.0F;
		float px = 1.0F/16.0F;
		float minX = 0.0F+2*px;
		float maxX = 1.0F-2*px;
		float minY = baseMin;
		float maxY = baseMax;
		float minZ = baseMin;
		float maxZ = baseMax;

		block.setBlockBounds(minX, baseMin, baseMin, maxX, baseMax, baseMax);
	    renderer.setRenderBoundsFromBlock(block);
	 	this.drawSides(icon, block, renderer);

               
		block.setBlockBounds(0.0F, ringMin, ringMin, minX, ringMax, ringMax);
	    renderer.setRenderBoundsFromBlock(block);
	 	this.drawSides(((blockpipe)RegBlocks.methane_pipe).sideIcon, block, renderer);
		block.setBlockBounds(maxX, ringMin, ringMin, 1.0F, ringMax, ringMax);
	    renderer.setRenderBoundsFromBlock(block);
	 	this.drawSides(((blockpipe)RegBlocks.methane_pipe).sideIcon, block, renderer);

//	 	if (block == SteamcraftBlocks.valvePipe) {
//	 		block.setBlockBounds(4.5F*px, 1.0F-5.5F*px, baseMax+1*px, 1.0F-4.5F*px, 1.0F-4.5F*px, baseMax+2*px);
//		    renderer.setRenderBoundsFromBlock(block);
//		 	this.drawSides(((BlockPipe)SteamcraftBlocks.pipe).copperIcon, block, renderer);
//
//		 	block.setBlockBounds(4.5F*px, 4.5F*px, baseMax+1*px, 1.0F-4.5F*px, 5.5F*px, baseMax+2*px);
//		    renderer.setRenderBoundsFromBlock(block);
//		 	this.drawSides(((BlockPipe)SteamcraftBlocks.pipe).copperIcon, block, renderer);
//
//		 	block.setBlockBounds(4.5F*px, 5.5F*px, baseMax+1*px, 5.5F*px, 1.0F-5.5F*px, baseMax+2*px);
//		    renderer.setRenderBoundsFromBlock(block);
//		 	this.drawSides(((BlockPipe)SteamcraftBlocks.pipe).copperIcon, block, renderer);
//
//		 	block.setBlockBounds(1.0F-5.5F*px, 5.5F*px, baseMax+1*px, 1.0F-4.5F*px, 1.0F-5.5F*px, baseMax+2*px);
//		    renderer.setRenderBoundsFromBlock(block);
//		 	this.drawSides(((BlockPipe)SteamcraftBlocks.pipe).copperIcon, block, renderer);
//
//		 	block.setBlockBounds(5.5F*px, 7.5F*px, baseMax+1*px, 1.0F-5.5F*px, 8.5F*px, baseMax+2*px);
//		    renderer.setRenderBoundsFromBlock(block);
//		 	this.drawSides(((BlockPipe)SteamcraftBlocks.pipe).copperIcon, block, renderer);
//
//		 	block.setBlockBounds(7.5F*px, 5.5F*px, baseMax+1*px, 8.5F*px, 1.0F-5.5F*px, baseMax+2*px);
//		    renderer.setRenderBoundsFromBlock(block);
//		 	this.drawSides(((BlockPipe)SteamcraftBlocks.pipe).copperIcon, block, renderer);
//
//		 	block.setBlockBounds(6.5F*px, 6.5F*px, baseMax, 9.5F*px, 9.5F*px, baseMax+2*px);
//		    renderer.setRenderBoundsFromBlock(block);
//		 	this.drawSides(((BlockPipe)SteamcraftBlocks.pipe).copperIcon, block, renderer);
//		//	block.setBlockBounds(4.5F*px, 4.5F*px, baseMax+1*px, 1.0F-4.5F*px, 1.0F-4.5F*px, baseMax+2*px);
//		    //renderer.setRenderBoundsFromBlock(block);
//		 	//this.drawSides(((BlockPipe)SteamcraftBlocks.pipe).copperIcon, block, renderer);
//	 	}
	}

	private void drawSides(IIcon icon, Block block, RenderBlocks renderer) {
		int x = 0;
		int y = 0;
		int z = 0;
	    Tessellator tessellator = Tessellator.instance;

        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderFaceXPos(block, (double)((float)x), (double)y, (double)z, icon);
        tessellator.draw();
        
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderFaceXNeg(block, (double)((float)x), (double)y, (double)z, icon);
        tessellator.draw();
        
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        renderer.renderFaceZNeg(block, (double)x, (double)y, (double)z, icon);
        tessellator.draw();
        
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderFaceZPos(block, (double)x, (double)y, (double)z, icon);
        tessellator.draw();
        
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        renderer.renderFaceYNeg(block, (double)x, (double)y, (double)z, icon);
        tessellator.draw();
        
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderFaceYPos(block, (double)x, (double)y, (double)z, icon);
        tessellator.draw();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		TileEntityMethanePipe pipe = (TileEntityMethanePipe) world.getTileEntity(x, y, z);
		float baseMin = 5.0F/16.0F;
		float baseMax = 11.0F/16.0F;
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
			if (pipe.doesConnect(direction) && world.getTileEntity(pipe.xCoord+direction.offsetX, pipe.yCoord+direction.offsetY, pipe.zCoord+direction.offsetZ) != null) {
				TileEntity tile = world.getTileEntity(pipe.xCoord+direction.offsetX, pipe.yCoord+direction.offsetY, pipe.zCoord+direction.offsetZ);
				if (tile instanceof IMethaneTransporter) {
					IMethaneTransporter target = (IMethaneTransporter) tile;
					if (target.doesConnect(direction.getOpposite())) {
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
		if (myDirections.size() == 1) {
		    renderer.overrideBlockTexture = ((blockpipe)RegBlocks.methane_pipe).sideIcon;
		    minX = minX - px;
		    maxX = maxX + px;
		    minY = minY - px;
		    maxY = maxY + px;
		    minZ = minZ - px;
		    maxZ = maxZ + px;
		}
		if (myDirections.size() == 2) {
			ForgeDirection direction = myDirections.get(0).getOpposite();
			while (!pipe.doesConnect(direction)) {
				direction = ForgeDirection.getOrientation((direction.flag+1)%5);
			}
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
		block.setBlockBounds(minX, baseMin, baseMin, maxX, baseMax, baseMax);
	    renderer.setRenderBoundsFromBlock(block);
	    renderer.renderStandardBlock(block, x, y, z);

	    block.setBlockBounds(baseMin, baseMin, minZ, baseMax, baseMax, maxZ);
	    renderer.setRenderBoundsFromBlock(block);
	    renderer.renderStandardBlock(block, x, y, z);

	    block.setBlockBounds(baseMin, minY, baseMin, baseMax, maxY, baseMax);
	    renderer.setRenderBoundsFromBlock(block);
	    renderer.renderStandardBlock(block, x, y, z);

	    renderer.overrideBlockTexture = ((blockpipe)RegBlocks.methane_pipe).sideIcon;
	    if (minX == 2*px) {
			block.setBlockBounds(0.0F, ringMin, ringMin, minX, ringMax, ringMax);
		    renderer.setRenderBoundsFromBlock(block);
		    renderer.renderStandardBlock(block, x, y, z);
	    }
	    if (maxX == 1.0F-2*px) {
			block.setBlockBounds(maxX, ringMin, ringMin, 1.0F, ringMax, ringMax);
		    renderer.setRenderBoundsFromBlock(block);
		    renderer.renderStandardBlock(block, x, y, z);
	    }
	    if (minY == 2*px) {
			block.setBlockBounds(ringMin, 0.0F, ringMin, ringMax, minY, ringMax);
		    renderer.setRenderBoundsFromBlock(block);
		    renderer.renderStandardBlock(block, x, y, z);
	    }
	    if (maxY == 1.0F-2*px) {
			block.setBlockBounds(ringMin, maxY, ringMin, ringMax, 1.0F, ringMax);
		    renderer.setRenderBoundsFromBlock(block);
		    renderer.renderStandardBlock(block, x, y, z);
	    }
	    if (minZ == 2*px) {
			block.setBlockBounds(ringMin, ringMin, 0.0F, ringMax, ringMax,minZ);
		    renderer.setRenderBoundsFromBlock(block);
		    renderer.renderStandardBlock(block, x, y, z);
	    }
	    if (maxZ == 1.0F-2*px) {
			block.setBlockBounds(ringMin, ringMin, maxZ, ringMax, ringMax, 1.0F);
		    renderer.setRenderBoundsFromBlock(block);
		    renderer.renderStandardBlock(block, x, y, z);
	    }
	    renderer.clearOverrideBlockTexture();
    
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return RegBlocks.tubeRenderID;
	}

}
