package universalteam.spawnchests.configuration;

import net.minecraft.client.Minecraft;
import universalteam.spawnchests.lib.Reference;

import java.io.File;

public class Config
{
	public static final File configLocation = new File(Minecraft.getMinecraft().mcDataDir, "config" + File.separator + Reference.MOD_ID);
}
