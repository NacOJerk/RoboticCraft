package com.kirelcodes.RoboticCraft.pathFinders;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import com.kirelcodes.RoboticCraft.robot.RobotFarmer;

public class FarmerPathFinder extends BasicPathfinder{
	private RobotFarmer robot;
	public FarmerPathFinder(RobotFarmer robot) {
		this.robot = robot;
	}
	
	
	@Override
	public boolean shouldStart() {
		return robot.isFarming();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void updateTask() {
		for(Block b : robot.getAllBlocks()){
			if(b.getType() != Material.CROPS)
				continue;
			if(b.getData() != 7)
				continue;
			try {
				robot.setTargetLocation(b.getLocation());
			} catch (Exception e) {
				robot.setFarming(false);
			}
			for(ItemStack drop : b.getDrops())
				robot.addItem(drop);
			robot.addItem(new ItemStack(Material.SEEDS));
			b.setType(Material.AIR);
			if(!robot.getInventory().contains(Material.SEEDS))
				continue;
			Material type = null;
			for(ItemStack is : robot.getInventory().getContents()){
				if(is.getType() != Material.SEEDS)
					continue;
				if(is.getAmount() == 1)
					robot.getInventory().remove(is);
				else
					is.setAmount(is.getAmount() - 1);
				break;
			}
			b.setType(Material.CROPS);
		}
	}

}
