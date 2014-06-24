package mod.HellCoder.things.Blocks.render;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import java.awt.Color;

import mod.HellCoder.things.Blocks.BlockRenderer;
import mod.HellCoder.things.Blocks.DigaBlock;
import mod.HellCoder.things.lib.RegBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

public class DigaBlockRender extends BlockRenderer
implements ISimpleBlockRenderingHandler
{
public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
{
  block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
  
  renderer.setRenderBoundsFromBlock(block);
    drawFaces(renderer, block, ((mod.HellCoder.things.Blocks.DigaBlock)block).icon[0], false);
    Color c = new Color(16777215);
    float r = c.getRed() / 255.0F;
    float g = c.getGreen() / 255.0F;
    float b = c.getBlue() / 255.0F;
    GL11.glColor3f(r, g, b);
    drawFaces(renderer, block, ((mod.HellCoder.things.Blocks.DigaBlock)block).icon[1], false);
    GL11.glColor3f(1.0F, 1.0F, 1.0F);
  
}

public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
{
  int bb = setBrightness(world, x, y, z, block);
//  int metadata = world.getBlockMetadata(x, y, z);
  block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
  renderer.setRenderBoundsFromBlock(block);
  renderer.renderStandardBlock(block, x, y, z);

    Tessellator t = Tessellator.instance;
    t.setColorOpaque_I(16777215);
    t.setBrightness(160);

    renderAllSides(world, x, y, z, block, renderer, ((mod.HellCoder.things.Blocks.DigaBlock)block).icon[1], false);


  renderer.clearOverrideBlockTexture();
  block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
  renderer.setRenderBoundsFromBlock(block);
  return true;
}

public boolean shouldRender3DInInventory(int modelId)
{
  return true;
}

public int getRenderId()
{
  return RegBlocks.DigaBlockRI;
}
}
