package com.kirelcodes.RoboticCraft.pathFinders;

import org.bukkit.Location;

import com.kirelcodes.RoboticCraft.robot.RobotBase;

public class FollowPathfinder extends BasicPathfinder {

	private RobotBase robot;
	private int delayManager;
	private int timeout;
	private Location previous;

	public FollowPathfinder(RobotBase robot) {
		this.robot = robot;
		this.delayManager = 0;
		this.timeout = 0;
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
		if (previous.distance(robot.getLocation()) > 2)
			timeout = 0;
		if (timeout >= 3) {
			robot.setStuck(true);
		}
		if (timeout >= 5 && robot.isStuck()) {
			robot.getNavigator().teleport(robot.getFollowTarget().getLocation().clone().add(1, 0, 1));
			robot.setStuck(false);
			timeout = 0;
		}
	}

	@Override
	public void afterTask() {
		delayManager++;
		previous = robot.getLocation();
	}

}
