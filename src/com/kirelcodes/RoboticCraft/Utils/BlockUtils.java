package com.kirelcodes.RoboticCraft.utils;

import java.lang.reflect.Field;

import org.bukkit.Material;
import org.bukkit.block.Block;

import static com.kirelcodes.RoboticCraft.utils.NMSClassInteracter.*;

public class BlockUtils {
	public static double getBlockStrength(Block b) {
		float strength = -1;
		try {
			Object nmsBlock = getOBC("util.CraftMagicNumbers").getMethod(
					"getBlock", Material.class).invoke(null, b.getType());
			Field f = getNMS("Block").getDeclaredField("strength");
			f.setAccessible(true);
			strength = f.getFloat(nmsBlock);
			f.setAccessible(false);
		} catch (Exception e) {
			return -1;
		}
		return strength;
	}
	public static double getMineTime(Block b){
		return getBlockStrength(b) / 5.32;
	}
}
