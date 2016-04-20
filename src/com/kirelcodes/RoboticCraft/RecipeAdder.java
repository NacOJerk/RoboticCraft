package com.kirelcodes.RoboticCraft;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import com.kirelcodes.RoboticCraft.gui.guiRobots.GUIRobotBasic;
import com.kirelcodes.RoboticCraft.gui.guiRobots.GUIRobotFarmer;
import com.kirelcodes.RoboticCraft.gui.guiRobots.GUIRobotFisher;
import com.kirelcodes.RoboticCraft.gui.guiRobots.GUIRobotHunter;
import com.kirelcodes.RoboticCraft.gui.guiRobots.GUIRobotCollector;
import com.kirelcodes.RoboticCraft.gui.guiRobots.GUIRobotLighter;
import com.kirelcodes.RoboticCraft.gui.guiRobots.GUIRobotLumberjack;
import com.kirelcodes.RoboticCraft.gui.guiRobots.GUIRobotMiner;
import com.kirelcodes.RoboticCraft.robot.RobotBase;
import com.kirelcodes.RoboticCraft.robot.RobotFarmer;
import com.kirelcodes.RoboticCraft.robot.RobotFisher;
import com.kirelcodes.RoboticCraft.robot.RobotHunter;
import com.kirelcodes.RoboticCraft.robot.RobotLighter;
import com.kirelcodes.RoboticCraft.robot.RobotLumberjack;
import com.kirelcodes.RoboticCraft.robot.RobotMiner;
import com.kirelcodes.RoboticCraft.robot.RobotCollector;
import com.kirelcodes.RoboticCraft.utils.ItemStackUtils;
import com.kirelcodes.RoboticCraft.utils.NMSClassInteracter;

public class RecipeAdder {
	private static ItemStack remoteBase, remoteFarmer, remoteHunter,
			remoteLighter, remoteLumberjack, remoteMiner, remoteFisher,
			remoteBreeder, remoteCollector;

	public static void addAll() {
		initializeItem();
		new RobotItem(RobotBase.class, remoteBase, addRecipeBasicRobot(),
				GUIRobotBasic.class);
		new RobotItem(RobotFarmer.class, remoteFarmer, addRecipeFarmerRobot(),
				GUIRobotFarmer.class);
		new RobotItem(RobotHunter.class, remoteHunter, addRecipeHunterRobot(),
				GUIRobotHunter.class);
		new RobotItem(RobotLighter.class, remoteLighter,
				addRecipeLighterRobot(), GUIRobotLighter.class);
		new RobotItem(RobotLumberjack.class, remoteLumberjack,
				addRecipeLumberjackRobot(), GUIRobotLumberjack.class);
		new RobotItem(RobotMiner.class, remoteMiner, addRecipeMinerRobot(),
				GUIRobotMiner.class);
		new RobotItem(RobotFisher.class, remoteFisher, addRecipeFisherRobot(),
				GUIRobotFisher.class);
		// new RobotItem(RobotCollector.class, remoteCollector, addRecipeCollectorRobot(), GUIRobotCollector.class);
		new RobotItem(RobotBase.class, remoteBase, addRecipeBasicRobot() , GUIRobotBasic.class);
		new RobotItem(RobotFarmer.class, remoteFarmer, addRecipeFarmerRobot() , GUIRobotFarmer.class);
		new RobotItem(RobotHunter.class, remoteHunter, addRecipeHunterRobot() , GUIRobotHunter.class);
		new RobotItem(RobotLighter.class, remoteLighter, addRecipeLighterRobot() , GUIRobotLighter.class);
		new RobotItem(RobotLumberjack.class, remoteLumberjack, addRecipeLumberjackRobot() , GUIRobotLumberjack.class);
		new RobotItem(RobotMiner.class, remoteMiner, addRecipeMinerRobot() , GUIRobotMiner.class);
		new RobotItem(RobotFisher.class, remoteFisher, addRecipeFisherRobot() , GUIRobotFisher.class);
		new RobotItem(RobotCollector.class, remoteCollector, addRecipeCollectorRobot(), GUIRobotCollector.class);
	}

	public static void initializeItem() {
		Material m = null;
		if (NMSClassInteracter.getVersion().contains("9")) {
			m = Material.END_CRYSTAL;
		} else
			m = Material.WATCH;
		remoteBase = ItemStackUtils.createItem(m, "&cRemote Control Basic",
				ChatColor.AQUA + "Remote for the basic robot");
		remoteFarmer = ItemStackUtils.createItem(m, "&cRemote Control Farmer",
				ChatColor.AQUA + "Remote for the farmer robot");
		remoteHunter = ItemStackUtils.createItem(m, "&cRemote Control Hunter",
				ChatColor.AQUA + "Remote for the hunter robot");
		remoteLighter = ItemStackUtils.createItem(m,
				"&cRemote Control Lighter", ChatColor.AQUA
						+ "Remote for the lighter robot");
		remoteLumberjack = ItemStackUtils.createItem(m,
				"&cRemote Control Lumberjack", ChatColor.AQUA
						+ "Remote for the lumberjack robot");
		remoteMiner = ItemStackUtils.createItem(m, "&cRemote Control Miner",
				ChatColor.AQUA + "Remote for the miner robot");
		remoteFisher = ItemStackUtils.createItem(m, "&cRemote Control Fisher",
				ChatColor.AQUA + "Remote for the fisher robot");
		remoteBreeder = ItemStackUtils.createItem(m,
				"&cRemote Control Breeder", ChatColor.AQUA
						+ "Remote for the breeder robot");
		remoteCollector = ItemStackUtils.createItem(m,
				"&cRemote Control Collector", ChatColor.AQUA
						+ "Remote for the collector robot");
	}

	public static ShapedRecipe addRecipeBasicRobot() {
		return new ShapedRecipe(remoteBase).shape("G G", "DSD", "III")
				.setIngredient('D', Material.GOLD_INGOT)
				.setIngredient('S', Material.ARMOR_STAND)
				.setIngredient('G', Material.REDSTONE)
				.setIngredient('I', Material.IRON_INGOT);
	}

	public static ShapedRecipe addRecipeFarmerRobot() {
		return new ShapedRecipe(remoteFarmer).shape("GAG", "DSD", "III")
				.setIngredient('D', Material.GOLD_INGOT)
				.setIngredient('S', Material.ARMOR_STAND)
				.setIngredient('G', Material.REDSTONE)
				.setIngredient('I', Material.IRON_INGOT)
				.setIngredient('A', Material.DIAMOND_HOE);
	}

	public static ShapedRecipe addRecipeHunterRobot() {
		return new ShapedRecipe(remoteHunter).shape("GAG", "DSD", "III")
				.setIngredient('D', Material.GOLD_INGOT)
				.setIngredient('S', Material.ARMOR_STAND)
				.setIngredient('G', Material.REDSTONE)
				.setIngredient('I', Material.IRON_INGOT)
				.setIngredient('A', Material.DIAMOND_SWORD);
	}

	public static ShapedRecipe addRecipeLighterRobot() {
		return new ShapedRecipe(remoteLighter).shape("GAG", "DSD", "III")
				.setIngredient('D', Material.GOLD_INGOT)
				.setIngredient('S', Material.ARMOR_STAND)
				.setIngredient('G', Material.REDSTONE)
				.setIngredient('I', Material.IRON_INGOT)
				.setIngredient('A', Material.TORCH);
	}

	public static ShapedRecipe addRecipeLumberjackRobot() {
		return new ShapedRecipe(remoteLumberjack).shape("GAG", "DSD", "III")
				.setIngredient('D', Material.GOLD_INGOT)
				.setIngredient('S', Material.ARMOR_STAND)
				.setIngredient('G', Material.REDSTONE)
				.setIngredient('I', Material.IRON_INGOT)
				.setIngredient('A', Material.GOLD_AXE);
	}

	public static ShapedRecipe addRecipeMinerRobot() {
		return new ShapedRecipe(remoteMiner).shape("GAG", "DSD", "III")
				.setIngredient('D', Material.GOLD_INGOT)
				.setIngredient('S', Material.ARMOR_STAND)
				.setIngredient('G', Material.REDSTONE)
				.setIngredient('I', Material.IRON_INGOT)
				.setIngredient('A', Material.DIAMOND_PICKAXE);
	}

	public static ShapedRecipe addRecipeFisherRobot() {
		return new ShapedRecipe(remoteFisher).shape("GAG", "DSD", "III")
				.setIngredient('D', Material.GOLD_INGOT)
				.setIngredient('S', Material.ARMOR_STAND)
				.setIngredient('G', Material.REDSTONE)
				.setIngredient('I', Material.IRON_INGOT)
				.setIngredient('A', Material.FISHING_ROD);
	}

	public static ShapedRecipe addRecipeBreederRobot() {
		return new ShapedRecipe(remoteBreeder).shape("GAG", "DSD", "III")
				.setIngredient('D', Material.GOLD_INGOT)
				.setIngredient('S', Material.ARMOR_STAND)
				.setIngredient('G', Material.REDSTONE)
				.setIngredient('I', Material.IRON_INGOT)
				.setIngredient('A', Material.WHEAT);
	}

	public static ShapedRecipe addRecipeCollectorRobot() {
		return new ShapedRecipe(remoteCollector).shape("GAG", "DSD", "III")
				.setIngredient('D', Material.GOLD_INGOT)
				.setIngredient('S', Material.ARMOR_STAND)
				.setIngredient('G', Material.REDSTONE)
				.setIngredient('I', Material.IRON_INGOT)
				.setIngredient('A', Material.HOPPER);
	}
}
