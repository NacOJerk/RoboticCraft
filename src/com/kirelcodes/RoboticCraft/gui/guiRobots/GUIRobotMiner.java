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
	private ItemStack itemFollow, itemNoFollow, itemMine, itemNoMine, openInventory;

	public GUIRobotMiner(RobotMiner robot) {
		setSize(27);
		setTitle("&cMiner Robot GUI");
		instalizeInventory();
		this.robot = robot;
		itemFollow = ItemStackUtils.createItem(Material.COMPASS, "&aFollow");
		itemNoFollow = ItemStackUtils.createItem(Material.COMPASS, "&cStop Follow");
		itemMine = ItemStackUtils.createItem(Material.DIAMOND_PICKAXE, "&aMine");
		itemNoMine = ItemStackUtils.createItem(Material.DIAMOND_PICKAXE, "&cStop Mine");
		itemNoMine = ItemStackUtils.createItem(Material.CHEST, "&cOpen Robot's Inventory");
		gettGUIAction().add(new GUIAction(itemFollow) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotMiner) gui).follow(player);
			}
		});
		gettGUIAction().add(new GUIAction(openInventory) {

			@Override
			public void actionNow(GUI gui, Player player) {
				player.closeInventory();
				player.openInventory(robot.getInventory());
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
		getInventory().setItem(13, openInventory);
		getInventory().setItem(14, (robot.isMining()) ? itemMine : itemNoMine);
		getInventory().setItem(12, (robot.isFollowing()) ? itemNoFollow : itemFollow);
	}

	public void follow(Entity p) {
		robot.setFollow(p);
		getInventory().setItem(12, itemNoFollow);
	}

	public void noFollow(Entity p) {
		robot.cancelFollow();
		getInventory().setItem(12, itemFollow);
	}

	public void Mine() {
		robot.setMining(true);
		getInventory().setItem(14, itemNoMine);
	}

	public void noMine() {
		robot.setMining(false);
		getInventory().setItem(14, itemMine);
	}

}
