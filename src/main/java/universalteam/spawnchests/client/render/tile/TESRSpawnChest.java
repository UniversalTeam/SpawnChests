package universalteam.spawnchests.client.render.tile;

import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import universalteam.spawnchests.tile.TileSpawnChest;

public class TESRSpawnChest extends TileEntitySpecialRenderer
{
	public static int RENDER_ID;

	private final ModelChest model = new ModelChest();

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float tick)
	{
		if (!(tile instanceof TileSpawnChest))
			return;

		TileSpawnChest tileChest = (TileSpawnChest) tile;
		ForgeDirection dir = null;

		if (tileChest.getWorldObj() != null)
			dir = tileChest.getOrientation();

		this.bindTexture(new ResourceLocation("textures/entity/chest/normal.png"));

		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float) x, (float) y + 1.0F, (float) z + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		short angle = 0;

		if (dir != null)
			if (dir == ForgeDirection.NORTH)
				angle = 180;
			else if (dir == ForgeDirection.SOUTH)
				angle = 0;
			else if (dir == ForgeDirection.WEST)
				angle = 90;
			else if (dir == ForgeDirection.EAST)
				angle = -90;

		GL11.glRotatef(angle, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		float adjustedLidAngle = tileChest.prevLidAngle + (tileChest.lidAngle - tileChest.prevLidAngle) * tick;
		adjustedLidAngle = 1.0F - adjustedLidAngle;
		adjustedLidAngle = 1.0F - adjustedLidAngle * adjustedLidAngle * adjustedLidAngle;
		model.chestLid.rotateAngleX = -(adjustedLidAngle * (float) Math.PI / 2.0F);
		model.renderAll();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
}
