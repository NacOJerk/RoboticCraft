package com.kirelcodes.RoboticCraft;

import org.bukkit.plugin.java.JavaPlugin;

import com.kirelcodes.RoboticCraft.gui.GUIListener;
import com.kirelcodes.RoboticCraft.listener.RobotListener;
import com.kirelcodes.RoboticCraft.robot.RobotBase;
import com.kirelcodes.RoboticCraft.robot.RobotCenter;

public class RoboticCraft extends JavaPlugin {
	private static RoboticCraft robotiCraft = null;
	private GUIListener controllerManager;

	@Override
	public void onEnable() {
		robotiCraft = this;
		controllerManager = new GUIListener(this);
		new RobotListener(this);
		RecipeAdder.addAll();
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
}
