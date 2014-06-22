/*    */ package mod.HellCoder.things.core.Localization;
/*    */ 
/*    */ import cpw.mods.fml.common.registry.LanguageRegistry;
/*    */ 
/*    */ public class LocalizationHandler
/*    */ {
/*    */   private static final String LANG_RESOURCE_LOCATION = "/mod/HellCoder/things/core/lang/";
/*  9 */   public static String[] localeFiles = { "/mod/HellCoder/things/core/lang/en_US.xml" };
/*    */ 
/*    */   public static void loadLanguages()
/*    */   {
/* 14 */     for (String localizationFile : localeFiles)
/* 15 */       LanguageRegistry.instance().loadLocalization(localizationFile, LocalizationUtil.getLocaleFromFileName(localizationFile), LocalizationUtil.isXMLLanguageFile(localizationFile));
/*    */   }
/*    */ }

/* Location:           C:\Users\Максим\Desktop\Pickaxe_Upgrade.zip
 * Qualified Name:     Pickaxe_Upgrade.core.Localization.LocalizationHandler
 * JD-Core Version:    0.6.2
 */