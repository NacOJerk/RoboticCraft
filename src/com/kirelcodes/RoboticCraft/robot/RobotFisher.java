package com.kirelcodes.RoboticCraft.robot;

import java.util.UUID;

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
	public RobotFisher(Location loc, UUID u) {
		super(loc, u);
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
