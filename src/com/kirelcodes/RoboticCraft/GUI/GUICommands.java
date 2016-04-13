package com.kirelcodes.RoboticCraft.gui;

import java.util.HashMap;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GUICommands {
	private Player p;
	private static HashMap<String, GUI> gui = new HashMap<>();
	ItemStack follow = new ItemStack(Material.COMPASS);
	ItemMeta followm = follow.getItemMeta();
	
	public static String getID() {
		Random randm = new Random();
		int ID = randm.nextInt(1000000) + 123456;
		if (exists(ID))
			return "" + getID();
		return "" + ID;
	}

	public static boolean exists(int ID) {
		return gui.containsKey(ID);
	}
	GUI basicRobo = new GUI("", 9, getID()) {

		@Override
		public void click(Player player, ItemStack itemStack) {
			followm.setDisplayName("§6Follow");
			follow.setItemMeta(followm);
			if(itemStack.getType() == follow.getType()){
				
			}
		}
	};
}
