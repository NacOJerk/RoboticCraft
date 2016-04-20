package com.kirelcodes.RoboticCraft.pathFinders;

import java.util.ArrayList;

import org.bukkit.scheduler.BukkitRunnable;

import com.kirelcodes.RoboticCraft.RoboticCraft;
import com.kirelcodes.RoboticCraft.robot.RobotBase;


public class PathManager extends BukkitRunnable{
	private ArrayList<BasicPathfinder> paths;
	private ArrayList<BasicPathfinder> running;
	private RobotBase robot;
	public PathManager(RobotBase robot){
		paths = new ArrayList<>();
		running = new ArrayList<>();
		runTaskTimer(RoboticCraft.getInstance(), 0L, 1L);
		this.robot = robot;
	}
	public void addPath(BasicPathfinder path){
		paths.add(path);
	}
	public void clearPaths(){
		paths = new ArrayList<>();
		running = new ArrayList<>();
	}
	@Override
	public void run() {
		if(!robot.hasFuel())
			return;
		for(BasicPathfinder path : paths){
			
			if(!running.contains(path)){
				if(path.shouldStart()){
					running.add(path);
					path.onStart();
				}
			}//Could be changed cus of the code above must double check :P
			if(running.contains(path)){
				if(!path.keepWorking()){
					running.remove(path);
					continue;
				}
				path.updateTask();
				path.afterTask();
			}
		}
	}
}
