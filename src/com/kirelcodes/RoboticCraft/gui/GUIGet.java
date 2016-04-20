package com.kirelcodes.RoboticCraft.gui;

import com.kirelcodes.RoboticCraft.RobotItem;
import com.kirelcodes.RoboticCraft.robot.RobotBase;

public class GUIGet {
	public static GUI getGUI(RobotBase robot) {
		try {	
			return RobotItem.getGUI(robot);
		} catch (Exception e) {
			return null;
		}
	}
}
