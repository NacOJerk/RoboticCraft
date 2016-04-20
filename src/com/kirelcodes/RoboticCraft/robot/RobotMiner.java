package com.kirelcodes.RoboticCraft.robot;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.kirelcodes.RoboticCraft.RoboticCraft;
import com.kirelcodes.RoboticCraft.configs.ConfigManager;
import com.kirelcodes.RoboticCraft.pathFinders.MinerPathfinder;
import com.kirelcodes.RoboticCraft.robot.animation.AnimationManager;
import com.kirelcodes.RoboticCraft.utils.BlockUtils;
import com.kirelcodes.RoboticCraft.utils.ItemStackUtils;

public class RobotMiner extends RobotBase {

	private Location startBlock;
	private boolean isMining;
	private boolean onDelay = false;
	private boolean isEasterEgg = false;

	public RobotMiner(Location loc, Player p) {
		super(loc, p.getUniqueId());
		getArmorStand().setItemInHand(new ItemStack(Material.DIAMOND_PICKAXE));
		try {
			getArmorStand()
					.setHelmet(
							ItemStackUtils
									.getSkullFromURL(
											"http://textures.minecraft.net/texture/b0b7d537b2616e69d4de323d8a83a0b8a61bc7e249e5ef10f9d91f6bf7b3",
											"Robot"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public RobotMiner(Location loc, UUID u) {
		super(loc, u);
		getArmorStand().setItemInHand(new ItemStack(Material.DIAMOND_PICKAXE));
		try {
			getArmorStand()
					.setHelmet(
							ItemStackUtils
									.getSkullFromURL(
											"http://textures.minecraft.net/texture/b0b7d537b2616e69d4de323d8a83a0b8a61bc7e249e5ef10f9d91f6bf7b3",
											"Robot"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void addPaths() {
		super.addPaths();
		pathManager.addPath(new MinerPathfinder(this));
	}

	public void egg() {
		isEasterEgg = true;
		getArmorStand().setHelmet(ItemStackUtils.getSkull("DrPiggy"));
		for (Entity en : getWorld()
				.getNearbyEntities(getLocation(), 10, 10, 10)) {
			if (en instanceof Player) {
				((Player) en).sendMessage(ChatColor.AQUA + "["
						+ ChatColor.LIGHT_PURPLE + "DrPiggy" + ChatColor.AQUA
						+ "]" + ChatColor.GOLD + " Oink Oink!");
			}
		}
	}

	public void mineBlock(Location loc, boolean ee) {
		if (!checkAllowed(loc))
			return;
		final Location loc2 = loc;
		onDelay = true;
		AnimationManager.mine.runAnimationCycle(getArmorStand(), 2L);
		new BukkitRunnable() {
			@Override
			public void run() {
				if (loc2.getBlock().getType() == Material.BEDROCK)
					setMining(false);
				for (ItemStack drop : loc2.getBlock().getDrops())
					addItem(drop);
				loc2.getBlock().setType(Material.AIR);
				setOnDelay(false);
			}
		}.runTaskLater(
				RoboticCraft.getInstance(),
				(ee ? 1
						: (long) (BlockUtils.getMineTime(loc.getBlock()) * 35 * ConfigManager
								.getMineSpeed())));
	}

	public boolean isMining() {
		return this.isMining;
	}

	public void setStartBlock(Location loc) {
		startBlock = loc.clone();
		setMining(true);
	}

	public void setMining(boolean mining) {
		this.isMining = mining;
		if (!mining)
			startBlock = null;
	}

	public Location getStartBlock() {
		return startBlock.clone();
	}

	public void setOnDelay(boolean delay) {
		this.onDelay = delay;
		if (!delay) {
			AnimationManager.mine.cancelTask(getArmorStand());
			deafult.setLocations(getArmorStand());
		}
	}

	public boolean onDelay() {
		return onDelay;
	}

	public boolean isEasterEgg() {
		return isEasterEgg;
	}
}
