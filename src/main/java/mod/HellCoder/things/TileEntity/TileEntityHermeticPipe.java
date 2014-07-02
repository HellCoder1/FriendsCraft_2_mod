package mod.HellCoder.things.TileEntity;

import java.util.ArrayList;

import mod.HellCoder.things.FriendsCraft2mod;
import mod.HellCoder.things.core.IMethaneTransporter;
import mod.HellCoder.things.core.UtilMethaneTransporter;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityHermeticPipe extends TileEntity implements IFluidHandler,IMethaneTransporter {
	
	protected int steam;
	protected FluidTank dummyFluidTank = FluidRegistry.isFluidRegistered("steam") ? new FluidTank(new FluidStack(FluidRegistry.getFluid("steam"), 0),10000) : null;


	@Override
	public float getPressure() {
		return this.getSteam()/1000.0F;
	}

	@Override
	public boolean canInsert(ForgeDirection face) {
		return true;
	}

	@Override
	public int getCapacity() {
		return 1000;
	}

	@Override
	public int getSteam() {
		return steam;
	}

	@Override
	public void insertSteam(int amount, ForgeDirection face) {
		this.steam += amount;
	}
	
	@Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        this.steam = par1NBTTagCompound.getShort("steam");
    }

    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("steam",(short) this.steam);
    }
	
	@Override
	public Packet getDescriptionPacket()
	{
    	super.getDescriptionPacket();
        NBTTagCompound access = new NBTTagCompound();
        access.setInteger("steam", steam);
        
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, access);
	}
	    

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
    	super.onDataPacket(net, pkt);
    	NBTTagCompound access = pkt.func_148857_g();
    	this.steam = access.getInteger("steam");
    	
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }
	
	@Override
	public void updateEntity() {
		if (FriendsCraft2mod.steamRegistered) {
			this.dummyFluidTank.setFluid(new FluidStack(FluidRegistry.getFluid("steam"), this.getSteam()*10));
		}
		if (!this.worldObj.isRemote) {
			UtilMethaneTransporter.generalDistributionEvent(worldObj, xCoord, yCoord, zCoord,ForgeDirection.values());
			UtilMethaneTransporter.generalPressureEvent(worldObj,xCoord, yCoord, zCoord, this.getPressure(), this.getCapacity());
		}

		ArrayList<ForgeDirection> myDirections = new ArrayList<ForgeDirection>();
		for (ForgeDirection direction : ForgeDirection.values()) {
			if (worldObj.getTileEntity(xCoord+direction.offsetX, yCoord+direction.offsetY, zCoord+direction.offsetZ) != null) {
				TileEntity tile = worldObj.getTileEntity(xCoord+direction.offsetX, yCoord+direction.offsetY, zCoord+direction.offsetZ);
				if (tile instanceof IMethaneTransporter) {
					IMethaneTransporter target = (IMethaneTransporter) tile;
					if (target.doesConnect(direction.getOpposite())) {
						myDirections.add(direction);
					}
				}
				else if (tile instanceof IFluidHandler && FriendsCraft2mod.steamRegistered) {
					IFluidHandler target = (IFluidHandler) tile;
					if (target.canDrain(direction.getOpposite(), FluidRegistry.getFluid("steam")) || target.canFill(direction.getOpposite(), FluidRegistry.getFluid("steam"))) {
						myDirections.add(direction);
					}
				}
			}
		}
		int i = 0;
		if (myDirections.size() > 0) {
			ForgeDirection direction = myDirections.get(0).getOpposite();

			if (myDirections.size() == 2 && this.steam > 0 && i < 10 && (worldObj.isAirBlock(xCoord+direction.offsetX, yCoord+direction.offsetY, zCoord+direction.offsetZ) || !worldObj.isSideSolid(xCoord+direction.offsetX, yCoord+direction.offsetY, zCoord+direction.offsetZ, direction.getOpposite()))) {
				this.worldObj.playSoundEffect(this.xCoord+0.5F, this.yCoord+0.5F, this.zCoord+0.5F, "steamcraft:leaking", 2.0F, 0.9F);
			}
			while (myDirections.size() == 2 && this.steam > 0 && i < 10 && (worldObj.isAirBlock(xCoord+direction.offsetX, yCoord+direction.offsetY, zCoord+direction.offsetZ) || !worldObj.isSideSolid(xCoord+direction.offsetX, yCoord+direction.offsetY, zCoord+direction.offsetZ, direction.getOpposite()))) {
				this.steam--;
				this.worldObj.spawnParticle("smoke", xCoord+0.5F, yCoord+0.5F, zCoord+0.5F, direction.offsetX*0.1F, direction.offsetY*0.1F, direction.offsetZ*0.1F);
				i++;
			}
		}
		this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	
	@Override
	public void decrSteam(int i) {
		this.steam -= i;
	}

	@Override
	public boolean doesConnect(ForgeDirection face) {
		return true;
	}
	
	@Override
	public boolean acceptsGauge(ForgeDirection face) {
		return true;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (resource.amount >= 10) {
			if (doFill) {
				this.steam += (resource.amount-resource.amount%10)/10;
			}
			FluidStack resource2 = resource.copy();
			resource2.amount = resource.amount-resource.amount%10;
			if (resource2 != null) {
				return dummyFluidTank.fill(resource2, doFill)+resource.amount%10;
			}
		}
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource,
			boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return (FriendsCraft2mod.steamRegistered ? fluid == FluidRegistry.getFluid("steam") : false);
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {

		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		if (FriendsCraft2mod.steamRegistered) {
			return new FluidTankInfo[] {dummyFluidTank.getInfo()};
		}
		return null;
	}
}
