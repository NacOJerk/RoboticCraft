package com.kirelcodes.RoboticCraft.gui.guiRobots;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.kirelcodes.RoboticCraft.gui.GUI;
import com.kirelcodes.RoboticCraft.robot.RobotLighter;
import com.kirelcodes.RoboticCraft.utils.ItemStackUtils;

import org.bukkit.ChatColor;

public class GUIRobotLighter extends GUI {

	private RobotLighter robot;
	private ItemStack Destroy, itemLight, itemNoLight, openInventory;

	public RobotLighter getRobot() {
		return robot;
	}

	public GUIRobotLighter(RobotLighter robot) {
		setSize(27);
		setTitle("&cLighter Robot GUI");
		instalizeInventory();
		this.robot = robot;
		Destroy = ItemStackUtils.createItem(Material.BARRIER, "&cDESTROY ROBOT");
		itemLight = ItemStackUtils.createItem(Material.TORCH, "&aLight");
		itemNoLight = ItemStackUtils.createItem(Material.TORCH, "&cStop Light");
		openInventory = ItemStackUtils.createItem(Material.CHEST, "&cOpen Robot's Inventory");
		gettGUIAction().add(new GUIAction(openInventory) {

			@Override
			public void actionNow(GUI gui, Player player) {
				player.closeInventory();
				player.openInventory(((GUIRobotLighter) gui).getRobot().getInventory());
			}
		});
		gettGUIAction().add(new GUIAction(Destroy) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotLighter) gui).getRobot().destroy();
				player.closeInventory();
			}
		});
		gettGUIAction().add(new GUIAction(itemLight) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotLighter) gui).Light(player);
			}
		});
		gettGUIAction().add(new GUIAction(itemNoLight) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotLighter) gui).noLight();
			}
		});
		ItemStack item = ItemStackUtils.createItem(Material.STAINED_GLASS_PANE, 0, ChatColor.BLACK + "DONT CLICK ME");
		gettGUIAction().add(new GUIAction(item) {

			@Override
			public void actionNow(GUI gui, Player player) {

			}
		});
		for (int i = 0; i < 27; i++) {
			getInventory().setItem(i, item);
		}
		getInventory().setItem(12, Destroy);
		getInventory().setItem(13, openInventory);
		getInventory().setItem(14, (robot.isLightning()) ? itemNoLight : itemLight);
	}

	public void Light(Player p) {
		robot.setLightning(true);
		robot.setFollow(p);
		getInventory().setItem(14, itemNoLight);
	}

	public void noLight() {
		robot.setLightning(false);
		robot.cancelFollow();
		getInventory().setItem(14, itemLight);
	}

}
