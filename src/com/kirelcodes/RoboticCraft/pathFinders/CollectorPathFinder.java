package com.kirelcodes.RoboticCraft.pathFinders;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;

import com.kirelcodes.RoboticCraft.robot.RobotCollector;

public class CollectorPathFinder extends BasicPathfinder {

	private RobotCollector robot;
	private Entity target;

	public CollectorPathFinder(RobotCollector robot) {
		this.robot = robot;
	}

	@Override
	public boolean shouldStart() {
		return robot.isCollecting();
	}

	@Override
	public void updateTask() {
		if (target == null || target.isDead()) {
			target = null;
			for (Entity en : robot.getWorld().getNearbyEntities(
					robot.getLocation(), 8, 8, 8)) {
				if (en instanceof Item) {
					Item item = (Item) en;
					if (item.isOnGround()) {
						target = en;
						try {
							robot.setTargetLocation(en.getLocation());
						} catch (Exception e) {
							robot.setCollecting(false);
						}
					}
				}
			}
			if(target == null){
				try {
					robot.setTargetLocation(robot.getStartBlock());
				} catch (Exception e) {robot.setCollecting(false);}
			}
		} else {
			for (Entity en : robot.getWorld().getNearbyEntities(
					robot.getLocation(), 1, 1, 1)) {
				if (en instanceof Item) {
					if (en == target) {
						Item item = (Item) en;
						robot.addItem(item.getItemStack());
						en.remove();
					}
				}
			}
		}
	}
}
