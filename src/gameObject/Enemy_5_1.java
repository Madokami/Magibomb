package gameObject;

import game.Game;

public class Enemy_5_1 extends Enemy_4{

	public Enemy_5_1(int x, int y, Game game) {
		super(x, y, game);
		run=new ImageSequence("/image/spriteSheet/actors/enemy/enemy_5_1/run",8);
		stand=new ImageSequence("/image/spriteSheet/actors/enemy/enemy_5_1/stand",8);	
		attack=new ImageSequence("/image/spriteSheet/actors/enemy/enemy_5_1/attack",13);	
		damage=new ImageSequence("/image/spriteSheet/actors/enemy/enemy_5_1/damage",5);
		sequence.startSequence(stand);
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
	public void useUltimate() {
		// TODO Auto-generated method stub
		
	}

}
