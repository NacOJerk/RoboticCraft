package com.kirelcodes.RoboticCraft.robot;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.kirelcodes.RoboticCraft.pathFinders.HunterPathfinder;
import com.kirelcodes.RoboticCraft.utils.ItemStackUtils;

public class RobotHunter extends RobotBase {
	private boolean isHunting;
	private Location startBlock;
	public RobotHunter(Location loc, Player p) {
		super(loc, p.getUniqueId());
		getArmorStand().setItemInHand(new ItemStack(Material.DIAMOND_SWORD));
		try {
			getArmorStand().setHelmet(ItemStackUtils.getSkullFromURL("http://textures.minecraft.net/texture/d3567ac356302583147f464af21dcb5d7ebcb22aab2847554c79b751f5bf48", "Robot"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		startBlock = loc;
	}
	@Override
	protected void addPaths() {
		super.addPaths();
		pathManager.addPath(new HunterPathfinder(this, startBlock));
	}
	
	public boolean isHunting(){
		return isHunting;
	}
	
	public void setHunting(boolean isHunting){
		this.isHunting = isHunting;
	}
}
