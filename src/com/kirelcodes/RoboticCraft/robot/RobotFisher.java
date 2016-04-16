package com.kirelcodes.RoboticCraft.robot;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class RobotFisher extends RobotBase {
	private boolean isFishing;

	public RobotFisher(Location loc) {
		super(loc);
		getArmorStand().setItemInHand(new ItemStack(Material.FISHING_ROD));
	}

	public boolean isFishing() {
		return isFishing;
	}

	public void setFishing(boolean isFishing) {
		this.isFishing = isFishing;
	}

}
