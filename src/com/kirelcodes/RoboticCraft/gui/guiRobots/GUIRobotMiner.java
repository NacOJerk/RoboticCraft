package com.kirelcodes.RoboticCraft.gui.guiRobots;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.kirelcodes.RoboticCraft.gui.GUI;
import com.kirelcodes.RoboticCraft.robot.RobotMiner;
import com.kirelcodes.RoboticCraft.utils.ItemStackUtils;

import net.md_5.bungee.api.ChatColor;

public class GUIRobotMiner extends GUI {

	private RobotMiner robot;
	private ItemStack Destroy, itemFollow, itemNoFollow, itemMine, itemNoMine, openInventory;

	public RobotMiner getRobot() {
		return robot;
	}

	public GUIRobotMiner(RobotMiner robot) {
		setSize(27);
		setTitle("&cMiner Robot GUI");
		instalizeInventory();
		this.robot = robot;
		Destroy = ItemStackUtils.createItem(Material.BARRIER, "&cDESTROY ROBOT");
		itemFollow = ItemStackUtils.createItem(Material.COMPASS, "&aFollow");
		itemNoFollow = ItemStackUtils.createItem(Material.COMPASS, "&cStop Follow");
		itemMine = ItemStackUtils.createItem(Material.DIAMOND_PICKAXE, "&aMine");
		itemNoMine = ItemStackUtils.createItem(Material.DIAMOND_PICKAXE, "&cStop Mine");
		openInventory = ItemStackUtils.createItem(Material.CHEST, "&cOpen Robot's Inventory");
		gettGUIAction().add(new GUIAction(itemFollow) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotMiner) gui).follow(player);
			}
		});
		gettGUIAction().add(new GUIAction(Destroy) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotMiner) gui).getRobot().destroy();
				player.closeInventory();
			}
		});
		gettGUIAction().add(new GUIAction(openInventory) {

			@Override
			public void actionNow(GUI gui, Player player) {
				player.closeInventory();
				player.openInventory(((GUIRobotMiner) gui).getRobot().getInventory());
			}
		});
		gettGUIAction().add(new GUIAction(itemNoFollow) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotMiner) gui).noFollow(player);
			}
		});
		gettGUIAction().add(new GUIAction(itemMine) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotMiner) gui).Mine();
			}
		});
		gettGUIAction().add(new GUIAction(itemNoMine) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotMiner) gui).noMine();
			}
		});
		for (int i = 0; i < 27; i++) {
			ItemStack item = ItemStackUtils.createItem(Material.STAINED_GLASS_PANE, new Random().nextInt(16),
					ChatColor.BLACK + "DONT CLICK ME");
			getInventory().setItem(i, item);
			gettGUIAction().add(new GUIAction(item) {

				@Override
				public void actionNow(GUI gui, Player player) {

				}
			});
		}
		getInventory().setItem(3, Destroy);
		getInventory().setItem(13, openInventory);
		getInventory().setItem(14, (robot.isMining()) ? itemNoMine : itemMine);
		getInventory().setItem(12, (robot.isFollowing()) ? itemNoFollow : itemFollow);
	}

	public void follow(Entity p) {
		robot.setFollow(p);
		if (robot.isMining())
			noMine();
		getInventory().setItem(12, itemNoFollow);
	}

	public void noFollow(Entity p) {
		robot.cancelFollow();
		getInventory().setItem(12, itemFollow);
	}

	public void Mine() {
		robot.setStartBlock(robot.getLocation());
		robot.setMining(true);
		if (robot.isFollowing())
			noFollow(robot.getFollowTarget());
		getInventory().setItem(14, itemNoMine);
	}

	public void noMine() {
		robot.setMining(false);
		getInventory().setItem(14, itemMine);
	}

}
