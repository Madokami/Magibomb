package gameObject;

import game.Game;

public abstract class Enemy_4 extends Enemy{
	
	protected ImageSequence attack,open;
	
	public Enemy_4(int x, int y, Game game) {
		super(x, y, game);
		
	}
	
	@Override
	public void useAbility1(){
		//change location
	}
	
}

	