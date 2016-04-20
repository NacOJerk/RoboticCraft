package com.kirelcodes.RoboticCraft.utils;

import org.bukkit.inventory.ItemStack;

import static com.kirelcodes.RoboticCraft.utils.NMSClassInteracter.*;

public class NBTRobotId {
	public static ItemStack setID(ItemStack item, int ID) throws Exception {
		Object nmsIS = asNMSCopy(item);
		boolean hasNBTC = (boolean) nmsIS.getClass().getMethod("hasTag")
				.invoke(nmsIS);
		Object nbtTag = (hasNBTC) ? nmsIS.getClass().getMethod("getTag")
				.invoke(nmsIS) : getNMS("NBTTagCompound").getConstructor()
				.newInstance();
		Object nbtInt = getNMS("NBTTagInt").getConstructor(int.class).newInstance(ID);
		nbtTag.getClass().getMethod("set", String.class, getNMS("NBTBase"))
				.invoke(nbtTag, "RobotID", nbtInt);
		nmsIS.getClass().getMethod("setTag", nbtTag.getClass())
				.invoke(nmsIS, nbtTag);
		ItemStack result = asBukkitCopy(nmsIS);
		return result;
	}
	public static ItemStack clearNBT(ItemStack item){
		String[] s = new String[item.getItemMeta().getLore().size()];
		item.getItemMeta().getLore().toArray(s);
		return ItemStackUtils.createItem(item.getType(), item.getDurability(), item.getItemMeta().getDisplayName(), s);
	}
	public static boolean hasID(ItemStack is) throws Exception{
		Object nmsIS = asNMSCopy(is);
		boolean hasNBTC = (boolean) nmsIS.getClass().getMethod("hasTag")
				.invoke(nmsIS);
		if (!hasNBTC)
			return false;
		Object nbtTag = nmsIS.getClass().getMethod("getTag").invoke(nmsIS);
		if (!(boolean) nbtTag.getClass().getMethod("hasKey", String.class)
				.invoke(nbtTag, "RobotID"))
			return false;
		return true;
	}
	public static int getID(ItemStack is) throws Exception{
		if(!hasID(is))
			return 0;
		Object nmsIS = asNMSCopy(is);
		Object nbtTag = nmsIS.getClass().getMethod("getTag").invoke(nmsIS);
		return (int) nbtTag.getClass().getMethod("getInt", String.class)
				.invoke(nbtTag, "RobotID");
	}
	public static ItemStack setFuel(ItemStack item, int fuel) throws Exception {
		Object nmsIS = asNMSCopy(item);
		boolean hasNBTC = (boolean) nmsIS.getClass().getMethod("hasTag")
				.invoke(nmsIS);
		Object nbtTag = (hasNBTC) ? nmsIS.getClass().getMethod("getTag")
				.invoke(nmsIS) : getNMS("NBTTagCompound").getConstructor()
				.newInstance();
		Object nbtInt = getNMS("NBTTagInt").getConstructor(int.class).newInstance(fuel);
		nbtTag.getClass().getMethod("set", String.class, getNMS("NBTBase"))
				.invoke(nbtTag, "RobotFuel", nbtInt);
		nmsIS.getClass().getMethod("setTag", nbtTag.getClass())
				.invoke(nmsIS, nbtTag);
		ItemStack result = asBukkitCopy(nmsIS);
		return result;
	}

	public static boolean hasFuel(ItemStack is) throws Exception{
		Object nmsIS = asNMSCopy(is);
		boolean hasNBTC = (boolean) nmsIS.getClass().getMethod("hasTag")
				.invoke(nmsIS);
		if (!hasNBTC)
			return false;
		Object nbtTag = nmsIS.getClass().getMethod("getTag").invoke(nmsIS);
		if (!(boolean) nbtTag.getClass().getMethod("hasKey", String.class)
				.invoke(nbtTag, "RobotFuel"))
			return false;
		return true;
	}
	public static int getFuel(ItemStack is) throws Exception{
		if(!hasFuel(is))
			return 0;
		Object nmsIS = asNMSCopy(is);
		Object nbtTag = nmsIS.getClass().getMethod("getTag").invoke(nmsIS);
		return (int) nbtTag.getClass().getMethod("getInt", String.class)
				.invoke(nbtTag, "RobotFuel");
	}

}
