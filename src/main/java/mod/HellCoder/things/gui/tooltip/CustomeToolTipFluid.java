package mod.HellCoder.things.gui.tooltip;

import java.util.List;

import net.minecraftforge.fluids.IFluidTank;
import cofh.api.energy.IEnergyStorage;
import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementBase;
import cofh.lib.gui.element.ElementEnergyStored;
import cofh.lib.util.helpers.StringHelper;

public class CustomeToolTipFluid extends ElementBase {

	protected IFluidTank tank;
	
	public CustomeToolTipFluid(GuiBase gui, int posX, int posY, int sizeX, int sizeY, IFluidTank tank){
		super(gui, posX, posY);
		this.tank = tank;
		
		this.sizeX = sizeX;//16
		this.sizeY = sizeY;//47
	}

	@Override
	public void addTooltip(List<String> list) {
		
		if (tank.getFluid() != null && tank.getFluidAmount() > 0) {
			list.add(StringHelper.getFluidName(tank.getFluid()));
		}
		list.add("" + tank.getFluidAmount() + " / " + tank.getCapacity() + " mB");
	}

	@Override
	public void drawBackground(int mouseX, int mouseY, float gameTicks) {
		// TODO Автоматически созданная заглушка метода
		
	}

	@Override
	public void drawForeground(int mouseX, int mouseY) {
		// TODO Автоматически созданная заглушка метода
		
	}
}
