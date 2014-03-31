package gameObject;

import game.Game;

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
