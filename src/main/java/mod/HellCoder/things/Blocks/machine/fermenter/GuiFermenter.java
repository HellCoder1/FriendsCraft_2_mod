package mod.HellCoder.things.Blocks.machine.fermenter;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mod.HellCoder.things.gui.tooltip.CustomToolTipElementFC;
import mod.HellCoder.things.gui.tooltip.CustomeToolTipFluid;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import cofh.core.gui.GuiBaseAdv;
import cofh.core.gui.element.TabEnergy;
import cofh.core.gui.element.TabInfo;
import cofh.lib.gui.element.ElementEnergyStored;
import cofh.lib.gui.element.ElementFluidTank;
import cofh.lib.render.RenderHelper;
import cofh.lib.util.helpers.StringHelper;

@SideOnly(Side.CLIENT)
public class GuiFermenter extends GuiBaseAdv
{
    private static final ResourceLocation Textures = new ResourceLocation("friendscraft", "textures/gui/GUIFermenter.png");
    
    private TileEntityFermenter tile;   

    public GuiFermenter(InventoryPlayer par1InventoryPlayer, TileEntityFermenter TileFermenter)
    {
        super(new ContainerFermenter(par1InventoryPlayer, TileFermenter), Textures);
        this.tile = TileFermenter;
    }

    @Override
    public void initGui()
    {
      super.initGui();     
      addTab(new TabInfo(this, "[WIP] кароч скоро заработает"));
    }
    
    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
        String s = this.tile.hasCustomInventoryName() ? this.tile.getInventoryName() : I18n.format(this.tile.getInventoryName(), new Object[0]);
        String fluid = String.valueOf(this.tile.tank.getFluidAmount());
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
    	super.drawGuiContainerBackgroundLayer(p_146976_1_, p_146976_2_, p_146976_3_);
    	
    	drawFluid1(this.tile.tank.getFluid(), guiLeft + 95, guiTop + 18, 32, 60, this.tile.tank.getCapacity());
    	
        this.mc.renderEngine.bindTexture(Textures);
        
        addElement(new CustomeToolTipFluid(this, 95, 18, 32, 60, this.tile.tank));
        this.drawTexturedModalRect(guiLeft + 95, guiTop + 18, 176, 68, 4, 56);
        
        int i1 = this.tile.getCookProgressScaled(40);
        this.drawTexturedModalRect(guiLeft + 47, guiTop + 31, 176, 0, i1 + 1, 18);
        
        int i2 = this.tile.getHeat(48);
        this.drawTexturedModalRect(guiLeft + 8, guiTop + 17 + 48 - i2, 176, 68 - i2, 10, i2);
        addElement(new CustomToolTipElementFC(this, 8, 17, 10, 48, this.tile.heat, "C"));
        
        int i3 = this.tile.getEnergy(48);
        this.drawTexturedModalRect(guiLeft + 157, guiTop + 17 + 48 - i3, 186, 68 - i3, 10, i3);
        addElement(new CustomToolTipElementFC(this, 157, 17, 10, 47, this.tile.rfStored.getEnergyStored(), this.tile.rfStored.getMaxEnergyStored(), "RF")); 
    }
    
    public void drawFluid1(FluidStack fluid, int x, int y, int width, int height, int maxCapacity) {
		if (fluid == null || fluid.getFluid() == null) {
			return;
		}
		IIcon icon = fluid.getFluid().getIcon(fluid);
		mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
		setGLColorFromInt(fluid.getFluid().getColor(fluid));
		int fullX = width / 16;
		int fullY = height / 16;
		int lastX = width - fullX * 16;
		int lastY = height - fullY * 16;
		int level = fluid.amount * height / maxCapacity;
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