package universalteam.spawnchests.content;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cpw.mods.fml.common.FMLLog;
import universalteam.spawnchests.configuration.Config;
import universalteam.spawnchests.lib.Reference;

import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;

public class ContentReader
{
	public static final File filesFolder = new File(Config.configLocation, "inventories");

	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public static void execute()
	{
		readJSONFiles();
	}

	private static void readJSONFiles()
	{
		for (File file : filesFolder.listFiles(new JSONFileNameFilter()))
			readJSON(file);
	}

	private static void readJSON(File jsonFile)
	{
		SpawnChestContents contents;

		try
		{
			contents = gson.fromJson(new FileReader(jsonFile), SpawnChestContents.class);
		}
		catch (Exception e)
		{
			FMLLog.severe("[%s] Failed to read the %s file, please check it for errors! This inventory will not be loaded!!", Reference.MOD_ID, jsonFile.getAbsolutePath().substring(jsonFile.getAbsolutePath().lastIndexOf(File.separator) + 1));
			e.printStackTrace();
			return;
		}

		SpawnChestInventories.registerInventory(contents);
	}

	public static class JSONFileNameFilter implements FilenameFilter
	{
		@Override
		public boolean accept(File dir, String name)
		{
			return name.endsWith(".json");
		}
	}
}
