package com.kirelcodes.RoboticCraft.robot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import com.kirelcodes.RoboticCraft.pathFinders.ConstructerPathFinder;

public class RobotConstructer extends RobotBase {

	private Location pos1, pos2;
	private List<Location> block = new ArrayList<>();
	private boolean isConstructing;

	public RobotConstructer(Location loc, UUID owner) {
		super(loc, owner);
	}

	@Override
	protected void addPaths() {
		super.addPaths();
		pathManager.addPath(new ConstructerPathFinder(this));
	}

	public void setposOne(Location pos1) {
		this.pos1 = pos1;
	}

	public void setposTwo(Location pos2) {
		this.pos2 = pos2;
	}

	public boolean isValid() {
		int x = pos1.getBlockX() - pos2.getBlockX();
		int y = pos1.getBlockY() - pos2.getBlockY();
		int z = pos1.getBlockZ() - pos2.getBlockZ();
		if (x == 0 && y == 0)
			return true;
		if (x == 0 && z == 0)
			return true;
		if (y == 0 && z == 0)
			return true;
		return false;
	}

	public Location getposOne() {
		return pos1.clone();
	}

	public Location getposTwo() {
		return pos2.clone();
	}

	public void setConstructing(boolean isConstructing) {
		if (isConstructing) {
			int x1 = (pos1.getBlockX() > pos2.getBlockX() ? pos1.getBlockX()
					: pos2.getBlockX());
			int y1 = (pos1.getBlockY() > pos2.getBlockY() ? pos1.getBlockY()
					: pos2.getBlockY());
			int z1 = (pos1.getBlockZ() > pos2.getBlockZ() ? pos1.getBlockZ()
					: pos2.getBlockZ());
			int x2 = (pos1.getBlockX() < pos2.getBlockX() ? pos1.getBlockX()
					: pos2.getBlockX());
			int y2 = (pos1.getBlockY() < pos2.getBlockY() ? pos1.getBlockY()
					: pos2.getBlockY());
			int z2 = (pos1.getBlockZ() < pos2.getBlockZ() ? pos1.getBlockZ()
					: pos2.getBlockZ());
			pos1 = new Location(pos1.getWorld(), x1, y1, z1);
			pos2 = new Location(pos2.getWorld(), x2, y2, z2);
			for (x1 += 0; x1 <= x2; x1++)
				for (z1 += 0; z1 <= z2; z1++)
					for (y1 += 0; y1 <= x2; y1++)
						block.add(new Location(pos1.getWorld(), x1, y1, z1));
		}
		this.isConstructing = isConstructing;
	}

	public boolean isConstructing() {
		return isConstructing;
	}

	public int getBlocksCount() {
		return block.size();
	}
	
	public boolean hasBlocks(){
		for(ItemStack stack : getInventory().getContents())
			if(stack!=null)
				if(stack.getType().isBlock())
					return true;
		return false;
	}
	
	public Location getBlock(int ID){
		return block.get(ID);
	}
	
	public void placeBlock(Location loc){
		int i = -1;
		for(ItemStack stack : getInventory().getContents()){
			i++;
			if(stack!=null){
				if(stack.getType().isBlock()){
					loc.getBlock().setType(stack.getType());
					if(stack.getAmount()>1){
						stack.setAmount(stack.getAmount()-1);
					}else{
						getInventory().setItem(i, null);
					}
					return;
				}
			}
		}
	}

}
