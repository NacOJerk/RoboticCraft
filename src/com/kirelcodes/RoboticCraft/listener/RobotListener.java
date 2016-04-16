package com.kirelcodes.RoboticCraft.listener;


import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.kirelcodes.RoboticCraft.RecipeAdder;
import com.kirelcodes.RoboticCraft.RoboticCraft;
import com.kirelcodes.RoboticCraft.gui.GUIGet;
import com.kirelcodes.RoboticCraft.robot.RobotBase;
import com.kirelcodes.RoboticCraft.robot.RobotCenter;
import com.kirelcodes.RoboticCraft.utils.NMSClassInteracter;

import static com.kirelcodes.RoboticCraft.utils.NBTRobotId.*;
public class RobotListener implements Listener {
	public RobotListener(Plugin p) {
		Bukkit.getPluginManager().registerEvents(this, p);
	}
	@EventHandler
	public void placeEnder(BlockPlaceEvent e){
		if(NMSClassInteracter.getVersion().contains("8"))
			return;
		if(e.getItemInHand().getType() == Material.END_CRYSTAL){
			final BlockPlaceEvent e4 = e;
			new BukkitRunnable() {
				
				@Override
				public void run() {
					ArrayList<Entity> nearby = new ArrayList<Entity>();
					nearby.addAll(e4.getBlock().getWorld().getNearbyEntities(
							e4.getBlock().getLocation(), 2.0, 2.0, 2.0));
					for(Entity e2 : nearby)
						if(e2.getType() == EntityType.ENDER_CRYSTAL)
							e2.remove();
				}
			}.runTaskLater(RoboticCraft.getInstance(), 20L);
		}
	}
	@EventHandler
	public void intaractAtArmor(PlayerArmorStandManipulateEvent e) {

		if (!e.getRightClicked().getCustomName()
				.contains(ChatColor.MAGIC + "NacOJerkGalShaked-"))
			return;
		e.setCancelled(true);
	}
	
	
	@EventHandler
	public void rightClickSpawner(PlayerInteractEvent e){
		if(!e.hasItem())
			return;
		ItemStack item = e.getItem();
		if(!RecipeAdder.containsItem(item))
			return;
		try {
			if(hasID(item))
				return;
			RobotBase robot = RecipeAdder.getRobot(item, e.getPlayer().getLocation());
			ItemStack cloneItem = e.getItem();
			e.getPlayer().getInventory().remove(item);
			cloneItem = setID(cloneItem, robot.getID());
			RecipeAdder.addRemote(cloneItem);
			e.getPlayer().getInventory().addItem(cloneItem);
			e.getPlayer().updateInventory();
		} catch (Exception e1) {
			return;
		}
	}
	
	@EventHandler
	public void rightClickRemote(PlayerInteractEvent e){
		if(!e.hasItem())
			return;
		ItemStack item = e.getItem();
		if(!RecipeAdder.containsItem(item))
			return;
		try {
			if(!hasID(item))
				return;
			int ID = getID(item);
			GUIGet.getGUI(RobotCenter.getRobot(ID)).openGUI(e.getPlayer());;
		} catch (Exception e1) {
			return;
		}
	}
	
}
