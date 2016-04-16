package com.kirelcodes.RoboticCraft.gui;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public abstract class GUI implements InventoryHolder {

	private Inventory inventory;
	private int size;
	private String title;
	private ArrayList<GUIAction> inventoryAction;

	public void setSize(int size) {
		this.size = size;
	}

	public GUI() {
		inventoryAction = new ArrayList<>();
	}

	public void setTitle(String title) {
		this.title = title.replaceAll("&", "§");
	}

	protected void instalizeInventory() {
		this.inventory = Bukkit.createInventory(this, getSize(), getTitle());
	}

	public int getSize() {
		return size;
	}

	public String getTitle() {
		return title;
	}

	public ArrayList<GUIAction> gettGUIAction() {
		return inventoryAction;
	}

	protected void addAction(GUIAction gui) {
		gettGUIAction().add(gui);
	}

	public abstract GUI getGUI();

	public void openGUI(Player p){
		p.openInventory(getInventory());
	}
	
	
	@Override
	public Inventory getInventory() {
		return inventory;
	}

	public static abstract class GUIAction {
		private ItemStack item;

		public GUIAction(ItemStack item) {
			this.item = item;
		}

		public ItemStack getItem() {
			return item;
		}

		public boolean isSame(ItemStack item) {
			return this.item.isSimilar(item);
		}

		public abstract void actionNow(GUI gui, Player player);
	}

}
