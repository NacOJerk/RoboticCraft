package com.kirelcodes.RoboticCraft.robot;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.kirelcodes.RoboticCraft.pathFinders.HunterPathfinder;

public class RobotHunter extends RobotBase {
	private boolean isHunting;
	public RobotHunter(Location loc) {
		super(loc);
		getArmorStand().setItemInHand(new ItemStack(Material.DIAMOND_SWORD));
	}
	@Override
	protected void addPaths() {
		pathManager.addPath(new HunterPathfinder(this));
	}
	
	public boolean isHunting(){
		return isHunting;
	}
	
	public void setHunting(boolean isHunting){
		this.isHunting = isHunting;
	}
}
