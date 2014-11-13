package cofh;

import cofh.api.transport.RegistryEnderAttuned;
import cofh.core.CoFHProps;
import cofh.core.Proxy;
import cofh.core.command.CommandFriend;
import cofh.core.command.CommandHandler;
import cofh.core.enchantment.CoFHEnchantment;
import cofh.core.entity.DropHandler;
import cofh.core.gui.GuiHandler;
import cofh.core.network.PacketCoFHBase;
import cofh.core.network.PacketCore;
import cofh.core.network.PacketCore.PacketTypes;
import cofh.core.network.PacketHandler;
import cofh.core.network.PacketSocial;
import cofh.core.util.ConfigHandler;
import cofh.core.util.FMLEventHandler;
import cofh.core.util.SocialRegistry;
import cofh.core.util.crafting.RecipeAugmentable;
import cofh.core.util.crafting.RecipeSecure;
import cofh.core.util.crafting.RecipeUpgrade;
import cofh.core.util.fluid.BucketHandler;
import cofh.core.util.oredict.OreDictionaryArbiter;
import cofh.core.world.FeatureParser;
import cofh.core.world.WorldHandler;
import cofh.lib.util.helpers.StringHelper;
import cofh.mod.BaseMod;
import cofh.mod.updater.UpdateManager;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

import java.io.File;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = CoFHCore.modId, name = CoFHCore.modName, version = CoFHCore.version, dependencies = CoFHCore.dependencies)
public class CoFHCore extends BaseMod {

	public static final String modId = "CoFHCore";
	public static final String modName = "CoFH Core";
	public static final String version = CoFHProps.VERSION;
	public static final String dependencies = CoFHProps.DEPENDENCIES;
	public static final String releaseURL = "https://raw.github.com/CoFH/CoFHCore/master/VERSION";

	@Instance(modId)
	public static CoFHCore instance;

	@SidedProxy(clientSide = "cofh.core.ProxyClient", serverSide = "cofh.core.Proxy")
	public static Proxy proxy;

	public static Logger log = LogManager.getLogger(modId);

	public static final ConfigHandler configCore = new ConfigHandler(version);
	public static final ConfigHandler configLoot = new ConfigHandler(version);
	public static final ConfigHandler configClient = new ConfigHandler(version);
	public static final GuiHandler guiHandler = new GuiHandler();

	public static MinecraftServer server;

	/* INIT SEQUENCE */
	public CoFHCore() {

		super(log);
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		CoFHProps.configDir = event.getModConfigurationDirectory();

		// loadLang();

		UpdateManager.registerUpdater(new UpdateManager(this, releaseURL, CoFHProps.DOWNLOAD_URL));
		configCore.setConfiguration(new Configuration(new File(CoFHProps.configDir, "/cofh/CoFHCore.cfg")));
		configClient.setConfiguration(new Configuration(new File(CoFHProps.configDir, "/cofh/Client.cfg")));
		MinecraftForge.EVENT_BUS.register(proxy);
		proxy.preinit();

		moduleCore();
		moduleLoot();

		FeatureParser.initialize();
		WorldHandler.initialize();
		FMLEventHandler.initialize();
		BucketHandler.initialize();
		PacketHandler.instance.initialize();
		RecipeSorter.register("cofh:augment", RecipeAugmentable.class, RecipeSorter.Category.SHAPED, "before:forge:shapedore");
		RecipeSorter.register("cofh:secure", RecipeSecure.class, RecipeSorter.Category.SHAPED, "before:cofh:upgrade");
		RecipeSorter.register("cofh:upgrade", RecipeUpgrade.class, RecipeSorter.Category.SHAPED, "before:forge:shapedore");

		registerOreDictionaryEntries();
	}

	@EventHandler
	public void initialize(FMLInitializationEvent event) {

		/* Register Handlers */
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, guiHandler);
		//CommandHandler.registerSubCommand(CommandFriend.instance);
		PacketCore.initialize();
		PacketSocial.initialize();
		SocialRegistry.initialize();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

		OreDictionaryArbiter.initialize();
		CoFHEnchantment.postInit();

		proxy.registerKeyBinds();
		proxy.registerRenderInformation();
		proxy.registerTickHandlers();
		proxy.registerPacketInformation();

		PacketHandler.instance.postInit();
	}

	@EventHandler
	public void loadComplete(FMLLoadCompleteEvent event) {

		try {
			FeatureParser.complete();
		} catch (Throwable t) {
			t.printStackTrace();
		}
		configCore.cleanUp(false, true);

		try {
			FeatureParser.parseGenerationFile();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {

		RegistryEnderAttuned.linkConf = new Configuration(new File(DimensionManager.getCurrentSaveRootDirectory(), "/cofh/EnderFrequencies.cfg"));
		RegistryEnderAttuned.linkConf.load();
		OreDictionaryArbiter.initialize();
		//CommandHandler.initCommands(event);
		server = event.getServer();
	}

	public void handleConfigSync(PacketCoFHBase payload) {

		FMLEventHandler.instance.handleIdMappingEvent(null);
	}

	public PacketCoFHBase getConfigSync() {

		PacketCoFHBase payload = PacketCore.getPacket(PacketTypes.CONFIG_SYNC);

		return payload;
	}

	public void registerOreDictionaryEntries() {

		registerOreDictionaryEntry("blockCloth", new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE));
		registerOreDictionaryEntry("coal", new ItemStack(Items.coal, 1, 0));
		registerOreDictionaryEntry("charcoal", new ItemStack(Items.coal, 1, 1));
	}

	private boolean registerOreDictionaryEntry(String oreName, ItemStack ore) {

		if (OreDictionary.getOres(oreName).isEmpty()) {
			OreDictionary.registerOre(oreName, ore);
			return true;
		}
		return false;
	}

	private boolean moduleCore() {

		String category = "general";

		String comment = "Enable this to be informed of non-critical updates. You will still receive critical update notifications.";
		CoFHProps.enableUpdateNotice = configCore.get(category, "EnableUpdateNotifications", true, comment);

		comment = "Enable this to log when a block is dismantled.";
		CoFHProps.enableDismantleLogging = configCore.get(category, "EnableDismantleLogging", CoFHProps.enableDismantleLogging, comment);

		comment = "Set to true to display death messages for any named entity.";
		CoFHProps.enableLivingEntityDeathMessages = configCore.get(category, "EnableGenericDeathMessage", true, comment);

		category = "gui.tooltips";
		comment = "This adds a tooltip prompting you to press Shift for more details on various items.";
		StringHelper.displayShiftForDetail = configCore.get(category, "DisplayHoldShiftForDetail", true, comment);

		comment = "This option determines if items contained in other items are displayed as a single quantity or a stack count.";
		StringHelper.displayStackCount = configCore.get(category, "DisplayStackCountInInventory", false, comment);

		category = "security";
		comment = "Enable this to allow for Server Ops to access 'secure' blocks. Your players will be warned upon server connection. (Default: false)";
		CoFHProps.enableOpSecureAccess = configCore.get(category, "OpsCanAccessSecureBlocks", false, comment);

		comment = "Enable this to be warned about Ops having access to 'secure' blocks when connecting to a server. (Default: true)";
		CoFHProps.enableOpSecureAccessWarning = configCore.get(category, "OpsCanAccessSecureBlocksWarning", true, comment);

		category = "tweaks";
		comment = "Set this to a value >1 to make trees grow more infrequently. Rate is 1/this value.";
		CoFHProps.treeGrowthChance = configCore.get(category, "TreeGrowthChance", 1, comment);

		configCore.save();

		// CLIENT ONLY
		// CoFHProps.soundVolume = MathHelper.clampF((float) configClient.get("sound", "Volume", 1.0F), 0.0F, 1.0F);

		configClient.save();

		return true;
	}

	private boolean moduleLoot() {

		configLoot.setConfiguration(new Configuration(new File(CoFHProps.configDir, "/cofh/CoFHLoot.cfg")));

		String category = "general";
		String comment = null;

		boolean enable = configLoot.get(category, "EnableModule", true);

		if (!enable) {
			configLoot.save();
			return false;
		}
		category = "feature.heads";

		comment = "If enabled, mobs only drop heads when killed by players.";
		DropHandler.mobPvEOnly = configLoot.get(category, "MobsDropOnPvEOnly", DropHandler.mobPvEOnly, comment);

		comment = "If enabled, players only drop heads when killed by other players.";
		DropHandler.playerPvPOnly = configLoot.get(category, "PlayersDropOnPvPOnly", DropHandler.playerPvPOnly, comment);

		category = "feature.heads.enable";

		DropHandler.playersEnabled = configLoot.get(category, "PlayersDropHeads", DropHandler.playersEnabled);
		DropHandler.creeperEnabled = configLoot.get(category, "CreepersDropHeads", DropHandler.creeperEnabled);
		DropHandler.skeletonEnabled = configLoot.get(category, "SkeletonsDropHeads", DropHandler.skeletonEnabled);
		DropHandler.skeletonEnabled = configLoot.get(category, "WitherSkeletonsDropHeads", DropHandler.witherSkeletonEnabled);
		DropHandler.zombieEnabled = configLoot.get(category, "ZombiesDropHeads", DropHandler.zombieEnabled);

		category = "feature.heads.chance";

		DropHandler.playerChance = configLoot.get(category, "PlayerDropChance", DropHandler.playerChance);
		DropHandler.creeperChance = configLoot.get(category, "CreeperDropChance", DropHandler.creeperChance);
		DropHandler.skeletonChance = configLoot.get(category, "SkeletonDropChance", DropHandler.skeletonChance);
		DropHandler.witherSkeletonChance = configLoot.get(category, "WitherSkeletonDropChance", DropHandler.witherSkeletonChance);
		DropHandler.zombieChance = configLoot.get(category, "ZombieDropChance", DropHandler.zombieChance);

		configLoot.save();

		MinecraftForge.EVENT_BUS.register(DropHandler.instance);

		return true;
	}

	/* BaseMod */
	@Override
	public String getModId() {

		return modId;
	}

	@Override
	protected String getAssetDir() {

		return "cofh";
	}

	@Override
	public String getModName() {

		return modName;
	}

	@Override
	public String getModVersion() {

		return version;
	}

}
