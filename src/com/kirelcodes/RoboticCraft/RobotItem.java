package com.kirelcodes.RoboticCraft;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import com.kirelcodes.RoboticCraft.robot.RobotBase;
import com.kirelcodes.RoboticCraft.utils.ItemStackUtils;

public class RobotItem {
	private static ArrayList<RobotItem> robotItems = new ArrayList<>();
	private Class<? extends RobotBase> robotClass;
	private ItemStack item;
	private Recipe recipe;

	public RobotItem(Class<? extends RobotBase> robotClass, ItemStack item,
			Recipe recipe) {
		this.robotClass = robotClass;
		this.item = item;
		this.recipe = recipe;
		Bukkit.addRecipe(getRecipe());
		robotItems.add(this);
	}
	public Class<? extends RobotBase> getRobotClass(){
		return robotClass;
	}
	public ItemStack getItem(){
		return item;
	}
	public Recipe getRecipe(){
		return recipe;
	}
	
	public static ItemStack getItem(Class<? extends RobotBase> clazz){
		for(RobotItem robotItem : robotItems){
			if(robotItem.getClass().getName().equalsIgnoreCase(clazz.getName()))
				return robotItem.getItem();
		}
		return null;
	}
	public static RobotBase getRobot(ItemStack item , Player p) throws Exception{
		for(RobotItem robot : robotItems){
			if(robot.getItem().isSimilar(item))
				return robot.getRobotClass().getConstructor(Location.class , Player.class).newInstance(p.getLocation() , p);
		}
		return null;
	}
	public static boolean containsItem(ItemStack item){
		for(RobotItem robot : robotItems){
			if(ItemStackUtils.isSimmilarNoNBT(item, robot.getItem()))
				return true;
		}
		return false;
	}
	
}
