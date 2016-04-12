package com.kirelcodes.RoboticCraft.pathFinders;

import org.bukkit.Location;

import com.kirelcodes.RoboticCraft.robot.RobotMiner;

public class MinerPathfinder extends BasicPathfinder{
	
	private RobotMiner robot;
	private int currentHeight;
	private int blockIterator;
	
	public MinerPathfinder(RobotMiner robot) {
		this.robot = robot;
		currentHeight = robot.getLocation().getBlockY();
		blockIterator = 0;
	}
	
	@Override
	public boolean shouldStart() {
		return robot.isMining();
	}

	@Override
	public void updateTask() {
		if(blockIterator==0)
			currentHeight--;
		if(currentHeight==1){
			robot.setMining(false);
		}
		blockIterator++;
		Location mine = robot.getStartBlock();
		mine.setY(currentHeight);
		switch(blockIterator){
		case 1:
			mine.add(0, 0, 0);
		case 2:
			mine.add(1, 0, 0);
		case 3:
			mine.add(2, 0, 0);
		case 4:
			mine.add(0, 0, 1);
		case 5:
			mine.add(1, 0, 1);
		case 6:
			mine.add(2, 0, 1);
		case 7:
			mine.add(0, 0, 2);
		case 8:
			mine.add(1, 0, 2);
		case 9:
			mine.add(2, 0, 2);
			blockIterator=0;
		}
		robot.mineBlock(mine);
	}

}
