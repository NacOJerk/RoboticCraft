package com.kirelcodes.RoboticCraft.robot.animation;

import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;

public class AnimationFrame {
	private EulerAngle head , body ,leftHand , rightHand , leftLeg, rightLeg;
	private ArmorStand armor;
	public AnimationFrame(ArmorStand armor){
		this.armor = armor;
	}
	public AnimationFrame(){
	
	}
	public AnimationFrame setArmorStand(ArmorStand armor){
		this.armor = armor;
		return this;
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
	public ArmorStand getArmorStand(){
		return armor;
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
}	
