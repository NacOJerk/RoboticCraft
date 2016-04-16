package com.kirelcodes.RoboticCraft.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.kirelcodes.RoboticCraft.RecipeAdder;
import com.kirelcodes.RoboticCraft.gui.GUIGet;
import com.kirelcodes.RoboticCraft.robot.RobotBase;
import com.kirelcodes.RoboticCraft.robot.RobotCenter;
import com.kirelcodes.RoboticCraft.utils.NMSClassInteracter;

import static com.kirelcodes.RoboticCraft.utils.NBTRobotId.*;

public class RobotListener implements Listener {
	public RobotListener(Plugin p) {
		Bukkit.getPluginManager().registerEvents(this, p);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void placeEnder(PlayerInteractEvent e) {
		if (NMSClassInteracter.getVersion().contains("8"))
			return;
		if (e.getPlayer().getItemInHand().getType() == Material.END_CRYSTAL) {
			if(e.getPlayer().getItemInHand() == null)
				return;
			if(!e.getPlayer().getItemInHand().hasItemMeta())
				return;
			if(!e.getPlayer().getItemInHand().getItemMeta().hasDisplayName())
				return;
			if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("§cRemote Control")) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void intaractAtArmor(PlayerArmorStandManipulateEvent e) {

		if (!e.getRightClicked().getCustomName()
				.contains(ChatColor.MAGIC + "NacOJerkGalShaked-"))
			return;
		e.setCancelled(true);
	}

	@EventHandler
	public void rightClickSpawner(PlayerInteractEvent e) {
		if (!e.hasItem())
			return;
		ItemStack item = e.getItem();
		if (!RecipeAdder.containsItem(item))
			return;
		try {
			if (hasID(item))
				return;
			RobotBase robot = RecipeAdder.getRobot(item, e.getPlayer()
					.getLocation());
			ItemStack cloneItem = e.getItem();
			e.getPlayer().getInventory().remove(item);
			cloneItem = setID(cloneItem, robot.getID());
			RecipeAdder.addRemote(cloneItem);
			e.getPlayer().getInventory().addItem(cloneItem);
			e.getPlayer().updateInventory();
		} catch (Exception e1) {
			return;
		}
	}

	@EventHandler
	public void rightClickRemote(PlayerInteractEvent e) {
		if (!e.hasItem())
			return;
		ItemStack item = e.getItem();
		if (!RecipeAdder.containsItem(item))
			return;
		try {
			if (!hasID(item))
				return;
			int ID = getID(item);
			GUIGet.getGUI(RobotCenter.getRobot(ID)).openGUI(e.getPlayer());
			;
		} catch (Exception e1) {
			return;
		}
	}

}
