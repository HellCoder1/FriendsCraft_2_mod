package mod.HellCoder.things.gui.tooltip;

import java.util.List;

import cofh.api.energy.IEnergyStorage;
import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementBase;
import cofh.lib.gui.element.ElementEnergyStored;

public class CustomToolTipElementFC extends ElementBase {

	protected int number;
	protected int numberMax;
	protected String name;
	
	public CustomToolTipElementFC(GuiBase gui, int posX, int posY, int sizeX, int sizeY, int number, int numberMax, String name){
		super(gui, posX, posY);
		this.number = number;
		this.numberMax = numberMax;
		this.name = name;
		
		this.sizeX = sizeX;//16
		this.sizeY = sizeY;//47
	}
	
	public CustomToolTipElementFC(GuiBase gui, int posX, int posY, int sizeX, int sizeY , int number, String name){
		super(gui, posX, posY);
		
		this.number = number;
		this.name = name;
		
		this.sizeX = sizeX;//16
		this.sizeY = sizeY;//47
	}

	@Override
	public void addTooltip(List<String> list) {
		if(numberMax != 0){
		list.add(number + "/" + numberMax + " " + name);
		} else {
			list.add(number + " " + name);
		}
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
