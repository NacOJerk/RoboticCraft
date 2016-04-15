package com.kirelcodes.RoboticCraft.pathFinders;

import org.bukkit.Location;
import org.bukkit.Material;

import com.kirelcodes.RoboticCraft.robot.RobotMiner;

public class MinerPathfinder extends BasicPathfinder {

	private RobotMiner robot;
	private int currentHeight;
	private int blockIterator;
	private int stairIterator;
	private Location previus;

	public MinerPathfinder(RobotMiner robot) {
		this.robot = robot;
		currentHeight = robot.getLocation().getBlockY();
		blockIterator = 0;
		stairIterator = 0;
	}

	@Override
	public boolean shouldStart() {
		return robot.isMining();
	}

	@Override
	public void onStart() {
		previus = robot.getStartBlock();
	}

	@Override
	public void updateTask() {
		if (robot.onDelay())
			return;
		if (blockIterator == 0) {
			currentHeight--;
			stairIterator++;
		}
		if (stairIterator == 9)
			stairIterator = 1;
		if (currentHeight == 1) {
			robot.setMining(false);
		}
		blockIterator++;
		Location mine = robot.getStartBlock().clone();
		mine.setY(currentHeight);
		Location check = mine.clone();
		try {
			robot.setTargetLocation(previus);
		} catch (Exception e) {
			robot.setMining(false);
		}
		switch (blockIterator) {
		case 1:
			if (stairIterator != 1)//SO just do that:
				mine.add(0, 0, 0);
			break;
		case 2:
			if (stairIterator != 2)
				mine.add(1, 0, 0);
			break;
		case 3:
			if (stairIterator != 3)
				mine.add(2, 0, 0);
			break;
		case 4:
			if (stairIterator != 8)
				mine.add(0, 0, 1);
			break;
		case 5:
			mine.add(1, 0, 1);
			break;
		case 6:
			if (stairIterator != 4)
				mine.add(2, 0, 1);
			break;
		case 7:
			if (stairIterator != 7)
				mine.add(0, 0, 2);
			break;
		case 8:
			if (stairIterator != 6)
				mine.add(1, 0, 2);
			break;
		case 9:
			if (stairIterator != 5)
				mine.add(2, 0, 2);
			blockIterator = 0;
			break;
		}
		if (mine.getBlock().getType() == Material.BEDROCK) {
			robot.setMining(false);
			return;
		}
		previus = mine;
		//System.out.print(stairIterator);
		//System.out.print(check);
		//System.out.print(mine);
		if(mine.getBlockX()==check.getBlockX()&&mine.getBlockY()==check.getBlockY()&&mine.getBlockZ()==check.getBlockZ()&&stairIterator==1)
			return;
		robot.mineBlock(mine, robot.isEasterEgg());
	}
}
