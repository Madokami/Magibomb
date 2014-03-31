package gameObject;

import game.Game;

public abstract class Enemy_4 extends Enemy{
	
	protected ImageSequence attack,open;
	
	public Enemy_4(int x, int y, Game game) {
		super(x, y, game);
		abi1TimerDuration=300+rand.nextInt(30);
	}
	
	@Override
	public void useAbility1(){
		//change location
	}
	
}

	