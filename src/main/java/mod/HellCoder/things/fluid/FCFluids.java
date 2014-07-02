package mod.HellCoder.things.fluid;

import net.minecraft.block.BlockDispenser;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;


public class FCFluids {
	
	public static Fluid methane;
	
	public static void preInit() {

		methane = new Fluid("methane").setLuminosity(0).setDensity(-1000).setViscosity(200).setGaseous(true).setTemperature(30);

		FluidRegistry.registerFluid(methane);

	}

	public static void initialize() {

	}

	public static void postInit() {

	}

	public static void registerDispenserHandlers() {

//		BlockDispenser.dispenseBehaviorRegistry.putObject(TFItems.itemBucket, new DispenserFilledBucketHandler());
//		BlockDispenser.dispenseBehaviorRegistry.putObject(Items.bucket, new DispenserEmptyBucketHandler());
	}

}