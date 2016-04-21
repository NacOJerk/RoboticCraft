package com.kirelcodes.RoboticCraft.gui.guiRobots;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.kirelcodes.RoboticCraft.RoboticCraft;
import com.kirelcodes.RoboticCraft.gui.GUI;
import com.kirelcodes.RoboticCraft.robot.RobotConstructer;
import com.kirelcodes.RoboticCraft.utils.ItemStackUtils;

public class GUIRobotConstructer extends GUIRobotBasic {

	private RobotConstructer robot;
	private ItemStack itemConstruct, itemStopConstruct, itemPos1, itemPos2;

	public GUIRobotConstructer(RobotConstructer robot) {
		super(robot);
		this.robot = robot;
		setSize(27);
		setTitle("&c Constructer Robot GUI");
		instalizeInventory();
		fillInventory();
		setRemovePos(11);
		setChestPos(13);
		setFollowPos(12);
		setFuelPos(10);
		itemConstruct = ItemStackUtils.createItem(Material.WORKBENCH,
				"&aConstruct", "");
		itemStopConstruct = ItemStackUtils.createItem(Material.WORKBENCH,
				"&cStop Constructing", "");
		itemPos1 = ItemStackUtils.createItem(Material.COMPASS,
				"&aSet Pos1", "");
		itemPos2 = ItemStackUtils.createItem(Material.COMPASS,
				"&aSet Pos2", "");
		getGUIAction().add(new GUIAction(itemConstruct) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotConstructer) gui).robot.setConstructing(true);
				((GUIRobotConstructer) gui).robot.getInventory().setItem(14,
						itemStopConstruct);
			}
		});
		getGUIAction().add(new GUIAction(itemStopConstruct) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotConstructer) gui).robot.setConstructing(false);
				((GUIRobotConstructer) gui).robot.getInventory().setItem(14,
						itemConstruct);
			}
		});
		getGUIAction().add(new GUIAction(itemPos1) {

			@Override
			public void actionNow(GUI gui, Player player) {
				RobotConstructer robot = ((GUIRobotConstructer) gui).robot;
				robot.setposOne(player.getLocation());
				if(!robot.isValid()){
					player.closeInventory();
					player.sendMessage(RoboticCraft.prefix+"§8This selection is invalid§4!");
				}
			}
		});
		getGUIAction().add(new GUIAction(itemPos2) {

			@Override
			public void actionNow(GUI gui, Player player) {
				RobotConstructer robot = ((GUIRobotConstructer) gui).robot;
				robot.setposTwo(player.getLocation());
				if(!robot.isValid()){
					player.closeInventory();
					player.sendMessage(RoboticCraft.prefix+"§8This selection is invalid§4!");
				}
			}
		});
		if (this.robot.isConstructing()) {
			getInventory().setItem(14, itemStopConstruct);
		} else {
			getInventory().setItem(14, itemConstruct);
		}
	}

}
// 0 1 2 3 4 5 6 7 8
// 9 10 11 12 13 14 15 16 17
// 18 19 20 21 22 23 24 25 26