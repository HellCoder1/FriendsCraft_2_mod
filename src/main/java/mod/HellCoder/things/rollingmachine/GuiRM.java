package mod.HellCoder.things.rollingmachine;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiRM extends GuiContainer
{
    private static final ResourceLocation furnaceGuiTextures = new ResourceLocation("friendscraft", "textures/gui/GUIRollingMachine.png");
    private TileEntityRM tile;
    private float lastin = 0f;

    public GuiRM(InventoryPlayer par1InventoryPlayer, TileEntityRM par2TileEntityIronOven)
    {
        super(new ContainerRM(par1InventoryPlayer, par2TileEntityIronOven));
        this.tile = par2TileEntityIronOven;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
        String s = this.tile.hasCustomInventoryName() ? this.tile.getInventoryName() : I18n.format(this.tile.getInventoryName(), new Object[0]);
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(furnaceGuiTextures);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        int i1;
        float charge;

        i1 = this.tile.getCookProgressScaled(40);
        this.drawTexturedModalRect(k + 67, l + 31, 176, 0, i1 + 1, 18);
        
        charge = tile.getEnergyProgress(500);
        drawTexturedModalRect(k + 156, l + 16 + 49 - charge, 127, 20 - charge, 12, charge + 1);

    }
    
	private void drawTexturedModalRect(int par1, float par2, int par3, float par4, int par5, float par6) {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)(par1 + 0), (double)(par2 + par6), (double)this.zLevel, (double)((float)(par3 + 0) * f), (double)((float)(par4 + par6) * f1));
        tessellator.addVertexWithUV((double)(par1 + par5), (double)(par2 + par6), (double)this.zLevel, (double)((float)(par3 + par5) * f), (double)((float)(par4 + par6) * f1));
        tessellator.addVertexWithUV((double)(par1 + par5), (double)(par2 + 0), (double)this.zLevel, (double)((float)(par3 + par5) * f), (double)((float)(par4 + 0) * f1));
        tessellator.addVertexWithUV((double)(par1 + 0), (double)(par2 + 0), (double)this.zLevel, (double)((float)(par3 + 0) * f), (double)((float)(par4 + 0) * f1));
        tessellator.draw();
		
	}

	private void drawPin(float centerX, float centerY, float verticalOffset,  float size, int texOffsetX, int texOffsetY, int texWidth, int texHeight){
		float fx = 0.00390625F; // image is 256x256 pixels, so this conversion factor normalizes to a number from 0 to 1
		float fy = 0.00390625F;

		float needleWidth = 0.3f*size;
		float backLength = needleWidth;

		float pointX = centerX+size;
		float pointY = centerY-verticalOffset;
		float backX = centerX-backLength;
		float backY = centerY-verticalOffset;
		float topX = centerX;
		float topY = centerY-needleWidth-verticalOffset;
		float botX = centerX;
		float botY = centerY+needleWidth-verticalOffset;

		int pointU = texOffsetX;
		int pointV = texOffsetY;
		int backU = texOffsetX+texWidth;
		int backV = texOffsetY+texHeight;
		int topU = texOffsetX;
		int topV = texOffsetY+texHeight;
		int botU = texOffsetX+texWidth;
		int botV = texOffsetY;

		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV((double) pointX,
				(double) pointY, (double) this.zLevel,
				(double) ((float) pointU * fx),
				(double) ((float) pointV * fy));
		tessellator.addVertexWithUV((double) topX,
				(double) topY, (double) this.zLevel,
				(double) ((float) topU * fx),
				(double) ((float) topV * fy));
		tessellator.addVertexWithUV((double) backX,
				(double) backY, (double) this.zLevel,
				(double) ((float) backU * fx),
				(double) ((float) backV * fy));
		tessellator.addVertexWithUV((double) botX, (double) botY,
				(double) this.zLevel, (double) ((float) botU * fx),
				(double) ((float) botV * fy));
		tessellator.draw();
	}
}