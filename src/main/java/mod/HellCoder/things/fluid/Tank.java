/**
 * Copyright (c) 2011-2014, SpaceToad and the BuildCraft Team
 * http://www.mod-buildcraft.com
 *
 * BuildCraft is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package mod.HellCoder.things.fluid;

import java.util.Locale;

import mod.HellCoder.things.core.INBTSerializable;
import mod.HellCoder.things.gui.tooltip.ToolTip;
import mod.HellCoder.things.gui.tooltip.ToolTipLine;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;

public class Tank extends FluidTank implements INBTSerializable {
	public int colorRenderCache = 0xFFFFFF;

	protected final ToolTip toolTip = new ToolTip() {
		@Override
		public void refresh() {
			refreshTooltip();
		}
	};

	private final String name;

	public Tank(String name, int capacity, TileEntity tile) {
		super(capacity);
		this.name = name;
		this.tile = tile;
	}

	public boolean isEmpty() {
		return getFluid() == null || getFluid().amount <= 0;
	}

	public boolean isFull() {
		return getFluid() != null && getFluid().amount >= getCapacity();
	}

	public Fluid getFluidType() {
		return getFluid() != null ? getFluid().getFluid() : null;
	}

	@Override
	public final NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		NBTTagCompound tankData = new NBTTagCompound();
		super.writeToNBT(tankData);
		writeTankToNBT(tankData);
		nbt.setTag(name, tankData);
		return nbt;
	}

	@Override
	public final FluidTank readFromNBT(NBTTagCompound nbt) {
		if (nbt.hasKey(name)) {
			// allow to read empty tanks
			setFluid(null);

			NBTTagCompound tankData = nbt.getCompoundTag(name);
			super.readFromNBT(tankData);
			readTankFromNBT(tankData);
		}
		return this;
	}

	public void writeTankToNBT(NBTTagCompound nbt) {
	}

	public void readTankFromNBT(NBTTagCompound nbt) {
	}

	public ToolTip getToolTip() {
		return toolTip;
	}

	protected void refreshTooltip() {
		toolTip.clear();
		int amount = 0;
		if (getFluid() != null && getFluid().amount > 0) {
			ToolTipLine fluidName = new ToolTipLine(getFluid().getFluid().getLocalizedName(getFluid()));
			fluidName.setSpacing(2);
			toolTip.add(fluidName);
			amount = getFluid().amount;
		}
		toolTip.add(new ToolTipLine(String.format(Locale.ENGLISH, "%,d / %,d", amount, getCapacity())));
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		return nbt;
	}

	@Override
	public void serializeNBT(NBTTagCompound nbt) {
		readFromNBT(nbt);
	}
}
