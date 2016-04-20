package com.kirelcodes.RoboticCraft.gui.guiRobots;

import org.bukkit.Material;
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
		this.robot = robot;
		itemMine = ItemStackUtils.createItem(Material.DIAMOND_PICKAXE, "&aMine");
		itemNoMine = ItemStackUtils.createItem(Material.DIAMOND_PICKAXE, "&cStop Mine");
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
		setRemovePos(3);
		setChestPos(13);
		getInventory().setItem(14, (robot.isMining()) ? itemNoMine : itemMine);
		setFollowPos(12);
	}

	public void Mine() {
		robot.setStartBlock(robot.getLocation());
		robot.setMining(true);
		if (robot.isFollowing())
			noFollow(robot.getFollowTarget());
		getInventory().setItem(14, itemNoMine);
	}

	public void noMine() {
		robot.setMining(false);
		getInventory().setItem(14, itemMine);
	}

}
