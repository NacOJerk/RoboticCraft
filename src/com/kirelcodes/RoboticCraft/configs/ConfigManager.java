package com.kirelcodes.RoboticCraft.configs;

public class ConfigManager {
	public static double getMineSpeed(){
		return Configs.SPEED.getConfig().getDouble("BlockMine");
	}
	public static double getLogSpeed(){
		return Configs.SPEED.getConfig().getDouble("LogMine");
	}
	public static double getHuntSpeed(){
		return Configs.SPEED.getConfig().getDouble("HuntDelay");
	}
	public static double getFishTime(){
		return Configs.SPEED.getConfig().getDouble("FishingTime") * 20;
	}
}
