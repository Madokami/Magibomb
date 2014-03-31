package gameObject;

import game.Game;

import java.util.LinkedList;

public class Enemy_Boss_2 extends Enemy{

	public Enemy_Boss_2(int x, int y, Game game) {
		super(x, y, game);
		run=new ImageSequence("/image/spriteSheet/actors/enemy/boss_2/run",12);
		stand=new ImageSequence("/image/spriteSheet/actors/enemy/boss_2/stand",8);	
		damage=new ImageSequence("/image/spriteSheet/actors/enemy/boss_2/damage",4);
		sequence.startSequence(stand);
		
		ultyTimerDuration=200;
	}

	@Override
	public void useUltimate() {
		/*
		LinkedList<Point> points = ai.obtainRandomValidPoints(game.getWallArray(), 10);
		if(points.size()>0){
			for(int i=0;i<points.size();i++){
				controller.addEntity(new Bomb(points.get(i).getX(),points.get(i).getY(),game,50,3,50));
			}
			ultyTimer=0;
		}
		*/
		if(chargeAtPlayer(40,20)){
			ultyTimer=0;
		}
		
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
