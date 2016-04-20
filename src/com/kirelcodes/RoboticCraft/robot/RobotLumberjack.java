package com.kirelcodes.RoboticCraft.robot;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.kirelcodes.RoboticCraft.RoboticCraft;
import com.kirelcodes.RoboticCraft.configs.ConfigManager;
import com.kirelcodes.RoboticCraft.pathFinders.LumberjackPathfinder;

public class RobotLumberjack extends RobotBase {
	private boolean isCutting;
	private boolean onDelay = false;

	public RobotLumberjack(Location loc, Player p) {
		super(loc, p.getUniqueId());
		getArmorStand().setItemInHand(new ItemStack(Material.DIAMOND_AXE));
	}
	public RobotLumberjack(Location loc, UUID u) {
		super(loc, u);
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
		}.runTaskLater(RoboticCraft.getInstance(), (long) (18 * ConfigManager.getLogSpeed()));
	}

	public void setOnDelay(boolean onDelay) {
		this.onDelay = onDelay;
	}
	public boolean isOnDelay(){
		return onDelay;
	}
	@Override
	protected void addPaths() {
		super.addPaths();
		pathManager.addPath(new LumberjackPathfinder(this));
	}

	public boolean isCutting() {
		return isCutting;
	}

	public void setCutting(boolean isCutting) {
		this.isCutting = isCutting;
	}

}
