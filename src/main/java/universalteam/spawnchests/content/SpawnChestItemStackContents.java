package universalteam.spawnchests.content;

import net.minecraft.item.ItemStack;

public class SpawnChestItemStackContents
{
	public String name;
	public boolean resetAfterDeath;
	public ItemStack[] items;

	public SpawnChestItemStackContents(String name, boolean resetAfterDeath, ItemStack[] items)
	{
		this.name = name;
		this.resetAfterDeath = resetAfterDeath;
		this.items = items;
	}
}
