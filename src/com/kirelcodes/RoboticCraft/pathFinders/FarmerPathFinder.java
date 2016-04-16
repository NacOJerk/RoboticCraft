package com.kirelcodes.RoboticCraft.pathFinders;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.kirelcodes.RoboticCraft.RoboticCraft;
import com.kirelcodes.RoboticCraft.robot.RobotFarmer;

public class FarmerPathFinder extends BasicPathfinder {
	private RobotFarmer robot;
	private List<Location> dirty;

	public FarmerPathFinder(RobotFarmer robot) {
		this.robot = robot;
	}

	@Override
	public boolean shouldStart() {
		return robot.isFarming();
	}

	@Override
	public void onStart() {
		this.dirty = new ArrayList<>();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void updateTask() {
		for (final Block b : robot.getAllBlocks()) {
			if (dirty.contains(b.getLocation().clone()))
				continue;
			// HOEING
			if (b.getLocation().clone().subtract(0, 1, 0).getBlock().getType() == Material.GRASS
					|| b.getLocation().clone().subtract(0, 1, 0).getBlock()
							.getType() == Material.DIRT) {
				try {
					this.robot.setTargetLocation(b.getLocation().clone());
				} catch (Exception e) {
					this.robot.setFarming(false);
				}
				dirty.add(b.getLocation().clone().subtract(0, 1, 0));
				new BukkitRunnable() {

					@Override
					public void run() {
						b.getLocation().clone().subtract(0, 1, 0).getBlock()
								.setType(Material.SOIL);
						dirty.remove(b.getLocation().subtract(0, 1, 0).clone());
					}
				}.runTaskLater(RoboticCraft.getInstance(), 25);
				return;
			}

			// FARMING
			if ((b.getType() != Material.CROPS
					&& b.getType() != Material.POTATO && b.getType() != Material.CARROT))
				continue;
			if (b.getData() != 7)
				continue;
			try {
				robot.setTargetLocation(b.getLocation());
			} catch (Exception e) {
				robot.setFarming(false);
			}
			for (ItemStack drop : b.getDrops())
				robot.addItem(drop);
			switch (b.getType()) {
			case CROPS:
				robot.addItem(new ItemStack(Material.SEEDS, 2));
				break;
			case POTATO:
				robot.addItem(new ItemStack(Material.POTATO_ITEM));
				break;
			case CARROT:
				robot.addItem(new ItemStack(Material.CARROT_ITEM));
				break;
			default:
				break;
			}
			final Material type = b.getType();
			dirty.add(b.getLocation().clone());
			new BukkitRunnable() {

				@Override
				public void run() {
					dirty.remove(b.getLocation().clone());
					b.setType(Material.AIR);
					if (type == Material.CROPS
							&& robot.getInventory().contains(Material.SEEDS)) {
						for (ItemStack is : robot.getInventory().getContents()) {
							if (is.getType() != Material.SEEDS)
								continue;
							if (is.getAmount() == 1)
								robot.getInventory().remove(is);
							else
								is.setAmount(is.getAmount() - 1);
							break;
						}
						b.setType(Material.CROPS);
					} else if (type == Material.POTATO
							&& robot.getInventory().contains(
									Material.POTATO_ITEM)) {
						for (ItemStack is : robot.getInventory().getContents()) {
							if (is.getType() != Material.POTATO_ITEM)
								continue;
							if (is.getAmount() == 1)
								robot.getInventory().remove(is);
							else
								is.setAmount(is.getAmount() - 1);
							break;
						}
						b.setType(Material.POTATO);
					} else if (type == Material.CARROT
							&& robot.getInventory().contains(
									Material.CARROT_ITEM)) {
						for (ItemStack is : robot.getInventory().getContents()) {
							if (is.getType() != Material.CARROT_ITEM)
								continue;
							if (is.getAmount() == 1)
								robot.getInventory().remove(is);
							else
								is.setAmount(is.getAmount() - 1);
							break;
						}
						b.setType(Material.CARROT);
					}
				}
			}.runTaskLater(RoboticCraft.getInstance(), 25);
		}
	}

}
