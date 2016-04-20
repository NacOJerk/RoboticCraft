package com.kirelcodes.RoboticCraft.gui.guiRobots;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.kirelcodes.RoboticCraft.gui.GUI;
import com.kirelcodes.RoboticCraft.robot.RobotFisher;
import com.kirelcodes.RoboticCraft.utils.ItemStackUtils;

public class GUIRobotFisher extends GUIRobotBasic {
	private RobotFisher robot;
	private ItemStack itemFish, itemNoFish;

	@Override
	public RobotFisher getRobot() {
		return robot;
	}

	public GUIRobotFisher(RobotFisher robot) {
		super(robot);
		setSize(27);
		setTitle("&cFisher Robot GUI");
		instalizeInventory();
		fillInventory();
		this.robot = robot;
		itemNoFish = ItemStackUtils.createItem(Material.FISHING_ROD, "&cStop Fish");
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
		setRemovePos(3);
		setChestPos(13);
		setFollowPos(12);
		getInventory().setItem(14, (robot.isFishing()) ? itemNoFish : itemFish);
	}

	public void Fish() {
		robot.setFishing(true);
		if (robot.isFollowing())
			noFollow(robot.getFollowTarget());
		getInventory().setItem(14, itemNoFish);
	}
	@Override
	public void follow(Entity p) {
		super.follow(p);
		noFish();
	}
	public void noFish() {
		robot.setFishing(false);
		getInventory().setItem(14, itemFish);
	}

}
