package mod.HellCoder.things;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.Metadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mod.HellCoder.HellCoderCore.Utils.FCLog;
import mod.HellCoder.HellCoderCore.Utils.VersionChecker;
import mod.HellCoder.things.TileEntity.TileEntityHermeticPipe;
import mod.HellCoder.things.core.Localization.LocalizationHandler;
import mod.HellCoder.things.fluid.FCFluids;
import mod.HellCoder.things.handler.ConfigurationHandler;
import mod.HellCoder.things.handler.GuiHandler;
import mod.HellCoder.things.lib.RegBlocks;
import mod.HellCoder.things.lib.RegItems;
import mod.HellCoder.things.lib.Recipes;
import mod.HellCoder.things.rollingmachine.RMRender;
import mod.HellCoder.things.rollingmachine.TileEntityRM;
import mod.HellCoder.things.tab.FCTab;
import mod.HellCoder.things.world.DigaOreGenerator;
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

@Mod(modid = FriendsCraft2mod.MOD_ID, name = FriendsCraft2mod.modname, version = "1.7.10-1.1", acceptedMinecraftVersions = "1.7.10", 
useMetadata = true, guiFactory = "mod.HellCoder.things.gui.client.GuiFactory")
public class FriendsCraft2mod {
	
	public static final String MOD_ID = "FC2";
	public static final String modname = "FriendsCraft2_mod";
	
	@Metadata(value = FriendsCraft2mod.MOD_ID)
	public static ModMetadata metadata;

	@Mod.Instance(value = FriendsCraft2mod.MOD_ID)
	public static FriendsCraft2mod instance;

	@SidedProxy(clientSide = "mod.HellCoder.things.ClientProxy", serverSide = "mod.HellCoder.things.CommonProxy")
	public static CommonProxy proxy;
	
	public static CreativeTabs tabsFC = new FCTab(CreativeTabs.getNextID(), "FC");
	
	protected static VersionChecker versionChecker;
	private final static  String versionURL = "https://dl.dropboxusercontent.com/s/natqj962xnjlipk/PickAxe%20right-click.txt";
	private final static  String mcfTopic = "http://www.minecraftforum.net/topic/1894204-162forgesspsmppickaxe-right-click-mod/";
	public static boolean allowUpdateCheck = true;
	public static int updateTimeoutMilliseconds = 3000;

	private static DigaOreGenerator DigaWorldGen;
	
	public boolean doPipeInteract = false;

    public static boolean steamRegistered;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		metadata = event.getModMetadata();
		
		MinecraftForge.EVENT_BUS.register(this);
		
		LocalizationHandler.loadLanguages();
		
		ConfigurationHandler.init(event.getSuggestedConfigurationFile());
 		
		//items
		FCLog.info("Loading Items");
		RegItems.init();
		FCLog.info("Load Items");

		//blocks
		FCLog.info("Loading Blocks");
		RegBlocks.init();
		FCLog.info("Load Blocks");
		
		//fluids
		FCFluids.preInit();
		
	}

	@Mod.EventHandler
	public static void init(FMLInitializationEvent event) {

		//worldgen
		DigaWorldGen = new DigaOreGenerator();
		GameRegistry.registerWorldGenerator(DigaWorldGen, 1);
		
		//recipes
		FCLog.info("Loading Recipes");
		Recipes.init();
		FCLog.info("Load Recipes");

		// tile Entity register
		GameRegistry.registerTileEntity(TileEntityRM.class, "RollingMachine");
		RenderingRegistry.registerBlockHandler(2105, RMRender.INSTANCE);
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
		
		GameRegistry.registerTileEntity(TileEntityHermeticPipe.class, "HermeticPipe");
		
		//versionchecker
        versionChecker = new VersionChecker(metadata.name, metadata.version, versionURL, mcfTopic);
        versionChecker.checkVersionWithLogging();
		
        //proxy
		proxy.registerTickHandler();
		proxy.registerDisplayInformation();
		proxy.initRenderingAndTextures();
		
		MinecraftForge.EVENT_BUS.register(proxy);
		FMLCommonHandler.instance().bus().register(instance);
		
		FMLInterModComms.sendMessage("Waila", "register", "mod.HellCoder.things.core.ProviderDemo.callbackRegister");
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		if (Loader.isModLoaded("BuildCraft|Transport")) {
			doPipeInteract = true;
		}
	}

}