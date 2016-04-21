package com.kirelcodes.RoboticCraft.utils;

import static com.kirelcodes.RoboticCraft.utils.NMSClassInteracter.asBukkitCopy;
import static com.kirelcodes.RoboticCraft.utils.NMSClassInteracter.asNMSCopy;
import static com.kirelcodes.RoboticCraft.utils.NMSClassInteracter.getNMS;

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

public class ItemStackUtils {

	public static ItemStack createItem(Material m, String name, String... lore) {
		ItemStack is = new ItemStack(m);
		if (m == Material.AIR)
			return is;
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(name.replaceAll("&", "§"));
		List<String> Lore = Arrays.asList(lore);
		im.setLore(Lore);
		is.setItemMeta(im);
		return is;
	}

	public static ItemStack createItem(Material m, int i, String name,
			String... lore) {
		ItemStack is = new ItemStack(m, 1, (short) i);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(name.replaceAll("&", "§"));
		List<String> Lore = Arrays.asList(lore);
		im.setLore(Lore);
		is.setItemMeta(im);
		return is;
	}

	public static ItemStack dataItem(Material m, int d) {
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

	public static ItemStack getSkullFromURL(String url, String name)
			throws Exception {
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta sm = (SkullMeta) skull.getItemMeta();
		sm.setOwner("NacOJerk");
		skull.setItemMeta(sm);
		url = Base64Coder.encodeString("{textures:{SKIN:{url:\"" + url
				+ "\"}}}");
		GameProfile gp = new GameProfile(UUID.randomUUID(), name);
		gp.getProperties().put("textures", new Property("textures", url));

		Object isskull = asNMSCopy(skull);
		Object nbt = getNMS("NBTTagCompound").getConstructor().newInstance();
		Method serialize = getNMS("GameProfileSerializer").getMethod(
				"serialize", getNMS("NBTTagCompound"), GameProfile.class);
		serialize.invoke(null, nbt, gp);
		Object nbtr = isskull.getClass().getMethod("getTag").invoke(isskull);
		nbtr.getClass().getMethod("set", String.class, getNMS("NBTBase"))
				.invoke(nbtr, "SkullOwner", nbt);
		isskull.getClass().getMethod("setTag", getNMS("NBTTagCompound"))
				.invoke(isskull, nbtr);
		skull = asBukkitCopy(isskull);
		return skull;
	}

	public static boolean isSimmilarNoNBT(ItemStack item, ItemStack itemC) {
		if(item.getType() != itemC.getType())
			return false;
		if(item.getDurability() != itemC.getDurability())
			return false;
		
		if (!(item.hasItemMeta() && itemC.hasItemMeta()))
			return false;
		if (item.hasItemMeta()) {
			if (!(item.getItemMeta().hasDisplayName() && itemC.getItemMeta()
					.hasDisplayName()))
				if (item.getItemMeta().hasDisplayName()
						|| itemC.getItemMeta().hasDisplayName())
					return false;
			if (!(item.getItemMeta().hasLore() && itemC.getItemMeta().hasLore()))
				if (item.getItemMeta().hasLore()
						|| itemC.getItemMeta().hasLore())
					return false;
			if (item.getItemMeta().hasDisplayName()) {
				if (!item.getItemMeta().getDisplayName()
						.equalsIgnoreCase(itemC.getItemMeta().getDisplayName()))
					return false;
			}
			if (item.getItemMeta().hasLore()) {
				if (item.getItemMeta().getLore().size() != itemC.getItemMeta()
						.getLore().size())
					return false;
				List<String> loreA = item.getItemMeta().getLore();
				List<String> loreB = itemC.getItemMeta().getLore();

				for (int i = 0; i < loreA.size(); i++) {
					if (!loreA.get(i).equalsIgnoreCase(loreB.get(i)))
						return false;
				}
			}
		}
		return true;
	}
}
