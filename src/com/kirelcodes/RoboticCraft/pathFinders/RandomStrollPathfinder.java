package com.kirelcodes.RoboticCraft.pathFinders;

import java.util.Random;

import org.bukkit.Location;

import com.kirelcodes.RoboticCraft.robot.RobotBase;

public class RandomStrollPathfinder extends BasicPathfinder{
	private int clock;
	private Random rand;
	private RobotBase robot;
	public RandomStrollPathfinder(RobotBase robot) {
		this.robot = robot;
	}
	
	
	@Override
	public void onStart() {
		clock = 0;
		rand = new Random();
	}
	
	@Override
	public boolean shouldStart() {
		return true;
	}

	@Override
	public void afterTask() {
		clock++;
	}
	@Override
	public void updateTask() {
		if((clock % 1200) != 0)
			return;
		int x1 = rand.nextInt(30) - 15;
		int x = robot.getLocation().clone().add(x1, 0, 0).getBlockX();
		int z1 = rand.nextInt(30) - 15;
		int z = robot.getLocation().clone().add(0, 0, z1).getBlockZ();
		int y = robot.getWorld().getHighestBlockYAt(x, z);
		Location loc = new Location(robot.getWorld(), x, y, z);
		try {
			robot.setTargetLocation(loc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
