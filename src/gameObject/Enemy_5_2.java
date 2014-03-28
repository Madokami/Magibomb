package gameObject;

import game.Game;

public class Enemy_5_2 extends Enemy_4{

	public Enemy_5_2(int x, int y, Game game) {
		super(x, y, game);
		run=new ImageSequence("/image/spriteSheet/actors/enemy/enemy_5_2/run",7);
		stand=new ImageSequence("/image/spriteSheet/actors/enemy/enemy_5_2/stand",8);	
		damage=new ImageSequence("/image/spriteSheet/actors/enemy/enemy_5_2/damage",4);
		sequence.startSequence(stand);
	}

	@Override
	public void useUltimate() {
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
