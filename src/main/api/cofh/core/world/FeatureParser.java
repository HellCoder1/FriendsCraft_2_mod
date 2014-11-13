package cofh.core.world;

import cofh.api.world.IFeatureGenerator;
import cofh.api.world.IFeatureParser;
import cofh.core.CoFHProps;
import cofh.core.util.CoreUtils;
import cofh.core.world.feature.DecorationParser;
import cofh.core.world.feature.FractalParser;
import cofh.core.world.feature.NormalParser;
import cofh.core.world.feature.SurfaceParser;
import cofh.core.world.feature.UnderfluidParser;
import cofh.core.world.feature.UniformParser;
import cofh.lib.util.WeightedRandomBlock;
import cofh.lib.util.WeightedRandomNBTTag;
import cofh.lib.util.helpers.MathHelper;
import cofh.lib.world.biome.BiomeInfo;
import cofh.lib.world.biome.BiomeInfoRarity;
import cofh.lib.world.biome.BiomeInfoSet;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonWriter;
import cpw.mods.fml.common.registry.GameRegistry;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.biome.BiomeGenBase.TempCategory;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.DungeonHooks.DungeonMob;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FeatureParser {

	private static File worldGenFolder;
	private static File vanillaGen;
	private static final String vanillaGenInternal = "assets/cofh/world/Vanilla.json";
	private static HashMap<String, IFeatureParser> templateHandlers = new HashMap<String, IFeatureParser>();
	private static Logger log = LogManager.getLogger("CoFHWorld");

	private FeatureParser() {

	}

	public static boolean registerTemplate(String template, IFeatureParser handler) {

		// TODO: provide this function through IFeatureHandler?
		if (!templateHandlers.containsKey(template)) {
			templateHandlers.put(template, handler);
			return true;
		}
		log.error("Attempted to register duplicate template '" + template + "'!");
		return false;
	}

	public static void initialize() {

		worldGenFolder = new File(CoFHProps.configDir, "/cofh/world/");

		if (!worldGenFolder.exists()) {
			try {
				worldGenFolder.mkdir();
			} catch (Throwable t) {
				// pokemon!
			}
		}
		vanillaGen = new File(CoFHProps.configDir, "/cofh/world/Vanilla.json");

		try {
			if (vanillaGen.createNewFile()) {
				CoreUtils.copyFileUsingStream(vanillaGenInternal, vanillaGen);
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}

		log.info("Registering default templates");
		registerTemplate("uniform", new UniformParser());
		registerTemplate("normal", new NormalParser());
		registerTemplate("surface", new SurfaceParser());
		registerTemplate("decoration", new DecorationParser());
		registerTemplate("underfluid", new UnderfluidParser());
		registerTemplate("underwater", new UnderfluidParser());
		registerTemplate("fractal", new FractalParser());
	}

	public static void complete() {

		if (!cofh.CoFHCore.configCore.isOldConfig()) {
			return;
		}
		log.fatal("Warning: CoFHWorld will now scan and update your worldgen files. This will only occur once.");

		JsonParser parser = new JsonParser();
		Gson writer = new Gson();

		ArrayList<File> worldGenList = new ArrayList<File>(5);
		addFiles(worldGenList, worldGenFolder);

		for (int i = 0; i < worldGenList.size(); ++i) {

			File genFile = worldGenList.get(i);
			if (genFile.isDirectory()) {
				addFiles(worldGenList, genFile);
				continue;
			}

			JsonObject genList;
			try {
				genList = (JsonObject) parser.parse(new FileReader(genFile));
			} catch (Throwable t) {
				log.error("Critical error reading from a world generation file: " + genFile + " > Please be sure the file is correct!", t);
				continue;
			}
			boolean saveFile = false;
			log.warn("Checking if " + genFile.getName() + " is from an old version.");
			for (Iterator<Entry<String, JsonElement>> iter = genList.entrySet().iterator(); iter.hasNext();) {
				Entry<String, JsonElement> genEntry = iter.next();

				JsonObject genObject = genEntry.getValue().getAsJsonObject();
				String templateName = parseTemplate(genObject);
				if ("uniform".equals(templateName) || "normal".equals(templateName)) {
					if (genObject.has("metadata")) {
						saveFile = true;

						JsonElement block = genObject.get("block");
						if (block.isJsonArray()) {
							JsonArray blocks = block.getAsJsonArray();
							JsonArray metas = genObject.getAsJsonArray("metadata");
							JsonArray weight = genObject.getAsJsonArray("weight");
							int s = blocks.size();
							if (s != metas.size() || s != weight.size()) {
								log.error("The entry '" + genEntry.getKey() + "' is invalid and will be removed.");
								iter.remove();
								continue;
							}
							JsonArray arr = new JsonArray();
							for (int j = 0; j < s; ++j) {
								JsonObject obj = new JsonObject();
								obj.add("name", new JsonPrimitive(blocks.get(j).getAsString()));
								obj.add("metadata", new JsonPrimitive(metas.get(j).getAsInt()));
								obj.add("weight", new JsonPrimitive(weight.get(j).getAsInt()));
								arr.add(obj);
							}
							genObject.remove("metadata");
							genObject.remove("weight");
							genObject.add("block", arr);
						} else {
							JsonObject obj = new JsonObject();
							obj.add("name", new JsonPrimitive(block.getAsString()));
							obj.add("metadata", new JsonPrimitive(genObject.get("metadata").getAsInt()));
							genObject.remove("metadata");
							if (genObject.has("weight")) {
								genObject.remove("weight");
							}
							genObject.add("block", obj);
						}
					}
				}
			}

			if (saveFile) {
				log.warn("File " + genFile + " is from an old version and will be converted to the new format.");

				JsonWriter w = null;
				try {
					w = new JsonWriter(new FileWriter(genFile));
					w.setIndent("    ");
					writer.toJson(genList, w);
				} catch (IOException e) {
					log.error("There was an error updating " + genFile.getName() + "!", e);
				} finally {
					if (w != null) {
						try {
							w.close();
						} catch (IOException e) {
							log.error("There was an error updating " + genFile.getName() + "!", e);
						}
					}
				}
			}
		}
	}

	private static void addFiles(ArrayList<File> list, File folder) {

		File[] fList = folder.listFiles();

		if (fList == null) {
			log.error("There are no World Generation files present in " + folder + ".");
			return;
		}
		list.addAll(Arrays.asList(fList));
	}

	public static void parseGenerationFile() {

		JsonParser parser = new JsonParser();

		ArrayList<File> worldGenList = new ArrayList<File>(5);
		addFiles(worldGenList, worldGenFolder);
		for (int i = 0, e = worldGenList.size(); i < e; ++i) {
			File genFile = worldGenList.get(i);
			if (genFile.equals(vanillaGen)) {
				if (!WorldHandler.genReplaceVanilla) {
					worldGenList.remove(i);
				}
				break;
			}
		}

		for (int i = 0; i < worldGenList.size(); ++i) {

			File genFile = worldGenList.get(i);
			if (genFile.isDirectory()) {
				addFiles(worldGenList, genFile);
				continue;
			}

			JsonObject genList;
			try {
				genList = (JsonObject) parser.parse(new FileReader(genFile));
			} catch (Throwable t) {
				log.error("Critical error reading from a world generation file: " + genFile + " > Please be sure the file is correct!", t);
				continue;
			}

			log.info("Reading world generation info from: " + genFile + ":");
			for (Entry<String, JsonElement> genEntry : genList.entrySet()) {
				try {
					if (parseGenerationEntry(genEntry.getKey(), genEntry.getValue())) {
						log.debug("Generation entry successfully parsed: \"" + genEntry.getKey() + "\"");
					} else {
						log.error("Error parsing generation entry: \"" + genEntry.getKey() + "\" > Please check the parameters. It *may* be a duplicate.");
					}
				} catch (Throwable t) {
					log.fatal("There was a severe error parsing '" + genEntry.getKey() + "'!", t);
				}
			}
		}
	}

	private static boolean parseGenerationEntry(String featureName, JsonElement genEntry) {

		JsonObject genObject = genEntry.getAsJsonObject();

		if (genObject.has("enabled")) {
			if (!genObject.get("enabled").getAsBoolean()) {
				log.info('"' + featureName + "\" is disabled.");
				return true;
			}
		}

		String templateName = parseTemplate(genObject);
		IFeatureParser template = templateHandlers.get(templateName);
		if (template != null) {
			IFeatureGenerator feature = template.parseFeature(featureName, genObject, log);
			if (feature != null) {
				return WorldHandler.addFeature(feature);
			}
			log.warn("Template '" + templateName + "' failed to parse its entry!");
		} else {
			log.warn("Unknown template + '" + templateName + "'.");
		}

		return false;
	}

	private static String parseTemplate(JsonObject genObject) {

		JsonElement genElement = genObject.get("template");
		if (genElement.isJsonObject()) {
			genObject = genElement.getAsJsonObject();

			return genObject.get("type").getAsString();
		} else {
			return genElement.getAsString();
		}
	}

	// TODO: move these helper functions outside core?

	public static BiomeInfoSet parseBiomeRestrictions(JsonObject genObject) {
		BiomeInfoSet set = null;
		if (genObject.has("biomes")) {
			JsonArray restrictionList = genObject.getAsJsonArray("biomes");
			set = new BiomeInfoSet(restrictionList.size());
			for (int i = 0, e = restrictionList.size(); i < e; i++) {
				BiomeInfo info = null;
				JsonElement element = restrictionList.get(i);
				if (element.isJsonNull()) {
					log.info("Null biome entry. Ignoring.");
				} else if (element.isJsonObject()) {
					JsonObject obj = element.getAsJsonObject();
					String type = obj.get("type").getAsString();
					boolean wl = obj.has("whitelist") ? obj.get("whitelist").getAsBoolean() : true;
					JsonElement value = obj.get("entry");
					JsonArray array = value.isJsonArray() ? value.getAsJsonArray() : null;
					String entry = array != null ? null : value.getAsString();
					int rarity = obj.has("rarity") ? obj.get("rarity").getAsInt() : -1;

					l: if (type.equalsIgnoreCase("name")) {
						if (array != null) {
							ArrayList<String> names = new ArrayList<String>();
							for (int k = 0, j = array.size(); k < j; k++) {
								names.add(array.get(k).getAsString());
							}
							if (rarity > 0)
								info = new BiomeInfoRarity(names, 4, true, rarity);
							else
								info = new BiomeInfo(names, 4, true);
						} else {
							if (rarity > 0)
								info = new BiomeInfoRarity(entry, rarity);
							else
								info = new BiomeInfo(entry);
						}
					} else {
						Object data = null;
						int t = -1;
						if (type.equalsIgnoreCase("temperature")) {
							if (array != null) {
								ArrayList<TempCategory> temps = new ArrayList<TempCategory>();
								for (int k = 0, j = array.size(); k < j; k++) {
									temps.add(TempCategory.valueOf(array.get(k).getAsString()));
								}
								data = EnumSet.copyOf(temps);
								t = 5;
							} else {
								data = TempCategory.valueOf(entry);
								t = 1;
							}
						} else if (type.equalsIgnoreCase("dictionary")) {
							if (array != null) {
								ArrayList<Type> tags = new ArrayList<Type>();
								for (int k = 0, j = array.size(); k < j; k++) {
									Type a = Type.valueOf(array.get(k).getAsString());
									if (a != null)
										tags.add(a);
								}
								data = tags.toArray(new Type[tags.size()]);
								t = 6;
							} else {
								data = Type.valueOf(entry);
								t = 2;
							}
						} else {
							log.warn("Biome entry of unknown type");
							break l;
						}
						if (data != null) {
							if (rarity > 0)
								info = new BiomeInfoRarity(data, t, wl, rarity);
							else
								info = new BiomeInfo(data, t, wl);
						}
					}
				} else {
					info = new BiomeInfo(element.getAsString());
				}
				if (info != null)
					set.add(info);
			}
		}
		return set;
	}

	public static Block parseBlockName(String blockRaw) {

		String[] blockTokens = blockRaw.split(":", 2);
		int i = 0;
		return GameRegistry.findBlock(blockTokens.length > 1 ? blockTokens[i++] : "minecraft", blockTokens[i]);
	}

	public static WeightedRandomBlock parseBlockEntry(JsonElement genElement) {

		if (genElement.isJsonNull()) {
			log.warn("Null Block entry!");
			return null;
		} else if (genElement.isJsonObject()) {
			JsonObject blockElement = genElement.getAsJsonObject();
			if (!blockElement.has("name")) {
				log.error("Block entry needs a name!");
				return null;
			}
			Block block = parseBlockName(blockElement.get("name").getAsString());
			if (block == null) {
				log.error("Invalid block entry!");
				return null;
			}
			int metadata = blockElement.has("metadata") ? MathHelper.clampI(blockElement.get("metadata").getAsInt(), 0, 15) : 0;
			int weight = blockElement.has("weight") ? MathHelper.clampI(blockElement.get("weight").getAsInt(), 1, 1000000) : 100;
			return new WeightedRandomBlock(block, metadata, weight);
		} else {
			Block block = parseBlockName(genElement.getAsString());
			if (block == null) {
				log.error("Invalid block entry!");
				return null;
			}
			return new WeightedRandomBlock(block, 0);
		}
	}

	public static boolean parseResList(JsonElement genElement, List<WeightedRandomBlock> resList) {

		if (genElement.isJsonArray()) {
			JsonArray blockList = genElement.getAsJsonArray();

			for (int i = 0, e = blockList.size(); i < e; i++) {
				WeightedRandomBlock entry = parseBlockEntry(blockList.get(i));
				if (entry == null) {
					return false;
				}
				resList.add(entry);
			}
		} else {
			WeightedRandomBlock entry = parseBlockEntry(genElement);
			if (entry == null) {
				return false;
			}
			resList.add(entry);
		}
		return true;
	}

	public static WeightedRandomNBTTag parseEntityEntry(JsonElement genElement) {

		if (genElement.isJsonNull()) {
			log.warn("Null entity entry!");
			return null;
		} else if (genElement.isJsonObject()) {
			JsonObject genObject = genElement.getAsJsonObject();
			NBTTagCompound data;
			if (genObject.has("spawnerTag")) {
				try {
					data = (NBTTagCompound)JsonToNBT.func_150315_a(genObject.get("spawnerTag").toString());
				} catch (NBTException e) {
					log.error("Invalid entity entry!", e);
					return null;
				}
			} else {
				data = new NBTTagCompound();
				String type = genObject.get("entity").getAsString();
				if (type == null) {
					log.error("Invalid entity entry!");
					return null;
				}
				data.setString("EntityId", type);
			}
			return new WeightedRandomNBTTag(genObject.get("weight").getAsInt(), data);
		} else {
			String type = genElement.getAsString();
			if (type == null) {
				log.error("Invalid entity entry!");
				return null;
			}
			NBTTagCompound tag = new NBTTagCompound();
			tag.setString("EntityId", type);
			return new WeightedRandomNBTTag(100, tag);
		}
	}

	public static boolean parseEntityList(JsonElement genElement, List<WeightedRandomNBTTag> list) {

		if (genElement.isJsonArray()) {
			JsonArray blockList = genElement.getAsJsonArray();

			for (int i = 0, e = blockList.size(); i < e; i++) {
				WeightedRandomNBTTag entry = parseEntityEntry(blockList.get(i));
				if (entry == null) {
					return false;
				}
				list.add(entry);
			}
		} else {
			WeightedRandomNBTTag entry = parseEntityEntry(genElement);
			if (entry == null) {
				return false;
			}
			list.add(entry);
		}
		return true;
	}

	public static DungeonMob parseWeightedStringEntry(JsonElement genElement) {

		int weight = 100;
		String type = null;
		if (genElement.isJsonNull()) {
			log.warn("Null string entry!");
			return null;
		} else if (genElement.isJsonObject()) {
			JsonObject genObject = genElement.getAsJsonObject();
			type = genObject.get("name").getAsString();
			if (type == null) {
				log.warn("Invalid string entry!");
				return null;
			}
			if (genObject.has("weight")) {
				weight = genObject.get("weight").getAsInt();
			}
		} else {
			type = genElement.getAsString();
			if (type == null) {
				log.warn("Invalid string entry!");
				return null;
			}
		}
		return new DungeonMob(weight, type);
	}

	public static boolean parseWeightedStringList(JsonElement genElement, List<DungeonMob> list) {

		if (genElement.isJsonArray()) {
			JsonArray blockList = genElement.getAsJsonArray();

			for (int i = 0, e = blockList.size(); i < e; i++) {
				DungeonMob entry = parseWeightedStringEntry(blockList.get(i));
				if (entry == null) {
					return false;
				}
				list.add(entry);
			}
		} else {
			DungeonMob entry = parseWeightedStringEntry(genElement);
			if (entry == null) {
				return false;
			}
			list.add(entry);
		}
		return true;
	}

}
