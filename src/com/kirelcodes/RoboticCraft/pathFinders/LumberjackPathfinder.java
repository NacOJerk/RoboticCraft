package com.kirelcodes.RoboticCraft.pathFinders;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import com.kirelcodes.RoboticCraft.RoboticCraft;
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
		if (target.getType() == Material.AIR
				|| (target.getY() - robot.getLocation().getY()) > 5)
			target = null;
		if (target == null || robot == null)
			return;
		if ((target.getY() - robot.getLocation().getY()) >= 5)
			blackList.add(target);
		blocks.clear();
	}

	private boolean contains(Location loc) {
		boolean nope = false;
		for (Block b : blackList) {
			if (b.getLocation().getBlockX() == loc.getBlockX()
					&& b.getLocation().getBlockY() == loc.getBlockY()
					&& b.getLocation().getBlockZ() == loc.getBlockZ()) {
				nope = true;
				break;
			}
		}
		return nope;
	}

	@Override
	public void updateTask() {
		new BukkitRunnable() {

			@Override
			public void run() {
				if (target == null)
					blocks.addAll(robot.getNearbyBlocks(10));
			}
		}.runTaskAsynchronously(RoboticCraft.getInstance());
		for (Block b : blocks) {
			if (b == null)
				continue;
			if (contains(b.getLocation()))
				continue;
			if (b.getType() == Material.LOG
					|| b.getType() == Material.LOG_2) {
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
			if (target.getLocation().distance(robot.getLocation()) <= 5) {
				robot.mineBlock(target);
				target = null;
			} else if (target.getLocation().getBlockX() == robot.getLocation()
					.getBlockX()) {
				if (target.getLocation().getBlockZ() == robot.getLocation()
						.getBlockZ()) {
					blackList.add(target);
					target = null;
				}
			}
		} catch (Exception e) {
		}
	}
}
