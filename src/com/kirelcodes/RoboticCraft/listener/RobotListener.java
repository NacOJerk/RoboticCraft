package com.kirelcodes.RoboticCraft.listener;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import com.kirelcodes.RoboticCraft.robot.RobotCenter;
import com.kirelcodes.RoboticCraft.robot.RobotMiner;

public class RobotListener implements Listener{
	public RobotListener(Plugin p){
		Bukkit.getPluginManager().registerEvents(this, p);
	}
	@EventHandler
	public void intaractAtArmor(PlayerArmorStandManipulateEvent e){
		
		if(!e.getRightClicked().getCustomName().contains(ChatColor.MAGIC + "NacOJerkGalShaked-"))
			return;
		e.setCancelled(true);
		int ID = Integer.parseInt(e.getRightClicked().getCustomName().split("-")[1]);
		if(!RobotCenter.exists(ID))
			return;
		if(e.getPlayerItem().getType()==Material.GOLDEN_CARROT){
			if(RobotCenter.getRobot(ID) instanceof RobotMiner){
				((RobotMiner)RobotCenter.getRobot(ID)).egg();
				if(e.getPlayerItem().getAmount()==1){
					
				}else{
					e.getPlayer().setItemInHand(null);
				}
			}
		}else{
		Inventory inven = RobotCenter.getRobot(ID).getInventory();
		e.getPlayer().openInventory(inven);
		}
	}
}
