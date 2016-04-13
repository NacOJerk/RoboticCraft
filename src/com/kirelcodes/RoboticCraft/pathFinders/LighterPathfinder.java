package com.kirelcodes.RoboticCraft.pathFinders;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.kirelcodes.RoboticCraft.robot.RobotBase;

public class LighterPathfinder extends BasicPathfinder {

	private RobotBase robot;
	private int delayManager;
	private int timeout;
	private Location previous;
	// This is the simple follower code. Now I add a timer to create the
	// torches.
	private int torchTimer;

	public LighterPathfinder(RobotBase robot) {
		this.robot = robot;
		this.delayManager = 0;
		this.timeout = 0;
		this.torchTimer = 0;
		this.robot.getInventory().addItem(new ItemStack(Material.TORCH, 64));
	}

	@Override
	public boolean shouldStart() {
		return this.robot.isFollowing();
	}

	@Override
	public void onStart() {
		previous = robot.getLocation();
	}

	@Override
	public void updateTask() {
		if ((delayManager % 5) != 0)
			return;
		try {
			robot.setTargetLocation(robot.getFollowTarget().getLocation()
					.clone().add(1, 0, 1));
		} catch (Exception e) {
			robot.cancelFollow();
		}
		if ((delayManager % 20) != 0
				&& robot.getLocation().distance(previous) <= 1
				&& robot.getFollowTarget().getLocation()
						.distance(robot.getLocation()) > 5) {
			timeout++;
		}
		if ((delayManager % 20) != 0) {
			// This happens every one second.
			torchTimer++;
		}
		if (previous.distance(robot.getLocation()) > 2)
			timeout = 0;
		if (timeout >= 3) {
			robot.setStuck(true);
		}
		if (timeout >= 5 && robot.isStuck()) {
			robot.getNavigator().teleport(
					robot.getFollowTarget().getLocation().clone().add(1, 0, 1));
			robot.setStuck(false);
			timeout = 0;
		}

		// Now we want to check if the timer reaches 3
		if (torchTimer >= 3) {
			if(this.robot.isStuck())
				return;
			// Place the torches
			if (this.robot.getInventory().contains(Material.TORCH))
				if (this.robot.getLocation().getBlock().getType() == Material.AIR) {
					this.robot.getLocation().getBlock().setType(Material.TORCH);
					this.robot.getInventory().remove(new ItemStack(Material.TORCH, 1));
				}
			torchTimer = 0;
		}
	}

	@Override
	public void afterTask() {
		delayManager++;
		previous = robot.getLocation();
	}

}