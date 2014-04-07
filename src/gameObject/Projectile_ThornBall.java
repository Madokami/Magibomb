package gameObject;

import game.Game;

/**
* <b>Description:</b>
* <br>
* Defines individual projectile
* <br>Requires input of coordinates, and outputs direction and speed of projectile
* @author Team 6
* @version 1.0
* @since 2014-03-31
*/
public class Projectile_ThornBall extends Projectile{

	protected int counter;
	protected int duration = 150;
	/**
	 * defines thornball projectile
	 * @param coordinates, game, game object
	 */
	public Projectile_ThornBall(int x, int y, Game game, GameObject o) {
		super(x, y, game, o);
		flyRight = new ImageSequence("/image/projectiles/thornBall", 3);
		this.orientation=ORIENTATION.RIGHT;
		setStartingAnimation();
		this.damage=10;
		invincibleDuration=30;
		//flySpeed=0;
		//this.setStartingVelocity(flySpeed);
	}
	
	/**
	 * uses current status to determine next iteration
	 */
	public void tick(){
		super.tick();
		counter++;
		if(counter>duration){
			remove();
		}
		
	}

}
