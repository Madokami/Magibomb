package gameObject;

import game.Game;

public class Projectile_Thunder extends Projectile{

	public Projectile_Thunder(int x, int y, Game game, GameObject o) {
		super(x, y, game, o);
		flyRight = new ImageSequence("/image/projectiles/sparkRight", 5);
		flyDown = new ImageSequence("/image/projectiles/sparkDown", 5);
		
		setStartingAnimation();
		flySpeed=30;
		
		this.setStartingVelocity(flySpeed);
	}

}
