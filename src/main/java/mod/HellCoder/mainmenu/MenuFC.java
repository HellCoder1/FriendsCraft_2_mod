package mod.HellCoder.mainmenu;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mod.HellCoder.HellCoderCore.Utils.EntityUtils;
import mod.HellCoder.HellCoderCore.Utils.FCLog;
import mod.HellCoder.HellCoderCore.Utils.player.FakeWorld;
import mod.HellCoder.mainmenu.InGameMenu.GuiButtonMainMenu;
import mod.HellCoder.mainmenu.InGameMenu.GuiButtonPanel;
import mod.HellCoder.HellCoderCore.Utils.player.FPlayer;
import mod.HellCoder.things.lib.RegItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovementInputFromOptions;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.util.List;

@SideOnly(Side.CLIENT)
public class MenuFC extends GuiMainMenuOLD
{
  private static final ResourceLocation TEXTURE_LOGO = new ResourceLocation("friendscraft", "textures/menu/logo.png");

  public static final ResourceLocation TEXTURE_MAIN_BG = new ResourceLocation("friendscraft", "textures/menu/MAINmenu_background.png");

  public static int updateCounter;

  String field_92025_p;
  
  public static final String field_96138_a = "Please click " + EnumChatFormatting.UNDERLINE + "here" + EnumChatFormatting.RESET + " for more information.";

  static World world;
  FPlayer fakePlayer;
  boolean erroredOut = false;

  int field_92024_r;
  int field_92022_t;
  int field_92021_u;
  int field_92020_v;
  int field_92019_w;

  protected GuiButtonPanel buttonPanel;

  @Override
  public void initGui()
  {
    super.initGui();

    this.buttonPanel = new GuiButtonPanel(100, GuiButtonPanel.AnchorType.BottomLeft, 0, 50, 150, 100, 20, this.width, this.height);

    while (this.buttonList.size() > 0)
    {
      GuiButton button = (GuiButton)this.buttonList.remove(this.buttonList.size() - 1);
      GuiButton newButton = this.buttonPanel.addButton(button.displayString, button.id);

      newButton.enabled = button.enabled;
    }

      this.buttonList.clear();
      this.buttonList.add(this.buttonPanel);

            try {
                boolean createNewWorld = world == null;

                if (createNewWorld)
                    world = new FakeWorld();

                if (createNewWorld || fakePlayer == null) {
                    fakePlayer = new FPlayer(mc, world, mc.getSession(), null, null);
                    fakePlayer.dimension = 0;
                    fakePlayer.movementInput = new MovementInputFromOptions(mc.gameSettings);
                    fakePlayer.eyeHeight = 1.82F;
                    setItem(fakePlayer);
                }

                RenderManager.instance.cacheActiveRenderInfo(world, mc.renderEngine, mc.fontRenderer, fakePlayer, fakePlayer, mc.gameSettings, 0.0F);
            } catch (Throwable e) {
                FCLog.severe("Main menu mob rendering encountered a serious error and has been disabled for the remainder of this session.");
                e.printStackTrace();
                erroredOut = true;
                fakePlayer = null;
                world = null;
            }
  }

  @Override
  protected void actionPerformed(GuiButton par1GuiButton)
  {
      GuiButtonMainMenu pressedButton = this.buttonPanel.getPressedButton();
      super.actionPerformed(pressedButton);
  }

   public void updateScreen()
   {
       updateCounter += 1;
   }

  public void drawScreen(int par1, int par2, float par3)
  {
	drawMainPic();
    byte b0 = 30;
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

    GL11.glPushMatrix();

    GL11.glTranslatef(215.0F, 50.0F, 0.0F);
    GL11.glRotatef(-20.0F, 0.0F, 0.0F, 1.0F);

    GL11.glPopMatrix();

    List brandings = Lists.reverse(FMLCommonHandler.instance().getBrandings(true));
    for (int i = 0; i < brandings.size(); i++)
    {
      String brd = (String)brandings.get(i);
      if ((!Strings.isNullOrEmpty(brd)))
      {
        drawString(this.fontRendererObj, brd, this.width - 2 - this.fontRendererObj.getStringWidth(brd), this.height - (10 + i * (this.fontRendererObj.FONT_HEIGHT + 1)), 16777215);
      }
    }

    String s1 = "Copyright Mojang AB.";
    drawString(this.fontRendererObj, s1, 2, this.height - 10, 16777215);

    if ((this.field_92025_p != null) && (this.field_92025_p.length() > 0))
    {
      drawRect(this.field_92022_t - 2, this.field_92021_u - 2, this.field_92020_v + 2, this.field_92019_w - 1, 1428160512);
      drawString(this.fontRendererObj, this.field_92025_p, this.field_92022_t, this.field_92021_u, 16777215);
      drawString(this.fontRendererObj, field_96138_a, (this.width - this.field_92024_r) / 2, ((GuiButtonMainMenu)this.buttonList.get(0)).yPosition - 12, 16777215);
    }
    drawLogo(b0);

      if (world != null && fakePlayer != null) {
          ScaledResolution sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
          int distanceToSide = ((mc.currentScreen.width / 2) - 98) / 2;
          float targetHeight = (float) (sr.getScaledHeight_double() / 2.5F) / 1.8F;
          EntityUtils.drawEntityOnScreenAtRotation(
                  sr.getScaledWidth() - distanceToSide,
                  (sr.getScaledHeight() / 2) + 30,
                  targetHeight,
                  0, updateCounter - 0.7F,
                  fakePlayer);
      }

    super.drawScreen(par1, par2, par3);

    this.buttonPanel.updateButtons(this.updateCounter, par3, par1, par2);
    this.buttonPanel.drawButton(this.mc, par1, par2);
  }

  public static void drawMainPic()
  {
        GL11.glPushMatrix();

        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        int width2 = scaledresolution.getScaledWidth();
        int height2 = scaledresolution.getScaledHeight();

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(3553);
        mc.renderEngine.bindTexture(TEXTURE_MAIN_BG);

        int y = 0;

        float u = 0.0F;
        float v = 0.0F;
        float u1 = 1.0F;
        float v1 = 1.0F;
        GL11.glBegin(7);
        GL11.glTexCoord2f(u, v);
        GL11.glVertex2f(0.0F, 0.0F);
        GL11.glTexCoord2f(u, v1);
        GL11.glVertex2f(0.0F, height2);
        GL11.glTexCoord2f(u1, v1);
        GL11.glVertex2f(width2, y + height2);
        GL11.glTexCoord2f(u1, v);
        GL11.glVertex2f(width2, 0.0F);
        GL11.glEnd();

        GL11.glPopMatrix();
  }

  public void drawLogo(int b0)
  {
        GL11.glPushMatrix();

        this.mc.renderEngine.bindTexture(TEXTURE_LOGO);

        float scale = 1.0F;

        GL11.glEnable(3042);

        GL11.glTranslatef(this.width / 2 - 250.0F * scale / 2.0F, b0 - 30, 1.0F);
        GL11.glScalef(1.0F, 1.0F, 1.0F);
        drawTexturedModalRect(0, 0, 0, 0, 250, 114);

        GL11.glDisable(3042);

        GL11.glPopMatrix();
  }

  private static void setItem(EntityLivingBase ent)
  {
      try
      {
          if (ent instanceof AbstractClientPlayer)
              ent.setCurrentItemOrArmor(0, new ItemStack(RegItems.NagibatorSword));
      }
      catch (Throwable e)
      {
          e.printStackTrace();
      }
  }
}