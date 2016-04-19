package com.kirelcodes.RoboticCraft.gui.guiRobots;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.kirelcodes.RoboticCraft.gui.GUI;
import com.kirelcodes.RoboticCraft.robot.RobotCollector;
import com.kirelcodes.RoboticCraft.utils.ItemStackUtils;

public class GUIRobotCollector extends GUI {

	private RobotCollector robot;
	private ItemStack Destroy, itemCollect, itemNoCollect, openInventory;

	public RobotCollector getRobot() {
		return robot;
	}

	public GUIRobotCollector(RobotCollector robot) {
		setSize(27);
		setTitle("&cMiner Robot GUI");
		instalizeInventory();
		this.robot = robot;
		Destroy = ItemStackUtils.createItem(Material.BARRIER, "&cDESTROY ROBOT");
		itemCollect = ItemStackUtils.createItem(Material.HOPPER, "&aCollect");
		itemNoCollect = ItemStackUtils.createItem(Material.HOPPER, "&cStop Collecting");
		openInventory = ItemStackUtils.createItem(Material.CHEST, "&cOpen Robot's Inventory");
		gettGUIAction().add(new GUIAction(openInventory) {

			@Override
			public void actionNow(GUI gui, Player player) {
				player.closeInventory();
				player.openInventory(((GUIRobotCollector) gui).getRobot().getInventory());
			}
		});
		gettGUIAction().add(new GUIAction(Destroy) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotCollector) gui).getRobot().destroy();
				player.closeInventory();
			}
		});
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
		getInventory().setItem(13, Destroy);
		getInventory().setItem(12, openInventory);
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