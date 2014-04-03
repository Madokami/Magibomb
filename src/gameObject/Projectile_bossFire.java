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
public class Projectile_bossFire extends Projectile{
	private int counter;
	private int duration = 60;
	public Projectile_bossFire(int x, int y, Game game, GameObject o) {
		super(x, y, game, o);
		flyRight = new ImageSequence("/image/projectiles/bossFire", 5);
		int vx = rand.nextInt(20)-10;
		int vy = rand.nextInt(20)-10;
		
		setVelX(vx);
		setVelY(vy);
		
		pAnimate.startSequence(flyRight);
	}
	public void tick(){
		super.tick();
		counter++;
		if(counter>duration){
			remove();
		}
		//setCollisionToImageSize();
		//removeIfOutSideScreen();
		//this.setCollisionToImageSize();
	}

}
