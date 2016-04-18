package com.kirelcodes.RoboticCraft;

import java.io.IOException;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.kirelcodes.RoboticCraft.gui.GUIListener;
import com.kirelcodes.RoboticCraft.listener.RobotListener;
import com.kirelcodes.RoboticCraft.robot.RobotBase;
import com.kirelcodes.RoboticCraft.robot.RobotCenter;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class RoboticCraft extends JavaPlugin {
	private static RoboticCraft robotiCraft = null;
	private GUIListener controllerManager;
	private static boolean usingWorldGuard = false;
	private static WorldGuardPlugin worldGuard;

	@Override
	public void onEnable() {
		robotiCraft = this;
		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) { // Failed to submit the stats :-(
			System.out.println("Error Submitting stats!");
		}
		controllerManager = new GUIListener(this);
		new RobotListener(this);
		RecipeAdder.addAll();
		worldGuard = setupWorldGuard();
	}

	@Override
	public void onDisable() {
		for (RobotBase robot : RobotCenter.getRobots()) {
			robot.destroy();
		}
	}

	public static RoboticCraft getInstance() {
		return robotiCraft;
	}

	public GUIListener getControllerManager() {
		return controllerManager;
	}
	
	private WorldGuardPlugin setupWorldGuard() {
	    Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");

	    if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
	        return null;
	    }
	    usingWorldGuard = true;
	    return (WorldGuardPlugin) plugin;
	}
	
	public static boolean usingWorldGuard(){
		return usingWorldGuard;
	}
	
	public static WorldGuardPlugin getWorldGuard(){
		return worldGuard;
	}
	
}
