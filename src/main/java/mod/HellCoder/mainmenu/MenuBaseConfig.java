/*     */ package mod.HellCoder.mainmenu;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Properties;
/*     */ import net.minecraft.client.Minecraft;
/*     */ 
/*     */ public class MenuBaseConfig
/*     */ {
/*  14 */   private static Minecraft mc = Minecraft.getMinecraft();
/*     */ 
/*  16 */   private static final Properties menuProps = new Properties();
/*     */   public static String selectedMenuName;
/*     */   public static boolean loopMusic;
/*     */   public static boolean muteMusic;
/*     */   public static int lastMusicIndex;
/*     */   public static int musicIndex;
/*     */   public static boolean musicSet;
/*     */   public static boolean hasPlayedMusic;
/*     */   public static boolean hasStartedMusic;
/*  27 */   public static boolean endMusic = true;
/*     */   public static double playerPosX;
/*     */   public static double playerPosY;
/*     */   public static double playerPosZ;
/*     */   public static int ticks;
/*     */ 
/*     */   public static void loadConfig()
/*     */   { 
              selectedMenuName = String.valueOf("FriendsCraft_2");
/*     */   }
/*     */ 
/*     */   public static void resetConfig()
/*     */   {
/*     */   }
/*     */ 
/*     */   public static void wipeConfig()
/*     */   {
              menuProps.setProperty("selectedMenu", "");
/*     */   }
/*     */ 
/*     */   public static void setProperty(String name, String value)
/*     */   {
/* 132 */       menuProps.setProperty(name, value);
/*     */   }
/*     */ }

/* Location:           C:\Users\Максим\Desktop\asadd.jar
 * Qualified Name:     net.aetherteam.mainmenu_api.MenuBaseConfig
 * JD-Core Version:    0.6.2
 */