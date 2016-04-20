package com.kirelcodes.RoboticCraft.pathFinders;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import com.kirelcodes.RoboticCraft.configs.ConfigManager;
import com.kirelcodes.RoboticCraft.robot.RobotFisher;
import org.bukkit.Material;

public class FisherPathfinder extends BasicPathfinder {
	private RobotFisher robot;
	private Block target;
	private int clock;
	private double time = ConfigManager.getFishTime();

	public FisherPathfinder(RobotFisher robot) {
		this.robot = robot;
	}

	@Override
	public boolean shouldStart() {
		return robot.isFishing();
	}

	@Override
	public void onStart() {
		clock = 1;
		target = null;
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
		int radius = 15;
		ArrayList<Block> water = new ArrayList<Block>();
		if (target == null) {
			int x = robot.getLocation().getBlockX();
			int z = robot.getLocation().getBlockZ();
			for (int y = radius; y > -radius; y--) {
				water.addAll(robot.getNearbyBlocks(
						robot.getWorld().getBlockAt(x, robot.getLocation().getBlockY() + y, z).getLocation(), radius));
			}
		}
		for (Block bs : water) {
			if (bs == null)
				continue;
			if (bs.getType() == Material.WATER || bs.getType() == Material.STATIONARY_WATER) {
				target = bs;
				try {
					robot.setTargetLocation(bs.getLocation());
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
		}
		if (target == null)
			return;
		if (!(target.getType() == Material.WATER || target.getType() == Material.STATIONARY_WATER))
			return;
		try {
			robot.setTargetLocation(target.getLocation());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (target.getLocation().distance(robot.getLocation()) > 4)
			return;
		if ((clock % time) != 0)
			return;
		Random rand = new Random();
		short s = (short) rand.nextInt(4);
		ItemStack fish = new ItemStack(Material.RAW_FISH, 1, s);
		robot.getInventory().addItem(fish);
		clock = 0;
	}
}
