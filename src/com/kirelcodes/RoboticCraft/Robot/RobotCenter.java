package com.kirelcodes.RoboticCraft.Robot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
/**
 * 
 * @author NacOJerk
 *
 */
public class RobotCenter {
	private static HashMap<Integer, RobotBase> robotBase = new HashMap<>();
	public static int addRobot(RobotBase robot){
		int ID = getID();
		robotBase.put(ID, robot);
		return ID;
	}
	public static boolean exists(int ID){
		return robotBase.containsKey(ID);
	}
	public static int getID(){
		Random randm = new Random();
		int ID = randm.nextInt(1000000) + 123456;
		if(exists(ID))
			return getID();
		return ID;
	}
	public static RobotBase getRobot(int ID){
		return robotBase.get(ID);
	}
	public static boolean removeRobot(int ID){
		return robotBase.remove(ID) != null;
	}
	public static List<RobotBase> getRobots(){
		return new ArrayList<>(robotBase.values());
	}
}
