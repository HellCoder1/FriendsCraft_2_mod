package mod.HellCoder.things.core;

import net.minecraftforge.common.util.ForgeDirection;

public interface IMethaneTransporter {
	public float getPressure();
	public boolean canInsert(ForgeDirection face);
	public int getCapacity();
	public int getSteam();
	public void insertSteam(int amount, ForgeDirection face);
	public void decrSteam(int i);
	public boolean doesConnect(ForgeDirection face);
	public abstract boolean acceptsGauge(ForgeDirection face);
}
