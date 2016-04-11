package com.kirelcodes.RoboticCraft;

import org.bukkit.plugin.java.JavaPlugin;

import com.kirelcodes.RoboticCraft.Robot.RobotBase;
import com.kirelcodes.RoboticCraft.Robot.RobotCenter;

public class RoboticCraft extends JavaPlugin{
	private static RoboticCraft robotiCraft= null;
	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		robotiCraft = this;
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
}
