package gameObject;


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
public class Enemy_Boss_2_small extends Enemy{

	
	public Enemy_Boss_2_small(int x, int y, Game game) {
		super(x, y, game);
		damage=new ImageSequence("/image/spriteSheet/actors/enemy/boss_2_small/damage",5);	
		stand = new ImageSequence("/image/spriteSheet/actors/enemy/boss_2_small/stand",1);	
		sequence.startSequence(stand);
		canMove=false;
		ultyCd=20;
		this.setHp(300);
		this.setSpeed(0);
		this.setCollisionDamage(1);
		this.setExp(1);
		
	}

	@Override
	public void useUltimate() {
		
		
	}

	@Override
	public void useAbility1() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void useAbility2() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void useAbility3() {
		// TODO Auto-generated method stub
		
	}
	public void remove(){
		super.remove();
		controller.addEntity(new Enemy_Boss_2(xGridNearest,yGridNearest,game));
		game.increaseEnemyCount();
	}

}
