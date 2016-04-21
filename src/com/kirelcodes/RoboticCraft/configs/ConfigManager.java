package com.kirelcodes.RoboticCraft.configs;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.entity.Player;
import static com.kirelcodes.RoboticCraft.configs.Configs.*;
public class ConfigManager {
	public static double getMineSpeed() {
		if(!SPEED.getConfig().contains("BlockMine")){
			SPEED.getConfig().set("BlockMine", SPEED.getDefaultConfig().get("BlockMine"));
			SPEED.saveConfig();
		}
		return SPEED.getConfig().getDouble("BlockMine");
	}

	public static double getLogSpeed() {
		if(!SPEED.getConfig().contains("LogMine")){
			SPEED.getConfig().set("LogMine", SPEED.getDefaultConfig().get("LogMine"));
			SPEED.saveConfig();
		}
		return SPEED.getConfig().getDouble("LogMine");
	}

	public static double getHuntSpeed() {
		if(!SPEED.getConfig().contains("HuntDelay")){
			SPEED.getConfig().set("HuntDelay", SPEED.getDefaultConfig().get("BlockMine"));
			SPEED.saveConfig();
		}
		return SPEED.getConfig().getDouble("HuntDelay");
	}

	public static double getFishTime() {
		if(!SPEED.getConfig().contains("FishingTime")){
			SPEED.getConfig().set("FishingTime", SPEED.getDefaultConfig().get("FishingTime"));
			SPEED.saveConfig();
		}
		return SPEED.getConfig().getDouble("FishingTime") * 20;
	}
	/*
	public static String getLang(String lang, Player p) {
		if(!containsLang(lang)){
			if(LANGUAGES.getDefaultConfig().contains(lang)){
				LANGUAGES.getConfig().set(lang, LANGUAGES.getDefaultConfig().get(lang));
				LANGUAGES.saveConfig();
			}
			return "";
		}
		String s = LANGUAGES.getConfig().getString(lang);
		s = s.replaceAll("&", "§");
		if (s.contains("%PLAYER%")) {
			s = s.replaceAll("%PLAYER%", p.getName());
		}
		if (s.contains("%PLAYERX%")) {
			s = s.replaceAll("%PLAYERX%", p.getLocation().getX() + "");
		}
		if (s.contains("%PLAYERY%")) {
			s = s.replaceAll("%PLAYERY%", p.getLocation().getY() + "");
		}
		if (s.contains("%PLAYERZ%")) {
			s = s.replaceAll("%PLAYERZ%", p.getLocation().getZ() + "");
		}
		List<String> matches = getMatches(s);
		for(String match : matches){
			String match2 = match.replaceAll("%", "");
			if(!containsLang(match2))
				continue;
			s.replaceAll(match, getLang(match2, p));
		}
		return s;

	}
	public static boolean containsLang(String lang){
		return LANGUAGES.getConfig().contains(lang);
	}
	private static List<String> getMatches(String input){
		Pattern regexField = Pattern.compile("(\\%\\w+\\%)");
		Matcher m = regexField.matcher(input);
		List<String> matches = new ArrayList<>();
		while(m.find())
			matches.add(m.group());
		return matches;
	}
	
	public static void setLang(String lang, String type) {
		LANGUAGES.getConfig().set(lang, type);
	}*/

}
