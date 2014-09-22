package universalteam.spawnchests.content;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraftforge.common.util.Constants;

import java.util.List;
import java.util.Map;

public class ContentSaveData extends WorldSavedData
{
	public static final String CONTENT_HANDLER_CONSTANT = "SC.contents";

	private static Map<Location, NBTTagCompound> chestCompounds = Maps.newHashMap();
	private static List<Location> resetAfterDeathChests = Lists.newArrayList();

	private static final String INVENTORY_NAME_TAG = "InventoryName";
	private static final String SLOT_TAG = "Slot";
	private static final String ITEMS_TAG = "Items";
	private static final String PLAYER_NAME_TAG = "Player.";

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
		compound.setString(INVENTORY_NAME_TAG, contents.name);
		Location loc = new Location(dimID, x, y, z);

		if (!chestCompounds.containsKey(loc))
			chestCompounds.put(loc, compound);

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

		if (chestCompounds.containsKey(loc))
			chestCompounds.remove(loc);

		if (resetAfterDeathChests.contains(loc))
			resetAfterDeathChests.remove(loc);
	}

	public static void onInventoryOpened(EntityPlayer player, int x, int y, int z)
	{
		Location loc = new Location(player.worldObj.provider.dimensionId, x, y, z);
		NBTTagCompound chestCompound = chestCompounds.get(loc);
		String name = PLAYER_NAME_TAG + player.getGameProfile().getName();

		if (!chestCompound.hasKey(name))
		{
			NBTTagCompound playerCompound = new NBTTagCompound();
			writeItemStacksToNBT(playerCompound, SpawnChestInventories.getItemStackContents(chestCompound.getString(INVENTORY_NAME_TAG)).items);
			chestCompound.setTag(name, playerCompound);
		}
	}

	public static void onInventoryClosed(EntityPlayer player, int x, int y, int z, ItemStack... leftOverItems)
	{
		Location loc = new Location(player.worldObj.provider.dimensionId, x, y, z);
		NBTTagCompound chestCompound = chestCompounds.get(loc);
		NBTTagCompound playerCompound = chestCompound.getCompoundTag(PLAYER_NAME_TAG + player.getGameProfile().getName());
		playerCompound.removeTag(ITEMS_TAG);

		if (leftOverItems == null)
			return;

		writeItemStacksToNBT(playerCompound, leftOverItems);
	}

	public static void onPlayerDied(EntityPlayer player)
	{
		for (Location loc : resetAfterDeathChests)
		{
			NBTTagCompound chestCompound = chestCompounds.get(loc);
			NBTTagCompound playerCompound = chestCompound.getCompoundTag(PLAYER_NAME_TAG + player.getGameProfile().getName());
			writeItemStacksToNBT(playerCompound, SpawnChestInventories.getItemStackContents(chestCompound.getString(INVENTORY_NAME_TAG)).items);
		}
	}

	public static ItemStack[] getItemsForPlayer(EntityPlayer player, int x, int y, int z)
	{
		Location loc = new Location(player.worldObj.provider.dimensionId, x, y, z);
		NBTTagCompound chestCompound = chestCompounds.get(loc);

		return readItemStacksFromNBT(chestCompound.getCompoundTag(PLAYER_NAME_TAG + player.getGameProfile().getName()));
	}

	private static void writeItemStacksToNBT(NBTTagCompound compound, ItemStack[] items)
	{
		NBTTagList tagList = new NBTTagList();

		for (int currentIndex = 0; currentIndex < items.length; ++currentIndex)
		{
			if (items[currentIndex] != null)
			{
				NBTTagCompound tagCompound = new NBTTagCompound();
				tagCompound.setByte(SLOT_TAG, (byte) currentIndex);
				items[currentIndex].writeToNBT(tagCompound);
				tagList.appendTag(tagCompound);
			}
		}

		compound.setTag(ITEMS_TAG, tagList);
	}

	private static ItemStack[] readItemStacksFromNBT(NBTTagCompound compound)
	{
		ItemStack[] items = new ItemStack[27];
		NBTTagList tagList = compound.getTagList(ITEMS_TAG, Constants.NBT.TAG_LIST);

		for (int i = 0; i < tagList.tagCount(); ++i)
		{
			NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
			byte slotIndex = tagCompound.getByte(SLOT_TAG);

			if (slotIndex >= 0 && slotIndex < items.length)
				items[slotIndex] = ItemStack.loadItemStackFromNBT(tagCompound);
		}

		return items;
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
