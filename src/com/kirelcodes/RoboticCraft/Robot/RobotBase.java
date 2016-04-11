package com.kirelcodes.RoboticCraft.Robot;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.kirelcodes.RoboticCraft.RoboticCraft;

import static com.kirelcodes.RoboticCraft.Utils.NMSClassInteracter.*;

/**
 * 
 * @author NacOJerk
 *
 */
public class RobotBase {
	private Chicken chick;
	private ArmorStand armorStand;
	private boolean isStuck;
	private int ID;
	private int fuel;
	private boolean isFollowing;
	private Entity followTarget;
	private RobotTask robotTask;
	private Inventory invetory;
	
	
	/**
	 * Responsible for the armor stand teleportation and target following
	 * 
	 * @author NacOJerk
	 *
	 */
	public class RobotTask implements Runnable {
		private int ID;
		private int mark;
		private int stuckCalc;
		private Location previus;
		public RobotTask() {
			this.ID = Bukkit.getScheduler().scheduleSyncRepeatingTask(
					RoboticCraft.getInstance(), this, 0L, 1L);
			mark = 0;
		}

		@Override
		public void run() {
			if(!hasFuel()){
				try {
					setTargetLocation(getLocation());
				} catch (Exception e) {
					//Error thrower here
				}
			}
			if ((mark % 5) == 0) {
				if (isFollowing()) {
					setFollow();
				}
			}
			if ((mark % 20) == 0) {
				if(isFollowing()){
				if (previus.distance(getLocation()) <= 2) 
					stuckCalc++;
				else{
					stuckCalc = 0;
					isStuck = false;
				}
				if(stuckCalc >= 3){
					isStuck = true;
				}
				if(stuckCalc == 5){
					getNavigator().teleport(getFollowTarget().getLocation().clone().add(1 , 0 , 1));
					isStuck = false;
					stuckCalc = 0;
				}
				}
			}
			
			getArmoStand().teleport(getLocation());
			this.previus = getLocation();
			mark++;
		}

		private void setFollow() {
			try {
				setTargetLocation(getFollowTarget().getLocation().clone()
						.add(1, 0, 1));
			} catch (Exception e) {
				// Error thrower here
			}
		}

		public void cancel() {
			Bukkit.getScheduler().cancelTask(ID);
		}
	}

	/**
	 * Spawns the robot
	 * 
	 * @param loc
	 *            the location where the robot would be spawned
	 */
	public RobotBase(Location loc) {
		Chicken chick = (Chicken) loc.getWorld().spawnEntity(loc,
				EntityType.CHICKEN);
		this.chick = chick;
		try {
			clearChicken();
		} catch (Exception e) {
			chick.remove();
			return;
		}
		this.armorStand = (ArmorStand) getWorld().spawnEntity(getLocation(),
				EntityType.ARMOR_STAND);
		setFuel(100);
		this.invetory = Bukkit.createInventory(null, 9*3);
		this.robotTask = new RobotTask();
		this.ID = RobotCenter.addRobot(this);		
	}
	

	/**
	 * 
	 * @return the location of the navigator (chicken)
	 */
	public Location getLocation() {
		return chick.getLocation();
	}

	public World getWorld() {
		return getLocation().getWorld();
	}

	/**
	 * 
	 * @return an NMS version of the chicken
	 * @throws Exception
	 */
	private Object getNMSHandle() throws Exception {
		return chick.getClass().getMethod("getHandle").invoke(chick);
	}

	/**
	 * Clears the chicken pathfinders Sets its size to the armor stand one (for
	 * the navigator) Sets it invisible
	 * 
	 * @throws Exception
	 */
	private void clearChicken() throws Exception {
		Object goalSelector = getField(getNMSHandle(), "goalSelector");
		LinkedHashSet<?> goalSelectorA = (LinkedHashSet<?>) getDeclaredField(goalSelector, "b");
		LinkedHashSet<?> goalSelectorB = (LinkedHashSet<?>) getDeclaredField(goalSelector, "c");
		goalSelectorA.clear();
		goalSelectorB.clear();
		getNMSHandle().getClass()
				.getMethod("setSize", float.class, float.class)
				.invoke(getNMSHandle(), 0.5F, 1.975F);
		getNMSHandle().getClass().getMethod("setInvisible", boolean.class)
				.invoke(getNMSHandle(), true);
	}

	/**
	 * Sets the speed of the chicken (Navigator)
	 * 
	 * @param speed
	 *            chicken's deafult speed is 0.25D
	 * @throws Exception
	 */
	public void setSpeed(double speed) throws Exception {
		Object MOVEMENT_SPEED = getNMS("GenericAttributes").getField(
				"MOVEMENT_SPEED").get(null);
		Object genericSpeed = getNMSHandle().getClass()
				.getMethod("getAttributeInstance", getNMS("IAttribute"))
				.invoke(getNMSHandle(), MOVEMENT_SPEED);
		genericSpeed.getClass().getMethod("setValue", double.class)
				.invoke(genericSpeed, speed);
	}

	/**
	 * Sets the location the robot should move to
	 * 
	 * @param loc
	 * @return did it start or not
	 * @throws Exception
	 */
	public boolean setTargetLocation(Location loc) throws Exception {
		Object navagation = getNMSHandle().getClass()
				.getMethod("getNavigation").invoke(getNMSHandle());
		Object path = navagation.getClass()
				.getMethod("a", double.class, double.class, double.class)
				.invoke(navagation, loc.getX(), loc.getY(), loc.getZ());
		if (path == null) {
			return false;
		}
		navagation.getClass()
				.getMethod("a", getNMS("PathEntity"), double.class)
				.invoke(navagation, path, 1.0D);
		navagation.getClass().getMethod("a", double.class)
				.invoke(navagation, 2.0D);
		return true;
	}

	/**
	 * 
	 * @return the navigator (Chicken)
	 */
	public Chicken getNavigator() {
		return chick;
	}

	/**
	 * 
	 * @return the armor which represents the robot
	 */
	public ArmorStand getArmoStand() {
		return armorStand;
	}

	/**
	 * 
	 * @return if the robot haven't moved for 3 seconds a distance bigger than 2
	 *         blocks
	 */
	public boolean isStuck() {
		return isStuck;
	}

	/**
	 * 
	 * @return the robot's ID
	 */
	public int getID() {
		return ID;
	}

	public int getFuel() {
		return fuel;
	}

	/**
	 * Between 0 to 100
	 * 
	 * @param fuel
	 */
	public void setFuel(int fuel) {
		if (!(0 <= fuel && fuel <= 100)) {
			// Error logger here
			return;
		}
		this.fuel = fuel;
	}

	public boolean hasFuel() {
		return 0 < fuel;
	}

	/**
	 * 
	 * @param en
	 *            entity which the robot would follow
	 */
	public void setFollow(Entity en) {
		this.followTarget = en;
		this.isFollowing = true;
	}

	public boolean isFollowing() {
		return isFollowing;
	}

	public Entity getFollowTarget() {
		return followTarget;
	}

	public void cancelFollow() {
		this.isFollowing = false;
		this.followTarget = null;
	}

	/**
	 * Destroys the robot and drops an armor remove (Robot remove)
	 */
	public void destroy() {
		// Should be replaced to drop the armor remote here
		remove();
	}

	/**
	 * Completely removes the robot
	 */
	public void remove() {
		this.robotTask.cancel();
		this.chick.remove();
		this.armorStand.remove();
		RobotCenter.removeRobot(getID());
	}
	public Inventory getInventory(){
		return invetory;
	}
	public HashMap<Integer, ItemStack> addItem(ItemStack ... item){
		return getInventory().addItem(item);
	}
	public void mineBlock(Block b){
	}
}
