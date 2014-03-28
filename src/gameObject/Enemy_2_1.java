package gameObject;

import game.Game;

public class Enemy_2_1 extends Enemy{

	public Enemy_2_1(int x, int y, Game game) {
		super(x, y, game);
		run=new ImageSequence("/image/spriteSheet/actors/enemy/enemy_2_1/run",6);
		damage=new ImageSequence("/image/spriteSheet/actors/enemy/enemy_2_1/damage",6);	
		stand=new ImageSequence("/image/spriteSheet/actors/enemy/enemy_2_1/stand",3);	
		sequence.startSequence(stand);
	}

	@Override
	public void useUltimate() {
		// TODO Auto-generated method stub
		
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

}
