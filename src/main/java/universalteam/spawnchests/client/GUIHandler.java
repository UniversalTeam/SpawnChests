package universalteam.spawnchests.client;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import universalteam.spawnchests.client.gui.GUISpawnChest;
import universalteam.spawnchests.inventory.container.ContainerSpawnChest;
import universalteam.spawnchests.tile.TileSpawnChest;

public class GUIHandler implements IGuiHandler
{
	public static final int SPAWN_CHEST_GUI_ID = 1;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if (ID == SPAWN_CHEST_GUI_ID)
		{
			TileSpawnChest tile = (TileSpawnChest) world.getTileEntity(x, y, z);
			return new ContainerSpawnChest(player.inventory, tile);
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if (ID == SPAWN_CHEST_GUI_ID)
		{
			TileSpawnChest tile = (TileSpawnChest) world.getTileEntity(x, y, z);
			return new GUISpawnChest(player.inventory, tile);
		}

		return null;
	}
}
