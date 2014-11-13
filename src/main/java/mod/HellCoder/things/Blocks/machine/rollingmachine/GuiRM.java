package mod.HellCoder.things.Blocks.machine.rollingmachine;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mod.HellCoder.things.gui.tooltip.CustomToolTipElementFC;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cofh.core.gui.GuiBaseAdv;
import cofh.core.gui.element.TabEnergy;
import cofh.lib.gui.element.ElementEnergyStored;

@SideOnly(Side.CLIENT)
public class GuiRM extends GuiBaseAdv
{
    private static final ResourceLocation furnaceGuiTextures = new ResourceLocation("friendscraft", "textures/gui/GUIRollingMachine.png");
    private TileEntityRM tile;
    private float lastin = 0f;

    public GuiRM(InventoryPlayer par1InventoryPlayer, TileEntityRM par2TileEntityIronOven)
    {
        super(new ContainerRM(par1InventoryPlayer, par2TileEntityIronOven));
        this.tile = par2TileEntityIronOven;
    }
    
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
        String s = this.tile.hasCustomInventoryName() ? this.tile.getInventoryName() : I18n.format(this.tile.getInventoryName(), new Object[0]);
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    public void initGui()
    {
      super.initGui();    
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(furnaceGuiTextures);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        
        int i1 = this.tile.getCookProgressScaled(40);
        this.drawTexturedModalRect(k + 67, l + 31, 176, 0, i1 + 1, 18);
        
        int i2 = this.tile.getPressure(48);
        this.drawTexturedModalRect(k + 8, l + 17 + 48 - i2, 176, 68 - i2, 10, i2);
        addElement(new CustomToolTipElementFC(this, 8, 8, 10, 47, this.tile.pressure, this.tile.pressuremax, "Bar"));
        
        int i3 = this.tile.getEnergy(48);
        this.drawTexturedModalRect(k + 157, l + 17 + 48 - i3, 186, 68 - i3, 10, i3);
        addElement(new CustomToolTipElementFC(this, 157, 17, 10, 47, this.tile.rfStored.getEnergyStored(), this.tile.rfStored.getMaxEnergyStored(), "RF"));
    }
    
    @Override
    protected void updateElementInformation()
    {
      super.updateElementInformation();
    }
}