package universalteam.spawnchests.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import universalteam.spawnchests.proxies.CommonProxy;

public class TileSpawnChest extends TileEntity implements ISidedInventory
{
	public float lidAngle;
	public float prevLidAngle;
	public int numUsingPlayers;
	private int ticksSinceSync;

	public ForgeDirection orientation = ForgeDirection.SOUTH;

	private String invName = "NONE";
	private ItemStack[] items = new ItemStack[27];

	@Override
	public void updateEntity()
	{
		super.updateEntity();

		if (++ticksSinceSync % 20 * 4 == 0)
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, CommonProxy.spawnChest, 1, numUsingPlayers);

		prevLidAngle = lidAngle;
		float angleIncrement = 0.1F;
		double adjustedXCoord, adjustedZCoord;

		if (numUsingPlayers > 0 && lidAngle == 0.0F)
		{
			adjustedXCoord = xCoord + 0.5D;
			adjustedZCoord = zCoord + 0.5D;
			worldObj.playSoundEffect(adjustedXCoord, yCoord + 0.5D, adjustedZCoord, "random.chestopen", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
		}

		if (numUsingPlayers == 0 && lidAngle > 0.0F || numUsingPlayers > 0 && lidAngle < 1.0F)
		{
			float var8 = lidAngle;

			if (numUsingPlayers > 0)
				lidAngle += angleIncrement;
			else
				lidAngle -= angleIncrement;

			if (lidAngle > 1.0F)
				lidAngle = 1.0F;

			if (lidAngle < 0.5F && var8 >= 0.5F)
			{
				adjustedXCoord = xCoord + 0.5D;
				adjustedZCoord = zCoord + 0.5D;
				worldObj.playSoundEffect(adjustedXCoord, yCoord + 0.5D, adjustedZCoord, "random.chestclosed", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
			}

			if (lidAngle < 0.0F)
				lidAngle = 0.0F;
		}
	}

	@Override
	public boolean receiveClientEvent(int eventID, int numUsingPlayers)
	{
		if (eventID == 1)
		{
			this.numUsingPlayers = numUsingPlayers;
			return true;
		}
		else
			return super.receiveClientEvent(eventID, numUsingPlayers);
	}

	@Override
	public void writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setString("SC.inventoryName", invName);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		this.invName = compound.getString("SC.inventoryName");
	}

	@Override
	public int getSizeInventory()
	{
		return 27;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return items[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount)
	{
		ItemStack itemStack = getStackInSlot(slot);

		if (itemStack != null)
		{
			if (itemStack.stackSize <= amount)
				setInventorySlotContents(slot, null);
			else
			{
				itemStack = itemStack.splitStack(amount);

				if (itemStack.stackSize == 0)
					setInventorySlotContents(slot, null);
			}
		}

		return itemStack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		if (items[slot] != null)
		{
			ItemStack itemStack = items[slot];
			items[slot] = null;
			return itemStack;
		}
		else
			return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{

	}

	@Override
	public String getInventoryName()
	{
		return "spawn chest";
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return true;
	}

	@Override
	public void openInventory()
	{
		++numUsingPlayers;
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, CommonProxy.spawnChest, 1, numUsingPlayers);
	}

	@Override
	public void closeInventory()
	{
		--numUsingPlayers;
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, CommonProxy.spawnChest, 1, numUsingPlayers);
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack)
	{
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side)
	{
		return new int[0];
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side)
	{
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side)
	{
		return false;
	}

	public void setOrientation(ForgeDirection orientation)
	{
		this.orientation = orientation;
	}

	public ForgeDirection getOrientation()
	{
		return orientation;
	}

	public boolean setInvName(String invName)
	{
		if (this.invName.equals("NONE"))
		{
			this.invName = invName;
			return true;
		}

		return false;
	}

	public String getInvName()
	{
		return invName;
	}
}
