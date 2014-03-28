package gameObject;

import game.Game;
import gameObject.GameObject.ORIENTATION;

import java.awt.Graphics;

import system.GameSystem;

public class TiroFinale extends Projectile{
	public TiroFinale(int x, int y, Game game, GameObject o) {
		super(x, y, game, o);
		flyRight = new ImageSequence("/image/projectiles/tiroFinaleRight", 6);
		flyDown = new ImageSequence("/image/projectiles/tiroFinaleDown", 6);
		//flyRight.setY(flyRight.getY()-GameSystem.GRID_SIZE-GameSystem.GRID_SIZE/5);
		flyRight.setWidth(1000);
		flyRight.setHeight(GameSystem.GRID_SIZE);
		flyRight.setX(flyRight.getX()+GameSystem.GRID_SIZE);
		flyRight.setY(flyRight.getY()+flyRight.getHeight()/2);
		flyDown.setHeight(1000);
		flyDown.setY(flyDown.getY()+GameSystem.GRID_SIZE);
		//flyDown.scale(2);
		//flyDown.setX(flyRight.getX()-GameSystem.GRID_SIZE-GameSystem.GRID_SIZE/5);
		setStartingAnimation();
		flySpeed=0;
		this.setStartingVelocity(flySpeed);
		imageWidth=1000;
		imageHeight=1000;
		allignOrientationWithOwner(owner);
		//collisionAdjust();
	}
	public void tick(){
		super.tick();
		setCollisionToImageSize();
		if(owner.channelling==false){
			remove();
		}
	
	}
	

	
}
