package gameObject;

import game.Game;

/**
* <b>Description:</b>
* <br>
* Defines projectile movmement with changing velocities
* <br>Requires input of coordinates, and outputs direction and speed of projectile
* @author Team 6
* @version 1.0
* @since 2014-03-31
*/
public class SaDash extends Projectile{
	int duration;
	int durationTimer;
	public SaDash(int x, int y, Game game, GameObject o, int duration) {
		super(x, y, game, o);
		this.duration = duration;
		flyRight = new ImageSequence("/image/projectiles/bariaRight", 12);
		flyDown = new ImageSequence("/image/projectiles/bariaDown", 12);
		
		setStartingAnimation();
		setStartingVelocity(0);
	}
	public void tick(){
		super.tick();
		durationTimer++;
		this.x=owner.x;
		this.y=owner.y;
		
		if(durationTimer>duration||!((MovableObject)owner).charging){
			remove();
		}
	}
	
	

}
