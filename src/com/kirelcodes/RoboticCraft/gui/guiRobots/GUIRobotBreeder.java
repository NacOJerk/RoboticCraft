package com.kirelcodes.RoboticCraft.gui.guiRobots;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.kirelcodes.RoboticCraft.gui.GUI;
import com.kirelcodes.RoboticCraft.robot.RobotBreeder;
import com.kirelcodes.RoboticCraft.utils.ItemStackUtils;

public class GUIRobotBreeder extends GUIRobotBasic {

	private RobotBreeder robot;
	private ItemStack itemBreed, itemNoBreed;

	@Override
	public RobotBreeder getRobot() {
		return robot;
	}

	public GUIRobotBreeder(RobotBreeder robot) {
		super(robot);
		setSize(27);
		setTitle("&cBreeder Robot GUI");
		instalizeInventory();
		this.robot = robot;
		itemBreed = ItemStackUtils.createItem(Material.WHEAT, "&aBreed");
		itemNoBreed = ItemStackUtils.createItem(Material.WHEAT, "&cStop Breed");

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
		setRemovePos(3);
		setChestPos(13);
		getInventory().setItem(14, (robot.isBreading()) ? itemNoBreed : itemBreed);
		setFollowPos(12);
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
