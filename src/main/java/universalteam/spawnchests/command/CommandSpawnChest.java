package universalteam.spawnchests.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import universalteam.spawnchests.content.SpawnChestInventories;
import universalteam.spawnchests.proxies.CommonProxy;

public class CommandSpawnChest extends CommandBase
{
	@Override
	public String getCommandName()
	{
		return "spawnchest";
	}

	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return "[name]";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args)
	{
		if (!(sender instanceof EntityPlayer))
			return;

		EntityPlayer player = (EntityPlayer) sender;

		if (args.length < 1 || args.length > 1)
			return;

		String name = args[0];

		if (!SpawnChestInventories.inventories.containsKey(name))
			return;

		ItemStack stack = new ItemStack(CommonProxy.spawnChest, 0, 1);
		stack.stackTagCompound = new NBTTagCompound();
		stack.getTagCompound().setString("SC.invName", name);

		player.setCurrentItemOrArmor(0, stack);
	}
}
