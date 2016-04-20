package com.kirelcodes.RoboticCraft.gui.guiRobots;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.kirelcodes.RoboticCraft.gui.GUI;
import com.kirelcodes.RoboticCraft.robot.RobotHunter;
import com.kirelcodes.RoboticCraft.utils.ItemStackUtils;

import org.bukkit.ChatColor;

public class GUIRobotHunter extends GUI {
	private RobotHunter robot;
	private ItemStack itemFollow, itemNoFollow, itemHunt, itemNoHunt, openInventory, Destroy;

	public RobotHunter getRobot() {
		return robot;
	}

	public GUIRobotHunter(RobotHunter robot) {
		setSize(27);
		setTitle("&cHunter Robot GUI");
		instalizeInventory();
		this.robot = robot;
		itemFollow = ItemStackUtils.createItem(Material.COMPASS, "&aFollow");
		itemNoFollow = ItemStackUtils.createItem(Material.COMPASS, "&cStop Follow");
		itemHunt = ItemStackUtils.createItem(Material.DIAMOND_SWORD, "&aHunt");
		itemNoHunt = ItemStackUtils.createItem(Material.DIAMOND_SWORD, "&cStop Hunt");
		openInventory = ItemStackUtils.createItem(Material.CHEST, "&cOpen Robot's Inventory");
		Destroy = ItemStackUtils.createItem(Material.BARRIER, "&cDESTROY ROBOT");
		gettGUIAction().add(new GUIAction(itemFollow) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotHunter) gui).follow(player);
			}
		});
		gettGUIAction().add(new GUIAction(Destroy) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotHunter) gui).getRobot().destroy();
				player.closeInventory();
			}
		});
		gettGUIAction().add(new GUIAction(openInventory) {

			@Override
			public void actionNow(GUI gui, Player player) {
				player.closeInventory();
				player.openInventory(((GUIRobotHunter) gui).getRobot().getInventory());
			}
		});
		gettGUIAction().add(new GUIAction(itemNoFollow) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotHunter) gui).noFollow(player);
			}
		});
		gettGUIAction().add(new GUIAction(itemHunt) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotHunter) gui).Hunt();
			}
		});
		gettGUIAction().add(new GUIAction(itemNoHunt) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotHunter) gui).noHunt();
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
		getInventory().setItem(14, (robot.isHunting()) ? itemNoHunt : itemHunt);
		getInventory().setItem(12, (robot.isFollowing()) ? itemNoFollow : itemFollow);
	}

	public void follow(Entity p) {
		robot.setFollow(p);
		if (robot.isHunting())
			noHunt();
		getInventory().setItem(12, itemNoFollow);
	}

	public void noFollow(Entity p) {
		robot.cancelFollow();
		getInventory().setItem(12, itemFollow);
	}

	public void Hunt() {
		robot.setHunting(true);
		if (robot.isFollowing())
			noFollow(robot.getFollowTarget());
		getInventory().setItem(14, itemNoHunt);
	}

	public void noHunt() {
		robot.setHunting(false);
		getInventory().setItem(14, itemHunt);
	}

}
