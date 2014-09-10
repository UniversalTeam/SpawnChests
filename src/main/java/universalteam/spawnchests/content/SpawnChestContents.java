package universalteam.spawnchests.content;

public class SpawnChestContents
{
	public String name;
	public boolean resetAfterDeath;
	public ItemEntry[] items;

	public static class ItemEntry
	{
		public String id;
		public int meta;
		public String nbtTags;
	}
}
