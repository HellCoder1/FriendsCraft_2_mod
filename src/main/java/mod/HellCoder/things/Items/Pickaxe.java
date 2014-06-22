package mod.HellCoder.things.Items;

import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

import mod.HellCoder.things.FriendsCraft2mod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class Pickaxe extends ItemPickaxe
{

	public int textureID = 0;
	
	public Pickaxe(ToolMaterial material, int texture) {
		super(material);
		
		this.textureID = texture;
		
		this.setCreativeTab(FriendsCraft2mod.tabsFC);
	}
	
	@Override
    public void registerIcons(IIconRegister iconRegister)
    {
            if (textureID == 0)
            { 
                    itemIcon = iconRegister.registerIcon("friendscraft:pickaxeWood"); 
            }
            else if (textureID == 1)
            { 
                    itemIcon = iconRegister.registerIcon("friendscraft:pickaxeStone"); 
            }
            else if (textureID == 2)
            { 
                    itemIcon = iconRegister.registerIcon("friendscraft:pickaxeIron"); 
            }
            else if (textureID == 3)
            { 
                    itemIcon = iconRegister.registerIcon("friendscraft:pickaxeDiamond"); 
            }
            else 
            { 
                    itemIcon = iconRegister.registerIcon("friendscraft:pickaxeGold"); 
            }
    }

	private static final Set field_150915_c = Sets.newHashSet(new Block[] {Blocks.cobblestone, Blocks.double_stone_slab, Blocks.stone_slab, Blocks.stone, Blocks.sandstone, Blocks.mossy_cobblestone, Blocks.iron_ore, Blocks.iron_block, Blocks.coal_ore, Blocks.gold_block, Blocks.gold_ore, Blocks.diamond_ore, Blocks.diamond_block, Blocks.ice, Blocks.netherrack, Blocks.lapis_ore, Blocks.lapis_block, Blocks.redstone_ore, Blocks.lit_redstone_ore, Blocks.rail, Blocks.detector_rail, Blocks.golden_rail, Blocks.activator_rail});


	   public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean show)
	   {
	     super.addInformation(stack, player, list, show);
	     if ((GuiScreen.isShiftKeyDown()) || (show)) {
	    	 
	    	 Integer Md = stack.getMaxDamage();
	    	 Integer d = stack.getMaxDamage() - stack.getItemDamage();

	    	 if (!getPlayerName(stack).isEmpty()) {
	      	   
	             list.add("Owner: " + getPlayerName(stack));
	           }

	         list.add("§4Uses: " + "§2"+d + "/" + Md);

	     } else {
	       list.add("Press SHIFT for more info");
	     }
	  }	
	   
	public static String getPlayerName(ItemStack stack) {
	        NBTTagCompound tag = initTags(stack);
	        return tag.getString("player_name");
	      }

    public static NBTTagCompound initTags(ItemStack stack) {
	        NBTTagCompound tag = stack.getTagCompound();

	        if (tag == null) {
	          tag = new NBTTagCompound();
	          stack.setTagCompound(tag);
	          tag.setString("player_name", "");
	        }
			return tag;
	    }    
	
    public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
        itemStack.stackTagCompound = new NBTTagCompound();

        itemStack.stackTagCompound.setString("player_name", player.getDisplayName());
    }    
    
public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float clickX, float clickY, float clickZ)
   {

     int i1 = world.getBlockMetadata(x, y, z);
     int posX = x;
     int posY = y;
     int posZ = z;
     int playerPosX = (int)Math.floor(player.posX);
     int playerPosY = (int)Math.floor(player.posY);
     int playerPosZ = (int)Math.floor(player.posZ);
     if (side == 0)
     {
       posY--;
     }
 
     if (side == 1)
     {
       posY++;
     }
 
     if (side == 2)
     {
       posZ--;
     }
 
     if (side == 3)
     {
       posZ++;
     }
 
     if (side == 4)
     {
       posX--;
     }
 
     if (side == 5)
     {
       posX++;
     }
     if ((posX == playerPosX) && ((posY == playerPosY) || (posY == playerPosY + 1) || (posY == playerPosY - 1)) && (posZ == playerPosZ))
     {
       return false;
     }
 
     boolean used = false;
     int invSlot = player.inventory.currentItem;
     int itemSlot = invSlot == -1 ? 8 : invSlot + 1;
     ItemStack secondStack = null;
 
     if (invSlot < 8)
     {
       secondStack = player.inventory.getStackInSlot(itemSlot);
       if ((secondStack != null) && ((secondStack.getItem() instanceof ItemBlock)))
       {
         used = secondStack.getItem().onItemUse(secondStack, player, world, x, y, z, side, clickX, clickY, clickZ);
         if (secondStack.stackSize < 1)
         {
           secondStack = null;
           player.inventory.setInventorySlotContents(itemSlot, null);
         }
       }
 
     }
     return used;
   }

}