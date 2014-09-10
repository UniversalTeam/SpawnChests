package universalteam.spawnchests.content;

import com.google.common.collect.Maps;

import java.util.Map;

public class SpawnChestInventories
{
	public static Map<String, SpawnChestContents> inventories = Maps.newHashMap();

	public static void registerInventory(SpawnChestContents contents)
	{
		registerInventory(contents.name, contents);
	}

	public static void registerInventory(String invName, SpawnChestContents contents)
	{
		inventories.put(invName, contents);
	}
}
