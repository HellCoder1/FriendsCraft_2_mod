package redstonearsenal.item.tool;

import cofh.api.energy.IEnergyContainerItem;
import cofh.api.item.IEmpowerableItem;
import cofh.entity.EntityCoFHFishHook;
import cofh.util.EnergyHelper;
import cofh.util.KeyBindingEmpower;
import cofh.util.MathHelper;
import cofh.util.ServerHelper;
import cofh.util.StringHelper;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

public class ItemFishingRodRF extends ItemFishingRod implements IEmpowerableItem, IEnergyContainerItem {

	IIcon normalIcons[] = new IIcon[2];
	IIcon activeIcons[] = new IIcon[2];
	IIcon drainedIcon;

	protected ToolMaterial toolMaterial;

	public int maxEnergy = 160000;
	public int maxTransfer = 1600;
	public int energyPerUse = 200;
	public int energyPerUseCharged = 800;

	public ItemFishingRodRF(ToolMaterial toolMaterial) {

		super();
		this.toolMaterial = toolMaterial;
		setMaxDamage(toolMaterial.getMaxUses());
		setNoRepair();
	}

	protected void useEnergy(ItemStack stack) {

		int unbreakingLevel = MathHelper.clampI(EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack), 0, 4);
		extractEnergy(stack, isEmpowered(stack) ? energyPerUseCharged * (5 - unbreakingLevel) / 5 : energyPerUse * (5 - unbreakingLevel) / 5, false);
	}

	protected int getEnergyPerUse(ItemStack stack) {

		int unbreakingLevel = MathHelper.clampI(EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack), 0, 4);
		return (isEmpowered(stack) ? energyPerUseCharged : energyPerUse) * (5 - unbreakingLevel) / 5;
	}

	@Override
	public int getItemEnchantability() {

		return toolMaterial.getEnchantability();
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {

		return EnumRarity.uncommon;
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {

		list.add(EnergyHelper.setDefaultEnergyTag(new ItemStack(item, 1, 0), 0));
		list.add(EnergyHelper.setDefaultEnergyTag(new ItemStack(item, 1, 0), maxEnergy));
	}

	@Override
	public boolean isItemTool(ItemStack stack) {

		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

		// TODO: WTF
		if (player.fishEntity != null) {
			int i = player.fishEntity.func_146034_e();
			useEnergy(stack);
		} else {
			world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

			if (ServerHelper.isServerWorld(world)) {
				world.spawnEntityInWorld(new EntityCoFHFishHook(world, player));
			}
		}
		player.swingItem();
		return stack;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean check) {

		if (StringHelper.displayShiftForDetail && !StringHelper.isShiftKeyDown()) {
			list.add(StringHelper.shiftForInfo());
		}
		if (!StringHelper.isShiftKeyDown()) {
			return;
		}
		if (stack.stackTagCompound == null) {
			EnergyHelper.setDefaultEnergyTag(stack, 0);
		}
		list.add(StringHelper.localize("info.cofh.charge") + ": " + stack.stackTagCompound.getInteger("Energy") + " / " + maxEnergy + " RF");

		list.add(StringHelper.ORANGE + getEnergyPerUse(stack) + " " + StringHelper.localize("info.redstonearsenal.tool.energyPerUse") + StringHelper.END);
		if (isEmpowered(stack)) {
			list.add(StringHelper.YELLOW + StringHelper.ITALIC + StringHelper.localize("info.cofh.press") + " "
					+ Keyboard.getKeyName(KeyBindingEmpower.instance.getKey()) + " " + StringHelper.localize("info.redstonearsenal.tool.chargeOff")
					+ StringHelper.END);
		} else {
			list.add(StringHelper.BRIGHT_BLUE + StringHelper.ITALIC + StringHelper.localize("info.cofh.press") + " "
					+ Keyboard.getKeyName(KeyBindingEmpower.instance.getKey()) + " " + StringHelper.localize("info.redstonearsenal.tool.chargeOn")
					+ StringHelper.END);
		}
	}

	@Override
	public int getDisplayDamage(ItemStack stack) {

		if (stack.stackTagCompound == null) {
			EnergyHelper.setDefaultEnergyTag(stack, 0);
		}
		return 1 + maxEnergy - stack.stackTagCompound.getInteger("Energy");
	}

	@Override
	public int getMaxDamage(ItemStack stack) {

		return 1 + maxEnergy;
	}

	@Override
	public boolean isDamaged(ItemStack stack) {

		return stack.getItemDamage() != Short.MAX_VALUE;
	}

	/* IEmpowerableItem */
	@Override
	public boolean isEmpowered(ItemStack stack) {

		return stack.stackTagCompound == null ? false : stack.stackTagCompound.getBoolean("Empowered");
	}

	@Override
	public boolean setEmpoweredState(ItemStack stack, boolean state) {

		if (getEnergyStored(stack) > 0) {
			stack.stackTagCompound.setBoolean("Empowered", state);
			return true;
		}
		stack.stackTagCompound.setBoolean("Empowered", false);
		return false;
	}

	@Override
	public void onStateChange(EntityPlayer player, ItemStack stack) {

		if (isEmpowered(stack)) {
			player.worldObj.playSoundAtEntity(player, "ambient.weather.thunder", 0.4F, 1.0F);
		} else {
			player.worldObj.playSoundAtEntity(player, "random.orb", 0.2F, 0.6F);
		}
	}

	/* IEnergyContainerItem */
	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {

		if (container.stackTagCompound == null) {
			EnergyHelper.setDefaultEnergyTag(container, 0);
		}
		int stored = container.stackTagCompound.getInteger("Energy");
		int receive = Math.min(maxReceive, Math.min(maxEnergy - stored, maxTransfer));

		if (!simulate) {
			stored += receive;
			container.stackTagCompound.setInteger("Energy", stored);
		}
		return receive;
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {

		if (container.stackTagCompound == null) {
			EnergyHelper.setDefaultEnergyTag(container, 0);
		}
		int stored = container.stackTagCompound.getInteger("Energy");
		int extract = Math.min(maxExtract, stored);

		if (!simulate) {
			stored -= extract;
			container.stackTagCompound.setInteger("Energy", stored);

			if (stored == 0) {
				setEmpoweredState(container, false);
			}
		}
		return extract;
	}

	@Override
	public int getEnergyStored(ItemStack container) {

		if (container.stackTagCompound == null) {
			EnergyHelper.setDefaultEnergyTag(container, 0);
		}
		return container.stackTagCompound.getInteger("Energy");
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {

		return maxEnergy;
	}

}
