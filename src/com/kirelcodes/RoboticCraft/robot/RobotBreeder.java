package com.kirelcodes.RoboticCraft.robot;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.kirelcodes.RoboticCraft.pathFinders.BreederPathFinder;

public class RobotBreeder extends RobotBase{
	
	private boolean isBreading=false;
	
	public RobotBreeder(Location loc, Player p) {
		super(loc, p.getUniqueId());
		getArmorStand().setItemInHand(new ItemStack(Material.WHEAT));
		
	}
	@Override
	protected void addPaths() {
		super.addPaths();
		pathManager.addPath(new BreederPathFinder(this));
	}
	public void breed() {
		Ageable a1 = null;
		Ageable a2 = null;
		for(Entity e : getArmorStand().getNearbyEntities(10, 10, 10)) {
			if(e instanceof Ageable) {
				Ageable a = (Ageable)e;
				if(a.canBreed()) {
					if(a1==null) {
						a1=a;
					} else {
						if(a1.getType()==a.getType()){
							a2=a;
							break;
						}
					}
				}
			}
		}
		if(a1 == null || a2 == null)
			return;
		a1.setBreed(true);
		a2.setBreed(true);
	}
	
	public boolean isBreading() {
		return isBreading;
	}

	public void setBreeding(boolean b) {
		isBreading=b;
		if(b){
			breed();
		}
	}
	
	
	
	
	
}
