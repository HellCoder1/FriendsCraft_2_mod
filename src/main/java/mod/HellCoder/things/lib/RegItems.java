package mod.HellCoder.things.lib;

import java.util.List;

import cofh.item.ItemBase;
import cofh.item.ItemBucket;
import cpw.mods.fml.common.registry.GameRegistry;
import mod.HellCoder.things.FriendsCraft2mod;
import mod.HellCoder.things.Items.*;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraftforge.common.util.EnumHelper;

public class RegItems {

	public static int armorFCHelmetID;
	public static int armorFCChestID;
	public static int armorFCLegsID;
	public static int armorFCBootsID;
	
	public static ArmorMaterial MaterialArmorDiga = EnumHelper.addArmorMaterial("DIGA", 33, new int[] {3, 8, 6, 3}, 10);
	
	public static Item diga;
	public static Item digaingot;
	public static Item NagibatorSword;
	public static Item pickaxeWood;
	public static Item pickaxeStone;
	public static Item pickaxeIron;
	public static Item pickaxeDiamond;
	public static Item pickaxeGold;
	public static Item Upgrade;
	public static Item FCHelmet;
	public static Item FCChest;
	public static Item FCLegs;
	public static Item FCBoots;
	public static Item CoolantBlock;
	public static Item GoldRedstoneGear;
	public static Item HighTemperaturePlate;
	public static Item Insulator;
	public static Item IronRedstoneGear;
	public static Item MeltingForm;
	public static Item MeltingPlate;
	public static Item Press;
	public static Item Rollers;
	public static Item SpecialPipe;
	
	public static ItemBase itemMaterial;

	public static void init() {

		/**
		 * Items
		 */
		diga = registerItem(new Item().setUnlocalizedName("diga").setTextureName("friendscraft:diga").setCreativeTab(FriendsCraft2mod.tabsFC));
		digaingot = registerItem(new Item().setUnlocalizedName("digaingot").setTextureName("friendscraft:digaingot").setCreativeTab(FriendsCraft2mod.tabsFC));
		Upgrade = registerItem(new Item().setUnlocalizedName("Upgrade").setTextureName("friendscraft:Upgrade").setCreativeTab(FriendsCraft2mod.tabsFC));
		CoolantBlock = registerItem(new Item().setUnlocalizedName("CoolantBlock").setTextureName("friendscraft:CoolantBlock").setCreativeTab(FriendsCraft2mod.tabsFC));
		GoldRedstoneGear = registerItem(new Item().setUnlocalizedName("GoldRedstoneGear").setTextureName("friendscraft:GoldRedstoneGear").setCreativeTab(FriendsCraft2mod.tabsFC));
		HighTemperaturePlate = registerItem(new Item().setUnlocalizedName("HighTemperaturePlate").setTextureName("friendscraft:HighTemperaturePlate").setCreativeTab(FriendsCraft2mod.tabsFC));
		IronRedstoneGear = registerItem(new Item().setUnlocalizedName("IronRedstoneGear").setTextureName("friendscraft:IronRedstoneGear").setCreativeTab(FriendsCraft2mod.tabsFC));
		MeltingForm = registerItem(new Item().setUnlocalizedName("MeltingForm").setTextureName("friendscraft:MeltingForm").setCreativeTab(FriendsCraft2mod.tabsFC));
		MeltingPlate = registerItem(new Item().setUnlocalizedName("MeltingPlate").setTextureName("friendscraft:MeltingPlate").setCreativeTab(FriendsCraft2mod.tabsFC));
		Press = registerItem(new Item().setUnlocalizedName("Press").setTextureName("friendscraft:Press").setCreativeTab(FriendsCraft2mod.tabsFC));
		Rollers = registerItem(new Item().setUnlocalizedName("Rollers").setTextureName("friendscraft:Rollers").setCreativeTab(FriendsCraft2mod.tabsFC));
		Insulator = registerItem(new Item().setUnlocalizedName("Insulator").setTextureName("friendscraft:Insulator").setCreativeTab(FriendsCraft2mod.tabsFC));
		SpecialPipe = registerItem(new Item().setUnlocalizedName("SpecialPipe").setTextureName("friendscraft:SpecialPipe").setCreativeTab(FriendsCraft2mod.tabsFC));
		
		
		/**
		 * Tools
		 */
		NagibatorSword = registerItem(new NagibatorSword(
				MatirialNagibator.NAGIBATOR).setUnlocalizedName(
				"NagibatorSword").setTextureName("friendscraft:Nagibator"));

		pickaxeWood = registerItem(new Pickaxe(ToolMaterial.WOOD, 0)
				.setUnlocalizedName("pickaxeWood"));
		pickaxeStone = registerItem(new Pickaxe(ToolMaterial.STONE, 1)
				.setUnlocalizedName("pickaxeStone"));
		pickaxeIron = registerItem(new Pickaxe(ToolMaterial.IRON, 2)
				.setUnlocalizedName("pickaxeIron"));
		pickaxeDiamond = registerItem(new Pickaxe(ToolMaterial.EMERALD, 3)
				.setUnlocalizedName("pickaxeDiamond"));
		pickaxeGold = registerItem(new Pickaxe(ToolMaterial.GOLD, 4)
				.setUnlocalizedName("pickaxeGold"));
		
		/**
		 * Armor
		 */

		FCHelmet = registerItem(new FCArmor(MaterialArmorDiga, armorFCHelmetID, 0).setUnlocalizedName("FCHelmet"));
		FCChest = registerItem(new FCArmor(MaterialArmorDiga, armorFCHelmetID, 1).setUnlocalizedName("FCChest"));
		FCLegs = registerItem(new FCArmor(MaterialArmorDiga, armorFCHelmetID, 2).setUnlocalizedName("FCLegs"));
		FCBoots = registerItem(new FCArmor(MaterialArmorDiga, armorFCHelmetID, 3).setUnlocalizedName("FCBoots"));
		
	}

	public static Item registerItem(Item item) {
		GameRegistry.registerItem(item, item.getUnlocalizedName().replace("item.", ""));

		return item;
	}

}
