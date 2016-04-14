package com.kirelcodes.RoboticCraft.robot;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.kirelcodes.RoboticCraft.RoboticCraft;
import com.kirelcodes.RoboticCraft.pathFinders.LumberjackPathfinder;
import com.kirelcodes.RoboticCraft.utils.BlockUtils;

public class RobotLumberjack extends RobotBase {
	private boolean isCutting;
	private boolean onDelay = false;

	public RobotLumberjack(Location loc) {
		super(loc);
		getArmorStand().setItemInHand(new ItemStack(Material.GOLD_AXE));
	}

	public void mineBlock(Location loc) {
		final Location loc2 = loc;
		onDelay = true;
		new BukkitRunnable() {
			@Override
			public void run() {
				for (ItemStack drop : loc2.getBlock().getDrops())
					addItem(drop);
				loc2.getBlock().setType(Material.AIR);
				setOnDelay(false);
			}
		}.runTaskLater(RoboticCraft.getInstance(), (long) (BlockUtils.getMineTime(loc.getBlock()) * 40));
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
