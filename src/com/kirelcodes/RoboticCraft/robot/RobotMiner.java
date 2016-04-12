package com.kirelcodes.RoboticCraft.robot;

import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import com.kirelcodes.RoboticCraft.RoboticCraft;
import com.kirelcodes.RoboticCraft.utils.BlockUtils;

public class RobotMiner extends RobotBase {

	private Location startBlock;
	private boolean isMining;
	
	public RobotMiner(Location loc) {
		super(loc);
	}
	
	public void mineBlock(final Location loc){
		new BukkitRunnable() {
			
			@Override
			public void run() {
				loc.getBlock().breakNaturally();				
				System.out.println(loc.getBlock().getType().name());
			}
		}.runTaskLaterAsynchronously(RoboticCraft.getInstance(), (long) (BlockUtils.getMineTime(loc.getBlock())*20));
	}
	
	public boolean isMining(){
		return this.isMining;
	}
	
	public void setStartBlock(Location loc){
		startBlock = loc.clone();
	}
	
	public void setMining(boolean mining){
		this.isMining = mining;
	}
	
	public Location getStartBlock(){
		return startBlock.clone();
	}

}
