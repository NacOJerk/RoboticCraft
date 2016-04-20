package com.kirelcodes.RoboticCraft.configs;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.kirelcodes.RoboticCraft.RoboticCraft;

public class BaseConfig {
	private FileConfiguration baseConfig = null;
	private File baseConfigFile = null;
	private String configName = "";

	public void reloadConfig() {
		if (baseConfigFile == null) { // Checks if the file is null
			baseConfigFile = new File(RoboticCraft.getInstance().getDataFolder(), configName + ".yml");
		}
		baseConfig = YamlConfiguration.loadConfiguration(baseConfigFile);
	}

	public FileConfiguration getConfig() {
		if (baseConfig == null)
			reloadConfig();
		return baseConfig;
	}

	public void saveConfig() {
		if (baseConfig == null | baseConfigFile == null)
			return;
		try {
			getConfig().save(baseConfigFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveDefaultConfig() {
		if (baseConfigFile == null)
			baseConfigFile = new File(RoboticCraft.getInstance().getDataFolder(), configName + ".yml");
		if (!baseConfigFile.exists())
			RoboticCraft.getInstance().saveResource(configName + ".yml", false);
	}

	///////////////////////////////////////

	public String getName() {
		return configName;
	}

	public BaseConfig(String name) {
		this.configName = name;
		reloadConfig();
		saveDefaultConfig();
	}
}
