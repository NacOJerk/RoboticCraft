package com.kirelcodes.RoboticCraft.gui.guiRobots;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.kirelcodes.RoboticCraft.gui.GUI;
import com.kirelcodes.RoboticCraft.robot.RobotFisher;
import com.kirelcodes.RoboticCraft.utils.ItemStackUtils;

import net.md_5.bungee.api.ChatColor;

public class GUIRobotFisher extends GUI {
	private RobotFisher robot;
	private ItemStack itemFollow, itemNoFollow, itemFish, itemNoFish, Destroy, openInventory;

	public RobotFisher getRobot() {
		return robot;
	}

	public GUIRobotFisher(RobotFisher robot) {
		setSize(27);
		setTitle("&cFisher Robot GUI");
		instalizeInventory();
		this.robot = robot;
		Destroy = ItemStackUtils.createItem(Material.BARRIER, "&cDESTROY ROBOT");
		itemFollow = ItemStackUtils.createItem(Material.COMPASS, "&aFollow");
		itemNoFollow = ItemStackUtils.createItem(Material.COMPASS, "&cStop Follow");
		itemFish = ItemStackUtils.createItem(Material.DIAMOND_SWORD, "&aFish");
		itemNoFish = ItemStackUtils.createItem(Material.DIAMOND_SWORD, "&cStop Fish");
		openInventory = ItemStackUtils.createItem(Material.CHEST, "&cOpen Robot's Inventory");
		gettGUIAction().add(new GUIAction(itemFollow) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotFisher) gui).follow(player);
			}
		});
		gettGUIAction().add(new GUIAction(Destroy) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotFisher) gui).getRobot().destroy();
			}
		});
		gettGUIAction().add(new GUIAction(openInventory) {

			@Override
			public void actionNow(GUI gui, Player player) {
				player.closeInventory();
				player.openInventory(((GUIRobotFisher) gui).getRobot().getInventory());
			}
		});
		gettGUIAction().add(new GUIAction(itemNoFollow) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotFisher) gui).noFollow(player);
			}
		});
		gettGUIAction().add(new GUIAction(itemFish) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotFisher) gui).Fish();
			}
		});
		gettGUIAction().add(new GUIAction(itemNoFish) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotFisher) gui).noFish();
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
		getInventory().setItem(14, (robot.isFishing()) ? itemNoFish : itemFish);
		getInventory().setItem(12, (robot.isFollowing()) ? itemNoFollow : itemFollow);
	}

	public void follow(Entity p) {
		robot.setFollow(p);
		if (robot.isFishing())
			noFish();
		getInventory().setItem(12, itemNoFollow);
	}

	public void noFollow(Entity p) {
		robot.cancelFollow();
		getInventory().setItem(12, itemFollow);
	}

	public void Fish() {
		robot.setFishing(true);
		if (robot.isFollowing())
			noFollow(robot.getFollowTarget());
		getInventory().setItem(14, itemNoFish);
	}

	public void noFish() {
		robot.setFishing(false);
		getInventory().setItem(14, itemFish);
	}

}
