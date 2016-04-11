package com.kirelcodes.RoboticCraft.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public abstract class GUI implements InventoryHolder {

	private int size;
	private Inventory inventory;
	
	public GUI(String title, int rows){
		inventory = Bukkit.createInventory(null, rows*9, title);
		this.size = rows*9;
	}
	
	public void show(Player player) {
		player.openInventory(inventory);
	}
	
	public abstract void click(Player player, ItemStack itemStack);
	
	protected String getName(ItemStack itemStack){
		if(itemStack == null){
			return "";
		}
		ItemMeta meta = itemStack.getItemMeta();
		
		if(meta == null || !meta.hasDisplayName()){
			return "";
		}
		return ChatColor.stripColor(meta.getDisplayName());
	}
	
	public int getSize(){
		return size;
	}
	
	public int getRows(){
		return size/9;
	}
	
	public Inventory getInventory(){
		return inventory;
	}

}
