package com.kirelcodes.RoboticCraft.gui.guiRobots;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.kirelcodes.RoboticCraft.gui.GUI;
import com.kirelcodes.RoboticCraft.robot.RobotCollector;
import com.kirelcodes.RoboticCraft.utils.ItemStackUtils;

public class GUIRobotCollector extends GUIRobotBasic {

	private RobotCollector robot;
	private ItemStack itemCollect, itemNoCollect;

	@Override
	public RobotCollector getRobot() {
		return robot;
	}

	public GUIRobotCollector(RobotCollector robot) {
		super(robot);
		setSize(27);
		setTitle("&cMiner Robot GUI");
		instalizeInventory();
		this.robot = robot;
		itemCollect = ItemStackUtils.createItem(Material.HOPPER, "&aCollect");
		itemNoCollect = ItemStackUtils.createItem(Material.HOPPER, "&cStop Collecting");
		gettGUIAction().add(new GUIAction(itemCollect) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotCollector) gui).Collect();
			}
		});
		gettGUIAction().add(new GUIAction(itemNoCollect) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotCollector) gui).noCollect();
			}
		});
		setRemovePos(3);
		setFollowPos(12);
		setChestPos(13);
		getInventory().setItem(14, (robot.isCollecting()) ? itemNoCollect : itemCollect);
	}

	public void Collect() {
		robot.setCollecting(true);
		getInventory().setItem(14, itemNoCollect);
	}

	public void noCollect() {
		robot.setCollecting(false);
		getInventory().setItem(14, itemCollect);
	}

}