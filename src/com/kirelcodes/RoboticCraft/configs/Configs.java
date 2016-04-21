package com.kirelcodes.RoboticCraft.configs;

import org.bukkit.configuration.file.FileConfiguration;

public enum Configs {
	SPEED(new BaseConfig("speeds")), LANGUAGES(new BaseConfig("languages"));
	private final BaseConfig config;

	public static void loadAll() {
	}

	private Configs(BaseConfig config) {
		this.config = config;
	}

	public void reloadConfig() {
		config.reloadConfig();
	}

	public void saveDefaultConfig() {
		config.saveDefaultConfig();
	}

	public void saveConfig() {
		config.saveConfig();
	}

	public FileConfiguration getConfig() {
		return config.getConfig();
	}

	public FileConfiguration getDefaultConfig() {
		return config.getDefaultConfig();
	}
}
