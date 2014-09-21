package universalteam.spawnchests.content;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cpw.mods.fml.common.FMLLog;
import universalteam.spawnchests.configuration.Config;
import universalteam.spawnchests.lib.Reference;

import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;

public class ContentReader
{
	public static final File filesFolder = new File(Config.configLocation, "inventories");

	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public static void execute()
	{
		addTestContents();

		checkFileStructure();

		readJSONFiles();
	}

	private static void checkFileStructure()
	{
		try
		{
			Files.createParentDirs(filesFolder);
			if (!filesFolder.exists())
				filesFolder.createNewFile();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private static void readJSONFiles()
	{
		if (filesFolder.listFiles(new JSONFileNameFilter()) == null)
			return;

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

	private static void addTestContents()
	{
		SpawnChestContents contents = new SpawnChestContents();
		contents.name = "test";
		contents.resetAfterDeath = false;
		SpawnChestContents.ItemEntry[] items = new SpawnChestContents.ItemEntry[1];
		SpawnChestContents.ItemEntry item = new SpawnChestContents.ItemEntry();
		item.id = "minecraft:wood";
		item.meta = 3;
		item.amount = 2;
		item.nbtTags = "{display:{Name:\"Test item\",Lore:[This is a test item]}}";
		items[0] = item;
		contents.items = items;
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
