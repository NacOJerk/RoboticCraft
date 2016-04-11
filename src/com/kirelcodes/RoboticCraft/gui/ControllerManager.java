package com.kirelcodes.RoboticCraft.gui;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public class ControllerManager implements Listener{
	
	private Map<String, GUI> controllers = new HashMap<>();
	
	public ControllerManager(JavaPlugin plugin){
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	public void addController(String id, GUI controller){
		controllers.put(id, controller);
	}
	
	public Collection<GUI> getControllers(){
		return controllers.values();
	}
	
	public GUI getController(String id){
		return controllers.get(id);
	}
	
	@EventHandler
	public void onDrag(InventoryDragEvent e){
		Inventory inv = e.getWhoClicked().getOpenInventory().getTopInventory();
		for(GUI controller : controllers.values()){
			if(controller.getInventory().getName().equals(inv.getName())){
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e){
		Player player = (Player) e.getWhoClicked();
		Inventory inv = player.getOpenInventory().getTopInventory();
		for(GUI controller : controllers.values()){
			if(controller.getInventory().getName().equals(inv.getName())){
				e.setCancelled(true);
				controller.click(player, e.getCurrentItem());
			}
		}
	}

}
