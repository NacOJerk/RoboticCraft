package com.kirelcodes.RoboticCraft.Utils;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
/**
 * 
 * @author NacOJerk
 *
 */
public class NMSClassInteracter {
	/**
	 * @return Minecraft version 
	 * @author NacOJerk
	 */
	public static String getVersion() {
		String version = Bukkit.getServer().getClass().getPackage().getName();
		version = version.split("\\.")[3];
		return version;
	}
	
	/**
	 * This takes a path and returns an NMS (net.minecraft.server) Class
	 * @param NMS String path (Dont include the "net.minecraft.server."
	 * @return NMS class
	 * @author NacOJerk
	 */
	public static Class<?> getNMS(String nms) {
		return getClass("net.minecraft.server." + getVersion() + "." + nms);
	}
	
	/**
	 * Returns a class from a path already after the try , catch check
	 * @param name path
	 * @return Class
	 * @author NacOJerk
	 */
	public static Class<?> getClass(String name) {
		try {
			return Class.forName(name);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;

		}
	}
	
	/**
	 * This takes a path and returns an OBC(org.bukkit.craftbukkit) Class
	 * @param OBC String path (Dont include the "org.bukkit.craftbukkit."
	 * @return OBC class
	 * @author NacOJerk
	 */
	public static Class<?> getOBC(String OBC) {
		return getClass("org.bukkit.craftbukkit." + getVersion() + "." + OBC);
	}
	
	/**
	 * This method converts an ItemStack (Bukkit) to an ItemStack (NMS)
	 * @param is ItemStack (Bukkit)
	 * @return ItemStack (NMS)
	 * @author NacOJerk
	 */
	public static Object asNMSCopy(ItemStack is) {
		Object nms = null;
		try {
			nms = getOBC("inventory.CraftItemStack").getMethod("asNMSCopy",
					ItemStack.class).invoke(null, is);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nms;
	}
	
	/**
	 * This method converts an Object of ItemStack (NMS) to an object of ItemStack (Bukkit) 
	 * @param nmsItem Must be of the class of net.minecraft.server.(Version).ItemStack
	 * @return ItemStack(Bukkit)
	 * @author NacOJerk
	 */
	public static ItemStack asBukkitCopy(Object nmsItem) {
		Object iso = null;
		try {
			iso = getOBC("inventory.CraftItemStack").getMethod("asBukkitCopy",
					getNMS("ItemStack")).invoke(null, nmsItem);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ItemStack is = (ItemStack) iso;
		return is;
	}
	/**
	 * 
	 * @param o
	 * @param field
	 * @return
	 * @throws Exception
	 */
	public static Object getField(Object o , String field)throws Exception{
		Field f = o.getClass().getField(field);
		f.setAccessible(true);
		Object o2 = f.get(o);
		f.setAccessible(false);
		return o2;
	}

}
