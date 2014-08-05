package mod.HellCoder.things.Items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;

public class Hammer extends Item {

	public Hammer()
	  {
	    super();

	    setMaxDamage(80);
	    setMaxStackSize(1);
	    this.canRepair = false;
	  }
	
	public static int maxDamage = 80;
	
	 public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b)
	  {
	    info.add("Uses Left: " + (itemStack.getMaxDamage() - itemStack.getItemDamage()));
	  }
}
