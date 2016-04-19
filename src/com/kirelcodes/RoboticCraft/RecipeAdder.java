package com.kirelcodes.RoboticCraft;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import com.kirelcodes.RoboticCraft.robot.RobotBase;
import com.kirelcodes.RoboticCraft.robot.RobotBreeder;
import com.kirelcodes.RoboticCraft.robot.RobotFarmer;
import com.kirelcodes.RoboticCraft.robot.RobotFisher;
import com.kirelcodes.RoboticCraft.robot.RobotHunter;
import com.kirelcodes.RoboticCraft.robot.RobotLighter;
import com.kirelcodes.RoboticCraft.robot.RobotLumberjack;
import com.kirelcodes.RoboticCraft.robot.RobotMiner;
import com.kirelcodes.RoboticCraft.utils.ItemStackUtils;
import com.kirelcodes.RoboticCraft.utils.NMSClassInteracter;

public class RecipeAdder {
	private static ItemStack remoteBase, remoteFarmer, remoteHunter,
			remoteLighter, remoteLumberjack, remoteMiner , remoteFisher , remoteBreeder;
	private static ArrayList<ItemStack> items;
	
	public static void addAll(){
		items = new ArrayList<>();
		initializeItem();
		addRecipeBasicRobot();
		addRecipeFarmerRobot();
		addRecipeHunterRobot();
		addRecipeLighterRobot();
		addRecipeLumberjackRobot();
		addRecipeMinerRobot();
		//addRecipeFisherRobot();
		//addRecipeBreederRobot();
	}
	
	
	
	
	public static void initializeItem(){
		Material m = null;
		if(NMSClassInteracter.getVersion().contains("9")){
			m = Material.END_CRYSTAL;
		}else
			m = Material.WATCH;
		remoteBase = ItemStackUtils.createItem(m, "&cRemote Control Basic", ChatColor.AQUA + "Remote for the basic robot");
		items.add(remoteBase);
		remoteFarmer = ItemStackUtils.createItem(m, "&cRemote Control Farmer", ChatColor.AQUA + "Remote for the farmer robot");
		items.add(remoteFarmer);
		remoteHunter = ItemStackUtils.createItem(m, "&cRemote Control Hunter", ChatColor.AQUA + "Remote for the hunter robot");
		items.add(remoteHunter);
		remoteLighter = ItemStackUtils.createItem(m, "&cRemote Control Lighter", ChatColor.AQUA + "Remote for the lighter robot");
		items.add(remoteLighter);
		remoteLumberjack = ItemStackUtils.createItem(m, "&cRemote Control Lumberjack", ChatColor.AQUA + "Remote for the lumberjack robot");
		items.add(remoteLumberjack);
		remoteMiner = ItemStackUtils.createItem(m, "&cRemote Control Miner", ChatColor.AQUA + "Remote for the miner robot");
		items.add(remoteMiner);
		remoteFisher = ItemStackUtils.createItem(m, "&cRemote Fisher Basic", ChatColor.AQUA + "Remote for the fisher robot");
		items.add(remoteFisher);
		remoteBreeder = ItemStackUtils.createItem(m, "&cRemote Breeder Basic", ChatColor.AQUA + "Remote for the breeder robot");
		items.add(remoteBreeder);
	}
	public static boolean containsItem(ItemStack item){
		for(ItemStack itemC : items)
			if(itemC.isSimilar(item))
				return true;
		return false;
	}
	public static void addRemote(ItemStack item){
		items.add(item);
	}
	public static ItemStack getItem(RobotBase robot){
		if(robot.getClass().getName().equalsIgnoreCase(RobotBase.class.getName())){
			return remoteBase;
		}
		if(robot.getClass().getName().equalsIgnoreCase(RobotMiner.class.getName())){
			return remoteMiner;
		}
		if(robot.getClass().getName().equalsIgnoreCase(RobotFarmer.class.getName())){
			return remoteFarmer;
		}
		if(robot.getClass().getName().equalsIgnoreCase(RobotHunter.class.getName())){
			return remoteHunter;
		}
		if(robot.getClass().getName().equalsIgnoreCase(RobotLighter.class.getName())){
			return remoteLighter;
		}
		if(robot.getClass().getName().equalsIgnoreCase(RobotLumberjack.class.getName())){
			return remoteLumberjack;
		}
		if(robot.getClass().getName().equalsIgnoreCase(RobotFisher.class.getName())){
			return remoteFisher;
		}
		if(robot.getClass().getName().equalsIgnoreCase(RobotBreeder.class.getName())){
			return remoteBreeder;
		}
		return null;
	}
	public static RobotBase getRobot(ItemStack item , Player p){
		if(remoteBase.isSimilar(item))
			return new RobotBase(p.getLocation(), p);
		if(remoteFarmer.isSimilar(item))
			return new RobotFarmer(p.getLocation(), p);
		if(remoteHunter.isSimilar(item))
			return new RobotHunter(p.getLocation(), p);
		if(remoteLighter.isSimilar(item))
			return new RobotLighter(p.getLocation(), p);
		if(remoteLumberjack.isSimilar(item))
			return new RobotLumberjack(p.getLocation(), p);
		if(remoteMiner.isSimilar(item))
			return new RobotMiner(p.getLocation(), p);
		if(remoteFisher.isSimilar(item))
			return new RobotFisher(p.getLocation(), p);
		if(remoteBreeder.isSimilar(item))
			return new RobotBreeder(p.getLocation(), p);
		return null;
	}
	
	public static void addRecipeBasicRobot() {
		Bukkit.addRecipe(new ShapedRecipe(remoteBase)
				.shape("G G", "DSD", "DDD")
				.setIngredient('D', Material.DIAMOND_BLOCK)
				.setIngredient('S', Material.ARMOR_STAND)
				.setIngredient('G', Material.GOLD_BLOCK));
	}

	public static void addRecipeFarmerRobot() {
		Bukkit.addRecipe(new ShapedRecipe(remoteFarmer)
				.shape("GAG", "DSD", "DDD")
				.setIngredient('D', Material.DIAMOND_BLOCK)
				.setIngredient('S', Material.ARMOR_STAND)
				.setIngredient('G', Material.GOLD_BLOCK)
				.setIngredient('A', Material.DIAMOND_HOE));
	}
	public static void addRecipeHunterRobot() {
		Bukkit.addRecipe(new ShapedRecipe(remoteHunter)
				.shape("GAG", "DSD", "DDD")
				.setIngredient('D', Material.DIAMOND_BLOCK)
				.setIngredient('S', Material.ARMOR_STAND)
				.setIngredient('G', Material.GOLD_BLOCK)
				.setIngredient('A', Material.DIAMOND_SWORD));
	}
	public static void addRecipeLighterRobot() {
		Bukkit.addRecipe(new ShapedRecipe(remoteLighter)
				.shape("GAG", "DSD", "DDD")
				.setIngredient('D', Material.DIAMOND_BLOCK)
				.setIngredient('S', Material.ARMOR_STAND)
				.setIngredient('G', Material.GOLD_BLOCK)
				.setIngredient('A', Material.TORCH));
	}
	public static void addRecipeLumberjackRobot() {
		Bukkit.addRecipe(new ShapedRecipe(remoteLumberjack)
				.shape("GAG", "DSD", "DDD")
				.setIngredient('D', Material.DIAMOND_BLOCK)
				.setIngredient('S', Material.ARMOR_STAND)
				.setIngredient('G', Material.GOLD_BLOCK)
				.setIngredient('A', Material.GOLD_AXE));
	}
	public static void addRecipeMinerRobot() {
		Bukkit.addRecipe(new ShapedRecipe(remoteMiner)
				.shape("GAG", "DSD", "DDD")
				.setIngredient('D', Material.DIAMOND_BLOCK)
				.setIngredient('S', Material.ARMOR_STAND)
				.setIngredient('G', Material.GOLD_BLOCK)
				.setIngredient('A', Material.DIAMOND_PICKAXE));
	}
	public static void addRecipeFisherRobot() {
		Bukkit.addRecipe(new ShapedRecipe(remoteFisher)
				.shape("GAG", "DSD", "DDD")
				.setIngredient('D', Material.DIAMOND_BLOCK)
				.setIngredient('S', Material.ARMOR_STAND)
				.setIngredient('G', Material.GOLD_BLOCK)
				.setIngredient('A', Material.FISHING_ROD));
	}
	public static void addRecipeBreederRobot() {
		Bukkit.addRecipe(new ShapedRecipe(remoteBreeder)
				.shape("GAG", "DSD", "DDD")
				.setIngredient('D', Material.DIAMOND_BLOCK)
				.setIngredient('S', Material.ARMOR_STAND)
				.setIngredient('G', Material.GOLD_BLOCK)
				.setIngredient('A', Material.WHEAT));
	}
}
