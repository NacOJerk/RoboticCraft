package com.kirelcodes.RoboticCraft.pathFinders;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

import com.kirelcodes.RoboticCraft.robot.RobotHunter;

public class HunterPathfinder extends BasicPathfinder {
	private RobotHunter robot;
	private Entity target;
	private int attackTimer;
	private Location startBlock;

	public HunterPathfinder(RobotHunter robot, Location startBlock) {
		this.robot = robot;
		this.startBlock = startBlock;
	}

	@Override
	public boolean shouldStart() {
		return robot.isHunting();
	}

	@Override
	public void afterTask() {
		if (target == null || target.isDead()
				|| target.getLocation().distance(robot.getLocation()) > 25)
			target = null;
	}

	@Override
	public void onStart() {
		this.attackTimer = 0;
	}
	@Override
	public void updateTask() {
		boolean found = false;
		if (target == null) {
			ArrayList<Entity> nearby = new ArrayList<Entity>();
			nearby.addAll(this.robot.getWorld().getNearbyEntities(
					this.robot.getLocation(), 10.0, 10.0, 10.0));
			if (nearby.isEmpty())
				return;
			for (Entity e : nearby) {
				if (e instanceof Player || e instanceof Wolf
						|| e instanceof Ocelot || e.equals(robot.getNavigator()) || e.equals(robot.getArmorStand()))
					continue;
				if (e instanceof Damageable) {
					target = e;
					found = true;
					break;
				}
			}
			if(!found){
				try {
					this.robot.setTargetLocation(startBlock);
				} catch (Exception e) {	this.robot.setHunting(false); }
			}
		}
		if (target == null)
			return;
		try {
			robot.setTargetLocation(target.getLocation().clone().add(0, 0, 0));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!this.robot.getWorld()
				.getNearbyEntities(this.robot.getLocation(), 2, 2, 2)
				.contains(target)) {
			return;
		}
		attackTimer++;
		if (attackTimer % 15 == 0) {
			if (((Damageable) this.target).getHealth() <= 7)
				attackTimer = 0;
			/*
			((Damageable) this.target).setHealth(((((Damageable) this.target)
					.getHealth() - 7) <= 0) ? 0 : ((Damageable) this.target)
					.getHealth() - 7);*/
			((Damageable) this.target).damage(7 , robot.getNavigator());
			if(((Damageable) this.target).getHealth() == 0 || ((Damageable) this.target).isDead()){
				target = null;
			}
		}
	}
}
