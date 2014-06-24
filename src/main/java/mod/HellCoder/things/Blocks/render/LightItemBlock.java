package mod.HellCoder.things.Blocks.render;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class LightItemBlock extends ItemBlock {

	 public static final int[] colors = { 16777215, 16777086, 16727041, 37119, 40960, 14540287, 5592439 };

	  public LightItemBlock(Block par1)
	  {
	    super(par1);
	    setMaxDamage(0);
	  }

	  public String getUnlocalizedName(ItemStack par1ItemStack)
	  {
	    return super.getUnlocalizedName() + "." + par1ItemStack.getItemDamage();
	  }
}
