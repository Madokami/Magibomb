package gameObject;

import game.Game;

import java.awt.Graphics;

public class Enemy_1_1 extends Enemy{
	private int chargeCounter=0;
	public Enemy_1_1(int x, int y, Game game) {
		super(x, y, game);
		
		//run=new ImageSequence("/image/spriteSheet/actors/enemy/enemy_1_1/run",8);
		//stand=new ImageSequence("/image/spriteSheet/actors/enemy/enemy_1_1/stand",8);
		stand=new ImageSequence("/image/spriteSheet/actors/enemy/boss_6/stand",10);
		damage=new ImageSequence("/image/spriteSheet/actors/enemy/enemy_1_1/damage",4);		
		sequence.startSequence(stand);

		
		// TODO Auto-generated constructor stub
	}
	public void tick(){
		super.tick();
		if(chargeCounter>=20){
			useUltimate();
			chargeCounter=0;
		}
		chargeCounter++;
	}
	@Override
	public void useUltimate() {
		// TODO Auto-generated method stub
		chargeAtPlayer(20,20);
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
