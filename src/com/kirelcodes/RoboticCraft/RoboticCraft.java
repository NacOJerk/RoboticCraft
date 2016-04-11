package com.kirelcodes.RoboticCraft;

import org.bukkit.plugin.java.JavaPlugin;

import com.kirelcodes.RoboticCraft.Robot.RobotBase;
import com.kirelcodes.RoboticCraft.Robot.RobotCenter;
import com.kirelcodes.RoboticCraft.gui.ControllerManager;

public class RoboticCraft extends JavaPlugin{
	private static RoboticCraft robotiCraft= null;
	private ControllerManager controllerManager;
	@Override
	public void onEnable() {
		robotiCraft = this;
		controllerManager = new ControllerManager(this);
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
