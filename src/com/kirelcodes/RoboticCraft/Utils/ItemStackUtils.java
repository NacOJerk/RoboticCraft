package com.kirelcodes.RoboticCraft.utils;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemStackUtils {

	public static ItemStack createItem(Material m, String name, String... lore) {
		ItemStack is = new ItemStack(m);
		if(m == Material.AIR)
			return is;
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(name.replaceAll("&", "§"));
		List<String> Lore = Arrays.asList(lore);
		im.setLore(Lore);
		is.setItemMeta(im);
		return is;
	}
	public static ItemStack createItem(Material m, int i,String name, String... lore) {
		ItemStack is = new ItemStack(m , 1 ,(short) i);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(name.replaceAll("&", "§"));
		List<String> Lore = Arrays.asList(lore);
		im.setLore(Lore);
		is.setItemMeta(im);
		return is;
	}
	public static ItemStack dataItem(Material m , int d){
		return new ItemStack(m, 1, (short) d);
	}
	public static ItemStack getSkull(String player) {
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta sm = (SkullMeta) skull.getItemMeta();
		sm.setOwner(player);
		skull.setItemMeta(sm);
		return skull;
	}

	public static ItemStack getSkull(String player, String name) {
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta sm = (SkullMeta) skull.getItemMeta();
		sm.setDisplayName(name.replaceAll("&", "§"));
		sm.setOwner(player);
		skull.setItemMeta(sm);
		return skull;
	}

	public static ItemStack getLeatherArmor(Material m, Color c) {
		ItemStack armor = new ItemStack(m);
		LeatherArmorMeta lam = (LeatherArmorMeta) armor.getItemMeta();
		lam.setColor(c);
		armor.setItemMeta(lam);
		return armor;
	}
	public static boolean isPerfect(ItemStack is){
		if(!is.hasItemMeta())
			return false;
		return (is.getItemMeta().hasLore() || is.getItemMeta().hasDisplayName());
	}
}
