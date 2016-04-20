package com.kirelcodes.RoboticCraft.robot;

import static com.kirelcodes.RoboticCraft.utils.NMSClassInteracter.getDeclaredField;
import static com.kirelcodes.RoboticCraft.utils.NMSClassInteracter.getField;
import static com.kirelcodes.RoboticCraft.utils.NMSClassInteracter.getNMS;
import static com.kirelcodes.RoboticCraft.utils.NMSClassInteracter.getVersion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import com.kirelcodes.RoboticCraft.RoboticCraft;
import com.kirelcodes.RoboticCraft.pathFinders.FollowPathfinder;
import com.kirelcodes.RoboticCraft.pathFinders.PathManager;
import com.kirelcodes.RoboticCraft.pathFinders.RandomStrollPathfinder;
import com.kirelcodes.RoboticCraft.robot.animation.AnimationFrame;
import com.kirelcodes.RoboticCraft.robot.animation.AnimationManager;
import com.kirelcodes.RoboticCraft.utils.ItemStackUtils;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.ps.PS;

/**
 * 
 * @author NacOJerk
 *
 */
public class RobotBase implements InventoryHolder {
	protected AnimationFrame deafult = new AnimationFrame().setBody(new EulerAngle(0, 0, 0))
			.setHead(new EulerAngle(0, 0, 0))
			.setLeftHand(new EulerAngle(0, 0, 0))
			.setLeftLeg(new EulerAngle(0, 0, 0))
			.setRightHand(new EulerAngle(0, 0, 0))
			.setRightLeg(new EulerAngle(0, 0, 0));
	private Chicken chick;
	private ArmorStand armorStand;
	private boolean isStuck;
	private int ID;
	private int fuel;
	private boolean isFollowing;
	private Entity followTarget;
	private RobotTask robotTask;
	private Inventory invetory;
	private Object nmsHandel;
	private UUID owner;
	protected PathManager pathManager;
	private Location targetLocation , previus;

	/**
	 * Responsible for the armor stand teleportation and target following
	 * 
	 * @author NacOJerk
	 *
	 */
	public class RobotTask implements Runnable {
		private int ID;

		// private int mark; not needed
		// private int stuckCalc;
		// private Location previus;

		public RobotTask() {
			this.ID = Bukkit.getScheduler().scheduleSyncRepeatingTask(
					RoboticCraft.getInstance(), this, 0L, 1L);
			// mark = 0;
		}

		@Override
		public void run() {
			if (!hasFuel()) {
				try {
					setTargetLocation(getLocation());
				} catch (Exception e) {
					// Error thrower here
				}
			}
			if (onTargetLocation()) {
				AnimationManager.walk.cancelTask(getArmorStand());
				deafult.setLocations(getArmorStand());
			}
			if(previus.distanceSquared(getLocation()) < 1){
				AnimationManager.walk.cancelTask(getArmorStand());
				deafult.setLocations(getArmorStand());
			}
			getArmorStand().teleport(getLocation());
			previus = getLocation();
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
	 * @param owner
	 * 
	 */
	public RobotBase(Location loc, UUID owner) {
		Chicken chick = null;
		if (getVersion().contains("9")) {
			try {
				chick = getSilentChicken(loc);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else
			chick = (Chicken) loc.getWorld().spawnEntity(loc,
					EntityType.CHICKEN);
		this.chick = chick;
		this.chick.setMaxHealth(Integer.MAX_VALUE);
		this.chick.setHealth(this.chick.getMaxHealth());
		final Chicken fchick = chick;
		Bukkit.getScheduler().scheduleSyncDelayedTask(
				RoboticCraft.getInstance(), new Runnable() {
					public void run() {
						try {
							clearChicken();
						} catch (Exception e) {
							fchick.remove();
							return;
						}
					}
				}, 2L);
		this.armorStand = (ArmorStand) getWorld().spawnEntity(getLocation(),
				EntityType.ARMOR_STAND);
		this.armorStand.setBasePlate(false);
		this.armorStand.setArms(true);
		try {
			this.armorStand
					.setHelmet(ItemStackUtils
							.getSkullFromURL(
									"http://textures.minecraft.net/texture/c510d9b61ca333d2946c61a26cb17e374d4adb573b46afdebaf89f65ba5d4ae2",
									"Robot"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.armorStand.setBoots(new ItemStack(Material.IRON_BOOTS));
		this.armorStand.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		this.armorStand.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		this.armorStand.setMarker(true);
		setFuel(100);
		this.pathManager = new PathManager();
		addPaths();
		this.invetory = Bukkit.createInventory(this, 9 * 3, ChatColor.RED
				+ "Robot's Inventory");
		this.robotTask = new RobotTask();
		this.ID = RobotCenter.addRobot(this);
		getArmorStand().setCustomName(
				ChatColor.MAGIC + "NacOJerkGalShaked-" + ID);
		getArmorStand().setCustomNameVisible(false);
		this.previus = getLocation();
		this.owner = owner;
		if (!checkAllowed(loc))
			remove();

	}

	/**
	 * Spawns the robot
	 * 
	 * @param loc
	 *            the location where the robot would be spawned
	 * @param owner
	 * 
	 */
	public RobotBase(Location loc, Player owner) {
		this(loc, owner.getUniqueId());
	}

	/**
	 * Adds the paths to the code
	 */
	protected void addPaths() {
		pathManager.addPath(new RandomStrollPathfinder(this));
		pathManager.addPath(new FollowPathfinder(this));
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

		return (nmsHandel == null) ? (nmsHandel = chick.getClass()
				.getMethod("getHandle").invoke(chick)) : nmsHandel;
	}

	/**
	 * Clears the chicken pathfinders Sets its size to the armor stand one (for
	 * the navigator) Sets it invisible
	 * 
	 * @throws Exception
	 */
	private void clearChicken() throws Exception {
		// chick.setCanPickupItems(true);
		chick.setCustomName(ChatColor.MAGIC + "" + getID());
		chick.setCustomNameVisible(false);
		Object goalSelector = getField(getNMSHandle(), "goalSelector");
		if (getVersion().contains("9")) {
			LinkedHashSet<?> goalSelectorA = (LinkedHashSet<?>) getDeclaredField(
					goalSelector, "b");
			LinkedHashSet<?> goalSelectorB = (LinkedHashSet<?>) getDeclaredField(
					goalSelector, "c");
			goalSelectorA.clear();
			goalSelectorB.clear();
		} else {
			List<?> goalSelectorA = (List<?>) getDeclaredField(goalSelector,
					"b");
			List<?> goalSelectorB = (List<?>) getDeclaredField(goalSelector,
					"c");
			goalSelectorA.clear();
			goalSelectorB.clear();

		}
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
	 * Gets the speed of the chicken (Navigator)
	 * 
	 * @throws Exception
	 */
	public double getSpeed() throws Exception {
		Object MOVEMENT_SPEED = getNMS("GenericAttributes").getField(
				"MOVEMENT_SPEED").get(null);
		Object genericSpeed = getNMSHandle().getClass()
				.getMethod("getAttributeInstance", getNMS("IAttribute"))
				.invoke(getNMSHandle(), MOVEMENT_SPEED);
		return (double) genericSpeed.getClass().getMethod("getValue")
				.invoke(genericSpeed);
	}

	public boolean onTargetLocation() {
		if (targetLocation == null)
			return false;
		if (targetLocation.distanceSquared(getLocation()) < 1) {
			targetLocation = null;
			return true;
		}
		return false;
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
				.invoke(navagation, path, getSpeed());
		navagation.getClass().getMethod("a", double.class)
				.invoke(navagation, 2.0D);
		targetLocation = loc;
		AnimationManager.walk.runAnimationCycle(getArmorStand(), 7L);
		return true;
	}

	/**
	 * Get blocks in a nearby radius
	 * 
	 * @param raduis
	 * @return
	 */
	public List<Block> getNearbyBlocks(int radius) {
		ArrayList<Location> locs = new ArrayList<>();
		for (int x = getLocation().getBlockX() - radius; x < getLocation()
				.getX() + radius; x++) {
			for (int y = getLocation().getBlockY() - radius; y < getLocation()
					.getY() + radius; y++) {
				for (int z = getLocation().getBlockZ() - radius; z < getLocation()
						.getZ() + radius; z++) {
					locs.add(getLocation().getWorld().getBlockAt(x, y, z)
							.getLocation());
				}
			}
		}
		ArrayList<Block> blocks = new ArrayList<>();
		for (Location loc : orderDistance(locs))
			blocks.add(loc.getBlock());
		return blocks;
	}

	public List<Location> orderDistance(List<Location> locations) {
		Location target = getLocation();
		int size = locations.size();
		List<Location> ordered = new ArrayList<>();
		while (ordered.size() < size) {
			Location closest = locations.get(0);
			double closestDist = closest.distance(target);
			for (Location loc : locations) {
				if (loc.distance(target) < closestDist) {
					closestDist = loc.distance(target);
					closest = loc;
				}
			}
			ordered.add(closest);
			locations.remove(closest);
		}
		return ordered;
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
	public ArmorStand getArmorStand() {
		return armorStand;
	}

	/**
	 * 
	 * @return if the robot haven't moved for 3 seconds a distance bigger than 1
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

	private void setID(int ID) {
		this.ID = ID;
	}

	/**
	 * Destroys the robot and drops an armor remove (Robot remove)
	 */
	public void destroy() {
		ArmorStand armor = (ArmorStand) getWorld().spawnEntity(getLocation(),
				EntityType.ARMOR_STAND);
		StringJoiner string = new StringJoiner(" , ", "{", "}");
		string.add("NacOSearilize");
		string.add(this.getClass().getName());
		string.add(getOwner().toString());
		string.add(getID() + "");
		armor.setCustomName(ChatColor.MAGIC + string.toString());
		armor.setCustomNameVisible(false);
		armor.setMarker(true);
		armor.setVisible(false);
		armor.setGravity(false);
		getLocation().getBlock().setType(Material.CHEST);
		Chest chesty = (Chest) getLocation().getBlock().getState();
		for (int i = 0; i < getInventory().getContents().length; i++)
			if (getInventory().getContents()[i] != null)
				chesty.getBlockInventory().addItem(
						getInventory().getContents()[i]);
		remove();
	}

	public static RobotBase getRobot(ArmorStand armor, Chest chest)
			throws Exception {
		String[] data = ChatColor.stripColor(
				armor.getCustomName().replaceAll("\\{", "")
						.replaceAll("\\}", "")).split(" , ");
		Inventory inven = chest.getInventory();
		String clazz = data[1];
		UUID uuid = UUID.fromString(data[2]);
		int ID = Integer.parseInt(data[3]);
		RobotBase robot = (RobotBase) Class.forName(clazz)
				.getConstructor(Location.class, UUID.class)
				.newInstance(armor.getLocation(), uuid);
		for (int i = 0; i < inven.getContents().length; i++)
			if (inven.getContents()[i] != null)
				robot.addItem(inven.getContents()[i]);
		RobotCenter.removeRobot(robot.getID());
		robot.setID(ID);
		RobotCenter.addRobotID(robot);
		chest.getInventory().clear();
		chest.getBlock().setType(Material.AIR);
		armor.remove();
		return robot;
	}

	/**
	 * Completely removes the robot
	 */
	public void remove() {
		this.robotTask.cancel();
		this.pathManager.cancel();
		this.chick.remove();
		this.armorStand.remove();
		RobotCenter.removeRobot(getID());
	}

	public Inventory getInventory() {
		return invetory;
	}

	public HashMap<Integer, ItemStack> addItem(ItemStack... item) {
		return getInventory().addItem(item);
	}

	public static Chicken getSilentChicken(Location loc) throws Exception {
		Object nbtTAG = getNMS("NBTTagCompound").getConstructor().newInstance();
		nbtTAG.getClass()
				.getDeclaredMethod("setString", String.class, String.class)
				.invoke(nbtTAG, "id", "Chicken");
		nbtTAG.getClass()
				.getDeclaredMethod("setBoolean", String.class, boolean.class)
				.invoke(nbtTAG, "Silent", true);
		Object world = loc.getWorld().getClass().getMethod("getHandle")
				.invoke(loc.getWorld());
		Object entity = null;
		if (getVersion().contains("9")) {
			entity = getNMS("ChunkRegionLoader").getMethod("a",
					nbtTAG.getClass(), getNMS("World"), double.class,
					double.class, double.class, boolean.class).invoke(null,
					nbtTAG, world, loc.getX(), loc.getY(), loc.getZ(), true);
			entity.getClass()
					.getMethod("setPosition", double.class, double.class,
							double.class)
					.invoke(entity, loc.getX(), loc.getY(), loc.getZ());
			Object craftChicken = entity.getClass()
					.getMethod("getBukkitEntity").invoke(entity);
			return (Chicken) craftChicken;
		} else {
			return (Chicken) loc.getWorld()
					.spawnEntity(loc, EntityType.CHICKEN);
		}
	}

	public void setStuck(boolean stuck) {
		this.isStuck = stuck;
	}

	public UUID getOwner() {
		return owner;
	}

	public boolean checkAllowed(Location loc) {
		if (RoboticCraft.usingWorldGuard())
			if (!RoboticCraft.getWorldGuard().canBuild(
					Bukkit.getPlayer(getOwner()), loc))
				return false;
		if (RoboticCraft.usingFactions()) {
			Faction facL = BoardColl.get().getFactionAt(PS.valueOf(loc));
			if (facL.getId().equals(FactionColl.get().getNone().getId())) {
				MPlayer mplayer = MPlayer.get(owner);
				if (facL.getId() != mplayer.getFactionId())
					return false;
			}
		}
		return true;
	}
}
