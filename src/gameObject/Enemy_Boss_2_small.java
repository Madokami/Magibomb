package gameObject;

import java.util.LinkedList;

import game.Game;

public class Enemy_Boss_2_small extends Enemy{

	private boolean teleported=true;
	
	public Enemy_Boss_2_small(int x, int y, Game game) {
		super(x, y, game);
		damage=new ImageSequence("/image/spriteSheet/actors/enemy/boss_2_small/damage",5);	
		stand = new ImageSequence("/image/spriteSheet/actors/enemy/boss_2_small/stand",1);	
		sequence.startSequence(stand);
		canMove=false;
		ultyTimerDuration=20;
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
	@Override
	public void takeDamage(int damage){
		super.takeDamage(damage);
		teleported=false;
	}
	public void remove(){
		super.remove();
		controller.addEntity(new Enemy_Boss_2(xGridNearest,yGridNearest,game));
		game.increaseEnemyCount();
	}

}
