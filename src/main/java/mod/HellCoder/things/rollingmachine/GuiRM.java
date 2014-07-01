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
        String pressure = String.valueOf(this.tile.pressure);
        String energy = String.valueOf(this.tile.storage.getEnergyStored());
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
        this.fontRendererObj.drawString(pressure + " bar", 8, 8, 4210752);
        this.fontRendererObj.drawString(energy + " RF", 139, 8, 4210752);
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
        this.drawTexturedModalRect(k + 67, l + 31, 176, 0, i1 + 1, 18);
        
        int i2 = this.tile.getPressure(48);
        this.drawTexturedModalRect(k + 8, l + 17 + 48 - i2, 177, 68 - i2, 10, i2);

        int i3 = this.tile.getEnergy(48);
        this.drawTexturedModalRect(k + 157, l + 17 + 48 - i3, 189, 68 - i3, 10, i3);        
//      this.drawTexturedModalRect(k + 56, l + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 2);

    }
}