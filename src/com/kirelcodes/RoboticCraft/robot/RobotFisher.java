package com.kirelcodes.RoboticCraft.robot;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.kirelcodes.RoboticCraft.pathFinders.FisherPathfinder;

public class RobotFisher extends RobotBase {
	private boolean isFishing;

	public RobotFisher(Location loc, Player p) {
		super(loc, p.getUniqueId());
		getArmorStand().setItemInHand(new ItemStack(Material.FISHING_ROD));
	}
	@Override
	protected void addPaths() {
		super.addPaths();
		pathManager.addPath(new FisherPathfinder(this));
	}
	public boolean isFishing() {
		return isFishing;
	}

	public void setFishing(boolean isFishing) {
		this.isFishing = isFishing;
	}

}
