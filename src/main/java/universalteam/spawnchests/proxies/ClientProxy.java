package universalteam.spawnchests.proxies;

import cpw.mods.fml.client.registry.ClientRegistry;
import universalteam.spawnchests.client.render.tile.TESRSpawnChest;
import universalteam.spawnchests.tile.TileSpawnChest;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit()
	{
		super.preInit();

		ClientRegistry.bindTileEntitySpecialRenderer(TileSpawnChest.class, new TESRSpawnChest());
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

	@Override
	public void serverStarting()
	{
		super.serverStarting();
	}
}
