package com.kirelcodes.RoboticCraft.pathFinders;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.block.Block;

import com.kirelcodes.RoboticCraft.robot.RobotFisher;
import com.kirelcodes.RoboticCraft.utils.ItemStackUtils;

import org.bukkit.Material;

public class FisherPathfinder extends BasicPathfinder {
	private RobotFisher robot;
	private Block target;
	private int clock;

	public FisherPathfinder(RobotFisher robot) {
		this.robot = robot;
	}

	@Override
	public boolean shouldStart() {
		return robot.isFishing();
	}

	@Override
	public void onStart() {
		clock = 0;
	}

	@Override
	public void afterTask() {
		clock++;
		if (target == null)
			return;
		if (target.getType() == Material.AIR || (target.getY() - robot.getLocation().getY()) > 5)
			target = null;
		if (target == null || robot == null)
			return;
	}

	@Override
	public void updateTask() {
		int radius = 20;
		ArrayList<Block> water = new ArrayList<Block>();
		if (target == null) {
			for (int x = robot.getLocation().getBlockX() - radius; x < robot.getLocation().getX() + radius; x++) {
				for (int y = robot.getLocation().getBlockY() - radius; y < robot.getLocation().getY() + radius; y++) {
					for (int z = robot.getLocation().getBlockZ() - radius; z < robot.getLocation().getZ()
							+ radius; z++) {
						if (robot.getWorld().getBlockAt(x, y, z).getType() == Material.WATER) {
							water.add(robot.getWorld().getBlockAt(x, y, z));
						}
					}
				}
			}
		}
		for (Block bs : water) {
			if (bs == null)
				continue;
			target = bs;
			break;
		}
		if (target == null)
			return;
		try {
			robot.setTargetLocation(target.getLocation());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if ((clock % 300) != 0)
			return;
		Random rand = new Random();
		robot.getInventory().addItem(ItemStackUtils.createItem(Material.RAW_FISH, rand.nextInt(4), "Raw Fish"));
		clock = 0;
	}
}
