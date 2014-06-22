package mod.HellCoder.things;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.Metadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import mod.HellCoder.HellCoderCore.Utils.FCLog;
import mod.HellCoder.HellCoderCore.Utils.ModVersionChecker;
import mod.HellCoder.things.TileEntity.TileRM;
import mod.HellCoder.things.core.CommonProxy;
import mod.HellCoder.things.core.HandlerForgeEvents;
import mod.HellCoder.things.core.Localization.LocalizationHandler;
import mod.HellCoder.things.handler.RMGuiHandler;
import mod.HellCoder.things.lib.RegBlocks;
import mod.HellCoder.things.lib.RegItems;
import mod.HellCoder.things.lib.Recipes;
import mod.HellCoder.things.tab.FCTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

@Mod(modid = "FC2", name = "FriendsCraft2_mod", version = "1.7.2-1.1.0", acceptedMinecraftVersions = "1.7.2", 
useMetadata = true, dependencies = "required-after:BuildCraft|Core")
public class FriendsCraft2mod {
	
	public boolean doPipeInteract = false;
	
	public static CreativeTabs tabsFC = new FCTab(CreativeTabs.getNextID(), "FC");

	@SidedProxy(clientSide = "mod.HellCoder.things.core.ClientProxy", serverSide = "mod.HellCoder.things.core.CommonProxy")
	public static CommonProxy proxy;

	public static ModVersionChecker versionChecker;
	private static String versionURL = "https://dl.dropboxusercontent.com/s/natqj962xnjlipk/PickAxe%20right-click.txt";
	private static String mcfTopic = "http://www.minecraftforum.net/topic/1894204-162forgesspsmppickaxe-right-click-mod/";
	public static boolean allowUpdateCheck = true;

	public static int updateTimeoutMilliseconds = 3000;

	@Metadata(value = "FC2")
	public static ModMetadata metadata;

	@Mod.Instance("FC2")
	public static FriendsCraft2mod instance;

	public static int energyPerPoint = 5;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		LocalizationHandler.loadLanguages();
		
		FCLog.info("Loading Items");
		RegItems.init();
		FCLog.info("Load Items");

		FCLog.info("Loading Blocks");
		RegBlocks.init();
		FCLog.info("Load Blocks");
		
		MinecraftForge.EVENT_BUS.register(this);
	}

	@EventHandler
	public static void init(FMLInitializationEvent event) {

		FCLog.info("Loading Recipes");
		Recipes.init();
		FCLog.info("Load Recipes");
		
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new RMGuiHandler());
		GameRegistry.registerTileEntity(TileRM.class, "RollingMachine");

		if(allowUpdateCheck){
		versionChecker = new ModVersionChecker(metadata.name, metadata.version, versionURL, mcfTopic);
		versionChecker.checkVersionWithLogging();
		}
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		if (Loader.isModLoaded("BuildCraft|Transport")) {
			doPipeInteract = true;
		}
	}

}