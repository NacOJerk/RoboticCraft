package com.kirelcodes.RoboticCraft.robot.animation;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.scheduler.BukkitRunnable;

import com.kirelcodes.RoboticCraft.RoboticCraft;
import com.kirelcodes.RoboticCraft.utils.NMSClassInteracter;

public class AnimationCycle {
	private ArrayList<AnimationFrame> frames = new ArrayList<>();
	private ArrayList<ArmorStand> endless = new ArrayList<>();
	public AnimationCycle(AnimationFrame... frames) {
		for (AnimationFrame frame : frames)
			this.frames.add(frame);
	}

	public ArrayList<AnimationFrame> getFrames() {
		return frames;
	}

	public void runAnimation(ArmorStand armor, long delay) {
		if(NMSClassInteracter.getVersion().contains("8")){
			new Runnable() {
				int i = 0;
				int id;
				@SuppressWarnings("deprecation")
				public void start(){
					id = Bukkit.getScheduler().scheduleAsyncRepeatingTask(RoboticCraft.getInstance(), this, 0L, delay);
				}
				public void run() {
					if (i == frames.size())
						cancel();
					frames.get(i).setLocations(armor);
					i++;
				}
				private void cancel(){
					Bukkit.getScheduler().cancelTask(id);
				}
			}.start();
			return;
		}
		new BukkitRunnable() {
			int i = 0;

			@Override
			public void run() {
				if (i == frames.size())
					cancel();
				frames.get(i).setLocations(armor);
				i++;
			}
		}.runTaskTimerAsynchronously(RoboticCraft.getInstance(), 0L, delay);
	}

	public void runAnimationCycle(ArmorStand armor, long delay) {
		endless.add(armor);
		if(NMSClassInteracter.getVersion().contains("8"))
		{
			new Runnable() {
				int i = 0;
				int id;
				@SuppressWarnings("deprecation")
				public void start(){
					id = Bukkit.getScheduler().scheduleAsyncRepeatingTask(RoboticCraft.getInstance(), this, 0L, delay);
				}
				public void run() {
					if(!endless.contains(armor))
						cancel();
					if (i == frames.size())
						i = 0;
					frames.get(i).setLocations(armor);
					i++;
				}
				private void cancel(){
					Bukkit.getScheduler().cancelTask(id);
				}
			}.start();
			return;
		}
		new BukkitRunnable() {
			int i = 0;

			@Override
			public void run() {
				if(!endless.contains(armor))
					cancel();
				if (i == frames.size())
					i = 0;
				frames.get(i).setLocations(armor);
				i++;
			}
		}.runTaskTimerAsynchronously(RoboticCraft.getInstance(), 0L, delay);
	}

	public void cancelTask(ArmorStand armor){
		ArrayList<ArmorStand> remove = new ArrayList<>();
		if(NMSClassInteracter.getVersion().contains("8"))
			return;
		for(ArmorStand ar : endless){
			if(ar.getCustomName() == null)
				continue;
			if(ar.getCustomName().equalsIgnoreCase(armor.getCustomName()))
				remove.add(ar);
		}
		endless.removeAll(remove);
	}
}
