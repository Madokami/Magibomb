package gameObject;

import java.util.LinkedList;

import system.GameSystem;
import game.Game;

public class Enemy_Boss_5 extends Enemy{
	private ImageSequence open;
	public Enemy_Boss_5(int x, int y, Game game) {
		super(x, y, game);
		dontFlip=true;
		
		open=new ImageSequence("/image/spriteSheet/actors/enemy/boss_5/open",10);
		open.setWidth(4*GameSystem.GRID_SIZE);
		open.setHeight(4*GameSystem.GRID_SIZE);
		stand=new ImageSequence("/image/spriteSheet/actors/enemy/boss_5/stand",10);
		stand.setWidth(4*GameSystem.GRID_SIZE);
		stand.setHeight(4*GameSystem.GRID_SIZE);
		sequence.startSequence(stand);
		
		collisionWidth=4*GameSystem.GRID_SIZE;
		collisionHeight=4*GameSystem.GRID_SIZE;
		
		this.spd=10;
		
		abi1TimerDuration=5;
		abi2TimerDuration=300;
		ultyTimerDuration=60;
		
	}
	
	public void tick(){
		ultyTimer++;
		if(ultyTimer>ultyTimerDuration){
			useUltimate();
		}
		abi1Timer++;
		if(abi1Timer>abi1TimerDuration){
			useAbility1();
			abi1Timer=0;
		}
		abi2Timer++;
		if(abi2Timer>abi2TimerDuration){
			useAbility2();
			abi2Timer=0;
		}
		
		if(hp<=0){
			remove();
		}
		sequence.tick();
		
		
		damageRenderer.tick();
		if(invincible){
			invincibleTimer++;
			if(invincibleTimer>invincibleTime){
				invincibleTimer=0;
				invincible=false;
			}
		}
		
		
		setXPosition(x+getVelX());
		setYPosition(y+getVelY());
		updatePosition();
		checkIfAtEdge();
		
		LinkedList<Player> playerHit = Physics.hitPlayer(this, game.getController().getPlayerList());
		if(playerHit!=null){
			for(int i=0;i<playerHit.size();i++){
				applyDamage(collisionDamage,30,playerHit.get(i));
			}
		}
		counter++;
		if(this.canMove){
			if(counter>(20+actionOffSet)){
				counter=0;
				actionOffSet=rand.nextInt(20);
				if(rand.nextInt(10)<8){
					if(GameSystem.isPlayerOne){
						moveRandomly();
						//chasePlayer();
					}
					
				}
				else{
					if(GameSystem.isPlayerOne){
						moveRandomly();
					}
				}
			}
		}
		int bombKicked=Physics.onTopOfBomb(this, game.getBombList());
		if(bombKicked!=-1){
			this.kickBomb();
		}
		
	
	}
	
	@Override
	public void useUltimate() {
		LinkedList<Point> points = ai.obtainRandomValidPoints(game.getWallArray(), 10);
		if(points.size()>0){
			for(int i=0;i<points.size();i++){
				controller.addEntity(new Projectile_ThornBall(points.get(i).getX(),points.get(i).getY(),game,this));
			}
			ultyTimer=0;
		}
		
	}

	@Override
	public void useAbility1() {
		controller.addEntity(new Projectile_bossFire(xGridNearest+1,yGridNearest+3,game,this));
		controller.addEntity(new Projectile_bossFire(xGridNearest+1,yGridNearest+3,game,this));
		
	}

	@Override
	public void useAbility2() {
		controller.addEntity(new Projectile_Building(xGridNearest,yGridNearest,game,this));
		
	}

	@Override
	public void useAbility3() {
		// TODO Auto-generated method stub
		
	}
	
}
