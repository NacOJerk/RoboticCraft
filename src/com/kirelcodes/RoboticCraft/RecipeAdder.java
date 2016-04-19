package com.kirelcodes.RoboticCraft;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import com.kirelcodes.RoboticCraft.robot.RobotBase;
import com.kirelcodes.RoboticCraft.robot.RobotCollector;
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
			remoteLighter, remoteLumberjack, remoteMiner , remoteFisher , remoteBreeder, remoteCollector;
	
	public static void addAll(){
		initializeItem();
		new RobotItem(RobotBase.class, remoteBase, addRecipeBasicRobot());
		new RobotItem(RobotFarmer.class, remoteFarmer, addRecipeFarmerRobot());
		new RobotItem(RobotHunter.class, remoteHunter, addRecipeHunterRobot());
		new RobotItem(RobotLighter.class, remoteLighter, addRecipeLighterRobot());
		new RobotItem(RobotLumberjack.class, remoteLumberjack, addRecipeLumberjackRobot());
		new RobotItem(RobotMiner.class, remoteMiner, addRecipeMinerRobot());
		new RobotItem(RobotFisher.class, remoteFisher, addRecipeFisherRobot());
		new RobotItem(RobotCollector.class, remoteCollector, addRecipeCollectorRobot());
	}
		
	public static void initializeItem(){
		Material m = null;
		if(NMSClassInteracter.getVersion().contains("9"))
			m = Material.END_CRYSTAL;
		else
			m = Material.WATCH;
		remoteBase = ItemStackUtils.createItem(m, "&cRemote Control Basic", ChatColor.AQUA + "Remote for the basic robot");
		remoteFarmer = ItemStackUtils.createItem(m, "&cRemote Control Farmer", ChatColor.AQUA + "Remote for the farmer robot");
		remoteHunter = ItemStackUtils.createItem(m, "&cRemote Control Hunter", ChatColor.AQUA + "Remote for the hunter robot");
		remoteLighter = ItemStackUtils.createItem(m, "&cRemote Control Lighter", ChatColor.AQUA + "Remote for the lighter robot");
		remoteLumberjack = ItemStackUtils.createItem(m, "&cRemote Control Lumberjack", ChatColor.AQUA + "Remote for the lumberjack robot");
		remoteMiner = ItemStackUtils.createItem(m, "&cRemote Control Miner", ChatColor.AQUA + "Remote for the miner robot");
		remoteFisher = ItemStackUtils.createItem(m, "&cRemote Control Fisher", ChatColor.AQUA + "Remote for the fisher robot");
		remoteBreeder = ItemStackUtils.createItem(m, "&cRemote Control Breeder", ChatColor.AQUA + "Remote for the breeder robot");
		remoteCollector = ItemStackUtils.createItem(m, "&cRemote Control Collector", ChatColor.AQUA + "Remote for the collector robot");
	}
	
	public static ShapedRecipe addRecipeBasicRobot() {
		return new ShapedRecipe(remoteBase)
				.shape("G G", "DSD", "DDD")
				.setIngredient('D', Material.DIAMOND_BLOCK)
				.setIngredient('S', Material.ARMOR_STAND)
				.setIngredient('G', Material.GOLD_BLOCK);
	}

	public static ShapedRecipe addRecipeFarmerRobot() {
		return new ShapedRecipe(remoteFarmer)
				.shape("GAG", "DSD", "DDD")
				.setIngredient('D', Material.DIAMOND_BLOCK)
				.setIngredient('S', Material.ARMOR_STAND)
				.setIngredient('G', Material.GOLD_BLOCK)
				.setIngredient('A', Material.DIAMOND_HOE);
	}
	public static ShapedRecipe addRecipeHunterRobot() {
		return new ShapedRecipe(remoteHunter)
				.shape("GAG", "DSD", "DDD")
				.setIngredient('D', Material.DIAMOND_BLOCK)
				.setIngredient('S', Material.ARMOR_STAND)
				.setIngredient('G', Material.GOLD_BLOCK)
				.setIngredient('A', Material.DIAMOND_SWORD);
	}
	public static ShapedRecipe addRecipeLighterRobot() {
		return new ShapedRecipe(remoteLighter)
				.shape("GAG", "DSD", "DDD")
				.setIngredient('D', Material.DIAMOND_BLOCK)
				.setIngredient('S', Material.ARMOR_STAND)
				.setIngredient('G', Material.GOLD_BLOCK)
				.setIngredient('A', Material.TORCH);
	}
	public static ShapedRecipe addRecipeLumberjackRobot() {
		return new ShapedRecipe(remoteLumberjack)
				.shape("GAG", "DSD", "DDD")
				.setIngredient('D', Material.DIAMOND_BLOCK)
				.setIngredient('S', Material.ARMOR_STAND)
				.setIngredient('G', Material.GOLD_BLOCK)
				.setIngredient('A', Material.GOLD_AXE);
	}
	public static ShapedRecipe addRecipeMinerRobot() {
		return new ShapedRecipe(remoteMiner)
				.shape("GAG", "DSD", "DDD")
				.setIngredient('D', Material.DIAMOND_BLOCK)
				.setIngredient('S', Material.ARMOR_STAND)
				.setIngredient('G', Material.GOLD_BLOCK)
				.setIngredient('A', Material.DIAMOND_PICKAXE);
	}
	public static ShapedRecipe addRecipeFisherRobot() {
		return new ShapedRecipe(remoteFisher)
				.shape("GAG", "DSD", "DDD")
				.setIngredient('D', Material.DIAMOND_BLOCK)
				.setIngredient('S', Material.ARMOR_STAND)
				.setIngredient('G', Material.GOLD_BLOCK)
				.setIngredient('A', Material.FISHING_ROD);
	}
	public static ShapedRecipe addRecipeBreederRobot() {
		return new ShapedRecipe(remoteBreeder)
				.shape("GAG", "DSD", "DDD")
				.setIngredient('D', Material.DIAMOND_BLOCK)
				.setIngredient('S', Material.ARMOR_STAND)
				.setIngredient('G', Material.GOLD_BLOCK)
				.setIngredient('A', Material.WHEAT);
	}
	public static ShapedRecipe addRecipeCollectorRobot() {
		return new ShapedRecipe(remoteCollector)
				.shape("GAG", "DSD", "DDD")
				.setIngredient('D', Material.DIAMOND_BLOCK)
				.setIngredient('S', Material.ARMOR_STAND)
				.setIngredient('G', Material.GOLD_BLOCK)
				.setIngredient('A', Material.HOPPER);
	}
}
