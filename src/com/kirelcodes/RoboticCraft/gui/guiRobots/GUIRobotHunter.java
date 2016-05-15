package com.kirelcodes.RoboticCraft.gui.guiRobots;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.kirelcodes.RoboticCraft.configs.ConfigManager;
import com.kirelcodes.RoboticCraft.gui.GUI;
import com.kirelcodes.RoboticCraft.robot.RobotHunter;
import com.kirelcodes.RoboticCraft.utils.ItemStackUtils;

public class GUIRobotHunter extends GUIRobotBasic {
	private RobotHunter robot;
	private ItemStack itemHunt, itemNoHunt;

	@Override
	public RobotHunter getRobot() {
		return robot;
	}

	public GUIRobotHunter(RobotHunter robot) {
		super(robot);
		setSize(27);
		setTitle(ConfigManager.getLang("HunterGUIT"));
		instalizeInventory();
		fillInventory();
		this.robot = robot;
		itemHunt = ItemStackUtils.createItem(Material.DIAMOND_SWORD, "&aHunt");
		itemNoHunt = ItemStackUtils.createItem(Material.DIAMOND_SWORD, "&cStop Hunt");
		getGUIAction().add(new GUIAction(itemHunt) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotHunter) gui).Hunt();
			}
		});
		getGUIAction().add(new GUIAction(itemNoHunt) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotHunter) gui).noHunt();
			}
		});
		setRemovePos(3);
		setChestPos(13);
		setFollowPos(12);
		getInventory().setItem(14, (robot.isHunting()) ? itemNoHunt : itemHunt);
	}

	public void Hunt() {
		robot.setHunting(true);
		if (robot.isFollowing())
			noFollow(robot.getFollowTarget());
		getInventory().setItem(14, itemNoHunt);
	}
	@Override
	public void follow(Entity p) {
		super.follow(p);
		noHunt();
	}
	public void noHunt() {
		robot.setHunting(false);
		getInventory().setItem(14, itemHunt);
	}

}
