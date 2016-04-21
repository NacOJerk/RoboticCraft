package com.kirelcodes.RoboticCraft;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.api.ResidenceApi;
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
	private static boolean usingResidence = false;

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

		// World Guard
		worldGuard = setupWorldGuard();
		// Factions
		setupFactions();
		// Residence
		setupResidence();

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

	private void setupFactions() {
		if (getServer().getPluginManager().getPlugin("Factions") != null) {
			usingFactions = true;
		}
	}

	private ResidenceApi setupResidence() {
		Plugin resPlug = getServer().getPluginManager().getPlugin("Residence");
		if (resPlug != null) {
			usingResidence = true;
			return Residence.getAPI();
		}
		return null;
	}

	public static boolean usingWorldGuard() {
		return usingWorldGuard;
	}

	public static boolean usingFactions() {
		return usingFactions;
	}

	public static boolean usingResidence() {
		return usingResidence;
	}

	public static WorldGuardPlugin getWorldGuard() {
		return worldGuard;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (command.getLabel().equalsIgnoreCase("remote")) {
			if (sender.isOp()) {
				if (args.length == 1) {
					if (!(sender instanceof Player)) {
						sender.sendMessage("[RoboticCraft] Only players can use this command!");
						return false;
					}
					for (RobotItem ri : RobotItem.getRobotItemList()) {
						String name = ri.getRobotClass().getName().split("\\.")[4];
						if (("Robot" + args[0]).equalsIgnoreCase(name)) {
							((Player) sender).getInventory().addItem(
									ri.getItem());
							sender.sendMessage("[RoboticCraft] Recieved a "
									+ name + " remote!");
							return false;
						}
					}
					sender.sendMessage("[RoboticCraft] Robot " + args[0]
							+ " wasn't found!");
				} else if (args.length == 2) {
					if (Bukkit.getPlayer(args[1]) != null) {
						for (RobotItem ri : RobotItem.getRobotItemList()) {
							String name = ri.getRobotClass().getName()
									.split("\\.")[4];
							if (("Robot" + args[0]).equalsIgnoreCase(name)) {
								Bukkit.getPlayer(args[1]).getInventory()
										.addItem(ri.getItem());
								Bukkit.getPlayer(args[1]).sendMessage("[RoboticCraft] You recived a " + args[0] + " remote");
								sender.sendMessage("[RoboticCraft] Send a "
										+ name + " remote to " + args[1] + "!");
								return false;
							}
						}
						sender.sendMessage("[RoboticCraft] Robot " + args[0]
								+ " wasn't found!");
					} else {
						sender.sendMessage("[RoboticCraft] Couldn't find player "
								+ args[1] + "!");
					}
				} else
					sender.sendMessage("[RoboticCraft] Correct usage: /remote [robot] {player}");
			}
		}
		return false;
	}

}
