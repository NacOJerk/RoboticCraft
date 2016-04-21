package com.kirelcodes.RoboticCraft.gui.guiRobots;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.kirelcodes.RoboticCraft.gui.GUI;
import com.kirelcodes.RoboticCraft.robot.RobotFarmer;
import com.kirelcodes.RoboticCraft.utils.ItemStackUtils;

import org.bukkit.ChatColor;

public class GUIRobotFarmer extends GUIRobotBasic {

	private RobotFarmer robot;
	private ItemStack itemFarm, itemNoFarm, pos1, pos2;

	@Override
	public RobotFarmer getRobot() {
		return robot;
	}

	public GUIRobotFarmer(RobotFarmer robot) {
		super(robot);
		setSize(27);
		setTitle("&cFarmer Robot GUI");
		instalizeInventory();
		fillInventory();
		this.robot = robot;
		itemFarm = ItemStackUtils.createItem(Material.DIAMOND_HOE, "&aFarm");
		itemNoFarm = ItemStackUtils.createItem(Material.DIAMOND_HOE,
				"&cStop Farm");
		pos1 = ItemStackUtils.createItem(Material.DIRT, 2, "&aset Pos1");
		pos2 = ItemStackUtils.createItem(Material.DIRT, 2, "&aset Pos2");
		getGUIAction().add(new GUIAction(itemFarm) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotFarmer) gui).Farm();
			}
		});
		getGUIAction().add(new GUIAction(itemNoFarm) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotFarmer) gui).noFarm();
			}
		});
		getGUIAction().add(new GUIAction(pos1) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotFarmer) gui).getRobot().setMarkOne(
						player.getLocation());
				player.sendMessage(ChatColor.AQUA + "[Robot] Setted pos1");
			}
		});
		getGUIAction().add(new GUIAction(pos2) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotFarmer) gui).getRobot().setMarkTwo(
						player.getLocation());
				player.sendMessage(ChatColor.AQUA + "[Robot] Setted pos2");
			}
		});
		setRemovePos(3);
		getInventory().setItem(4, pos1);
		getInventory().setItem(22, pos2);
		setChestPos(13);
		setFollowPos(12);
		getInventory().setItem(14, (robot.isFarming()) ? itemNoFarm : itemFarm);
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
