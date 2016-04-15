package com.kirelcodes.RoboticCraft.robot;

import org.bukkit.Location;

import com.kirelcodes.RoboticCraft.pathFinders.LighterPathfinder;
import com.kirelcodes.RoboticCraft.pathFinders.RandomStrollPathfinder;
import com.kirelcodes.RoboticCraft.utils.ItemStackUtils;

public class RobotLighter extends RobotBase{

	public RobotLighter(Location loc) {
		super(loc);
		try {
			getArmorStand().setHelmet(ItemStackUtils.getSkullFromURL("http://textures.minecraft.net/texture/79f330f080a2a3d1a8adab3e67f3ed3811658b287305db4b5c4a6097757535", "Robot"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void addPaths() {
		pathManager.addPath(new RandomStrollPathfinder(this));
		pathManager.addPath(new LighterPathfinder(this));
	}
}
