package universalteam.spawnchests;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import universalteam.spawnchests.lib.Reference;
import universalteam.spawnchests.proxies.CommonProxy;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, dependencies = "after:UniversalCore")
public class SpawnChests
{
	@Mod.Instance(Reference.MOD_ID)
	public static SpawnChests instance;

	@SidedProxy(clientSide = "universalteam.spawnchests.proxies.ClientProxy", serverSide = "universalteam.spawnchests.proxies.CommonProxy")
	public static CommonProxy proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		proxy.preInit();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.init();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit();
	}

	@Mod.EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
		proxy.serverStarting();
	}
}
