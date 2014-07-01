package mod.HellCoder.mainmenu;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class FC_TUGButton extends GuiButton
{
  private static final ResourceLocation TEXTURE_TUG_BUTTON = new ResourceLocation("friendscraft", "textures/menu/tug.png");

  public FC_TUGButton(int par1, int par2, int par3, int par4, int par5) {
    super(par1, par2, par3, par4, par5, "");
  }

  public void drawButton(Minecraft par1Minecraft, int mouseX, int mouseY)
  {
    if (this.enabled)
    {
      FontRenderer fontrenderer = par1Minecraft.fontRenderer;

      GL11.glPushMatrix();

      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);

      Minecraft mc = Minecraft.getMinecraft();
//      ScaledResolution scaledresolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
//      int width = scaledresolution.getScaledWidth();
//      int height = scaledresolution.getScaledHeight();

      boolean flag = (mouseX >= this.xPosition) && (mouseY >= this.yPosition) && (mouseX < this.xPosition + this.width) && (mouseY < this.yPosition + this.height);
      float R = 1.0F;
      float G = 1.0F;
      float B = 1.0F;
      float scaling = 0.68F;

      if (flag)
      {
        scaling = 0.70F;
      }

      GL11.glColor4f(R, G, B, 1.0F);
      GL11.glTranslatef(55 - 250.0F * scaling / 2.0F, 1.0F - 50, 1.0F);
      GL11.glScalef(scaling, scaling, scaling);
      
      mc.renderEngine.bindTexture(TEXTURE_TUG_BUTTON);

      drawTexturedModalRect(0, 0, 0, 0, 195, 184);

      GL11.glDisable(3042);

      GL11.glPopMatrix();

      this.field_146123_n = ((mouseX >= this.xPosition) && (mouseY >= this.yPosition) && (mouseX < this.xPosition + this.width) && (mouseY < this.yPosition + this.height));
      int k = getHoverState(this.field_146123_n);
      mouseDragged(par1Minecraft, mouseX, mouseY);
      int l = 14737632;

      if (!this.enabled)
      {
        l = -6250336;
      }
      else if (this.field_146123_n)
      {
        l = 16777120;
      }

      drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, l);
    }
  }
}