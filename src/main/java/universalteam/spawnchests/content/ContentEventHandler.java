package universalteam.spawnchests.content;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class ContentEventHandler
{
	@SubscribeEvent
	public void onPlayerDied(LivingDeathEvent event)
	{
		if (event.entityLiving instanceof EntityPlayer)
			ContentSaveData.onPlayerDied((EntityPlayer) event.entityLiving);
	}
}
