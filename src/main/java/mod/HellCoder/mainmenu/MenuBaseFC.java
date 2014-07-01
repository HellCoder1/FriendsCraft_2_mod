package mod.HellCoder.mainmenu;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import cpw.mods.fml.client.GuiModList;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import mod.HellCoder.mainmenu.InGameMenu.GuiButtonMainMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonLanguage;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;

import org.apache.commons.io.Charsets;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

@SideOnly(Side.CLIENT)
public class MenuBaseFC extends MenuBase
{
  private static final ResourceLocation TEXTURE_LOGO = new ResourceLocation("friendscraft", "textures/menu/logo.png");

  private static final ResourceLocation TEXTURE_MAIN_BG = new ResourceLocation("friendscraft", "textures/menu/MAINmenu_background.png");
  
  private static final ResourceLocation TEXTURE_TUG_BG = new ResourceLocation("friendscraft", "textures/menu/tug/menu_background.jpg");

  private static final ResourceLocation TEXTURE_TUG_BANNER = new ResourceLocation("friendscraft", "textures/menu/tug/banner.png");

  private static final ResourceLocation TEXTURE_TUG_SCREENY = new ResourceLocation("friendscraft", "textures/menu/tug/screeny.png");

  private static final ResourceLocation TEXTURE_SEED_AND_MOA = new ResourceLocation("friendscraft", "textures/menu/tug/seed_and_moa.jpg");

  private static final Random rand = new Random();
  private static FC_TUGButton TUGbutton;
  private float updateCounter = 0.0F;

  private String splashText = "missingno";
  private GuiFCButton buttonResetDemo;
  private GuiButton field_73883_a = null;

  private int panoramaTimer = 0;

  private DynamicTexture viewportTexture;
  private boolean field_96141_q = true;
  private static boolean field_96140_r = false;
  private static boolean field_96139_s = false;
  private String field_92025_p;

  private static final ResourceLocation splashTexts = new ResourceLocation("texts/splashes.txt");
  
  public static final String field_96138_a = "Please click " + EnumChatFormatting.UNDERLINE + "here" + EnumChatFormatting.RESET + " for more information.";
  private int field_92024_r;
  private int field_92023_s;
  private int field_92022_t;
  private int field_92021_u;
  private int field_92020_v;
  private int field_92019_w;
  private GuiFCButton fmlModButton = null;
  private GuiButtonMainMenu hideOptionsButton = null;
  private GuiFCButton optionsButton = null;
  private GuiFCButton quitButton = null;
  private GuiFCButton singleplayerButton = null;
  private GuiFCButton multiplayerButton = null;
  private GuiFCButton backButton = null;


  private boolean TUGopen = false;
  private ResourceLocation viewportTextureLocation;

  public MenuBaseFC()
  {
    BufferedReader bufferedreader = null;
    try
    {
      ArrayList arraylist = new ArrayList();
      bufferedreader = new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(splashTexts).getInputStream(), Charsets.UTF_8));
      String s;
      while ((s = bufferedreader.readLine()) != null)
      {
        s = s.trim();

        if (s.length() > 0)
        {
          arraylist.add(s);
        }
      }

      do
      {
        this.splashText = ((String)arraylist.get(rand.nextInt(arraylist.size())));
      }
      while (this.splashText.hashCode() == 125780783);
    }
    catch (IOException ioexception)
    {
    }
    finally
    {
      if (bufferedreader != null)
      {
        try
        {
          bufferedreader.close();
        }
        catch (IOException ioexception1)
        {
        }
      }

    }

    this.updateCounter = rand.nextFloat();
  }

  public void updateScreen()
  {
    this.panoramaTimer += 1;
  }

  public boolean doesGuiPauseGame()
  {
    return false;
  }

  protected void keyTyped(char par1, int par2)
  {
  }

  public void initGui()
  {
    super.initGui();

    this.viewportTexture = new DynamicTexture(256, 256);
    this.viewportTextureLocation = this.mc.renderEngine.getDynamicTextureLocation("logo", this.viewportTexture);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());

    if ((calendar.get(2) + 1 == 11) && (calendar.get(5) == 9))
    {
      this.splashText = "Happy birthday, ez!";
    }
    else if ((calendar.get(2) + 1 == 6) && (calendar.get(5) == 1))
    {
      this.splashText = "Happy birthday, Notch!";
    }
    else if ((calendar.get(2) + 1 == 12) && (calendar.get(5) == 24))
    {
      this.splashText = "Merry X-mas!";
    }
    else if ((calendar.get(2) + 1 == 1) && (calendar.get(5) == 1))
    {
      this.splashText = "Happy new year!";
    }
    else if ((calendar.get(2) + 1 == 10) && (calendar.get(5) == 31))
    {
      this.splashText = "OOoooOOOoooo! Spooky!";
    }

    int i = this.height / 4 + 68;

    this.backButton = new GuiFCButton(13, 3, this.height - 35, 180, 20, "Back To Menu");

    this.buttonList.add(this.backButton);

    if (this.mc.isDemo())
    {
      addDemoButtons(i, 24);
    }
    else
    {
      addSingleplayerMultiplayerButtons(i, 24);
    }

    this.fmlModButton = new GuiFCButton(6, 30, i + 48 - 45, "Mods");
    this.buttonList.add(this.fmlModButton);

    func_96137_a(i, 24);

    this.hideOptionsButton = new GuiButtonMainMenu(0, 30, i + 27 + 24, I18n.format("menu.options"), true);
    this.optionsButton = new GuiFCButton(0, 30, i + 27 + 28, 200, 20, I18n.format("menu.options"));
    this.quitButton = new GuiFCButton(4, 30, i + 27 + 52, 200, 20, I18n.format("menu.quit"));

    this.buttonList.add(this.optionsButton);
    this.buttonList.add(this.quitButton);


    this.field_92025_p = "";
    String s = System.getProperty("os_architecture");
    String s1 = System.getProperty("java_version");

    if ("ppc".equalsIgnoreCase(s))
    {
      this.field_92025_p = ("" + EnumChatFormatting.BOLD + "Notice!" + EnumChatFormatting.RESET + " PowerPC compatibility will be dropped in Minecraft 1.6");
    }
    else if ((s1 != null) && (s1.startsWith("1.5")))
    {
      this.field_92025_p = ("" + EnumChatFormatting.BOLD + "Notice!" + EnumChatFormatting.RESET + " Java 1.5 compatibility will be dropped in Minecraft 1.6");
    }

    this.field_92023_s = this.fontRendererObj.getStringWidth(this.field_92025_p);
    this.field_92024_r = this.fontRendererObj.getStringWidth(field_96138_a);
    int j = Math.max(this.field_92023_s, this.field_92024_r);
    this.field_92022_t = ((this.width - j) / 2);
    this.field_92021_u = (((GuiFCButton)this.buttonList.get(0)).yPosition - 24);
    this.field_92020_v = (this.field_92022_t + j);
    this.field_92019_w = (this.field_92021_u + 24);

    TUGbutton = new FC_TUGButton(14, 33, 20, 50, 50);

    this.buttonList.add(TUGbutton);
  }

  private void func_96137_a(int par2, int par3)
  {
    if (this.field_96141_q)
    {
      if (!field_96140_r)
      {
        field_96140_r = true;
      }
      else if (field_96139_s)
      {
        func_98060_b(par2, par3);
      }
    }
  }

  private void func_98060_b(int par2, int par3)
  {
    this.fmlModButton.xPosition = (this.width / 2 + 2);

    GuiFCButton realmButton = new GuiFCButton(3, 30, par2 - 45 + par3 * 2, I18n.format("menu.online"));

    realmButton.xPosition = (this.width / 2 - 100);
    this.buttonList.add(realmButton);
  }

  private void addSingleplayerMultiplayerButtons(int par1, int par2)
  {
    this.singleplayerButton = new GuiFCButton(1, 30, par1 - 49, I18n.format("menu.singleplayer"));
    this.multiplayerButton = new GuiFCButton(2, 30, par1 - 49 + par2 * 1, I18n.format("menu.multiplayer"));

    this.buttonList.add(this.singleplayerButton);
    this.buttonList.add(this.multiplayerButton);
  }

  private void addDemoButtons(int par1, int par2)
  {
    this.buttonList.add(new GuiFCButton(11, 30, par1, I18n.format("menu.playdemo")));
    this.buttonList.add(this.buttonResetDemo = new GuiFCButton(12, 30, par1 - 45 + par2 * 1, I18n.format("menu.resetdemo")));
    ISaveFormat isaveformat = this.mc.getSaveLoader();
    WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");

    if (worldinfo == null)
    {
      this.buttonResetDemo.enabled = false;
    }
  }

  protected void actionPerformed(GuiButton par1GuiButton)
  {
    if (par1GuiButton.id == 0)
    {
      this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
    }

    if (par1GuiButton.id == 1)
    {
      this.mc.displayGuiScreen(new GuiSelectWorld(this));

    }

    if (par1GuiButton.id == 2)
    {
      this.mc.displayGuiScreen(new GuiMultiplayer(this));
    }

    if (par1GuiButton.id == 3)
    {
//      this.mc.displayGuiScreen(new GuiScreenOnlineServers(this));
    }

    if (par1GuiButton.id == 4)
    {
      this.mc.shutdown();
    }

    if (par1GuiButton.id == 6)
    {
      this.mc.displayGuiScreen(new GuiModList(this));
    }

    if (par1GuiButton.id == 11)
    {
      this.mc.launchIntegratedServer("Demo_World", "Demo_World", DemoWorldServer.demoWorldSettings);
    }

    if (par1GuiButton.id == 12)
    {
      ISaveFormat isaveformat = this.mc.getSaveLoader();
      WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");

      if (worldinfo != null)
      {
        GuiYesNo guiyesno = GuiSelectWorld.func_152129_a((GuiYesNoCallback) this, worldinfo.getWorldName(), 12);
        this.mc.displayGuiScreen(guiyesno);
      }
    }
    if (par1GuiButton.id == 14) {
      try
      {
        System.out.println("WOW");

        this.TUGopen = (!this.TUGopen);
        this.mc.displayGuiScreen(this);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private void connectToServer(ServerData par1ServerData)
  {
    this.mc.displayGuiScreen(new GuiConnecting(this, this.mc, par1ServerData));
  }

  public void confirmClicked(boolean par1, int par2)
  {
    if ((par1) && (par2 == 12))
    {
      ISaveFormat isaveformat = this.mc.getSaveLoader();
      isaveformat.flushCache();
      isaveformat.deleteWorldDirectory("Demo_World");
      this.mc.displayGuiScreen(this);
    }
    else if (par2 == 13)
    {
      if (par1)
      {
        try
        {
          Class oclass = Class.forName("java.awt.Desktop");
          Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
          oclass.getMethod("browse", new Class[] { URI.class }).invoke(object, new Object[] { new URI("http://tinyurl.com/javappc") });
        }
        catch (Throwable throwable)
        {
          throwable.printStackTrace();
        }
      }

      this.mc.displayGuiScreen(this);
    }
  }

  public void drawPanorama(int par1, int par2, float par3)
  {
	  GL11.glPushMatrix();

      Minecraft mc = Minecraft.getMinecraft();
      ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
      int width2 = scaledresolution.getScaledWidth();
      int height2 = scaledresolution.getScaledHeight();

      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glEnable(3553);
      this.mc.renderEngine.bindTexture(TEXTURE_MAIN_BG);

      int x = 0;
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
/* this.field_146297_k.getTextureManager().bindTexture(titlePanoramaPaths[var10]); */
  public void drawLogo(int k, int b0)
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

  public void drawScreen(int par1, int par2, float par3)
  {
	 drawPanorama(par1, par2, par3);
    Tessellator tessellator = Tessellator.instance;
    short short1 = 274;
    int k = this.width / 2 - short1 / 2;
    byte b0 = 30;


    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

    if (this.TUGopen)
    {
      GL11.glPushMatrix();

      Minecraft mc = Minecraft.getMinecraft();
      ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
      int width2 = scaledresolution.getScaledWidth();
      int height2 = scaledresolution.getScaledHeight();

      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glEnable(3553);
      this.mc.renderEngine.bindTexture(TEXTURE_TUG_BG);

      int x = 0;
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

    GL11.glPushMatrix();
    GL11.glTranslatef(215.0F, 50.0F, 0.0F);
    GL11.glRotatef(-20.0F, 0.0F, 0.0F, 1.0F);
    float f1 = 1.4F - MathHelper.abs(MathHelper.sin((float)(Minecraft.getSystemTime() % 1000L) / 1000.0F * 3.141593F * 2.0F) * 0.1F);
    f1 = f1 * 100.0F / (this.fontRendererObj.getStringWidth(this.splashText) + 32);
    GL11.glScalef(f1, f1, f1);

    GL11.glPopMatrix();
    String s = "Minecraft 1.6.4";

    if (this.mc.isDemo())
    {
      s = s + " Demo";
    }

    List brandings = Lists.reverse(FMLCommonHandler.instance().getBrandings(true));
    for (int i = 0; i < brandings.size(); i++)
    {
      String brd = (String)brandings.get(i);
      if ((!Strings.isNullOrEmpty(brd)) && (!this.TUGopen))
      {
        drawString(this.fontRendererObj, brd, this.width - 2 - this.fontRendererObj.getStringWidth(brd), this.height - (10 + i * (this.fontRendererObj.FONT_HEIGHT + 1)), 16777215);
      }
    }

    if (!this.TUGopen)
    {
      String s1 = "Copyright Mojang AB.";
      drawString(this.fontRendererObj, s1, 2, this.height - 10, 16777215);
    }

    if ((this.field_92025_p != null) && (this.field_92025_p.length() > 0))
    {
      drawRect(this.field_92022_t - 2, this.field_92021_u - 2, this.field_92020_v + 2, this.field_92019_w - 1, 1428160512);
      drawString(this.fontRendererObj, this.field_92025_p, this.field_92022_t, this.field_92021_u, 16777215);
      drawString(this.fontRendererObj, field_96138_a, (this.width - this.field_92024_r) / 2, ((GuiFCButton)this.buttonList.get(0)).yPosition - 12, 16777215);
    }

    super.drawScreen(par1, par2, par3);

    if (this.updateCounter < 0.0001D)
    {
      drawTexturedModalRect(k + 0, b0 + 0, 0, 0, 99, 44);
      drawTexturedModalRect(k + 99, b0 + 0, 129, 0, 27, 44);
      drawTexturedModalRect(k + 99 + 26, b0 + 0, 126, 0, 3, 44);
      drawTexturedModalRect(k + 99 + 26 + 3, b0 + 0, 99, 0, 26, 44);
      drawTexturedModalRect(k + 155, b0 + 0, 0, 45, 155, 44);
    }
    else if(!this.TUGopen)
    {
      drawLogo(k, b0);
      TUGbutton.visible = true;
      this.fmlModButton.visible = true;
      this.hideOptionsButton.visible = true;
      this.optionsButton.visible = true;
      this.quitButton.visible = true;
      this.singleplayerButton.visible = true;
      this.multiplayerButton.visible = true;
    }
    else {
      this.fmlModButton.visible = false;
      this.hideOptionsButton.visible = false;
      this.optionsButton.visible = false;
      this.quitButton.visible = false;
      this.singleplayerButton.visible = false;
      this.multiplayerButton.visible = false;

    }

    this.backButton.visible = false;
  }

  protected void mouseClicked(int par1, int par2, int par3)
  {
    super.mouseClicked(par1, par2, par3);

    if ((this.field_92025_p.length() > 0) && (par1 >= this.field_92022_t) && (par1 <= this.field_92020_v) && (par2 >= this.field_92021_u) && (par2 <= this.field_92019_w))
    {
      GuiConfirmOpenLink guiconfirmopenlink = new GuiConfirmOpenLink((GuiYesNoCallback) this, "http://tinyurl.com/javappc", 13, true);
      guiconfirmopenlink.copyLinkToClipboard();
      this.mc.displayGuiScreen(guiconfirmopenlink);
    }
  }

  static Minecraft func_98058_a(MenuBaseFC par0GuiMainMenu)
  {
    return par0GuiMainMenu.mc;
  }

  static void func_98061_a(MenuBaseFC par0GuiMainMenu, int par2, int par3)
  {
    par0GuiMainMenu.func_98060_b(par2, par3);
  }

  static boolean func_98059_a(boolean par0)
  {
    field_96139_s = par0;
    return par0;
  }

  public int getListButtonX()
  {
    return this.width - 110;
  }

  public int getListButtonY()
  {
    Minecraft mc = Minecraft.getMinecraft();
    ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
    int width = scaledresolution.getScaledWidth();
    int height = scaledresolution.getScaledHeight();

    return 4;
  }

  public int getJukeboxButtonX()
  {
    return this.width - 24;
  }

  public int getJukeboxButtonY()
  {
    return 4;
  }

  public String getName()
  {
    return "FriendsCraft_2";
  }

  public String getVersion()
  {
    return "Nigga";
  }
}