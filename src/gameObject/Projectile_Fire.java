package gameObject;

import game.Game;

public class Projectile_Fire extends Projectile{
	int counter;
	int duration=90;
	
	/**
	 * defines fire projectile
	 * @param coordinates, game, game object
	 * @return projectile
	 */
	public Projectile_Fire(int x, int y, Game game, GameObject o) {
		super(x, y, game, o);
		flyRight = new ImageSequence("/image/projectiles/kyFire", 8);
		flyRight.scale(2.0);
		flyRight.setX(flyRight.getX()+22);
		flyRight.setY(flyRight.getY()+22);
		this.setDamage(25);
		hp = 100;
		this.pAnimate.startSequence(flyRight);
		setStartingVelocity(0);
		
		this.collisionWidth=100;
		this.collisionHeight=100;
		
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
		this.x=owner.x-22;
		this.y=owner.y-22;
	}
}
