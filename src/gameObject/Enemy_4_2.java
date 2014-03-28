package gameObject;

import game.Game;

public class Enemy_4_2 extends Enemy_4{

	public Enemy_4_2(int x, int y, Game game) {
		super(x, y, game);
		run=new ImageSequence("/image/spriteSheet/actors/enemy/enemy_4_2/run",6);
		stand=new ImageSequence("/image/spriteSheet/actors/enemy/enemy_4_2/stand",6);	
		attack=new ImageSequence("/image/spriteSheet/actors/enemy/enemy_4_2/attack",10);	
		open=new ImageSequence("/image/spriteSheet/actors/enemy/enemy_4_2/open",5);	
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
