package com.kirelcodes.RoboticCraft.pathFinders;

import org.bukkit.Location;
import org.bukkit.block.Block;

import com.kirelcodes.RoboticCraft.robot.RobotConstructer;

public class ConstructerPathFinder extends BasicPathfinder {

	private RobotConstructer robot;
	private int buffer;
	private Location target;

	public ConstructerPathFinder(RobotConstructer robot) {
		this.robot = robot;
	}

	@Override
	public void onStart() {
		buffer = 0;
	}

	@Override
	public boolean shouldStart() {
		return this.robot.isConstructing() && this.robot.isValid()
				&& this.robot.hasBlocks();
	}

	@Override
	public boolean keepWorking() {
		return shouldStart();
	}

	@Override
	public void updateTask() {
		if (buffer <= this.robot.getBlocksCount())
			if (this.robot.hasBlocks()) {
				if (target == null) {
					target = this.robot.getBlock(buffer);
					buffer++;
					try {
						this.robot.setTargetLocation(target);
					} catch (Exception e) {
						this.robot.setConstructing(false);
					}
				} else {
					for (Block b : this.robot.getNearbyBlocks(2)) {
						if (b.getLocation() == target) {
							this.robot.placeBlock(target);
							target = null;
						}
					}
				}
			}
	}

}
