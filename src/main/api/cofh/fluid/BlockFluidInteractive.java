package cofh.fluid;

import cofh.util.BlockWrapper;

import gnu.trove.map.TMap;
import gnu.trove.map.hash.THashMap;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;

public class BlockFluidInteractive extends BlockFluidCoFHBase {

	protected final TMap<BlockWrapper, BlockWrapper> collisionMap = new THashMap<BlockWrapper, BlockWrapper>();

	public BlockFluidInteractive(Fluid fluid, Material material, String name) {

		super(fluid, material, name);
	}

	public BlockFluidInteractive(String modName, Fluid fluid, Material material, String name) {

		super(modName, fluid, material, name);
	}

	public boolean addInteraction(Block preBlock, Block postBlock) {

		if (preBlock == null || postBlock == null) {
			return false;
		}
		return addInteraction(preBlock, -1, postBlock, 0);
	}

	public boolean addInteraction(Block preBlock, int preMeta, Block postBlock, int postMeta) {

		if (preBlock == null || postBlock == null || postMeta < 0) {
			return false;
		}
		if (preMeta < 0) {
			collisionMap.put(new BlockWrapper(preBlock, preMeta), new BlockWrapper(postBlock, postMeta));
		} else {
			collisionMap.put(new BlockWrapper(preBlock, preMeta), new BlockWrapper(postBlock, postMeta));
		}
		return true;
	}

	public boolean addInteraction(Block preBlock, int preMeta, Block postBlock) {

		return addInteraction(preBlock, preMeta, postBlock, 0);
	}

	public boolean hasInteraction(Block preBlock, int preMeta) {

		return collisionMap.containsKey(new BlockWrapper(preBlock, preMeta)) || collisionMap.containsKey(new BlockWrapper(preBlock, -1));
	}

	public BlockWrapper getInteraction(Block preBlock, int preMeta) {

		if (collisionMap.containsKey(new BlockWrapper(preBlock, preMeta))) {
			return collisionMap.get(new BlockWrapper(preBlock, preMeta));
		}
		return collisionMap.get(new BlockWrapper(preBlock, -1));
	}

}
