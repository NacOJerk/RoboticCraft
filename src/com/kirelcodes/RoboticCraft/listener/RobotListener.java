package com.kirelcodes.RoboticCraft.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class RobotListener implements Listener{
	public RobotListener(Plugin p){
		Bukkit.getPluginManager().registerEvents(this, p);
	}
}
