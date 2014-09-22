package universalteam.spawnchests.content;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

import java.util.List;
import java.util.Map;

public class ContentSaveData extends WorldSavedData
{
	public static String CONTENT_HANDLER_CONSTANT = "SC.contents";

	private static Map<Location, NBTTagCompound> chestCompound = Maps.newHashMap();
	private static List<Location> resetAfterDeathChests = Lists.newArrayList();

	public ContentSaveData()
	{
		super(CONTENT_HANDLER_CONSTANT);
	}

	public static void addNewChestToWorld(World world, int x, int y, int z, SpawnChestItemStackContents contents)
	{
		addNewChestToWorld(world.provider.dimensionId, x, y, z, contents);
	}

	public static void addNewChestToWorld(int dimID, int x, int y, int z, SpawnChestItemStackContents contents)
	{
		NBTTagCompound compound = new NBTTagCompound();
		compound.setString("inventoryName", contents.name);
		Location loc = new Location(dimID, x, y, z);

		if (!chestCompound.containsKey(loc))
			chestCompound.put(loc, compound);

		if (contents.resetAfterDeath && !resetAfterDeathChests.contains(loc))
			resetAfterDeathChests.add(loc);
	}

	public static void removeChestFromWorld(World world, int x, int y, int z)
	{
		removeChestFromWorld(world.provider.dimensionId, x, y, z);
	}

	public static void removeChestFromWorld(int dimID, int x, int y, int z)
	{
		Location loc = new Location(dimID, x, y, z);

		if (chestCompound.containsKey(loc))
			chestCompound.remove(loc);

		if (resetAfterDeathChests.contains(loc))
			resetAfterDeathChests.remove(loc);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{

	}

	@Override
	public void writeToNBT(NBTTagCompound compound)
	{

	}

	public static class Location
	{
		int dimID;
		int x;
		int y;
		int z;

		public Location(int dimID, int x, int y, int z)
		{
			this.dimID = dimID;
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}
}
