package universalteam.spawnchests.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import universalteam.spawnchests.SpawnChests;
import universalteam.spawnchests.client.GUIHandler;
import universalteam.spawnchests.client.render.tile.TESRSpawnChest;
import universalteam.spawnchests.content.ContentSaveData;
import universalteam.spawnchests.content.SpawnChestInventories;
import universalteam.spawnchests.proxies.CommonProxy;
import universalteam.spawnchests.tile.TileSpawnChest;

public class BlockSpawnChest extends Block implements ITileEntityProvider
{
	public BlockSpawnChest()
	{
		super(Material.wood);
		this.setCreativeTab(CommonProxy.spawnChestTab);
		this.setBlockName("spawnchest");
		this.setBlockUnbreakable();
		this.disableStats();
		this.setResistance(6000000.0F);
		this.setStepSound(soundTypePiston);
		this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.875f, 0.9375f);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileSpawnChest();
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
	{
		ForgeDirection orientation;
		int facing = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		switch (facing)
		{
			case 0:
				orientation = ForgeDirection.NORTH;
				break;
			case 1:
				orientation = ForgeDirection.EAST;
				break;
			case 2:
				orientation = ForgeDirection.SOUTH;
				break;
			case 3:
				orientation = ForgeDirection.WEST;
				break;
			default:
				orientation = ForgeDirection.SOUTH;
		}

		TileSpawnChest tile = (TileSpawnChest) world.getTileEntity(x, y, z);
		tile.setOrientation(orientation);

		if (stack.getTagCompound() == null || !SpawnChestInventories.defaultContents.containsKey("SC.inventoryName"))
			return;

		tile.setInvName(stack.getTagCompound().getString("SC.inventoryName"));
		ContentSaveData.addNewChestToWorld(world, x, y, z, SpawnChestInventories.getItemStackContents().get(("SC.inventoryName")));
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
	{
		super.breakBlock(world, x, y, z, block, meta);

		ContentSaveData.removeChestFromWorld(world, x, y, z);
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public int getRenderType()
	{
		return TESRSpawnChest.RENDER_ID;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
	{
		if (player.isSneaking() || world.isSideSolid(x, y + 1, z, ForgeDirection.DOWN))
			return true;
		else if (!world.isRemote && world.getTileEntity(x, y, z) instanceof TileSpawnChest)
		{
			ContentSaveData.onInventoryOpened(player, x, y, z);
			((TileSpawnChest) world.getTileEntity(x, y, z)).items = ContentSaveData.getItemsForPlayer(player, x, y, z);
			player.openGui(SpawnChests.instance, GUIHandler.SPAWN_CHEST_GUI_ID, world, x, y, z);
		}

		return true;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		blockIcon = iconRegister.registerIcon("planks_oak");
	}

	@Override
	public boolean onBlockEventReceived(World world, int x, int y, int z, int eventId, int eventData)
	{
		super.onBlockEventReceived(world, x, y, z, eventId, eventData);
		TileEntity tile = world.getTileEntity(x, y, z);
		return tile != null && tile.receiveClientEvent(eventId, eventData);
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
	{
		TileSpawnChest tile = (TileSpawnChest) world.getTileEntity(x, y, z);
		NBTTagCompound compound = new NBTTagCompound();
		compound.setString("SC.inventoryName", tile.getInvName());
		ItemStack stack = new ItemStack(this, 1, 0);
		stack.setTagCompound(compound);

		return stack;
	}
}
