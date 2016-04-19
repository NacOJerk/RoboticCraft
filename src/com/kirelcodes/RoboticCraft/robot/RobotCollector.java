package com.kirelcodes.RoboticCraft.robot;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.kirelcodes.RoboticCraft.pathFinders.CollectorPathFinder;

public class RobotCollector extends RobotBase {
	private boolean isCollecting;
	private Location startBlock;

	public RobotCollector(Location loc, Player p) {
		super(loc, p.getUniqueId());
		startBlock = loc;
	}

	@Override
	protected void addPaths() {
		super.addPaths();
		pathManager.addPath(new CollectorPathFinder(this));
	}

	public boolean isCollecting() {
		return isCollecting;
	}

	public void setCollecting(boolean isCollecting) {
		this.isCollecting = isCollecting;
	}
	
	public Location getStartBlock(){
		return startBlock;
	}

}
