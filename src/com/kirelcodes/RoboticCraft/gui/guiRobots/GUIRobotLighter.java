package com.kirelcodes.RoboticCraft.gui.guiRobots;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.kirelcodes.RoboticCraft.configs.ConfigManager;
import com.kirelcodes.RoboticCraft.gui.GUI;
import com.kirelcodes.RoboticCraft.robot.RobotLighter;
import com.kirelcodes.RoboticCraft.utils.ItemStackUtils;

public class GUIRobotLighter extends GUI {

	private RobotLighter robot;
	private ItemStack Destroy, itemLight, itemNoLight, openInventory;

	public RobotLighter getRobot() {
		return robot;
	}

	public GUIRobotLighter(RobotLighter robot) {
		setSize(27);
		setTitle(ConfigManager.getLang("LighterGUIT"));
		instalizeInventory();
		this.robot = robot;
		Destroy = ItemStackUtils.createItem(Material.BARRIER, "&cDESTROY ROBOT");
		itemLight = ItemStackUtils.createItem(Material.TORCH, "&aLight");
		itemNoLight = ItemStackUtils.createItem(Material.TORCH, "&cStop Light");
		openInventory = ItemStackUtils.createItem(Material.CHEST, "&cOpen Robot's Inventory");
		int fuelD = 0;
		ChatColor color = ChatColor.WHITE;
		if(robot.getFuel() >= 75){
			fuelD = 5;
			color = ChatColor.GREEN;
		}
		else if (robot.getFuel() >= 50){
			fuelD = 4;
			color = ChatColor.YELLOW;
		}
		else if(robot.getFuel() >= 25){
			fuelD = 1;
			color = ChatColor.RED;
		}
		else if(robot.getFuel() >= 0){
			fuelD = 14;
			color = ChatColor.DARK_RED;
		}
		ItemStack fuel = ItemStackUtils.createItem(Material.STAINED_GLASS_PANE, fuelD, color + ConfigManager.getLang("BasicItem_Fuel") + robot.getFuel());
		getGUIAction().add(new GUIAction(fuel) {
			
			@Override
			public void actionNow(GUI gui, Player player) {
				// TODO Auto-generated method stub
				
			}
		});
		getGUIAction().add(new GUIAction(openInventory) {

			@Override
			public void actionNow(GUI gui, Player player) {
				player.closeInventory();
				player.openInventory(((GUIRobotLighter) gui).getRobot().getInventory());
			}
		});
		getGUIAction().add(new GUIAction(Destroy) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotBasic) gui).getRobot().guiDestroy(player);
				player.closeInventory();
			}
		});
		getGUIAction().add(new GUIAction(itemLight) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotLighter) gui).Light(player);
			}
		});
		getGUIAction().add(new GUIAction(itemNoLight) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotLighter) gui).noLight();
			}
		});
		ItemStack item = ItemStackUtils.createItem(Material.STAINED_GLASS_PANE, 0, ChatColor.BLACK + "DONT CLICK ME");
		getGUIAction().add(new GUIAction(item) {

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
		getInventory().setItem(8, fuel);
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
