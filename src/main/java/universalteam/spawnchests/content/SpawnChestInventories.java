package universalteam.spawnchests.content;

import com.google.common.collect.Maps;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import universalteam.spawnchests.lib.Reference;

import java.util.Map;

public class SpawnChestInventories
{
	public static Map<String, SpawnChestContents> defaultContents = Maps.newHashMap();
	protected static Map<String, SpawnChestItemStackContents> itemStackContents;

	public static void registerInventory(SpawnChestContents contents)
	{
		registerInventory(contents.name, contents);
	}

	public static void registerInventory(String invName, SpawnChestContents contents)
	{
		defaultContents.put(invName, contents);
	}

	public static void convertToItemStacks()
	{
		itemStackContents = Maps.newHashMap();

		for (SpawnChestContents contents : defaultContents.values())
		{
			ItemStack[] itemStacks = new ItemStack[64];

			for (SpawnChestContents.ItemEntry entry : contents.items)
			{
				ItemStack stack;
				Item item = getItemFromName(entry.id);

				if (item == null)
				{
					FMLLog.warning("[%s] the item: %s in file %s doesn't seem to exist! Not loading this item!!", Reference.MOD_ID, entry.id, contents.name);
					continue;
				}

				Integer meta = entry.meta;

				if (meta == null)
					meta = 0;

				stack = new ItemStack(item, meta, entry.amount);

				try
				{
					NBTBase compoundBase = JsonToNBT.func_150315_a(entry.nbtTags);

					if (!(compoundBase instanceof NBTTagCompound))
					{
						FMLLog.warning("[%s] the tag from the item %s in %s seems to contain an invalid tag!, the nbt data for this item will not be loaded!", Reference.MOD_ID, entry.id, contents.name);
						return;
					}

					stack.setTagCompound((NBTTagCompound) compoundBase);
				}
				catch (NBTException e)
				{
					FMLLog.warning("[%s] Failed to read the NBTTags from %s in %s, the nbt data for this item will not be loaded!", Reference.MOD_ID, entry.id, contents.name);
				}
			}

			if (contents.resetAfterDeath == null)
				contents.resetAfterDeath = false;

			itemStackContents.put(contents.name, new SpawnChestItemStackContents(contents.name, contents.resetAfterDeath, itemStacks));
		}
	}

	public static boolean isConverted()
	{
		return itemStackContents != null && itemStackContents.size() > 0;
	}

	public static Map<String, SpawnChestItemStackContents> getItemStackContents()
	{
		return isConverted() ? itemStackContents : null;
	}

	protected static Item getItemFromName(String name)
	{
		if (name.contains(":") && GameRegistry.findItem(name.substring(0, name.indexOf(':')), name.substring(name.indexOf(':') + 1)) != null)
			return GameRegistry.findItem(name.substring(0, name.indexOf(':')), name.substring(name.indexOf(':') + 1));
		else if (Item.itemRegistry.getObject(name) != null)
			return (Item) Item.itemRegistry.getObject(name);
		else
			return null;
	}

	public static class SpawnChestItemStackContents
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
}
