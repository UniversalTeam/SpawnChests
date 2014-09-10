package universalteam.spawnchests.configuration;

import net.minecraft.client.Minecraft;

import java.io.File;

public class Config
{
	public static final File configLocation = new File(Minecraft.getMinecraft().mcDataDir, "config" + File.separator + "FlatBedrock");
}
