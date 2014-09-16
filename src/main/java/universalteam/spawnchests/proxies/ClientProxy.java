package universalteam.spawnchests.proxies;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import universalteam.spawnchests.client.render.item.ItemRenderSpawnChest;
import universalteam.spawnchests.client.render.tile.TESRSpawnChest;
import universalteam.spawnchests.tile.TileSpawnChest;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit()
	{
		super.preInit();

		TESRSpawnChest.RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
		ClientRegistry.bindTileEntitySpecialRenderer(TileSpawnChest.class, new TESRSpawnChest());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(spawnChest), new ItemRenderSpawnChest());
	}

	@Override
	public void init()
	{
		super.init();
	}

	@Override
	public void postInit()
	{
		super.postInit();
	}

}
