package gameObject;

import game.Game;
import gameObject.GameObject.ORIENTATION;

import java.awt.Graphics;

public class SaDash extends Projectile{
	int duration;
	int durationTimer;
	public SaDash(int x, int y, Game game, GameObject o, int duration) {
		super(x, y, game, o);
		this.duration = duration;
		flyRight = new ImageSequence("/image/projectiles/bariaRight", 4);
		flyDown = new ImageSequence("/image/projectiles/bariaDown", 4);
		
		setStartingAnimation();
		setStartingVelocity(0);
		/*
		ss=SpriteData.saDash;
		ssWidth=48;
		ssHeight=48;
		ssX=1;
		ssY=1;
		MS=1;
		frames=12;
		if(o.orientation==ORIENTATION.RIGHT){
			direction="right";
		}
		else if(o.orientation==ORIENTATION.LEFT){
			direction="left";
		}
		if(o.orientation==ORIENTATION.UP){
			direction="up";
		}
		if(o.orientation==ORIENTATION.DOWN){
			direction="down";
		}
		this.x=o.x;
		this.y=o.y;
		*/
	}
	public void tick(){
		super.tick();
		durationTimer++;
		this.x=owner.x;
		this.y=owner.y;
		
		if(durationTimer>duration||!((MovableObject)owner).charging){
			remove();
		}
		
		/*
		if(owner.orientation==ORIENTATION.RIGHT){
			direction="right";
		}
		else if(owner.orientation==ORIENTATION.LEFT){
			direction="left";
		}
		if(owner.orientation==ORIENTATION.UP){
			direction="up";
		}
		if(owner.orientation==ORIENTATION.DOWN){
			direction="down";
		}
		
		Animate.animate(this);
		*/
	}
	
	

}
