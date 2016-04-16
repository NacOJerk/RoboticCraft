package com.kirelcodes.RoboticCraft.gui.guiRobots;

import java.util.Random;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.kirelcodes.RoboticCraft.gui.GUI;
import com.kirelcodes.RoboticCraft.robot.RobotBase;
import com.kirelcodes.RoboticCraft.utils.ItemStackUtils;

public class GUIRobotBasic extends GUI{
	private RobotBase robot;
	private ItemStack itemFollow , itemNoFollow;
	public GUIRobotBasic(RobotBase robot){
		setSize(27);
		setTitle("&cBasic Robot GUI");
		instalizeInventory();
		this.robot = robot;
		itemFollow = ItemStackUtils.createItem(Material.COMPASS, "&aFollow");
		itemNoFollow = ItemStackUtils.createItem(Material.COMPASS , "&cStop Follow");
		gettGUIAction().add(new GUIAction(itemFollow) {
			
			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotBasic)gui).follow(player);
			}
		});
		gettGUIAction().add(new GUIAction(itemNoFollow) {
			
			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotBasic)gui).noFollow(player);
			}
		});
		for(int i = 0 ; i < 27 ; i++){
			ItemStack item = ItemStackUtils.createItem(Material.STAINED_GLASS_PANE, new Random().nextInt(16), ChatColor.BLACK +"DONT CLICK ME");
			getInventory().setItem(i, item);
			gettGUIAction().add(new GUIAction(item) {
				
				@Override
				public void actionNow(GUI gui, Player player) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		getInventory().setItem(13, (robot.isFollowing()) ? itemNoFollow : itemFollow);
	}
	public void follow(Entity p){
		robot.setFollow(p);
		getInventory().setItem(13, itemNoFollow);
	}
	public void noFollow(Entity p){
		robot.cancelFollow();
		getInventory().setItem(13, itemFollow);
	}
}