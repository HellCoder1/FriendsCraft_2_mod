package cofh.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class CoFHTilePacket extends CoFHPacket {

	public static void initialize() {

		PacketHandler.instance.registerPacket(CoFHTilePacket.class);
	}

	public CoFHTilePacket() {

	}

	public CoFHTilePacket(TileEntity theTile) {

		addInt(theTile.xCoord);
		addInt(theTile.yCoord);
		addInt(theTile.zCoord);

	}

	@Override
	public void handleClientSide(EntityPlayer player) {

		handlePacket(player, false);
	}

	@Override
	public void handleServerSide(EntityPlayer player) {

		handlePacket(player, true);
	}

	@Override
	public void handlePacket(EntityPlayer player, boolean isServer) {

		TileEntity tile = player.worldObj.getTileEntity(getInt(), getInt(), getInt());

		if (tile instanceof ITilePacketHandler) {
			((ITilePacketHandler) tile).handleTilePacket(this, isServer);
		} else {
			// TODO: Throw error, bad packet
		}
	}

	public static CoFHTilePacket newPacket(TileEntity theTile) {

		return new CoFHTilePacket(theTile);
	}
}
