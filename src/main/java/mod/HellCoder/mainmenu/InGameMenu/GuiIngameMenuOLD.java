package mod.HellCoder.mainmenu.InGameMenu;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiShareToLan;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.stats.StatList;

@SideOnly(Side.CLIENT)
public class GuiIngameMenuOLD extends GuiScreen
{
  private int updateCounter2;
  private int updateCounter;

  public void initGui()
  {
    this.updateCounter2 = 0;
    this.buttonList.clear();
    byte b0 = -16;
    boolean flag = true;
    this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + b0, I18n.format("menu.returnToMenu")));

    if (!this.mc.isIntegratedServerRunning())
    {
      ((GuiButton)this.buttonList.get(0)).displayString = I18n.format("menu.disconnect");
    }

    this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 24 + b0, I18n.format("menu.returnToGame")));
    this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + b0, 98, 20, I18n.format("menu.options")));
    GuiButton guibutton;
    this.buttonList.add(guibutton = new GuiButton(7, this.width / 2 + 2, this.height / 4 + 96 + b0, 98, 20, I18n.format("menu.shareToLan")));
    this.buttonList.add(new GuiButton(5, this.width / 2 - 100, this.height / 4 + 48 + b0, 98, 20, I18n.format("gui.achievements")));
    this.buttonList.add(new GuiButton(6, this.width / 2 + 2, this.height / 4 + 48 + b0, 98, 20, I18n.format("gui.stats")));
    guibutton.enabled = ((this.mc.isSingleplayer()) && (!this.mc.getIntegratedServer().getPublic()));
  }

  protected void actionPerformed(GuiButton par1GuiButton)
  {
    switch (par1GuiButton.id)
    {
    case 0:
      this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
      break;
    case 1:
      par1GuiButton.enabled = false;
//      this.mc.statFileWriter.readStat(StatList.leaveGameStat, 1);
      this.mc.theWorld.sendQuittingDisconnectingPacket();
      this.mc.loadWorld((WorldClient)null);
      this.mc.displayGuiScreen(new GuiMainMenu());
    case 2:
    case 3:
    default:
      break;
    case 4:
      this.mc.displayGuiScreen((GuiScreen)null);
      this.mc.setIngameFocus();
      break;
    case 5:
//      this.mc.displayGuiScreen(new GuiAchievements(this.mc.statFileWriter));
      break;
    case 6:
//      this.mc.displayGuiScreen(new GuiStats(this, this.mc.statFileWriter));
      break;
    case 7:
      this.mc.displayGuiScreen(new GuiShareToLan(this));
    }
  }

  public void updateScreen()
  {
    super.updateScreen();
    this.updateCounter += 1;
  }

  public void drawScreen(int par1, int par2, float par3)
  {
    drawDefaultBackground();
    drawCenteredString(this.fontRendererObj, "Game menu", this.width / 2, 40, 16777215);
    super.drawScreen(par1, par2, par3);
  }
}