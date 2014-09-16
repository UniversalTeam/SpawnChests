package universalteam.spawnchests.proxies;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import universalteam.spawnchests.block.BlockSpawnChest;
import universalteam.spawnchests.content.ContentReader;
import universalteam.spawnchests.content.SpawnChestInventories;
import universalteam.spawnchests.creative.CreativeTabSpawnChests;
import universalteam.spawnchests.itemblock.ItemBlockSpawnChest;
import universalteam.spawnchests.tile.TileSpawnChest;

public class CommonProxy
{
	public static CreativeTabs spawnChestTab;
	public static Block spawnChest;

	public void preInit()
	{
		ContentReader.execute();

		initCreativeTabs();

		initBlocks();

		initTiles();
	}

	public void init()
	{

	}

	public void postInit()
	{
		SpawnChestInventories.convertToItemStacks();
	}

	private void initCreativeTabs()
	{
		spawnChestTab = new CreativeTabSpawnChests();
	}

	private void initBlocks()
	{
		spawnChest = new BlockSpawnChest();

		GameRegistry.registerBlock(spawnChest, ItemBlockSpawnChest.class, "spawnchest");
	}

	private void initTiles()
	{
		GameRegistry.registerTileEntity(TileSpawnChest.class, "spawnchest");
	}
}
