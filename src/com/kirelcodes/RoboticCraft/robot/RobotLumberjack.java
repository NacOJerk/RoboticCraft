package com.kirelcodes.RoboticCraft.robot;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.kirelcodes.RoboticCraft.RoboticCraft;
import com.kirelcodes.RoboticCraft.pathFinders.LumberjackPathfinder;

public class RobotLumberjack extends RobotBase {
	private boolean isCutting;
	@SuppressWarnings("unused")
	private boolean onDelay = false;

	public RobotLumberjack(Location loc) {
		super(loc);
		getArmorStand().setItemInHand(new ItemStack(Material.DIAMOND_AXE));
	}

	public void mineBlock(Block b) {
		final Block b2 = b;
		onDelay = true;
		new BukkitRunnable() {
			@Override
			public void run() {
				for (ItemStack drop : b2.getDrops())
					addItem(drop);
				b2.setType(Material.AIR);
				setOnDelay(false);
			}
		}.runTaskLater(RoboticCraft.getInstance(), (long) 10);
	}

	public void setOnDelay(boolean onDelay) {
		this.onDelay = onDelay;
	}

	@Override
	protected void addPaths() {
		pathManager.addPath(new LumberjackPathfinder(this));
	}

	public boolean isCutting() {
		return isCutting;
	}

	public void setCutting(boolean isCutting) {
		this.isCutting = isCutting;
	}

}
