package gameObject;

import game.Game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Fire extends GameObject{
int strength;
private int counter;

	public Fire(int x, int y, Game game,int Strength) {
		super(x,y,game);
		
		this.imageWidth=collisionWidth;
		this.imageHeight=collisionHeight;
		
		ss=SpriteData.bricks;
		image=ss.grabImage(5, 15, 32, 32);
		this.strength = Strength;
		counter=0;
	}
	public void tick(){
		counter++;
		if(counter>5){
			remove();
		}
		
		LinkedList<Player> playerHit = Physics.hitPlayer(this, game.getController().getPlayerList());
		if(playerHit!=null){
			for(int i=0;i<playerHit.size();i++){
				applyDamage(strength,15,playerHit.get(i));
			}
		}
		
		LinkedList<Enemy> enemies=Physics.collision(this, game.getEnemyList());
		for(int i=0;i<enemies.size();i++){
			applyDamage(strength,10,enemies.get(i));
		}
		
		int wallHit=Physics.hitWall(this, game.getBrickList());
		if(wallHit!=-1){
			applyDamage(strength,10,game.getBrickList().get(wallHit));
		}
		int bombHit=Physics.hitBomb(this, game.getBombList());
		if(bombHit!=-1){
			applyDamage(strength,10,game.getBombList().get(bombHit));
		}
		LinkedList<Projectile> projTemp = Physics.projectileHit(this, controller.getProjectileList());
		for(int i = 0;i<projTemp.size();i++){
			applyDamage(strength,15,projTemp.get(i));
		}
		
	}

	
	
	public int getStrength(){
		return strength;
	}
	public void remove(){
		game.getController().removeFire(this);
	}
}
