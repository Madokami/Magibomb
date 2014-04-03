package gameObject;

import game.Game;

/**
* <b>Description:</b>
* <br>
* New type of enemy is defined with corresponding attributes such as speed and abilites
* <br>Enemy spawns at specific coordinates on grid map
* <br>Enemy is unique and displayed with images corresponding to its animation
* @author Team 6
* @version 1.0
* @since 2014-03-31
*/
public class Enemy_1_3 extends Enemy{

	public Enemy_1_3(int x, int y, Game game) {
		super(x, y, game);
		run=new ImageSequence("/image/spriteSheet/actors/enemy/enemy_1_3/run",9);
		stand=new ImageSequence("/image/spriteSheet/actors/enemy/enemy_1_3/stand",9);	
		sequence.startSequence(stand);
		ultyTimerDuration=150;
	}

	@Override
	public void useUltimate() {
		String dir = ai.isValidStraightLine(controller.getWallArray(), this.xGridNearest,yGridNearest,game.getPlayer().xGridNearest,game.getPlayer().yGridNearest);
		if(dir!="stop"){
			if(dir=="right"){
				controller.addEntity(new Enemy_1_2(xGridNearest-1,yGridNearest,game));
			}
			else if(dir=="left"){
				controller.addEntity(new Enemy_1_2(xGridNearest+1,yGridNearest,game));
			}
			else if(dir=="up"){
				controller.addEntity(new Enemy_1_2(xGridNearest,yGridNearest+1,game));
			}
			else if(dir=="down"){
				controller.addEntity(new Enemy_1_2(xGridNearest,yGridNearest-1,game));
			}
			game.increaseEnemyCount();
			this.ultyTimer=0;
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
