package com.kirelcodes.RoboticCraft.gui.guiRobots;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.kirelcodes.RoboticCraft.gui.GUI;
import com.kirelcodes.RoboticCraft.robot.RobotLumberjack;
import com.kirelcodes.RoboticCraft.utils.ItemStackUtils;

import org.bukkit.ChatColor;

public class GUIRobotLumberjack extends GUI {

	private RobotLumberjack robot;
	private ItemStack Destroy, itemFollow, itemNoFollow, itemCut, itemNoCut, openInventory;

	public RobotLumberjack getRobot() {
		return robot;
	}

	public GUIRobotLumberjack(RobotLumberjack robot) {
		setSize(27);
		setTitle("&cLumberjack Robot GUI");
		instalizeInventory();
		this.robot = robot;
		Destroy = ItemStackUtils.createItem(Material.BARRIER, "&cDESTROY ROBOT");
		itemFollow = ItemStackUtils.createItem(Material.COMPASS, "&aFollow");
		itemNoFollow = ItemStackUtils.createItem(Material.COMPASS, "&cStop Follow");
		itemCut = ItemStackUtils.createItem(Material.GOLD_AXE, "&aCut");
		itemNoCut = ItemStackUtils.createItem(Material.GOLD_AXE, "&cStop Cut");
		openInventory = ItemStackUtils.createItem(Material.CHEST, "&cOpen Robot's Inventory");
		gettGUIAction().add(new GUIAction(itemFollow) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotLumberjack) gui).follow(player);
			}
		});
		gettGUIAction().add(new GUIAction(Destroy) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotLumberjack) gui).getRobot().destroy();
				player.closeInventory();
			}
		});
		gettGUIAction().add(new GUIAction(openInventory) {

			@Override
			public void actionNow(GUI gui, Player player) {
				player.closeInventory();
				player.openInventory(((GUIRobotLumberjack) gui).getRobot().getInventory());
			}
		});
		gettGUIAction().add(new GUIAction(itemNoFollow) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotLumberjack) gui).noFollow(player);
			}
		});
		gettGUIAction().add(new GUIAction(itemCut) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotLumberjack) gui).Cut();
			}
		});
		gettGUIAction().add(new GUIAction(itemNoCut) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotLumberjack) gui).noCut();
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
		getInventory().setItem(14, (robot.isCutting()) ? itemNoCut : itemCut);
		getInventory().setItem(12, (robot.isFollowing()) ? itemNoFollow : itemFollow);
	}

	public void follow(Entity p) {
		robot.setFollow(p);
		if (robot.isCutting())
			noCut();
		getInventory().setItem(12, itemNoFollow);
	}

	public void noFollow(Entity p) {
		robot.cancelFollow();
		getInventory().setItem(12, itemFollow);
	}

	public void Cut() {
		robot.setCutting(true);
		if (robot.isFollowing())
			noFollow(robot.getFollowTarget());
		getInventory().setItem(14, itemNoCut);
	}

	public void noCut() {
		robot.setCutting(false);
		getInventory().setItem(14, itemCut);
	}

}
