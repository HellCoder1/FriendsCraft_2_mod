package mod.HellCoder.things.Blocks.machine.fermenter;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiFermenter extends GuiContainer
{
    private static final ResourceLocation furnaceGuiTextures = new ResourceLocation("friendscraft", "textures/gui/GUIFermenter.png");
    private static final ResourceLocation BLOCK_TEXTURE = TextureMap.locationBlocksTexture;
    
    private TileEntityFermenter tile;   

    public GuiFermenter(InventoryPlayer par1InventoryPlayer, TileEntityFermenter par2TileEntityIronOven)
    {
        super(new ContainerFermenter(par1InventoryPlayer, par2TileEntityIronOven));
        this.tile = par2TileEntityIronOven;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
        String s = this.tile.hasCustomInventoryName() ? this.tile.getInventoryName() : I18n.format(this.tile.getInventoryName(), new Object[0]);
        String pressure = String.valueOf(this.tile.pressure);
        String energy = String.valueOf(this.tile.mjStored);
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
        this.fontRendererObj.drawString(pressure + " C", 8, 8, 4210752);
        this.fontRendererObj.drawString(energy + " MJ", 137, 8, 4210752);
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
        float Pressure;
        
        i1 = this.tile.getCookProgressScaled(40);
        this.drawTexturedModalRect(k + 47, l + 31, 176, 0, i1 + 1, 18);
        
        int i2 = this.tile.getHeat(48);
        this.drawTexturedModalRect(k + 8, l + 17 + 48 - i2, 176, 68 - i2, 10, i2);

        int i3 = this.tile.getEnergy(48);
        this.drawTexturedModalRect(k + 157, l + 17 + 48 - i3, 186, 68 - i3, 10, i3);        

        drawFluid(this.tile.tank.getFluid(), this.tile.getFluidScale(59), k + 95, l + 19, 31, 59);
        
//        drawTexturedModalRect(k + 95, l + 18, 176, 68, 31, 59);
    }
    
	private void drawFluid(FluidStack fluid, int level, int x, int y, int width, int height) {
		if (fluid == null || fluid.getFluid() == null) {
			return;
		}
		IIcon icon = fluid.getFluid().getIcon(fluid);
		mc.renderEngine.bindTexture(BLOCK_TEXTURE);
		this.setGLColorFromInt(fluid.getFluid().getColor(fluid));
		int fullX = width / 16;
		int fullY = height / 16;
		int lastX = width - fullX * 16;
		int lastY = height - fullY * 16;
		int fullLvl = (height - level) / 16;
		int lastLvl = (height - level) - fullLvl * 16;
		for (int i = 0; i < fullX; i++) {
			for (int j = 0; j < fullY; j++) {
				if (j >= fullLvl) {
					drawCutIcon(icon, x + i * 16, y + j * 16, 16, 16, j == fullLvl ? lastLvl : 0);
				}
			}
		}
		for (int i = 0; i < fullX; i++) {
			drawCutIcon(icon, x + i * 16, y + fullY * 16, 16, lastY, fullLvl == fullY ? lastLvl : 0);
		}
		for (int i = 0; i < fullY; i++) {
			if (i >= fullLvl) {
				drawCutIcon(icon, x + fullX * 16, y + i * 16, lastX, 16, i == fullLvl ? lastLvl : 0);
			}
		}
		drawCutIcon(icon, x + fullX * 16, y + fullY * 16, lastX, lastY, fullLvl == fullY ? lastLvl : 0);
	}
	
	//The magic is here
		private void drawCutIcon(IIcon icon, int x, int y, int width, int height, int cut) {
			Tessellator tess = Tessellator.instance;
			tess.startDrawingQuads();
			tess.addVertexWithUV(x, y + height, zLevel, icon.getMinU(), icon.getInterpolatedV(height));
			tess.addVertexWithUV(x + width, y + height, zLevel, icon.getInterpolatedU(width), icon.getInterpolatedV(height));
			tess.addVertexWithUV(x + width, y + cut, zLevel, icon.getInterpolatedU(width), icon.getInterpolatedV(cut));
			tess.addVertexWithUV(x, y + cut, zLevel, icon.getMinU(), icon.getInterpolatedV(cut));
			tess.draw();
		}
		
		public static void setGLColorFromInt(int color) {
			float red = (color >> 16 & 255) / 255.0F;
			float green = (color >> 8 & 255) / 255.0F;
			float blue = (color & 255) / 255.0F;
			GL11.glColor4f(red, green, blue, 1.0F);
		}
}