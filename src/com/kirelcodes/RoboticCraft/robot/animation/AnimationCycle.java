package com.kirelcodes.RoboticCraft.robot.animation;

import java.util.ArrayList;

import org.bukkit.entity.ArmorStand;
import org.bukkit.scheduler.BukkitRunnable;

import com.kirelcodes.RoboticCraft.RoboticCraft;




public class AnimationCycle {
	private ArrayList<AnimationFrame> frames = new ArrayList<>();
	public AnimationCycle(AnimationFrame ... frames){
		for(AnimationFrame frame : frames)
			this.frames.add(frame);
	}
	public ArrayList<AnimationFrame> getFrames(){
		return frames;
	}
	public void runAnimation(ArmorStand armor , long delay){
		new BukkitRunnable() {
			int i = 0;
			@Override
			public void run() {
				if(i == frames.size())
					cancel();
				frames.get(i).setLocations(armor);
			}
		}.runTaskTimer(RoboticCraft.getInstance(), 0L, delay);
	}
	
}
