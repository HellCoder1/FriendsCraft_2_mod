package mod.HellCoder.things.Items;

import java.util.List;

import mod.HellCoder.things.FriendsCraft2mod;
import mod.HellCoder.things.lib.RegItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NagibatorSword extends Item {
	private float field_150934_a;
	private final MatirialNagibator toolMaterial;
	private static final String __OBFID = "CL_00000072";

	public NagibatorSword(MatirialNagibator material) {
		this.toolMaterial = material;
		this.maxStackSize = 1;
		this.setMaxDamage(material.getMaxUses());
		this.setCreativeTab(FriendsCraft2mod.tabsFC);
		this.field_150934_a = 4.0F + material.getDamageVsEntity();
	}

	public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
		itemStack.stackTagCompound = new NBTTagCompound();

		itemStack.stackTagCompound.setString("player_name",
				player.getDisplayName());
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

	public float func_150931_i() {
		return this.toolMaterial.getDamageVsEntity();
	}

	public float func_150893_a(ItemStack p_150893_1_, Block p_150893_2_) {
		if (p_150893_2_ == Blocks.web) {
			return 15.0F;
		} else {
			Material material = p_150893_2_.getMaterial();
			return material != Material.plants && material != Material.vine
					&& material != Material.coral
					&& material != Material.leaves
					&& material != Material.gourd ? 1.0F : 1.5F;
		}
	}

	/**
	 * Current implementations of this method in child classes do not use the
	 * entry argument beside ev. They just raise the damage on the stack.
	 */
	public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase) {
		par1ItemStack.damageItem(1, par3EntityLivingBase);
		return true;
	}

	public boolean onBlockDestroyed(ItemStack p_150894_1_, World p_150894_2_,
			Block p_150894_3_, int p_150894_4_, int p_150894_5_,
			int p_150894_6_, EntityLivingBase p_150894_7_) {
		if ((double) p_150894_3_.getBlockHardness(p_150894_2_, p_150894_4_,
				p_150894_5_, p_150894_6_) != 0.0D) {
			p_150894_1_.damageItem(2, p_150894_7_);
		}

		return true;
	}

	/**
	 * Returns True is the item is renderer in full 3D when hold.
	 */
	@SideOnly(Side.CLIENT)
	public boolean isFull3D() {
		return true;
	}

	/**
	 * returns the action that specifies what animation to play when the items
	 * is being used
	 */
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.block;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 72000;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 500, 2));;
		return par1ItemStack;
	}

	public boolean func_150897_b(Block p_150897_1_) {
		return p_150897_1_ == Blocks.web;
	}

	/**
	 * Return the enchantability factor of the item, most of the time is based
	 * on material.
	 */
	public int getItemEnchantability() {
		return this.toolMaterial.getEnchantability();
	}

	/**
	 * Return the name for this tool's material.
	 */
	public String getToolMaterialName() {
		return this.toolMaterial.toString();
	}

	/**
	 * Return whether this item is repairable in an anvil.
	 */
	public boolean getIsRepairable(ItemStack par1ItemStack,
			ItemStack par2ItemStack) {
		return this.toolMaterial.func_150995_f() == par2ItemStack.getItem() ? true
				: super.getIsRepairable(par1ItemStack, par2ItemStack);
	}

	/**
	 * Gets a map of item attribute modifiers, used by ItemSword to increase hit
	 * damage.
	 */
	public Multimap getItemAttributeModifiers() {
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage
				.getAttributeUnlocalizedName(), new AttributeModifier(
				field_111210_e, "Weapon modifier",
				(double) this.field_150934_a, 0));
		return multimap;
	}

	public static class MobInfo {
		String c;
		boolean special;

		public MobInfo(String c, boolean special) {
			this.c = c;
			this.special = special;
		}
	}
}
