package com.kirelcodes.RoboticCraft.pathFinders;

import com.kirelcodes.RoboticCraft.robot.RobotBase;

public class FollowPathfinder extends BasicPathfinder{

	private RobotBase robot;
	private int delayManager;
	
	public FollowPathfinder(RobotBase robot) {
		this.robot = robot;
		this.delayManager = 0;
	}
	
	@Override
	public boolean shouldStart() {
		return this.robot.isFollowing();
	}

	@Override
	public void updateTask() {
		if((delayManager%5)!=0)
			return;
		
	}
	
	@Override
	public void afterTask() {
		delayManager++;
	}

}
