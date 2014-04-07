package gameObject;

import java.util.LinkedList;

import game.Game;

/**
* <b>Description:</b>
* <br>
* New type of enemy boss is defined with corresponding attributes such as speed and abilites
* <br>Booss spawns at specific coordinates on grid map
* <br>Boss is unique and displayed with images corresponding to its animation
* @author Team 6
* @version 1.0
* @since 2014-03-31
*/
public class Enemy_Boss_1 extends Enemy{
	protected ImageSequence attack;
	/**
	 * defines new enemy boss
	 * @panam coordinates, game object
	 */
	public Enemy_Boss_1(int x, int y, Game game) {
		super(x, y, game);
		attack = new ImageSequence("/image/spriteSheet/actors/enemy/boss_1/attack",10);
		attack.setAnimationSpeed(0.5);
		run=new ImageSequence("/image/spriteSheet/actors/enemy/boss_1/run",8);
		stand=new ImageSequence("/image/spriteSheet/actors/enemy/boss_1/stand",8);
		damage=new ImageSequence("/image/spriteSheet/actors/enemy/boss_1/damage",4);		
		sequence.startSequence(stand);
		
		this.setHp(250);
		this.setSpeed(10);
		this.setCollisionDamage(40);
		this.setExp(150);
		
		ultyCd=300;
		abi1Cd=200;
		abi2Cd=600;
	}

	@Override
	/**
	 * defines boss attributes
	 */
	public void useUltimate() {
		LinkedList<Point> points = ai.obtainRandomValidPoints(game.getWallArray(), 10);
		if(points.size()>0){
			for(int i=0;i<points.size();i++){
				controller.addEntity(new Projectile_ThornBall(points.get(i).getX(),points.get(i).getY(),game,this));
			}
			ultyTimer=0;
		}
		
	}

	@Override
	public void useAbility1() {
		setVelX(0);
		setVelY(0);
		sequence.startSequence(attack, stand);
		controller.addEntity(new Scissors(this.xGridNearest,this.yGridNearest,game,this));
		
	}

	@Override
	public void useAbility2() {
		int temp = rand.nextInt(3);
		if(temp==0){
			controller.addEntity(new Enemy_1_1(xGridNearest,yGridNearest,game));
		}
		else if(temp==1){
			controller.addEntity(new Enemy_1_2(xGridNearest,yGridNearest,game));
		}
		else{
			controller.addEntity(new Enemy_1_3(xGridNearest,yGridNearest,game));
		}
		game.increaseEnemyCount();
	}

	@Override
	public void useAbility3() {
		// TODO Auto-generated method stub
		
	}
	

}
