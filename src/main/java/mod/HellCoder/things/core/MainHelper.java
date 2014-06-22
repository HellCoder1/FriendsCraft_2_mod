package mod.HellCoder.things.core;

import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;

public class MainHelper {
	 public static Item get(String name)
     {
             return GameRegistry.findItem("FC2", name);
     }
}
