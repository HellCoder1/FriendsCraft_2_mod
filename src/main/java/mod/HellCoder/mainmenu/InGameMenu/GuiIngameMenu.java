package mod.HellCoder.mainmenu.InGameMenu;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.texture.TextureManager;

import org.lwjgl.opengl.GL11;

public class GuiIngameMenu extends GuiIngameMenuOLD
{
  protected GuiButtonPanel buttonPanel;
  protected int updateCounter;
  protected boolean begunTweening;
  protected float tweenTime;

  public void initGui()
  {
    super.initGui();

    this.buttonPanel = new GuiButtonPanel(100, GuiButtonPanel.AnchorType.BottomLeft, 22, 20, 150, 100, 16, this.width, this.height);

    while (this.buttonList.size() > 0)
    {
      GuiButton button = (GuiButton)this.buttonList.remove(this.buttonList.size() - 1);
      GuiButton newButton = this.buttonPanel.addButton(button.displayString, button.id);

      newButton.enabled = button.enabled;
    }

    this.buttonList.clear();
    this.buttonList.add(this.buttonPanel);
  }

  protected void actionPerformed(GuiButton par1GuiButton)
  {
    GuiButtonMainMenu pressedButton = this.buttonPanel.getPressedButton();
    super.actionPerformed(pressedButton);
  }

  public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    if (!this.begunTweening)
    {
      this.tweenTime = (-partialTicks);
      this.begunTweening = true;
    }

    float tweenPct = Math.min(0.5F, (this.updateCounter + partialTicks - this.tweenTime) / 20.0F);
    float tweenAmount = (float)Math.sin(tweenPct * 3.141592653589793D);

    int colour = (int)(192.0F * tweenAmount) << 24;

    GL11.glPushMatrix();
    GL11.glTranslatef(-10.0F + tweenAmount * 20.0F, 0.0F, 0.0F);

    drawGradientRect(10, 0, 184, this.height, colour, colour);
    drawRect(10, 0, 11, this.height, -1);

    GL11.glTranslatef(-10.0F + tweenAmount * 10.0F, 0.0F, 0.0F);
    GL11.glPushMatrix();
    GL11.glTranslatef(27.0F, 40.0F, 0.0F);
    GL11.glScalef(2.0F, 2.0F, 1.0F);
    this.mc.renderEngine.tick();
    drawString(this.fontRendererObj, "Game Menu", 0, 0, 16997215);
    GL11.glPopMatrix();

    this.buttonPanel.updateButtons(this.updateCounter, partialTicks, mouseX, mouseY);
    this.buttonPanel.drawButton(this.mc, mouseX, mouseY);

    GL11.glPopMatrix();
  }

  public void updateScreen()
  {
    super.updateScreen();
    this.updateCounter += 1;
  }
}