/*     */ package mod.HellCoder.mainmenu;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.SoundManager;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public abstract class MenuBase extends GuiScreen
/*     */ {
/*  39 */   private GuiButton field_73883_a = null;
/*     */   private int menuX;
/*     */   private int menuY;
/*     */   private int jukeWidth;
/*     */   private int jukeHeight;
/*  43 */   private boolean jukeboxOpen = false;
/*  44 */   private List jukeButtonList = new ArrayList();
/*     */ 
/*  47 */   protected ResourceLocation splashesLocation = new ResourceLocation("texts/splashes.txt");
/*     */ 
  public void initGui()
   {
   this.jukeHeight = 256;
     this.jukeWidth = 256;
 
     updateScreen();
   }

/*     */   public void drawScreen(int x, int y, float something)
/*     */   {
/*  92 */     super.drawScreen(x, y, something);
/*     */ 
/*  94 */     Minecraft mc = Minecraft.getMinecraft();
/*  95 */     ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
/*  96 */     int width = scaledresolution.getScaledWidth();
/*  97 */     int height = scaledresolution.getScaledHeight();
/*     */ 
/*  99 */     this.menuX = (width + mc.displayWidth / 2);
/* 100 */     this.menuY = (height + mc.displayHeight / 2);
/*     */ 
              this.jukeButtonList.clear();
/*     */ }
/*     */   public int getJukeboxButtonX()
/*     */   {
/* 157 */     Minecraft mc = Minecraft.getMinecraft();
/* 158 */     ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
/* 159 */     int width = scaledresolution.getScaledWidth();
/* 160 */     int height = scaledresolution.getScaledHeight();
/*     */ 
/* 162 */     return width / 2 + 192;
/*     */   }
/*     */ 
/*     */   public int getJukeboxButtonY()
/*     */   {
/* 171 */     Minecraft mc = Minecraft.getMinecraft();
/* 172 */     ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
/* 173 */     int width = scaledresolution.getScaledWidth();
/* 174 */     int height = scaledresolution.getScaledHeight();
/*     */ 
/* 176 */     return 4;
/*     */   }
/*     */ 
/*     */   public int getListButtonX()
/*     */   {
/* 185 */     Minecraft mc = Minecraft.getMinecraft();
/* 186 */     ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
/* 187 */     int width = scaledresolution.getScaledWidth();
/* 188 */     int height = scaledresolution.getScaledHeight();
/*     */ 
/* 190 */     return 5;
/*     */   }
/*     */ 
/*     */   public int getListButtonY()
/*     */   {
/* 199 */     Minecraft mc = Minecraft.getMinecraft();
/* 200 */     ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
/* 201 */     int width = scaledresolution.getScaledWidth();
/* 202 */     int height = scaledresolution.getScaledHeight();
/*     */ 
/* 204 */     return height - 25;
/*     */   }
/*     */ 
/*     */   protected void mouseClicked(int par1, int par2, int par3)
/*     */   {
    if (par3 == 0)
     {

/* 225 */       for (int l = 0; l < this.jukeButtonList.size(); l++)
/*     */       {
/* 227 */         GuiButton guibutton = (GuiButton)this.jukeButtonList.get(l);
/*     */ 
/* 229 */         if (guibutton.mousePressed(this.mc, par1, par2))
/*     */         {
/* 231 */           this.field_73883_a = guibutton;
///* 232 */           this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);

/**           if (guibutton.id == 0)
          {
             this.jukebox.toggleMute();
           }
 
           if (guibutton.id == 1)
           {
             this.jukebox.toggleLoop();
           }  
 
           if (guibutton.id == 2)
           {
             MenuBaseConfig.musicIndex += 1;
             MenuBaseConfig.setProperty("musicIndex", String.valueOf(MenuBaseConfig.musicIndex));
 
             this.jukebox.muteMusic();
           } **/
 
           if (guibutton.id == 3)
           {
           }
         }
       }
 
       if (!this.jukeboxOpen)
       {
         for (int l = 0; l < this.buttonList.size(); l++)
         {
           GuiButton guibutton = (GuiButton)this.buttonList.get(l);
 
           if (guibutton.mousePressed(this.mc, par1, par2))
           {
             this.field_73883_a = guibutton;
//             this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
             actionPerformed(guibutton);
           }
         }
       }
     }
}
/*     */ 
/*     */   protected void mouseMovedOrUp(int par1, int par2, int par3)
/*     */   {
/* 281 */     if ((this.field_73883_a != null) && (par3 == 0) && (!this.jukeboxOpen))
/*     */     {
/* 283 */       this.field_73883_a.mouseReleased(par1, par2);
/* 284 */       this.field_73883_a = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 294 */     return "Default Name";
/*     */   }
/*     */ 
/*     */   public String getVersion()
/*     */   {
/* 304 */     return "1.0.0";
/*     */   }
/*     */ 
/*     */   public String[] getPlaylist()
/*     */   {
/* 330 */     return new String[0];
/*     */   }
/*     */ 
/*     */   public boolean useJukebox()
/*     */   {
/* 339 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isJukeboxOpen()
/*     */   {
/* 348 */     return this.jukeboxOpen;
/*     */   }
/*     */ }

/* Location:           C:\Users\Максим\Desktop\asadd.jar
 * Qualified Name:     net.aetherteam.mainmenu_api.MenuBase
 * JD-Core Version:    0.6.2
 */