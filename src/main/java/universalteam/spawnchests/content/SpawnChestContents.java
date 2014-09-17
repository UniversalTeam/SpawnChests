package universalteam.spawnchests.content;

public class SpawnChestContents
{
	public String name;
	public Boolean resetAfterDeath;
	public ItemEntry[] items;

	public static class ItemEntry
	{
		public String id;
		public Integer meta;
		public Integer amount;
		public String nbtTags;
	}
}
