package com.kirelcodes.RoboticCraft.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.kirelcodes.RoboticCraft.gui.GUI.GUIAction;

public class GUIListener implements Listener{
	
	public GUIListener(Plugin p){
		Bukkit.getServer().getPluginManager().registerEvents(this, p);
	}
	@EventHandler
	public void clickInventory(InventoryClickEvent e){
		if(e.getClickedInventory() == null)
			return;
		if(e.getClickedInventory().getHolder() == null)
			return;
		if(!(e.getClickedInventory().getHolder() instanceof GUI))
			return;
		GUI gui = (GUI) e.getClickedInventory().getHolder();
		if(e.getCurrentItem() == null)
			return;
		ItemStack item = e.getCurrentItem();
		if(!(e.getWhoClicked() instanceof Player))
			return;
		Player player = (Player) e.getWhoClicked();
		GUIAction guiAc = null;
		for(GUIAction guiAction : gui.getGUIAction()){
			if(!guiAction.isSame(item))
				continue;
			guiAc = guiAction;
			break;
		}
		if(guiAc == null)
			return;
		e.setCancelled(true);
		guiAc.actionNow(gui, player);
	}
}
