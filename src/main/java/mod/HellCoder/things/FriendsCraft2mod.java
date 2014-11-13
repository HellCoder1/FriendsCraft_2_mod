package mod.HellCoder.things;

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.Mod.Metadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import mod.HellCoder.HellCoderCore.Utils.FCLog;
import mod.HellCoder.HellCoderCore.Utils.VersionChecker;
import mod.HellCoder.things.Blocks.machine.fermenter.TileEntityFermenter;
import mod.HellCoder.things.Blocks.machine.rollingmachine.TileEntityRM;
import mod.HellCoder.things.TileEntity.TileEntityHermeticPipe;
import mod.HellCoder.things.core.Localization.LocalizationHandler;
import mod.HellCoder.things.fluid.FCFluids;
import mod.HellCoder.things.handler.ConfigurationHandler;
import mod.HellCoder.things.handler.GuiHandler;
import mod.HellCoder.things.lib.Recipes;
import mod.HellCoder.things.lib.RegBlocks;
import mod.HellCoder.things.lib.RegItems;
import mod.HellCoder.things.tab.FCTab;
import mod.HellCoder.things.world.DigaOreGenerator;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = FriendsCraft2mod.MOD_ID, name = FriendsCraft2mod.modname, version = "1.7.10-0.1.1", dependencies = "required-after:CoFHCore", acceptedMinecraftVersions = "1.7.10", 
useMetadata = true,  guiFactory = "mod.HellCoder.things.gui.client.GuiFactory")
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
		FMLCommonHandler.instance().bus().register(new ConfigurationHandler());
		
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

		GameRegistry.registerTileEntity(TileEntityFermenter.class, "FCFermenter");
		GameRegistry.registerTileEntity(TileEntityRM.class, "RollingMachine");
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
		
		GameRegistry.registerTileEntity(TileEntityHermeticPipe.class, "HermeticPipe");
		//versionchecker
        versionChecker = new VersionChecker(metadata.name, metadata.version, versionURL, mcfTopic);
        versionChecker.checkVersionWithLogging();
		
        //proxy
		proxy.registerTickHandler();
		proxy.registerDisplayInformation();
		proxy.initRenderingAndTextures();
		proxy.registerRenderThings();
		
		MinecraftForge.EVENT_BUS.register(proxy);
		FMLCommonHandler.instance().bus().register(instance);
		
		FMLInterModComms.sendMessage("Waila", "register", "mod.HellCoder.things.core.WailaProvider.callbackRegister");
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		if (Loader.isModLoaded("BuildCraft|Transport")) {
			doPipeInteract = true;
		}
	}

}