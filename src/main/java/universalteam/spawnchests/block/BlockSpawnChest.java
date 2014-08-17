package universalteam.spawnchests.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import universalteam.spawnchests.client.render.tile.TESRSpawnChest;
import universalteam.spawnchests.tile.TileSpawnChest;

public class BlockSpawnChest extends Block implements ITileEntityProvider
{
	protected BlockSpawnChest()
	{
		super(Material.wood);
		this.setBlockName("spawnchest");
		this.setHardness(2.5f);
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
		int dir = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
		int meta;

		switch (dir)
		{
			case 0:
				meta = ForgeDirection.SOUTH.ordinal();
			case 1:
				meta = ForgeDirection.WEST.ordinal();
			case 2:
				meta = ForgeDirection.NORTH.ordinal();
			case 3:
				meta = ForgeDirection.EAST.ordinal();
			default:
				meta = ForgeDirection.SOUTH.ordinal();
		}

		world.setBlockMetadataWithNotify(x, y, z, meta, 3);
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
}
