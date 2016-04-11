package com.kirelcodes.RoboticCraft.Robot;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;

import static com.kirelcodes.RoboticCraft.Utils.NMSClassInteracter.*;

public class RobotBase {
	private Chicken chick;
	private ArmorStand armorStand;
	private boolean isStuck;
	private int ID;
	private int fuel;

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
		this.armorStand = (ArmorStand) getWorld().spawnEntity(getLocation(), EntityType.ARMOR_STAND); 
		setFuel(100);
		this.ID = RobotCenter.addRobot(this);
	}

	public Location getLocation() {
		return chick.getLocation();
	}

	public World getWorld() {
		return getLocation().getWorld();
	}

	private Object getNMSHandle() throws Exception {
		return chick.getClass().getMethod("getHandle").invoke(chick);
	}

	private void clearChicken() throws Exception {
		Object goalSelector = getField(getNMSHandle(), "goalSelector");
		List<?> goalSelectorA = (List<?>) getField(goalSelector, "b");
		List<?> goalSelectorB = (List<?>) getField(goalSelector, "c");
		goalSelectorA.clear();
		goalSelectorB.clear();
		getNMSHandle().getClass()
				.getMethod("setSize", float.class, float.class)
				.invoke(getNMSHandle(), 0.5F, 1.975F);
		getNMSHandle().getClass().getMethod("setInvisible", boolean.class).invoke(getNMSHandle(), true);
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
	 * Setts the location the robot should move to
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
		if(path == null){
			return false;
		}
		navagation.getClass().getMethod("a", getNMS("PathEntity") , double.class).invoke(navagation, path , 1.0D);
		navagation.getClass().getMethod("a", double.class).invoke(navagation, 2.0D);
		return true;
	}

	public Chicken getChicken() {
		return chick;
	}

	public ArmorStand getArmoStand() {
		return armorStand;
	}

	public boolean isStuck() {
		return isStuck;
	}

	public int getID() {
		return ID;
	}

	public int getFuel() {
		return fuel;
	}
	/**
	 * Between 0 to 100 
	 * @param fuel
	 */
	public void setFuel(int fuel){
		if(!(0 <= fuel && fuel <= 100)){
			//Error logger here
			return;
		}
		this.fuel = fuel;
	}
	public boolean hasFuel(){
		return 0 < fuel;
	}
}
