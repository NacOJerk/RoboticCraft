package com.kirelcodes.RoboticCraft.pathFinders;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;

import com.kirelcodes.RoboticCraft.robot.RobotBase;
import com.kirelcodes.RoboticCraft.robot.RobotLumberjack;

public class LumberjackPathfinder extends BasicPathfinder {
	private RobotLumberjack robot;
	private Block target;
	private ArrayList<Block> blackList = new ArrayList<Block>();
	private List<Block> blocks = new ArrayList<>();

	public LumberjackPathfinder(RobotLumberjack robot) {
		this.robot = robot;
	}

	@Override
	public void onStart() {
		blackList = new ArrayList<>();
	}

	@Override
	public boolean shouldStart() {
		return robot.isCutting();
	}

	@Override
	public void afterTask() {
		if (target == null)
			return;
		if (target.getType() == Material.AIR || (target.getY() - robot.getLocation().getY()) >= 9)
			target = null;
		if (target == null || robot == null)
			return;
		if ((target.getY() - robot.getLocation().getY()) >= 9)
			blackList.add(target);
		blocks.clear();
	}

	@Override
	public void updateTask() {
		int radius = 10;
		if (target == null) {
			int x = robot.getLocation().getBlockX();
			int z = robot.getLocation().getBlockZ();
			for (int y = radius; y > -radius; y--) {
				blocks.addAll(RobotBase.getNearbyBlocks(
						robot.getWorld().getBlockAt(x, robot.getLocation().getBlockY() + y, z).getLocation(), radius));
			}
		}
		for (Block b : blocks) {
			if (b == null)
				continue;
			if (blackList.contains(b))
				continue;
			if (b.getType() == Material.LOG || b.getType() == Material.LOG_2) {
				if (!blackList.contains(b)) {
					target = b;
					break;
				}
			}
		}

		if (target == null)
			return;
		try {
			if (robot == null || target == null)
				return;
			robot.setTargetLocation(target.getLocation());
			if (target.getLocation().distance(robot.getLocation()) <= 6) {
				robot.mineBlock(target);
				target = null;
			} else if (target.getLocation().getBlockX() == robot.getLocation().getBlockX()) {
				if (target.getLocation().getBlockZ() == robot.getLocation().getBlockZ()) {
					blackList.add(target);
					target = null;
				}
			}
		} catch (Exception e) {
		}
	}
}
