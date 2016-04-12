package com.kirelcodes.RoboticCraft.robot;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.kirelcodes.RoboticCraft.RoboticCraft;
import com.kirelcodes.RoboticCraft.pathFinders.MinerPathfinder;
import com.kirelcodes.RoboticCraft.utils.BlockUtils;

public class RobotMiner extends RobotBase {

	private Location startBlock;
	private boolean isMining;
	private boolean onDelay= false;
	public RobotMiner(Location loc) {
		super(loc);
		getArmoStand().setItemInHand(new ItemStack(Material.DIAMOND_PICKAXE));
	}
	
	@Override
	protected void addPaths() {
		pathManager.addPath(new MinerPathfinder(this));
	}
	
	public void mineBlock(Location loc){
		final Location loc2 = loc;
		onDelay = true;
		new BukkitRunnable() {
			@Override
			public void run() {
				if(loc2.getBlock().getType() == Material.BEDROCK)
					setMining(false);
				for(ItemStack drop : loc2.getBlock().getDrops())
					addItem(drop);
				loc2.getBlock().setType(Material.AIR);
				setOnDelay(false);
			}
		}.runTaskLater(RoboticCraft.getInstance(), (long) (BlockUtils.getMineTime(loc.getBlock())*20));
	}
	
	public boolean isMining(){
		return this.isMining;
	}
	
	public void setStartBlock(Location loc){
		startBlock = loc.clone();
		setMining(true);
	}
	
	public void setMining(boolean mining){
		this.isMining = mining;
	}
	
	public Location getStartBlock(){
		return startBlock.clone();
	}
	public void setOnDelay(boolean delay){
		this.onDelay = delay;
	}
	public boolean onDelay(){
		return onDelay;
	}
}
