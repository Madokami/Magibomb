package gameObject;

import system.BufferedImageLoader;
import system.GameSystem;
import game.Game;

public class Projectile_PinkArrow extends Projectile{

	public Projectile_PinkArrow(int x, int y, Game game,GameObject o) {
		super(x, y, game,o);
		flyRight = new ImageSequence("/image/projectiles/pinkArrowRight", 5);
		flyDown = new ImageSequence("/image/projectiles/pinkArrowDown", 5);
		flyRight.scale(0.3);
		flyDown.scale(1.3);
		/*
		flyRight.setY(flyRight.getY()-GameSystem.GRID_SIZE-GameSystem.GRID_SIZE/5);
		flyDown.setX(flyRight.getX()-GameSystem.GRID_SIZE-GameSystem.GRID_SIZE/5);
		*/
		setStartingAnimation();
		flySpeed=40;
		
		this.setStartingVelocity(flySpeed);
		
	}
	public void tick(){
		super.tick();
		setCollisionToImageSize();
		removeIfOutSideScreen();
		//this.setCollisionToImageSize();
	}

}
