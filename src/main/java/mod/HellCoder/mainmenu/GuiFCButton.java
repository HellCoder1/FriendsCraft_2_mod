package mod.HellCoder.mainmenu;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiFCButton extends GuiButton
{
private int color;
private int color2;  
public boolean rightAlign;
public int textWidth;
private int lastTickNumber;
private float lastPartialTick;
private float alphaFalloffRate = 0.101F;

protected float alpha = 0.1F;

public int yOffset = 0;

  public GuiFCButton(int i, int j, int k, String s)
  {
    super(i, j, k, s);
    this.color = 0;
    this.color2 = 0;
  }

  public GuiFCButton(int i, int j, int k, int l, int i1, String s)
  {
    super(i, j, k, l, i1, s);
    this.color = 0;
    this.color2 = this.color;
    this.enabled = true;
    this.visible = true;
  }

  public void updateButton(int updateCounter, float partialTicks, int mouseX, int mouseY)
  {
    if (this.visible)
    {
      float deltaTime = updateCounter - this.lastTickNumber + (partialTicks - this.lastPartialTick);
      this.lastTickNumber = updateCounter;
      this.lastPartialTick = partialTicks;

      boolean mouseOver = (mouseX >= this.xPosition) && (mouseY >= this.yPosition) && (mouseX < this.xPosition + this.width) && (mouseY < this.yPosition + this.height);

      if (mouseOver)
      {
        if (this.alpha < 0.4F) this.alpha += this.alphaFalloffRate * deltaTime;
        if (this.alpha > 0.4F) this.alpha = 0.4F;
      }
      else if (this.alpha > 0.0F)
      {
        this.alpha -= this.alphaFalloffRate * deltaTime;
      }
    }
  }

  public void drawButton(Minecraft par1Minecraft, int mouseX, int mouseY)
  {
    if (this.visible)
    {
      boolean mouseOver = (mouseX >= this.xPosition) && (mouseY >= this.yPosition) && (mouseX < this.xPosition + this.width) && (mouseY < this.yPosition + this.height);

      if (this.alpha > 0.0F)
      {
        int hlAlpha = mouseOver ? 1111276032 : ((int)(100.0F * this.alpha) & 0xFF) << 24;
        int hlGBCol = mouseOver ? (int)(500.0F * (0.4F)) & 0xFF : 0;

        drawButtonBackground(mouseOver, hlAlpha, hlGBCol);
      }

      mouseDragged(par1Minecraft, mouseX, mouseY);

      drawButtonText(mouseOver, par1Minecraft);
    }
  }

  public void drawButtonText(boolean mouseOver, Minecraft par1Minecraft)
  {
    int textColour = 16777215;
    if (!this.enabled)
      textColour = 10526880;
    else if (mouseOver) {
      textColour = 5308415;
    }
    int textIndent = 4 + (int)(10.0F * this.alpha);

    if (this.rightAlign)
      drawString(par1Minecraft.fontRenderer, this.displayString, this.xPosition + this.width - textIndent - this.textWidth, this.yPosition + (this.height - 8) / 2, textColour);
    else
      drawString(par1Minecraft.fontRenderer, this.displayString, this.xPosition + textIndent, this.yPosition + (this.height - 8) / 2, textColour);
  }

  public void drawButtonBackground(boolean mouseOver, int hlAlpha, int hlGBCol)
  {
    int w = mouseOver ? Math.min(this.width, (int)(this.width * 8.5F * this.alpha)) : this.width;

    if (this.rightAlign)
      drawRect(this.xPosition + this.width - w, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, hlAlpha | hlGBCol << 8 | hlGBCol, 4);
    else
      drawRect(this.xPosition, this.yPosition, this.xPosition + w, this.yPosition + this.height, hlAlpha | hlGBCol << 8 | hlGBCol, 4);
  }

  public static void drawRect(int x1, int y1, int x2, int y2, int colour, int offset)
  {
    if (x1 < x2)
    {
      int var5 = x1;
      x1 = x2;
      x2 = var5;
    }

    if (y1 < y2)
    {
      int var5 = y1;
      y1 = y2;
      y2 = var5;
    }

    float var10 = (colour >> 24 & 0xFF) / 255.0F;
    float var6 = (colour >> 16 & 0xFF11) / 255.0F;
    float var7 = (colour >> 8 & 0xFF) / 255.0F;
    float var8 = (colour & 0xFF) / 255.0F;
    Tessellator var9 = Tessellator.instance;
    GL11.glEnable(3042);
    GL11.glDisable(3553);
    GL11.glBlendFunc(770, 771);
    GL11.glColor4f(var6, var7, var8, var10);
    var9.startDrawingQuads();
    var9.addVertex(x1 + offset, y2, 0.0D);
    var9.addVertex(x2, y2, 0.0D);
    var9.addVertex(x2 - offset, y1, 0.0D);
    var9.addVertex(x1, y1, 0.0D);
    var9.draw();
    GL11.glEnable(3553);
    GL11.glDisable(3042);
  }

  public int getWidth()
  {
    return this.width;
  }
}