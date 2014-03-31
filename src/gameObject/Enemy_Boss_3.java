package gameObject;

import game.Game;

public class Enemy_Boss_3 extends Enemy{
	protected ImageSequence attack;
	protected ImageSequence summon;
	public Enemy_Boss_3(int x, int y, Game game) {
		super(x, y, game);
		stand=new ImageSequence("/image/spriteSheet/actors/enemy/boss_3/stand",8);	
		damage=new ImageSequence("/image/spriteSheet/actors/enemy/boss_3/shuffle",8);
		attack=new ImageSequence("/image/spriteSheet/actors/enemy/boss_3/thunder",8);
		summon=new ImageSequence("/image/spriteSheet/actors/enemy/boss_3/tsukaima",8);
		
		ultyTimerDuration=60;
	}

	@Override
	public void useUltimate() {
		//laser attack
		setVelX(0);
		setVelY(0);
		sequence.startSequence(attack, stand);
		controller.addEntity(new Projectile_Thunder(this.xGridNearest,this.yGridNearest,game,this));
		
		ultyTimer=0;
	}

	@Override
	public void useAbility1() {
		//summon stuff
	}

	@Override
	public void useAbility2() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void useAbility3() {
		// TODO Auto-generated method stub
		
	}

}
