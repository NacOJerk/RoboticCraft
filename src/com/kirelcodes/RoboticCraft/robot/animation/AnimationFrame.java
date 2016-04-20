package com.kirelcodes.RoboticCraft.robot.animation;

import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;

public class AnimationFrame {
	private EulerAngle head , body ,leftHand , rightHand , leftLeg, rightLeg;
	public AnimationFrame(){
	
	}
	public AnimationFrame setHead(EulerAngle head){
		this.head = head;
		return this;
	}
	public AnimationFrame setBody(EulerAngle body){
		this.body = body;
		return this;
	}
	public AnimationFrame setLeftHand(EulerAngle leftHand){
		this.leftHand = leftHand;
		return this;
	}	
	public AnimationFrame setRightHand(EulerAngle rightHand){
		this.rightHand = rightHand;
		return this;
	}
	public AnimationFrame setLeftLeg(EulerAngle leftLeg){
		this.leftLeg = leftLeg;
		return this;
	}
	public AnimationFrame setRightLeg(EulerAngle rightLeg){
		this.rightHand = rightLeg;
		return this;
	}
	public EulerAngle getHead(){
		return head;
	}
	public EulerAngle getBody(){
		return body;
	}
	public EulerAngle getLeftHand(){
		return leftHand;
	}
	public EulerAngle getRightHand(){
		return rightHand;
	}
	public EulerAngle getLeftLeg(){
		return leftLeg;
	}
	public EulerAngle getRightLeg(){
		return rightLeg;
	}
	public void setLocations(ArmorStand armor){
		armor.setHeadPose((getHead() == null) ? armor.getHeadPose() : getHead());
		armor.setBodyPose((getBody() == null) ? armor.getBodyPose() : getBody());
		armor.setLeftArmPose((getLeftHand() == null) ? armor.getLeftArmPose() : getLeftHand());
		armor.setRightArmPose((getRightHand() == null) ? armor.getRightArmPose() : getRightHand());
		armor.setLeftLegPose((getLeftLeg() == null) ? armor.getLeftLegPose() : getLeftLeg());
		armor.setRightLegPose((getRightLeg() == null) ? armor.getRightLegPose() : getRightLeg());
	}
}	
