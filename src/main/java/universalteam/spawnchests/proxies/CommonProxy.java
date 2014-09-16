package universalteam.spawnchests.proxies;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.command.CommandHandler;
import net.minecraft.creativetab.CreativeTabs;
import universalteam.spawnchests.block.BlockSpawnChest;
import universalteam.spawnchests.command.CommandSpawnChest;
import universalteam.spawnchests.content.ContentReader;
import universalteam.spawnchests.content.SpawnChestInventories;
import universalteam.spawnchests.creative.CreativeTabSpawnChests;
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

	public void serverStarting()
	{
		((CommandHandler) FMLCommonHandler.instance().getSidedDelegate().getServer().getCommandManager()).registerCommand(new CommandSpawnChest());
	}

	private void initCreativeTabs()
	{
		spawnChestTab = new CreativeTabSpawnChests();
	}

	private void initBlocks()
	{
		spawnChest = new BlockSpawnChest();

		GameRegistry.registerBlock(spawnChest, "spawnchest");
	}

	private void initTiles()
	{
		GameRegistry.registerTileEntity(TileSpawnChest.class, "spawnchest");
	}
}
