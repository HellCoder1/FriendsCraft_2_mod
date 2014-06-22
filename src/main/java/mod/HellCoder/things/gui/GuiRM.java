package mod.HellCoder.things.gui;

import mod.HellCoder.things.TileEntity.TileRM;
import mod.HellCoder.things.container.ContainerRecon;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

public class GuiRM extends GuiContainer {

	public static final ResourceLocation gui = new ResourceLocation("friendscraft","textures/gui/GUIRollingMachine.png");
	private TileRM machine;

	public GuiRM(InventoryPlayer inventoryplayer, World world, TileRM tile) {
		super(new ContainerRecon(inventoryplayer,tile));
		this.machine = tile;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(gui);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
}
