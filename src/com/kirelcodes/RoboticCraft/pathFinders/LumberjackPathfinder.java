package com.kirelcodes.RoboticCraft.pathFinders;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;

import com.kirelcodes.RoboticCraft.robot.RobotLumberjack;

public class LumberjackPathfinder extends BasicPathfinder {
	private RobotLumberjack robot;

	public LumberjackPathfinder(RobotLumberjack robot) {
		this.robot = robot;
	}

	@Override
	public boolean shouldStart() {
		return robot.isCutting();
	}

	@Override
	public void updateTask() {
		List<Block> blocks = new ArrayList<Block>();
		int radius = 5;
		for (int x = robot.getLocation().getBlockX() - radius; x < robot.getLocation().getX() + radius; x++) {
			for (int y = robot.getLocation().getBlockY() - radius; y < robot.getLocation().getY() + radius; y++) {
				for (int z = robot.getLocation().getBlockZ() - radius; z < robot.getLocation().getZ() + radius; z++) {
					blocks.add(robot.getLocation().getWorld().getBlockAt(x, y, z));
				}
			}
		}
		for (Block b : blocks) {
			if (b == null)
				return;
			if (b.getType() == Material.LOG) {
				try {
					robot.setTargetLocation(b.getLocation());
					robot.mineBlock(b.getLocation());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
