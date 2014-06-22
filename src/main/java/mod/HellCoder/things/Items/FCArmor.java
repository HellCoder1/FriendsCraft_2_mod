package mod.HellCoder.things.Items;

import java.util.List;

import mod.HellCoder.things.FriendsCraft2mod;
import mod.HellCoder.things.lib.RegItems;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class FCArmor extends ItemArmor {

	public FCArmor(ArmorMaterial diga, int id,
			int slot) {
		super(diga, id, slot);
		this.setCreativeTab(FriendsCraft2mod.tabsFC);

		if(slot == 0) {
			this.setTextureName("friendscraft:FCHelmet");
		}else if (slot == 1) {
			this.setTextureName("friendscraft:FCChest");
		}else if (slot == 2) {
			this.setTextureName("friendscraft:FCLegs");
		}else if (slot == 3) {
			this.setTextureName("friendscraft:FCBoots");
		}	
	}

	public String getArmorTexture(ItemStack itemstack, Entity entity, int slot, String type) {
		if (itemstack.getItem() == RegItems.FCHelmet || itemstack.getItem() == RegItems.FCChest || itemstack.getItem() == RegItems.FCBoots) {  
				return "friendscraft:textures/armor/fc_armor_layer_1.png";
		}else if (itemstack.getItem() == RegItems.FCLegs) {  
			return "friendscraft:textures/armor/fc_armor_layer_2.png";
		}else{
			return null;
		}
	}
	
	@Override
	public boolean getIsRepairable(ItemStack armor, ItemStack stack) {
	  return stack.getItem() == RegItems.diga; //Alllows certain items to repair this armor.
	}

	public void onCreated(ItemStack itemstack, World world, EntityPlayer entityplayer) {

		itemstack.stackTagCompound = new NBTTagCompound();

		itemstack.stackTagCompound.setString("player_name",
				entityplayer.getDisplayName());
		
		if (itemstack.getItem() == RegItems.FCHelmet) {
			itemstack.addEnchantment(Enchantment.aquaAffinity, 2);
		}else if (itemstack.getItem() == RegItems.FCChest) {
			itemstack.addEnchantment(Enchantment.fireProtection, 4);
		}else if (itemstack.getItem() == RegItems.FCBoots) {
			itemstack.addEnchantment(Enchantment.thorns, 5);
		}else if (itemstack.getItem() == RegItems.FCLegs) {
			itemstack.addEnchantment(Enchantment.unbreaking, 4);
		}
	}
	
	public void addInformation(ItemStack stack, EntityPlayer player, List list,
			boolean show) {
		initTags(stack);
		super.addInformation(stack, player, list, show);
		if ((GuiScreen.isShiftKeyDown()) || (show)) {
			Integer Md = stack.getMaxDamage();
			Integer d = stack.getMaxDamage() - stack.getItemDamage();

			list.add("§4Uses: " + "§2" + d + "/" + Md);

			if (!getPlayerName(stack).isEmpty()) {

				list.add("Owner: " + getPlayerName(stack));
			}

		} else {
			list.add("Press SHIFT for more info");
		}
	}
	
	public static void setName(ItemStack stack, String t, String n) {
		NBTTagCompound tag = initTags(stack);
		tag.setString("player_name", t);
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
}