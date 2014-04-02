package gameObject;

import game.Game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Random;

import system.GameSystem;

public abstract class Enemy extends MovableObject{
	
	public int score, exp;
	public Random rand;
	public int counter;
	public int collisionDamage;
	protected Ai ai;
	public int actionOffSet;
	protected boolean canMove=true;
	
	protected int ultyTimer,ultyTimerDuration,abi1Timer,abi1TimerDuration,abi2Timer,abi2TimerDuration;
	
	public Enemy(int x,int y, Game game){
		super(x,y,game);
		//image = ss.grabImage(10, 1, 32, 32);
		ssX=10;
		ssY=1;
		ss=SpriteData.char2;
		image = ss.grabImage(ssX, ssY, ssWidth, ssHeight);
		
		score = 50;
		exp = 50;
		collisionDamage=20;
		
		counter = 0;
		rand = new Random();
		ai=new Ai();
		actionOffSet = rand.nextInt(20);
		
		controller.addEntity(new Projectile_SpawnEffect(xGridNearest,yGridNearest,game,this));
	}
	public void tick(){
		super.tick();
		/*
		if(Physics.overlapWithOtherEnemies(this, game.getEnemyList())){
			this.moveToLastAcceptableLocation();
		}
		*/
		if(GameSystem.twoPlayerMode){
			if(GameSystem.isPlayerOne){
				if(positionUpdateTimer>15){
					positionUpdateTimer=0;
					sendCommand("updateX"+Double.toString(x));
					sendCommand("updateY"+Double.toString(y));
				}
			}
		}
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
						try{
						//chasePlayer();
						}catch(Exception e){
							moveRandomly();
						}
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
		/*
		if(counter>40){
			counter=0;
			moveRandomly();
		}
		*/
		
		if(hp<=0){
			remove();
		}
		/*
		if(super.x<=0)
			moveRight();
		if(super.x>=Game.WIDTH*Game.SCALE-23)
			moveLeft();
		if(super.y<=0)
			super.moveDown();
		if(super.y>=Game.HEIGHT*Game.SCALE-23)
			super.moveUp();
		if(Physics.collision(game.p, this)){
			//game.c.removeEntity(this);
		}
		*/
	
	}
	public void providePoints(Player p){
		p.expCurrent+=exp;
		p.score+=score;
	}
	public void moveRandomly(){
		int temp = rand.nextInt(4);
		if(temp==0){
			moveUp();
			sendCommand("moveUp");
		}
		else if(temp==1){
			moveDown();
			sendCommand("moveDown");
		}
		else if(temp==2){
			moveLeft();
			sendCommand("moveLeft");
		}
		else if(temp==3){
			moveRight();
			sendCommand("moveRight");
		}
	}
	public void chasePlayer(){
		String s;
		s=ai.makeStep(game.getWallArray(), game.getPlayer().xGridNearest, game.getPlayer().yGridNearest, lastX, lastY);
		if(s.equals("up")) {
			if(!Physics.blockedByEnemy(this, game.getEnemyList(), lastX, lastY-1)){
				this.sendCommand("moveUp");
				moveUp();
			}
		}
		else if(s.equals("down")) {
			if(!Physics.blockedByEnemy(this, game.getEnemyList(), lastX, lastY+1)){
				sendCommand("moveDown");
				moveDown();
			}
		}
		else if(s.equals("left")) {
			if(!Physics.blockedByEnemy(this, game.getEnemyList(), lastX-1, lastY)) {
				sendCommand("moveLeft");
				moveLeft();
			}
		}
		else if(s.equals("right")) {
			if(!Physics.blockedByEnemy(this, game.getEnemyList(), lastX+1, lastY-1)) {
				sendCommand("moveRight");
				moveRight();
			}
		}
		else if(s.equals("stop")) {
			moveRandomly();
		}
	}
	public boolean chargeAtPlayer(int speed,int duration){
		String dir=ai.isValidStraightLine(game.getWallArray(), game.getPlayer().xGridNearest, game.getPlayer().yGridNearest, xGridNearest, yGridNearest);
		if(dir.equals("stop")){
			return false;
		}
		else if(dir.equals("right")){
			moveRight();
			startCharge(speed,duration);
		}
		else if(dir.equals("left")){
			moveLeft();
			startCharge(speed,duration);
		}
		else if(dir.equals("up")){
			moveUp();
			startCharge(speed,duration);
		}
		else if(dir.equals("down")){
			moveDown();
			startCharge(speed,duration);
		}
		return true;
	}
	
	public void moveToDirection(String dir){
		
		if(dir.equals("right")){
			moveRight();
		}
		else if(dir.equals("left")){
			moveLeft();
		}
		else if(dir.equals("up")){
			moveUp();
		}
		else if(dir.equals("down")){
			moveDown();
		}
		return;
	}
	
	public void remove(){
		game.decreaseEnemyCount();
		game.getController().removeEntity(this);
		providePoints(game.getPlayer());
	}
	
	public abstract void useUltimate();
	public abstract void useAbility1();
	public abstract void useAbility2();
	public abstract void useAbility3();
}
