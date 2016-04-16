package com.kirelcodes.RoboticCraft.pathFinders;

import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import com.kirelcodes.RoboticCraft.RoboticCraft;
import com.kirelcodes.RoboticCraft.robot.RobotBreeder;

public class BreederPathFinder extends BasicPathfinder {
	
	private double nearX=4;
	private double nearY=4;
	private double nearZ=4;
	private int clock;
	private RobotBreeder robot;

	public BreederPathFinder(RobotBreeder robot) {
		this.robot=robot;
	}
	@Override
	public boolean shouldStart() {
		return robot.isBreading();
	}
	@Override
	public void onStart() {
		clock = 0;
	}
	@Override
	public void afterTask() {
		clock++;
	}

	@Override
	public void updateTask() {
		/**
		if((clock % 600) != 0)
			return;
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
		**/
		Ageable a1=null;
		double distance=0;
		for(Entity e : robot.getArmorStand().getNearbyEntities(nearX, nearY, nearZ)) {
			if(e instanceof Ageable&&((Ageable)e).canBreed()) {
				a1=(Ageable)e;
				try {
					robot.setTargetLocation(e.getLocation());
				} catch (Exception e1) {e1.printStackTrace();}
				distance=e.getLocation().distance(robot.getLocation());
				final Ageable age=a1;
				new BukkitRunnable() {
					
					@Override
					public void run() {
						for(Entity a : robot.getArmorStand().getNearbyEntities(nearX, nearY, nearZ)) {
							if(a instanceof Ageable&&((Ageable)a).canBreed()&&a.getType()==age.getType()) {
								((Ageable)a).setBreed(true);
								age.setBreed(true);
							}
						}
						
					}
				}.runTaskLater(RoboticCraft.getInstance(), (long) (20*distance));
				break;
			}
		}
	}

}
