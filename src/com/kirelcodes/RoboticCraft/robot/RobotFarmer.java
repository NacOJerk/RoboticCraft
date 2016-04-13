package com.kirelcodes.RoboticCraft.robot;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import com.kirelcodes.RoboticCraft.pathFinders.FarmerPathFinder;

public class RobotFarmer extends RobotBase {
	private Location mark1, mark2;
	private boolean isFarming;

	public RobotFarmer(Location loc) {
		super(loc);
		getArmorStand().setItemInHand(new ItemStack(Material.DIAMOND_HOE));
	}

	@Override
	protected void addPaths() {
		super.addPaths();
		pathManager.addPath(new FarmerPathFinder(this));
	}
	
	
	
	public void setMarkOne(Location mark1) {
		this.mark1 = mark1;
	}

	public void setMarkTwo(Location mark2) {
		this.mark2 = mark2;
	}

	public Location getMarkOne() {
		return mark1.clone();
	}

	public Location getMarkTwo() {
		return mark2.clone();
	}

	public void setFarming(boolean isFarming) {
		this.isFarming = isFarming;
	}

	public boolean isFarming() {
		return isFarming;
	}

	private ArrayList<Integer> getXList(){
		ArrayList<Integer> arrayL = new ArrayList<>();
		int xMark1 = getMarkOne().getBlockX();
		int xMark2 = getMarkTwo().getBlockX();
		if(xMark1 > xMark2){
			for(int i = xMark2 ; i <= xMark1 ; i++){
				arrayL.add(i);
			}
		}else{
			for(int i = xMark1 ; i <= xMark2 ; i++){
				arrayL.add(i);
			}
		}
		return arrayL;
	}
	
	private ArrayList<Integer> getZList(){
		ArrayList<Integer> arrayL = new ArrayList<>();
		int zMark1 = getMarkOne().getBlockZ();
		int zMark2 = getMarkTwo().getBlockZ();
		if(zMark1 > zMark2){
			for(int i = zMark2 ; i <= zMark1 ; i++){
				arrayL.add(i);
			}
		}else{
			for(int i = zMark1 ; i <= zMark2 ; i++){
				arrayL.add(i);
			}
		}		
		return arrayL;
	}

	
	
	public List<Block> getAllBlocks(){
		ArrayList<Block> blocks = new ArrayList<>();
		for(int x : getXList()){
			for(int z : getZList()){
				blocks.add(new Location(getMarkOne().getWorld(), x, getMarkOne().getY(), z).getBlock());
				blocks.add(new Location(getMarkOne().getWorld(), x, getMarkOne().getY() - 1, z).getBlock());
			}
		}
		return blocks;
	}
	
	
	
	
	
}
