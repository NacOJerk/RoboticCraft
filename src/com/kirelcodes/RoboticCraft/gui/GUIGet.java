package com.kirelcodes.RoboticCraft.gui;

import com.kirelcodes.RoboticCraft.gui.guiRobots.GUIRobotBasic;
import com.kirelcodes.RoboticCraft.gui.guiRobots.GUIRobotBreeder;
import com.kirelcodes.RoboticCraft.gui.guiRobots.GUIRobotFarmer;
import com.kirelcodes.RoboticCraft.gui.guiRobots.GUIRobotFisher;
import com.kirelcodes.RoboticCraft.gui.guiRobots.GUIRobotHunter;
import com.kirelcodes.RoboticCraft.gui.guiRobots.GUIRobotLighter;
import com.kirelcodes.RoboticCraft.gui.guiRobots.GUIRobotLumberjack;
import com.kirelcodes.RoboticCraft.gui.guiRobots.GUIRobotMiner;
import com.kirelcodes.RoboticCraft.robot.RobotBase;
import com.kirelcodes.RoboticCraft.robot.RobotBreeder;
import com.kirelcodes.RoboticCraft.robot.RobotFarmer;
import com.kirelcodes.RoboticCraft.robot.RobotFisher;
import com.kirelcodes.RoboticCraft.robot.RobotHunter;
import com.kirelcodes.RoboticCraft.robot.RobotLighter;
import com.kirelcodes.RoboticCraft.robot.RobotLumberjack;
import com.kirelcodes.RoboticCraft.robot.RobotMiner;

public class GUIGet {
	public static GUI getGUI(RobotBase robot){
		if(robot.getClass().getName().equalsIgnoreCase(RobotBase.class.getName())){
			return new GUIRobotBasic(robot);
		}
		if(robot.getClass().getName().equalsIgnoreCase(RobotMiner.class.getName())){
			return new GUIRobotMiner((RobotMiner) robot);
		}
		if(robot.getClass().getName().equalsIgnoreCase(RobotFarmer.class.getName())){
			return new GUIRobotFarmer((RobotFarmer) robot);
		}
		if(robot.getClass().getName().equalsIgnoreCase(RobotHunter.class.getName())){
			return new GUIRobotHunter((RobotHunter) robot);
		}
		if(robot.getClass().getName().equalsIgnoreCase(RobotLighter.class.getName())){
			return new GUIRobotLighter((RobotLighter) robot);
		}
		if(robot.getClass().getName().equalsIgnoreCase(RobotLumberjack.class.getName())){
			return new GUIRobotLumberjack((RobotLumberjack) robot);
		}
		if(robot.getClass().getName().equalsIgnoreCase(RobotFisher.class.getName())){
			return new GUIRobotFisher((RobotFisher) robot);
		}
		if(robot.getClass().getName().equalsIgnoreCase(RobotBreeder.class.getName())){
			return new GUIRobotBreeder((RobotBreeder) robot);
		}
		return null;
	}
}
