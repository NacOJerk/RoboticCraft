package com.kirelcodes.RoboticCraft.gui.guiRobots;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.kirelcodes.RoboticCraft.gui.GUI;
import com.kirelcodes.RoboticCraft.robot.RobotLumberjack;
import com.kirelcodes.RoboticCraft.utils.ItemStackUtils;

public class GUIRobotLumberjack extends GUIRobotBasic {

	private RobotLumberjack robot;
	private ItemStack itemCut, itemNoCut;

	@Override
	public RobotLumberjack getRobot() {
		return robot;
	}

	public GUIRobotLumberjack(RobotLumberjack robot) {
		super(robot);
		setSize(27);
		setTitle("&cLumberjack Robot GUI");
		this.robot = robot;
		itemCut = ItemStackUtils.createItem(Material.GOLD_AXE, "&aCut");
		itemNoCut = ItemStackUtils.createItem(Material.GOLD_AXE, "&cStop Cut");

		gettGUIAction().add(new GUIAction(itemCut) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotLumberjack) gui).Cut();
			}
		});

		gettGUIAction().add(new GUIAction(itemNoCut) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotLumberjack) gui).noCut();
			}
		});

		setRemovePos(3);
		setChestPos(13);
		setFollowPos(12);
		getInventory().setItem(14, (robot.isCutting()) ? itemNoCut : itemCut);

	}

	public void Cut() {
		robot.setCutting(true);
		if (robot.isFollowing())
			noFollow(robot.getFollowTarget());
		getInventory().setItem(14, itemNoCut);
	}

	@Override
	public void follow(Entity p) {
		super.follow(p);
		noCut();
	}

	public void noCut() {
		robot.setCutting(false);
		getInventory().setItem(14, itemCut);
	}

}
