package mod.HellCoder.things.lib;

import java.util.List;

import cofh.item.ItemBase;
import cofh.util.ItemHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import mod.HellCoder.things.FriendsCraft2mod;
import mod.HellCoder.things.Items.*;
import mod.HellCoder.things.Items.tool.NagibatorSword;
import mod.HellCoder.things.Items.tool.Scythe;
import mod.HellCoder.things.fluid.FCFluids;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class RegItems {

	public static int armorFCHelmetID;
	public static int armorFCChestID;
	public static int armorFCLegsID;
	public static int armorFCBootsID;
	
	public static ArmorMaterial MaterialArmorDiga = EnumHelper.addArmorMaterial("DIGA", 33, new int[] {3, 8, 6, 3}, 10);
	public static final Item.ToolMaterial Sword_MATERIAL = EnumHelper.addToolMaterial("DIGA", 3, 100, 8.0F, 0, 25);
	
	public static Item diga;
	public static Item digaingot;
	public static Item NagibatorSword;
	public static Item FCHelmet;
	public static Item FCChest;
	public static Item FCLegs;
	public static Item FCBoots;
	public static Item IronGear;
	public static Item GoldGear;
	public static Item IronRedstoneGear;
	public static Item GoldRedstoneGear;
	public static Item CoolantBlock;
	public static Item HighTemperaturePlate;
	public static Item Insulator;
	public static Item MeltingForm;
	public static Item Press;
	public static Item Rollers;
	public static Item scythe;
	public static Item ironPlate;
	public static Item smallIronPlate;
	public static Item compressedPlants;
	public static Item smallTank;
	public static Item scytheBlade;
	public static Item scissors;
	public static Item termalDispenser;
	public static Item pressureRegulator;
	public static Item pressureTank;
	public static Item rawOrganicMaterial;
	public static Item Hammer;
	
	public static ItemBase itemMaterial;
	
	public static ItemStack copperIngot;
	
	public static void init() {

		itemMaterial = (ItemBase) new ItemBase("friendscraft").setUnlocalizedName("material").setCreativeTab(FriendsCraft2mod.tabsFC);
		
		/**
		 * Items
		 */
		diga = registerItem(new Item().setUnlocalizedName("diga").setTextureName("friendscraft:digaAnim").setCreativeTab(FriendsCraft2mod.tabsFC));
		digaingot = registerItem(new Item().setUnlocalizedName("digaingot").setTextureName("friendscraft:digaingotAnim").setCreativeTab(FriendsCraft2mod.tabsFC));
		IronGear = registerItem(new Item().setUnlocalizedName("IronGear").setTextureName("friendscraft:IronGear").setCreativeTab(FriendsCraft2mod.tabsFC));
		GoldGear = registerItem(new Item().setUnlocalizedName("GoldGear").setTextureName("friendscraft:GoldGear").setCreativeTab(FriendsCraft2mod.tabsFC));
		IronRedstoneGear = registerItem(new Item().setUnlocalizedName("IronRedstoneGear").setTextureName("friendscraft:IronRedstoneGear").setCreativeTab(FriendsCraft2mod.tabsFC));
		GoldRedstoneGear = registerItem(new Item().setUnlocalizedName("GoldRedstoneGear").setTextureName("friendscraft:GoldRedstoneGear").setCreativeTab(FriendsCraft2mod.tabsFC));
		CoolantBlock = registerItem(new Item().setUnlocalizedName("CoolantBlock").setTextureName("friendscraft:CoolantBlock").setCreativeTab(FriendsCraft2mod.tabsFC));
		HighTemperaturePlate = registerItem(new Item().setUnlocalizedName("HighTemperaturePlate").setTextureName("friendscraft:HighTemperaturePlate").setCreativeTab(FriendsCraft2mod.tabsFC));
		MeltingForm = registerItem(new Item().setUnlocalizedName("MeltingForm").setTextureName("friendscraft:MeltingForm").setCreativeTab(FriendsCraft2mod.tabsFC));
		Press = registerItem(new Item().setUnlocalizedName("Press").setTextureName("friendscraft:Press").setCreativeTab(FriendsCraft2mod.tabsFC));
		Rollers = registerItem(new Item().setUnlocalizedName("Rollers").setTextureName("friendscraft:Rollers").setCreativeTab(FriendsCraft2mod.tabsFC));
		Insulator = registerItem(new Item().setUnlocalizedName("Insulator").setTextureName("friendscraft:Insulator").setCreativeTab(FriendsCraft2mod.tabsFC));
		ironPlate = registerItem(new Item().setUnlocalizedName("IronPlate").setTextureName("friendscraft:IronPlate").setCreativeTab(FriendsCraft2mod.tabsFC));
		smallIronPlate = registerItem(new Item().setUnlocalizedName("SmallIronPlate").setTextureName("friendscraft:SmallIronPlate").setCreativeTab(FriendsCraft2mod.tabsFC));
		OreDictionary.registerOre("plateIron", smallIronPlate);
		compressedPlants = registerItem(new Item().setUnlocalizedName("CompressedPlants").setTextureName("friendscraft:CompressedPlants").setCreativeTab(FriendsCraft2mod.tabsFC));
		smallTank = registerItem(new Item().setUnlocalizedName("SmallTank").setTextureName("friendscraft:SmallTank").setCreativeTab(FriendsCraft2mod.tabsFC));
		scytheBlade = registerItem(new Item().setUnlocalizedName("ScytheBlade").setTextureName("friendscraft:ScytheBlade").setCreativeTab(FriendsCraft2mod.tabsFC));
		scissors = registerItem(new Item().setUnlocalizedName("Scissors").setTextureName("friendscraft:Scissors").setCreativeTab(FriendsCraft2mod.tabsFC));
		termalDispenser = registerItem(new Item().setUnlocalizedName("TermalDispenser").setTextureName("friendscraft:TermalDispenser").setCreativeTab(FriendsCraft2mod.tabsFC));
		pressureRegulator = registerItem(new Item().setUnlocalizedName("PressureRegulator").setTextureName("friendscraft:PressureRegulator").setCreativeTab(FriendsCraft2mod.tabsFC));
		pressureTank = registerItem(new Item().setUnlocalizedName("PressureTank").setTextureName("friendscraft:PressureTank").setCreativeTab(FriendsCraft2mod.tabsFC));
		rawOrganicMaterial = registerItem(new Item().setUnlocalizedName("Raw_Organic_Material").setTextureName("friendscraft:RawOrganicMaterial").setCreativeTab(FriendsCraft2mod.tabsFC));
		
		
		
		/**
		 * Tools
		 */
		NagibatorSword = registerItem(new NagibatorSword(Sword_MATERIAL)
		.setUnlocalizedName("NagibatorSword").setTextureName("friendscraft:Nagibator").setCreativeTab(FriendsCraft2mod.tabsFC));
		
		scythe = registerItem((new Scythe(ToolMaterial.IRON).setUnlocalizedName("Scythe").setTextureName("friendscraft:scythe").setCreativeTab(FriendsCraft2mod.tabsFC)));
		
		Hammer = registerItem(new Hammer().setUnlocalizedName("Hammer").setTextureName("friendscraft:Hammer").setCreativeTab(FriendsCraft2mod.tabsFC));
		/**
		 * Armor
		 */
		FCHelmet = registerItem(new FCArmor(MaterialArmorDiga, armorFCHelmetID, 0).setUnlocalizedName("FCHelmet"));
		FCChest = registerItem(new FCArmor(MaterialArmorDiga, armorFCHelmetID, 1).setUnlocalizedName("FCChest"));
		FCLegs = registerItem(new FCArmor(MaterialArmorDiga, armorFCHelmetID, 2).setUnlocalizedName("FCLegs"));
		FCBoots = registerItem(new FCArmor(MaterialArmorDiga, armorFCHelmetID, 3).setUnlocalizedName("FCBoots"));
		
		/**
		 * Materials
		 */
		copperIngot = itemMaterial.addOreDictItem(64, "ingotCopper");
		
		     /* Storage */
		     ItemHelper.addStorageRecipe(copperIngot, "nuggetCopper");
		     ItemHelper.addReverseStorageRecipe(copperIngot, "blockCopper");
	}

	public static Item registerItem(Item item) {
		GameRegistry.registerItem(item, item.getUnlocalizedName().replace("item.", ""));

		return item;
	}

}
