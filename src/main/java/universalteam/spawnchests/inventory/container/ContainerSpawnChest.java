package universalteam.spawnchests.inventory.container;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import universalteam.spawnchests.inventory.slot.SlotOutput;
import universalteam.spawnchests.tile.TileSpawnChest;

public class ContainerSpawnChest extends Container
{
	private static final int INV_ROWS = 3;
	private static final int INV_COLUMNS = 9;

	private TileSpawnChest tile;

	public ContainerSpawnChest(InventoryPlayer invPlayer, TileSpawnChest tile)
	{
		this.tile = tile;
		tile.openInventory();

		for (int chestRow = 0; chestRow < INV_ROWS; ++chestRow)
			for (int chestColumns = 0; chestColumns < INV_COLUMNS; ++chestColumns)
				this.addSlotToContainer(new SlotOutput(tile, chestColumns + chestRow * INV_ROWS, 8 + chestColumns * 18, 17 + chestRow * 18));

		for (int invRow = 0; invRow < INV_ROWS; ++invRow)
			for (int invColumn = 0; invColumn < INV_COLUMNS; ++invColumn)
				this.addSlotToContainer(new Slot(invPlayer, invColumn + invRow * 9 + 9, 8 + invColumn * 18, 84 + invRow * 18));

		for (int actionBarSlot = 0; actionBarSlot < INV_COLUMNS; ++actionBarSlot)
				this.addSlotToContainer(new Slot(invPlayer, actionBarSlot, 8 + actionBarSlot * 18, 142));
	}

	@Override
	public void onContainerClosed(EntityPlayer player)
	{
		super.onContainerClosed(player);
		tile.closeInventory();
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return tile.isUseableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot)
	{
		return null;
	}
}
