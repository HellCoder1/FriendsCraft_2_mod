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
import mod.HellCoder.things.core.Localization.LocalizationHandler;
import mod.HellCoder.things.handler.GuiHandlerFurnace;
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

@Mod(modid = "FC2", name = "FriendsCraft2_mod", version = "1.7.2-1.1.0", acceptedMinecraftVersions = "1.7.2", 
useMetadata = true)
public class FriendsCraft2mod {
	
	@Metadata(value = "FC2")
	public static ModMetadata metadata;

	@Mod.Instance(value = "FC2")
	public static FriendsCraft2mod instance;

	@SidedProxy(clientSide = "mod.HellCoder.things.ClientProxy", serverSide = "mod.HellCoder.things.CommonProxy")
	public static CommonProxy proxy;

	public static final int DEFAULT_PERDITION_DRAIN = 1;
    public static final int DEFAULT_PERDITION_INTERVAL = 20;
	
	public static CreativeTabs tabsFC = new FCTab(CreativeTabs.getNextID(), "FC");
	
	protected static VersionChecker versionChecker;
	private final static  String versionURL = "https://dl.dropboxusercontent.com/s/natqj962xnjlipk/PickAxe%20right-click.txt";
	private final static  String mcfTopic = "http://www.minecraftforum.net/topic/1894204-162forgesspsmppickaxe-right-click-mod/";
	public static boolean allowUpdateCheck = true;

	public static int updateTimeoutMilliseconds = 3000;
	
	public static int energyPerPoint = 5;
	private static DigaOreGenerator DigaWorldGen;
	
	public boolean doPipeInteract = false;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		metadata = event.getModMetadata();
		
		MinecraftForge.EVENT_BUS.register(this);
		
		LocalizationHandler.loadLanguages();
 		
		FCLog.info("Loading Items");
		RegItems.init();
		FCLog.info("Load Items");

		FCLog.info("Loading Blocks");
		RegBlocks.init();
		FCLog.info("Load Blocks");
		
	}

	@Mod.EventHandler
	public static void init(FMLInitializationEvent event) {

		DigaWorldGen = new DigaOreGenerator();
		GameRegistry.registerWorldGenerator(DigaWorldGen, 1);
		
		FCLog.info("Loading Recipes");
		Recipes.init();
		FCLog.info("Load Recipes");

		GameRegistry.registerTileEntity(TileEntityRM.class, "RollingMachine");
		RenderingRegistry.registerBlockHandler(2105, RMRender.INSTANCE);
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandlerFurnace());
		
		FMLCommonHandler.instance().bus().register(instance);
		
        versionChecker = new VersionChecker(metadata.name, metadata.version, versionURL, mcfTopic);
        versionChecker.checkVersionWithLogging();
		
		proxy.registerTickHandler();
		proxy.registerDisplayInformation();
		proxy.initRenderingAndTextures();
		
		FMLInterModComms.sendMessage("Waila", "register", "mod.HellCoder.things.core.ProviderDemo.callbackRegister");
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		if (Loader.isModLoaded("BuildCraft|Transport")) {
			doPipeInteract = true;
		}
	}

}