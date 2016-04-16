package com.kirelcodes.RoboticCraft.pathFinders;

import java.util.List;

import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;

import com.kirelcodes.RoboticCraft.robot.RobotBreader;

public class BreaderPathFinder extends BasicPathfinder {
	
	private double nearX=4;
	private double nearY=4;
	private double nearZ=4;
	
	private RobotBreader robot;

	public BreaderPathFinder(RobotBreader robot) {
		this.robot=robot;
	}
	@Override
	public boolean shouldStart() {

		return robot.isBreading();
	}
	

	@Override
	public synchronized void updateTask() {
		Ageable a1 = null;
		for(Entity e : robot.getArmorStand().getNearbyEntities(nearX, nearY, nearZ)) {
			if(e instanceof Ageable) {
				Ageable a = (Ageable)e;
				if(a.canBreed()) {
					if(a1==null) {
						a1=a;
						try {
							robot.setTargetLocation(a.getLocation());
						} catch (Exception e1) {e1.printStackTrace();}
						break;
					}
				}
			}
		}
		while(true) {
			final List<Entity> l = robot.getArmorStand().getNearbyEntities(nearX, nearY, nearZ);
			if(l.contains(a1)) {
				for(Entity a : l) {
					if(a instanceof Ageable&&a!=a1&&a.getType()==a1.getType()&&((Ageable)a).canBreed()) {
						a1.setBreed(true);
						((Ageable)a).setBreed(true);
						break;
					}
				}
			}
		}
	}

}
