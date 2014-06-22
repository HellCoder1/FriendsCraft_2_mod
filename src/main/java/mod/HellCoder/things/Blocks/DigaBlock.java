package mod.HellCoder.things.Blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mod.HellCoder.things.FriendsCraft2mod;
import mod.HellCoder.things.lib.RegBlocks;
import mod.HellCoder.things.lib.RegItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.Material;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class DigaBlock extends Block {

	public DigaBlock(Material material) {
		super(material);
		this.setCreativeTab(FriendsCraft2mod.tabsFC);
	}

}
