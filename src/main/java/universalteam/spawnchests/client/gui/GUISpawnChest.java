package universalteam.spawnchests.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import universalteam.spawnchests.inventory.container.ContainerSpawnChest;
import universalteam.spawnchests.tile.TileSpawnChest;

public class GUISpawnChest extends GuiContainer
{

	public GUISpawnChest(InventoryPlayer invPlayer, TileSpawnChest tile)
	{
		super(new ContainerSpawnChest(invPlayer, tile));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
	{

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(new ResourceLocation("spawnchests", "textures/gui/spawnChest.png"));
		int xStart = (width - xSize) / 2;
		int yStart = (height - ySize) / 2;
		this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
	}
}
