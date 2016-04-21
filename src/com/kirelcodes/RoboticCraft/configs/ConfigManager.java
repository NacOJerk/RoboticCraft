package com.kirelcodes.RoboticCraft.configs;

import org.bukkit.entity.Player;

public class ConfigManager {
	public static double getMineSpeed() {
		return Configs.SPEED.getConfig().getDouble("BlockMine");
	}

	public static double getLogSpeed() {
		return Configs.SPEED.getConfig().getDouble("LogMine");
	}

	public static double getHuntSpeed() {
		return Configs.SPEED.getConfig().getDouble("HuntDelay");
	}

	public static double getFishTime() {
		return Configs.SPEED.getConfig().getDouble("FishingTime") * 20;
	}

	public static String getLang(String lang, Player p) {
		String s = Configs.Languages.getConfig().getString(lang);
		if (s.contains("%prefix%")) {
			s = s.replace("%prefix%", Configs.Languages.getConfig().getString("Prefix"));
		}
		if (s.contains("%player%")) {
			s = s.replace("%player%", p.getName());
		}
		if (s.contains("%playerx%")) {
			s = s.replace("%playerx%", p.getLocation().getX() + "");
		}
		if (s.contains("%playery%")) {
			s = s.replace("%playery%", p.getLocation().getY() + "");
		}
		if (s.contains("%playerz%")) {
			s = s.replace("%playerz%", p.getLocation().getZ() + "");
		}
		return s;
	}

	public static void setLang(String lang, String type) {
		Configs.Languages.getConfig().set(lang, type);
	}

}
