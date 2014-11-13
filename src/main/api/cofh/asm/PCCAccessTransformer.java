package cofh.asm;

import cpw.mods.fml.common.asm.transformers.AccessTransformer;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public class PCCAccessTransformer extends AccessTransformer {

	private static PCCAccessTransformer instance;
	private static List<String> mapFileList = new LinkedList<String>();

	public PCCAccessTransformer() throws IOException {

		super();
		instance = this;

		// file names are case sensitive. do not alter.
		mapFileList.add("CoFH_at.cfg");
		// CoFH_at.cfg must also contain all entries from cofhlib_at.cfg

		for (String file : mapFileList) {
			readMappingFile(file);
		}
	}

	public static void addTransformerMap(String mapFile) {

		if (instance == null) {
			mapFileList.add(mapFile);
		} else {
			instance.readMappingFile(mapFile);
		}
	}

	private void readMappingFile(String mapFile) {

		System.out.println("Adding Accesstransformer map: " + mapFile);
		try {
			Method parentMapFile = AccessTransformer.class.getDeclaredMethod("readMapFile", String.class);
			parentMapFile.setAccessible(true);
			parentMapFile.invoke(this, mapFile);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
