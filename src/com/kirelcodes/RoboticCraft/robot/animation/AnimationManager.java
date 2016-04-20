package com.kirelcodes.RoboticCraft.robot.animation;

import org.bukkit.util.EulerAngle;

public class AnimationManager {
	public static final AnimationCycle walk = new AnimationCycle(
			new AnimationFrame().setLeftLeg(new EulerAngle(0.5, 0, 0))
					.setRightLeg(new EulerAngle(-0.5, 0, 0)),
			new AnimationFrame().setLeftLeg(new EulerAngle(-0.5, 0, 0))
					.setRightLeg(new EulerAngle(0.5, 0, 0))),
			mine = new AnimationCycle(
					new AnimationFrame().setRightHand(new EulerAngle(-1.75, 0,
							0)),
					new AnimationFrame().setRightHand(new EulerAngle(-1, 0, 0)));

}
