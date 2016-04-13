package com.kirelcodes.RoboticCraft.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import static com.kirelcodes.RoboticCraft.utils.NMSClassInteracter.*;

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
	
	public static ItemStack getSkullFromURL(String url , String name) throws Exception{
		ItemStack skull = new ItemStack(Material.SKULL_ITEM,1, (short) 3);
		SkullMeta sm = (SkullMeta) skull.getItemMeta();
		sm.setOwner("NacOJerk");
		skull.setItemMeta(sm);
		url = Base64Coder.encodeString("{textures:{SKIN:{url:\""+url+"\"}}}");
		GameProfile gp = new GameProfile(UUID.randomUUID(), name);
		gp.getProperties().put("textures", new Property("textures", url));
		
		Object isskull = asNMSCopy(skull);
		Object nbt = getNMS("NBTTagCompound").getConstructor().newInstance();
		Method serialize = getNMS("GameProfileSerializer").getMethod("serialize", getNMS("NBTTagCompound"), GameProfile.class);
		serialize.invoke(null, nbt, gp);
		Object nbtr = isskull.getClass().getMethod("getTag").invoke(isskull);
		nbtr.getClass().getMethod("set", String.class, getNMS("NBTBase")).invoke(nbtr, "SkullOwner", nbt);
		isskull.getClass().getMethod("setTag", getNMS("NBTTagCompound")).invoke(isskull, nbtr);
		skull = asBukkitCopy(isskull);
		return skull;
	}
}
