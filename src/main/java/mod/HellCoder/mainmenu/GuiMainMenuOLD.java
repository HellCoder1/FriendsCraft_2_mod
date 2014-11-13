package mod.HellCoder.mainmenu;

import cpw.mods.fml.client.GuiModList;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import org.lwjgl.opengl.GLContext;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@SideOnly(Side.CLIENT)
public class GuiMainMenuOLD extends GuiScreen implements GuiYesNoCallback
{
    private static final Random rand = new Random();
    float updateCounter;
    DynamicTexture viewportTexture;
    private final Object field_104025_t = new Object();
    private String field_92025_p;
    private String field_146972_A;
    private String field_104024_v;
    public static final String field_96138_a = "Please click " + EnumChatFormatting.UNDERLINE + "here" + EnumChatFormatting.RESET + " for more information.";
    int field_92024_r;
    int field_92023_s;
    private int field_92022_t;
    private int field_92021_u;
    private int field_92020_v;
    private int field_92019_w;
    ResourceLocation field_110351_G;

    @SuppressWarnings("unchecked")
    public GuiMainMenuOLD()
    {
        this.field_146972_A = field_96138_a;

        this.updateCounter = rand.nextFloat();
        this.field_92025_p = "";

        if (!GLContext.getCapabilities().OpenGL20 && !OpenGlHelper.func_153193_b())
        {
            this.field_92025_p = I18n.format("title.oldgl1", new Object[0]);
            this.field_146972_A = I18n.format("title.oldgl2", new Object[0]);
            this.field_104024_v = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
        }
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char p_73869_1_, int p_73869_2_) {}

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    @SuppressWarnings("unchecked")
    public void initGui()
    {
        this.viewportTexture = new DynamicTexture(256, 256);
        this.field_110351_G = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        int i = this.height / 4 + 48;

        this.buttonList.add(new GuiButton(4, this.width / 2 + 2, i + 72 + 12, 98, 20, I18n.format("menu.quit", new Object[0])));
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, i + 72 + 12, 98, 20, I18n.format("menu.options", new Object[0])));

        this.addSingleplayerMultiplayerButtons(i, 24);

        synchronized (this.field_104025_t)
        {
            this.field_92023_s = this.fontRendererObj.getStringWidth(this.field_92025_p);
            this.field_92024_r = this.fontRendererObj.getStringWidth(this.field_146972_A);
            int j = Math.max(this.field_92023_s, this.field_92024_r);
            this.field_92022_t = (this.width - j) / 2;
            this.field_92021_u = ((GuiButton)this.buttonList.get(0)).yPosition - 24;
            this.field_92020_v = this.field_92022_t + j;
            this.field_92019_w = this.field_92021_u + 24;
        }
    }

    @SuppressWarnings("unchecked")
    private void addSingleplayerMultiplayerButtons(int p_73969_1_, int p_73969_2_)
    {
        GuiButton realmsButton = new GuiButton(14, this.width / 2 - 100, p_73969_1_ + p_73969_2_ * 2, I18n.format("menu.online", new Object[0]));
        GuiButton fmlModButton = new GuiButton(6, this.width / 2 - 100, p_73969_1_ + p_73969_2_ * 2, "Mods");
        fmlModButton.xPosition = this.width / 2 + 2;
        realmsButton.width = 98;
        fmlModButton.width = 98;
        this.buttonList.add(realmsButton);
        this.buttonList.add(fmlModButton);
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, p_73969_1_ + p_73969_2_, I18n.format("menu.multiplayer", new Object[0])));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, p_73969_1_, I18n.format("menu.singleplayer", new Object[0])));
    }

    protected void actionPerformed(GuiButton p_146284_1_)
    {
        if (p_146284_1_.id == 0)
        {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }

        if (p_146284_1_.id == 1)
        {
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
        }

        if (p_146284_1_.id == 2)
        {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }

        if (p_146284_1_.id == 14)
        {
            this.func_140005_i();
        }

        if (p_146284_1_.id == 4)
        {
            this.mc.shutdown();
        }

        if (p_146284_1_.id == 6)
        {
            this.mc.displayGuiScreen(new GuiModList(this));
        }

        if (p_146284_1_.id == 11)
        {
            this.mc.launchIntegratedServer("Demo_World", "Demo_World", DemoWorldServer.demoWorldSettings);
        }

        if (p_146284_1_.id == 12)
        {
            ISaveFormat isaveformat = this.mc.getSaveLoader();
            WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");

            if (worldinfo != null)
            {
                GuiYesNo guiyesno = GuiSelectWorld.func_152129_a(this, worldinfo.getWorldName(), 12);
                this.mc.displayGuiScreen(guiyesno);
            }
        }
    }

    private void func_140005_i()
    {
        RealmsBridge realmsbridge = new RealmsBridge();
        realmsbridge.switchToRealms(this);
    }

    public void confirmClicked(boolean p_73878_1_, int p_73878_2_)
    {
        if (p_73878_1_ && p_73878_2_ == 12)
        {
            ISaveFormat isaveformat = this.mc.getSaveLoader();
            isaveformat.flushCache();
            isaveformat.deleteWorldDirectory("Demo_World");
            this.mc.displayGuiScreen(this);
        }
        else if (p_73878_2_ == 13)
        {
            this.mc.displayGuiScreen(this);
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_)
    {
        super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
        synchronized (this.field_104025_t)
        {
            if (this.field_92025_p.length() > 0 && p_73864_1_ >= this.field_92022_t && p_73864_1_ <= this.field_92020_v && p_73864_2_ >= this.field_92021_u && p_73864_2_ <= this.field_92019_w)
            {
                GuiConfirmOpenLink guiconfirmopenlink = new GuiConfirmOpenLink(this, this.field_104024_v, 13, true);
                guiconfirmopenlink.func_146358_g();
                this.mc.displayGuiScreen(guiconfirmopenlink);
            }
        }
    }
}