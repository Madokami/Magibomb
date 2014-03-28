package gameObject;

import game.Game;

public class Enemy_3_2 extends Enemy{

	public Enemy_3_2(int x, int y, Game game) {
		super(x, y, game);
		run=new ImageSequence("/image/spriteSheet/actors/enemy/enemy_3_2/run",8);
		stand=new ImageSequence("/image/spriteSheet/actors/enemy/enemy_3_2/stand",8);	
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
