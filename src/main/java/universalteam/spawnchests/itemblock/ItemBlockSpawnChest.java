package universalteam.spawnchests.itemblock;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemBlockSpawnChest extends ItemBlock
{
	public ItemBlockSpawnChest(Block block)
	{
		super(block);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean visible)
	{
		if (!stack.hasTagCompound() || !stack.getTagCompound().hasKey("SC.inventoryName"))
			return;

		list.add(stack.getTagCompound().getString("SC.inventoryName"));
	}
}
