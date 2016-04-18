package com.kirelcodes.RoboticCraft.robot;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.kirelcodes.RoboticCraft.pathFinders.LighterPathfinder;
import com.kirelcodes.RoboticCraft.pathFinders.RandomStrollPathfinder;
import com.kirelcodes.RoboticCraft.utils.ItemStackUtils;

public class RobotLighter extends RobotBase {
	private boolean isLightning;

	public RobotLighter(Location loc, Player p) {
		super(loc, p.getUniqueId());
		try {
			getArmorStand().setHelmet(ItemStackUtils.getSkullFromURL(
					"http://textures.minecraft.net/texture/79f330f080a2a3d1a8adab3e67f3ed3811658b287305db4b5c4a6097757535",
					"Robot"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setLightning(boolean isLightning) {
		this.isLightning = isLightning;
	}

	public boolean isLightning() {
		return isLightning;
	}

	@Override
	protected void addPaths() {
		pathManager.addPath(new RandomStrollPathfinder(this));
		pathManager.addPath(new LighterPathfinder(this));
	}
}
