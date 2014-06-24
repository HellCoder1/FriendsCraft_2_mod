package mod.HellCoder.things.rollingmachine;

import mod.HellCoder.things.lib.RegBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RMRender implements ISimpleBlockRenderingHandler
{
	public static final RMRender INSTANCE = new RMRender();
		@Override
		public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
		{
			int meta=3;
			if (block.getIdFromBlock(block) == RegBlocks.rollingmachineactive.getIdFromBlock(RegBlocks.rollingmachineactive))meta=1;
			Tessellator tessellator = Tessellator.instance;
			block.setBlockBoundsForItemRender();
			renderer.setRenderBoundsFromBlock(block);
			GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, -1.0F, 0.0F);
			renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, meta));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 1.0F, 0.0F);
			renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, meta));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 0.0F, -1.0F);
			renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, meta));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 0.0F, 1.0F);
			renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, meta));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(-1.0F, 0.0F, 0.0F);
			renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, meta));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(1.0F, 0.0F, 0.0F);
			renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, meta));
			tessellator.draw();
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		}
		@Override
		public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
		{
			int direction = renderer.blockAccess.getBlockMetadata(x, y, z) & 3;
			if (direction > 0)
			{
				renderer.uvRotateTop = direction - 1;
			}
			else
			{
				renderer.uvRotateTop = 3;
			}
			boolean flag = renderer.renderStandardBlock(block, x, y, z);
			renderer.uvRotateTop = 0;
			return flag;
		}
		
		@Override
		public int getRenderId()
		{
			return 2105;
		}
		@Override
		public boolean shouldRender3DInInventory(int modelId) {
			return true;
		}
}