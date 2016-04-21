package com.kirelcodes.RoboticCraft.gui.guiRobots;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.kirelcodes.RoboticCraft.gui.GUI;
import com.kirelcodes.RoboticCraft.robot.RobotMiner;
import com.kirelcodes.RoboticCraft.utils.ItemStackUtils;

public class GUIRobotMiner extends GUIRobotBasic {

	private RobotMiner robot;
	private ItemStack itemMine, itemNoMine;

	@Override
	public RobotMiner getRobot() {
		return robot;
	}

	public GUIRobotMiner(RobotMiner robot) {
		super(robot);
		setSize(27);
		setTitle("&cMiner Robot GUI");
		instalizeInventory();
		fillInventory();
		this.robot = robot;
		itemMine = ItemStackUtils.createItem(Material.DIAMOND_PICKAXE, "&aMine");
		itemNoMine = ItemStackUtils.createItem(Material.DIAMOND_PICKAXE, "&cStop Mine");
		getGUIAction().add(new GUIAction(itemMine) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotMiner) gui).Mine();
			}
		});
		getGUIAction().add(new GUIAction(itemNoMine) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotMiner) gui).noMine();
			}
		});
		setRemovePos(3);
		setChestPos(13);
		setFollowPos(12);
		getInventory().setItem(14, (robot.isMining()) ? itemNoMine : itemMine);
	}

	public void Mine() {
		robot.setStartBlock(robot.getLocation());
		robot.setMining(true);
		if (robot.isFollowing())
			noFollow(robot.getFollowTarget());
		getInventory().setItem(14, itemNoMine);
	}
	@Override
	public void follow(Entity p) {
		super.follow(p);
		noMine();
	}
	public void noMine() {
		robot.setMining(false);
		getInventory().setItem(14, itemMine);
	}

}
