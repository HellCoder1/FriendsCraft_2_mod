package mod.HellCoder.things.tab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mod.HellCoder.things.lib.RegItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class FCTab extends CreativeTabs
 {
   public FCTab(int par1, String par2Str)
   {
     super(par1, par2Str);
   }

 @Override
 public Item getTabIconItem() {
 	return RegItems.NagibatorSword;
 } 
}