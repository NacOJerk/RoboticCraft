package com.kirelcodes.RoboticCraft.pathFinders;

import java.util.ArrayList;

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

	public HunterPathfinder(RobotHunter robot) {
		this.robot = robot;
		this.attackTimer = 0;
	}

	@Override
	public boolean shouldStart() {
		return robot.isHunting();
	}

	@Override
	public void afterTask() {
		super.afterTask();
	}

	@Override
	public void updateTask() {
		if (target == null) {
			ArrayList<Entity> nearby = new ArrayList<Entity>();
			nearby.addAll(this.robot.getWorld().getNearbyEntities(this.robot.getLocation(), 5.0, 5.0, 5.0));
			if (nearby.isEmpty())
				return;
			for (Entity e : nearby) {
				if (e instanceof Player || e instanceof Wolf || e instanceof Ocelot)
					continue;
				if (e instanceof Damageable) {
					target = e;
					break;
				}
			}
		}
		if (target == null)
			return;
		this.robot.setFollow(target);
		if (!this.robot.getWorld().getNearbyEntities(this.robot.getLocation(), 1, 1, 1).contains(target)) {
			return;
		}
		attackTimer++;
		if (attackTimer % 10 == 0) {
			if (((Damageable) this.target).getHealth() <= 7)
				attackTimer = 0;
			((Damageable) this.target).setHealth(((Damageable) this.target).getHealth() - 7);
		}
	}
}
