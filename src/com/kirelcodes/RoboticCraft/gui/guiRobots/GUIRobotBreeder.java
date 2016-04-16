package com.kirelcodes.RoboticCraft.gui.guiRobots;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.kirelcodes.RoboticCraft.gui.GUI;
import com.kirelcodes.RoboticCraft.robot.RobotBreader;
import com.kirelcodes.RoboticCraft.utils.ItemStackUtils;

public class GUIRobotBreeder extends GUI{

	private RobotBreader robot;
	private ItemStack itemFollow, itemNoFollow, itemBreed, itemNoBreed, openInventory, Destroy;

	public RobotBreader getRobot() {
		return robot;
	}

	public GUIRobotBreeder(RobotBreader robot) {
		setSize(27);
		setTitle("&cBreeder Robot GUI");
		instalizeInventory();
		this.robot = robot;
		Destroy = ItemStackUtils.createItem(Material.BARRIER, "&cDESTROY ROBOT");
		itemFollow = ItemStackUtils.createItem(Material.COMPASS, "&aFollow");
		itemNoFollow = ItemStackUtils.createItem(Material.COMPASS, "&cStop Follow");
		itemBreed = ItemStackUtils.createItem(Material.WHEAT, "&aBreed");
		itemNoBreed = ItemStackUtils.createItem(Material.WHEAT, "&cStop Breed");
		openInventory = ItemStackUtils.createItem(Material.CHEST, "&cOpen Robot's Inventory");
		
		gettGUIAction().add(new GUIAction(itemFollow) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotBreeder) gui).follow(player);
			}
		});
		gettGUIAction().add(new GUIAction(Destroy) {

			@Override
			public void actionNow(GUI gui, Player player) {
				robot.destroy();
			}
		});
		gettGUIAction().add(new GUIAction(openInventory) {

			@Override
			public void actionNow(GUI gui, Player player) {
				player.closeInventory();
				player.openInventory(((GUIRobotBreeder) gui).getRobot().getInventory());
			}
		});
		gettGUIAction().add(new GUIAction(itemNoFollow) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotBreeder) gui).noFollow(player);
			}
		});
		gettGUIAction().add(new GUIAction(itemBreed) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotBreeder) gui).Farm();
			}
		});
		gettGUIAction().add(new GUIAction(itemNoBreed) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotBreeder) gui).noFarm();
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
		getInventory().setItem(14, (robot.isBreading()) ? itemNoBreed : itemBreed);
		getInventory().setItem(12, (robot.isFollowing()) ? itemNoFollow : itemFollow);
	}

	public void follow(Entity p) {
		robot.setFollow(p);
		if (robot.isBreading())
			noFarm();
		getInventory().setItem(12, itemNoFollow);
	}

	public void noFollow(Entity p) {
		robot.cancelFollow();
		getInventory().setItem(12, itemFollow);
	}

	public void Farm() {
		robot.setBreeding(true);
		if (robot.isFollowing())
			noFollow(robot.getFollowTarget());
		getInventory().setItem(14, itemNoBreed);
	}

	public void noFarm() {
		robot.setBreeding(false);
		getInventory().setItem(14, itemBreed);
	}

}
