package gameObject;

import game.Game;

public class Projectile_ThornBall extends Projectile{

	protected int counter;
	protected int duration = 150;
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
	
	public void tick(){
		super.tick();
		counter++;
		if(counter>duration){
			remove();
		}
		
	}

}
