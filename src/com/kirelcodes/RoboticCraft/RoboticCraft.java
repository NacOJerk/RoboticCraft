package com.kirelcodes.RoboticCraft;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.kirelcodes.RoboticCraft.configs.Configs;
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
	private static boolean usingFactions = false;
	
	@Override
	public void onEnable() {
		robotiCraft = this;
		Configs.SPEED.getClass();
		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) { // Failed to submit the stats :-(
			System.out.println("Error Submitting stats!");
		}
		controllerManager = new GUIListener(this);
		new RobotListener(this);
		RecipeAdder.addAll();
		
		//World Guard
		worldGuard = setupWorldGuard();
		//Factions
		setupFactions();
		
		RobotCenter.getID();
		for (World w : getServer().getWorlds()) {
			for (Entity en : w.getEntities()) {
				if (en == null)
					continue;
				if (!(en instanceof ArmorStand))
					continue;
				ArmorStand armor = (ArmorStand) en;
				if (armor.isCustomNameVisible())
					continue;
				if (armor.getCustomName() == null)
					continue;
				if (!ChatColor.stripColor(armor.getCustomName()).startsWith(
						"{NacOSearilize"))
					continue;
				if (armor.getLocation().getBlock().getType() != Material.CHEST)
					continue;
				Chest chest = (Chest) armor.getLocation().getBlock().getState();
				try {
					RobotBase.getRobot(armor, chest);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}

	}

	@Override
	public void onDisable() {
		try {
			for (RobotBase robot : RobotCenter.getRobots()) {
				robot.destroy();
			}
		} catch (Exception e) {

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
	
	private void setupFactions(){
		if(getServer().getPluginManager().getPlugin("Factions")!=null){
			usingFactions = true;
		}
	}

	public static boolean usingWorldGuard() {
		return usingWorldGuard;
	}
	
	public static boolean usingFactions() {
		return usingFactions;
	}

	public static WorldGuardPlugin getWorldGuard() {
		return worldGuard;
	}

}
