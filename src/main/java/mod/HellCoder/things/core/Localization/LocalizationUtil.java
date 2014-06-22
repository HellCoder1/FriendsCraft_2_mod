/*    */ package mod.HellCoder.things.core.Localization;
/*    */ 
/*    */ import cpw.mods.fml.common.registry.LanguageRegistry;
/*    */ 
/*    */ public class LocalizationUtil
/*    */ {
/*    */   public static boolean isXMLLanguageFile(String fileName)
/*    */   {
/*  9 */     return fileName.endsWith(".xml");
/*    */   }
/*    */ 
/*    */   public static String getLocaleFromFileName(String fileName)
/*    */   {
/* 14 */     return fileName.substring(fileName.lastIndexOf('/') + 1, fileName.lastIndexOf('.'));
/*    */   }
/*    */ 
/*    */   public static String getLocalizedString(String key)
/*    */   {
/* 19 */     return LanguageRegistry.instance().getStringLocalization(key);
/*    */   }
/*    */ }

/* Location:           C:\Users\Максим\Desktop\Pickaxe_Upgrade.zip
 * Qualified Name:     Pickaxe_Upgrade.core.Localization.LocalizationUtil
 * JD-Core Version:    0.6.2
 */