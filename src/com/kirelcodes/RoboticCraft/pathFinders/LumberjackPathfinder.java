package com.kirelcodes.RoboticCraft.pathFinders;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.kirelcodes.RoboticCraft.robot.RobotLumberjack;

public class LumberjackPathfinder extends BasicPathfinder {
	private RobotLumberjack robot;
	private Block target;
	private ArrayList<Block> bl = new ArrayList<Block>();

	public LumberjackPathfinder(RobotLumberjack robot) {
		this.robot = robot;
	}

	@Override
	public boolean shouldStart() {
		return robot.isCutting();
	}

	@Override
	public void afterTask() {
		if(target.getType() == Material.AIR){
			target = null;
		}
	}

	@Override
	public void updateTask() {
		List<Block> blocks = new ArrayList<Block>();
		if (target == null) {
			int radius = 10;
			for (int x = robot.getLocation().getBlockX() - radius; x < robot.getLocation().getX() + radius; x++) {
				for (int y = robot.getLocation().getBlockY() - radius; y < robot.getLocation().getY() + radius; y++) {
					for (int z = robot.getLocation().getBlockZ() - radius; z < robot.getLocation().getZ()
							+ radius; z++) {
						blocks.add(robot.getLocation().getWorld().getBlockAt(x, y, z));
					}
				}
			}
		}
		for (Block b : blocks) {
			if (b == null)
				return;
			if (b.getType() == Material.LOG) {
				if (!bl.contains(b)) {
					target = b;
					break;
				}
			}
		}
		if (target == null)
			return;
		try {
			robot.setTargetLocation(target.getLocation());
			if (target.getLocation().distance(robot.getLocation()) <= 5) {
				robot.mineBlock(target);
				target = null;
			} else if (target.getLocation().getBlockX() == robot.getLocation().getBlockX()) {
				if (target.getLocation().getBlockZ() == robot.getLocation().getBlockZ()) {
					bl.add(target);
					target = null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
