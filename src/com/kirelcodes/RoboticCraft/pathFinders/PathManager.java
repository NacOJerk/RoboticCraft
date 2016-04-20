package com.kirelcodes.RoboticCraft.pathFinders;

import java.util.ArrayList;

import org.bukkit.Bukkit;

import com.kirelcodes.RoboticCraft.RoboticCraft;


public class PathManager implements Runnable{
	private ArrayList<BasicPathfinder> paths;
	private ArrayList<BasicPathfinder> running;
	private int ID;
	@SuppressWarnings("deprecation")
	public PathManager(){
		paths = new ArrayList<>();
		running = new ArrayList<>();
		ID = Bukkit.getScheduler().scheduleAsyncRepeatingTask(RoboticCraft.getInstance(), this, 0L, 1L);
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
	public void cancel(){
		Bukkit.getScheduler().cancelTask(ID);
	}
}
