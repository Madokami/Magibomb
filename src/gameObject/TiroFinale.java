package gameObject;

import game.Game;
import system.GameSystem;

/**
* <b>Description:</b>
* <br>
* Defines individual projectile
* <br>Requires input of coordinates, and outputs direction and speed of projectile
* @author Team 6
* @version 1.0
* @since 2014-03-31
*/
public class TiroFinale extends Projectile{
	/**
	 * Define individual projectile
	 * @param coordinates, game, game object o
	 */
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
	/**
	 * Checks current conditions to determine future scenario such as collision
	 */
	public void tick(){
		super.tick();
		setCollisionToImageSize();
		if(owner.channelling==false){
			remove();
		}
	
	}
	

	
}
