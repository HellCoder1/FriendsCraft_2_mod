package mod.HellCoder.mainmenu.InGameMenu;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class GuiButtonPanel extends GuiButton
{
  protected AnchorType anchorType = AnchorType.BottomLeft;

  protected List<GuiButtonMainMenu> buttons = new ArrayList();
  protected GuiButtonMainMenu pressedButton;
  protected int xOffset;
  protected int yOffset;
  protected int buttonSpacing = 16;
  protected int nextButtonId;

  public GuiButtonPanel(int buttonIdBase, AnchorType anchorType, int xOffset, int yOffset, int width, int height, int buttonSpacing, int containerWidth, int containerHeight)
  {
    super(buttonIdBase, 0, 0, width, height, null);
    this.anchorType = anchorType;
    this.xOffset = xOffset;
    this.yOffset = yOffset;
    this.buttonSpacing = buttonSpacing;
    this.nextButtonId = (buttonIdBase + 1);

    updatePosition(containerWidth, containerHeight);
  }

  public GuiButtonMainMenu addButton(String displayText, int buttonId)
  {
    GuiButtonMainMenu button = new GuiButtonMainMenu(buttonId, displayText);
    this.buttons.add(button);
    updateButtonPositions();
    return button;
  }

  public void updatePosition(int containerWidth, int containerHeight)
  {
    this.yPosition = ((this.anchorType == AnchorType.TopRight) || (this.anchorType == AnchorType.TopLeft) ? this.yOffset : containerHeight - this.height - this.yOffset);
    this.xPosition = ((this.anchorType == AnchorType.TopLeft) || (this.anchorType == AnchorType.BottomLeft) ? this.xOffset : containerWidth - this.width - this.xOffset);

    updateButtonPositions();
  }

  private void updateButtonPositions()
  {
    int buttonXPosition;
    if ((this.anchorType == AnchorType.TopLeft) || (this.anchorType == AnchorType.BottomLeft))
    {
      buttonXPosition = 0;

      for (GuiButtonMainMenu button : this.buttons)
      {
        button.rightAlign = false;
        button.xPosition = buttonXPosition;
      }
    }
    else
    {
      for (GuiButtonMainMenu button : this.buttons)
      {
        button.rightAlign = true;
        button.xPosition = (this.width - button.getWidth());
      }
    }
  }

  public void drawButton(Minecraft minecraft, int mouseX, int mouseY)
  {
    mouseX -= this.xPosition;
    mouseY -= this.yPosition;

    int buttonYPosition = 0;
    int visibleButtonCount = 0;

    for (GuiButtonMainMenu button : this.buttons) {
      if (button.enabled) visibleButtonCount++;
    }
    if ((this.anchorType == AnchorType.BottomLeft) || (this.anchorType == AnchorType.BottomRight))
    {
      buttonYPosition = this.height - this.buttonSpacing * visibleButtonCount;
    }

    GL11.glPushMatrix();
    GL11.glTranslatef(this.xPosition, this.yPosition, 0.0F);

    for (GuiButtonMainMenu button : this.buttons)
    {
      if (button.enabled)
      {
        button.yPosition = (buttonYPosition + button.yOffset);
        buttonYPosition += this.buttonSpacing;
      }

      button.drawButton(minecraft, mouseX, mouseY);
    }

    GL11.glPopMatrix();
  }

  public void updateButtons(int updateCounter, float partialTicks, int mouseX, int mouseY)
  {
    mouseX -= this.xPosition;
    mouseY -= this.yPosition;

    for (GuiButtonMainMenu button : this.buttons)
      button.updateButton(updateCounter, partialTicks, mouseX, mouseY);
  }

  public GuiButtonMainMenu getPressedButton()
  {
    return this.pressedButton;
  }

  public boolean mousePressed(Minecraft minecraft, int mouseX, int mouseY)
  {
    mouseX -= this.xPosition;
    mouseY -= this.yPosition;

    for (GuiButtonMainMenu button : this.buttons)
    {
      if (button.mousePressed(minecraft, mouseX, mouseY))
      {
        this.pressedButton = button;
        return true;
      }
    }

    return false;
  }

  public void mouseReleased(int mouseX, int mouseY)
  {
    if (this.pressedButton != null)
    {
      this.pressedButton.mouseReleased(mouseX, mouseY);
      this.pressedButton = null;
    }
  }

  public static enum AnchorType
  {
    TopLeft, 
    BottomLeft, 
    TopRight, 
    BottomRight;
  }
}