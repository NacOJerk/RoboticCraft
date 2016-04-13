package com.kirelcodes.RoboticCraft.robot;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.kirelcodes.RoboticCraft.RoboticCraft;
import com.kirelcodes.RoboticCraft.pathFinders.MinerPathfinder;
import com.kirelcodes.RoboticCraft.utils.BlockUtils;
import com.kirelcodes.RoboticCraft.utils.ItemStackUtils;

public class RobotMiner extends RobotBase {

	private Location startBlock;
	private boolean isMining;
	private boolean onDelay= false;
	public RobotMiner(Location loc) {
		super(loc);
		getArmorStand().setItemInHand(new ItemStack(Material.DIAMOND_PICKAXE));
		try {
			getArmorStand().setHelmet(ItemStackUtils.getSkullFromURL("http://textures.minecraft.net/texture/b0b7d537b2616e69d4de323d8a83a0b8a61bc7e249e5ef10f9d91f6bf7b3", "Robot"));
		} catch (Exception e) {
			e.printStackTrace();
		}

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
