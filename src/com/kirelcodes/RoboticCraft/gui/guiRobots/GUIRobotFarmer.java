package com.kirelcodes.RoboticCraft.gui.guiRobots;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.kirelcodes.RoboticCraft.gui.GUI;
import com.kirelcodes.RoboticCraft.robot.RobotFarmer;
import com.kirelcodes.RoboticCraft.utils.ItemStackUtils;

import net.md_5.bungee.api.ChatColor;

public class GUIRobotFarmer extends GUI {

	private RobotFarmer robot;
	private ItemStack itemFollow, itemNoFollow, itemFarm, itemNoFarm, openInventory, Destroy, pos1, pos2;

	public RobotFarmer getRobot() {
		return robot;
	}

	public GUIRobotFarmer(RobotFarmer robot) {
		setSize(27);
		setTitle("&cFarmer Robot GUI");
		instalizeInventory();
		this.robot = robot;
		Destroy = ItemStackUtils.createItem(Material.BARRIER, "&cDESTROY ROBOT");
		itemFollow = ItemStackUtils.createItem(Material.COMPASS, "&aFollow");
		itemNoFollow = ItemStackUtils.createItem(Material.COMPASS, "&cStop Follow");
		itemFarm = ItemStackUtils.createItem(Material.DIAMOND_HOE, "&aFarm");
		itemNoFarm = ItemStackUtils.createItem(Material.DIAMOND_HOE, "&cStop Farm");
		pos1 = ItemStackUtils.createItem(Material.DIRT, 2, "&aset Pos1");
		pos2 = ItemStackUtils.createItem(Material.DIRT, 2, "&aset Pos2");
		openInventory = ItemStackUtils.createItem(Material.CHEST, "&cOpen Robot's Inventory");
		gettGUIAction().add(new GUIAction(itemFollow) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotFarmer) gui).follow(player);
			}
		});
		gettGUIAction().add(new GUIAction(Destroy) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotFarmer) gui).getRobot().destroy();
			}
		});
		gettGUIAction().add(new GUIAction(openInventory) {

			@Override
			public void actionNow(GUI gui, Player player) {
				player.closeInventory();
				player.openInventory(((GUIRobotFarmer) gui).getRobot().getInventory());
			}
		});
		gettGUIAction().add(new GUIAction(itemNoFollow) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotFarmer) gui).noFollow(player);
			}
		});
		gettGUIAction().add(new GUIAction(itemFarm) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotFarmer) gui).Farm();
			}
		});
		gettGUIAction().add(new GUIAction(itemNoFarm) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotFarmer) gui).noFarm();
			}
		});
		gettGUIAction().add(new GUIAction(pos1) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotFarmer) gui).getRobot().setMarkOne(player.getLocation());
				player.sendMessage(ChatColor.AQUA + "[Robot] Setted pos1");
			}
		});
		gettGUIAction().add(new GUIAction(pos2) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotFarmer) gui).getRobot().setMarkTwo(player.getLocation());
				player.sendMessage(ChatColor.AQUA + "[Robot] Setted pos2");
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
		getInventory().setItem(4, pos1);
		getInventory().setItem(22, pos2);
		getInventory().setItem(13, openInventory);
		getInventory().setItem(14, (robot.isFarming()) ? itemNoFarm : itemFarm);
		getInventory().setItem(12, (robot.isFollowing()) ? itemNoFollow : itemFollow);
	}

	public void follow(Entity p) {
		robot.setFollow(p);
		if (robot.isFarming())
			noFarm();
		getInventory().setItem(12, itemNoFollow);
	}

	public void noFollow(Entity p) {
		robot.cancelFollow();
		getInventory().setItem(12, itemFollow);
	}

	public void Farm() {
		robot.setFarming(true);
		if (robot.isFollowing())
			noFollow(robot.getFollowTarget());
		getInventory().setItem(14, itemNoFarm);
	}

	public void noFarm() {
		robot.setFarming(false);
		getInventory().setItem(14, itemFarm);
	}

}
