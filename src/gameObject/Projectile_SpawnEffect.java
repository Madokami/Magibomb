package gameObject;

import game.Game;
import gameObject.GameObject.ORIENTATION;

public class Projectile_SpawnEffect extends Projectile{
	private int counter;
	private int duration = 15;
	public Projectile_SpawnEffect(int x, int y, Game game, GameObject o) {
		super(x, y, game, o);
		flyRight = new ImageSequence("/image/projectiles/effectRing", 6);
		flyDown = new ImageSequence("/image/projectiles/effectRing", 6);
		this.orientation=ORIENTATION.RIGHT;
		setStartingAnimation();
		this.damage=0;
		invincibleDuration=30;
	}
	public void tick(){
		super.tick();
		counter++;
		if(counter>duration){
			remove();
		}
		
	}
	
	
	
}
