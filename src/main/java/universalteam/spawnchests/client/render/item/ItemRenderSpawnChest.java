package universalteam.spawnchests.client.render.item;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.model.ModelChest;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ItemRenderSpawnChest implements IItemRenderer
{
	private final ModelChest model;

	public ItemRenderSpawnChest()
	{
		this.model = new ModelChest();
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(new ResourceLocation("textures/entity/chest/normal.png"));

		GL11.glPushMatrix();

		switch (type)
		{
			case ENTITY:
				GL11.glTranslatef(-1.5F, -1.5F, -1.5F);
			case EQUIPPED:
				GL11.glTranslatef(1.0F, 1.0F, 1.0F);
			case EQUIPPED_FIRST_PERSON:
				GL11.glTranslatef(0.0F, 0.0F, 0.0F);
			case INVENTORY:
				GL11.glTranslatef(0.0F, 0.075F, 0.0F);
		}

		GL11.glRotatef(180, 1, 0, 0);
		GL11.glRotatef(-90, 0, 1, 0);
		model.renderAll();
		GL11.glPopMatrix();
	}
}
