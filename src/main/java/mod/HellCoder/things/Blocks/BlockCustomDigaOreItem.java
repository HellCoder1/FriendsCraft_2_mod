package mod.HellCoder.things.Blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class BlockCustomDigaOreItem extends ItemBlock {

	 public static final int[] colors = { 16777215, 16777086, 16727041, 37119, 40960, 14540287, 5592439 };

	  public BlockCustomDigaOreItem(Block par1)
	  {
	    super(par1);
	    setMaxDamage(0);
	  }

	  public int getMetadata(int par1)
	  {
	    return par1;
	  }

	  public String getUnlocalizedName(ItemStack par1ItemStack)
	  {
	    return super.getUnlocalizedName() + "." + par1ItemStack.getItemDamage();
	  }
}
