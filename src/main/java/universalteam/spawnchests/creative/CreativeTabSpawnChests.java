package universalteam.spawnchests.creative;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import universalteam.spawnchests.content.SpawnChestInventories;
import universalteam.spawnchests.proxies.CommonProxy;

import java.util.List;

public class CreativeTabSpawnChests extends CreativeTabs
{
	public CreativeTabSpawnChests()
	{
		super("spawnchests");
	}

	@Override
	public Item getTabIconItem()
	{
		return Item.getItemFromBlock(CommonProxy.spawnChest);
	}

	@Override
	public void displayAllReleventItems(List list)
	{
		for (String name : SpawnChestInventories.getItemStackContents().keySet())
		{
			ItemStack stack = new ItemStack(CommonProxy.spawnChest, 0, 1);

			if (!stack.hasTagCompound())
				stack.stackTagCompound = new NBTTagCompound();

			stack.getTagCompound().setString("SC.inventoryName", name);
			list.add(stack);
		}
	}

	@Override
	public String getBackgroundImageName()
	{
		return "item_search.png";
	}

	@Override
	public boolean hasSearchBar()
	{
		return true;
	}
}
