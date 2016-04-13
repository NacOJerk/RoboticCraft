package com.kirelcodes.RoboticCraft;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.kirelcodes.RoboticCraft.gui.ControllerManager;
import com.kirelcodes.RoboticCraft.robot.RobotBase;
import com.kirelcodes.RoboticCraft.robot.RobotCenter;
import com.kirelcodes.RoboticCraft.robot.RobotFarmer;

public class RoboticCraft extends JavaPlugin{
	private static RoboticCraft robotiCraft= null;
	private ControllerManager controllerManager;
	@Override
	public void onEnable() {
		robotiCraft = this;
		controllerManager = new ControllerManager(this);
		for(Player p : Bukkit.getOnlinePlayers()){
			RobotFarmer robyTheFarmer = new RobotFarmer(p.getLocation());
			robyTheFarmer.setMarkOne(p.getLocation());
			robyTheFarmer.setMarkTwo(p.getLocation().clone().add(5, 0, 5));
			robyTheFarmer.setFarming(true);
			break;
		}
	}
	@Override
	public void onDisable() {
		for(RobotBase robot : RobotCenter.getRobots()){
			robot.remove();
		}
	}
	public static RoboticCraft getInstance(){
		return robotiCraft;
	}
	
	public ControllerManager getControllerManager(){
		return controllerManager;
	}
}
