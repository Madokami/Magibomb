package gameObject;

import game.Game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import system.GameSystem;

/**
* <b>Description:</b>
* <br>
* GameObject class is extended by most game objects
* @author Team 6
* @version 1.0
* @since 2014-03-31
*/
public abstract class GameObject{
	//stores the absolute position of character
	//stores the absolute position of character
		public int serialNumber;
	
		protected double x;
		protected double y;
		protected int xGridNearest;
		protected int yGridNearest;
		protected int lastX,lastY,nextX,nextY; //used to implement moving into grids only
		public double xTemp,yTemp;
		protected SpriteSheet ss;
		protected SpriteSheet ssStand;
		protected Image image;
		//stores the last moved position of the character
		public double curX;
		public double curY;
		//detects hitting bricks
		protected int ssX=1;
		protected int ssY=1;
		protected int ssWidth=32;
		protected int ssHeight=32;
		protected int imageWidth=ssWidth;
		protected int imageHeight=ssHeight;
		protected int frames = 3;
		
		
		protected int targetX,targetY;
		
		public boolean finishingMove;
		public boolean atEdge;
		protected Image standGif;
		
		protected boolean channelling=false;
		
		
		Random rand;
		
		// MS is how fast the sprite changes pose
		public double MS = 0.2;
		
		public Game game;
		public String direction;
		public double i=0;
		public boolean blocked;
		
		public boolean invincible = false;
		public int invincibleTimer;
		public int invincibleTime;
		
		public int collisionWidth;
		public int collisionHeight;
		public int renderXShift=0;
		public int renderYShift=0;
		
		protected DamageRenderer damageRenderer;
		protected Controller controller;
	
		
		public enum ORIENTATION{
			DOWN,
			UP,
			LEFT,
			RIGHT,
			//STAND
		};
		public ORIENTATION orientation = ORIENTATION.DOWN;
		public double hp;
		
		/**
		 * Overall GameObject
		 * <br><br>
		 * <b>Inputs:</b>
		 * <br><b>x</b>,<b>y</b> - coordinates
		 * <br><b>game</b> - Game object
		 */
		public GameObject(int x, int y,Game game){
			setSerialNumber();
			
			xGridNearest=x;
			yGridNearest=y;
			lastX=x;
			lastY=y;
			nextX=x;
			nextY=y;
			this.x=(x-1)*GameSystem.GRID_SIZE;
			this.y=(y-1)*GameSystem.GRID_SIZE;
			this.game = game;
			curX=this.x;
			curY=this.y;
			direction = "stand";
			rand = new Random();
			collisionWidth=GameSystem.GRID_SIZE;
			collisionHeight=GameSystem.GRID_SIZE;
			
			damageRenderer=new DamageRenderer(this);
			controller=game.getController();
		}
		public void tick(){
			//updates position of char
			//maps the position to the closest "grid"
			damageRenderer.tick();
			if(invincible){
				invincibleTimer++;
				if(invincibleTimer>invincibleTime){
					invincibleTimer=0;
					invincible=false;
				}
			}
		}
		

		public double getX(){
			return x;
		}
		public double getY(){
			return y;
		}
		public void setX(double x){
			this.x = x;
		}
		public void setY(double y){
			this.y=y;
		}
	
		
		public void render(Graphics g){
			g.drawImage(image,(int)x+renderXShift,(int)y+renderYShift,imageWidth,imageHeight,null);
			
		}
		public void renderDamage(Graphics g){
			damageRenderer.render(g);
		}
		public Game getGame(){
			return this.game;
		}
		
		
		/**
		 * Designates bounds of specific rectangle of grid map
		 * <br><br>
		 * <b>Inputs:</b>
		 * <br><b>width</b> - width of rectangle
		 * <br><b>height</b> - height of rectangle
		 */
		public final Rectangle getBounds(int width, int height){
			double xCord=this.x;
			double yCord=this.y;		
			return new Rectangle((int)xCord,(int)yCord,width,height);
		}
		public void playDeathSound(){
			
		}
		public void setInvincible(int time){
			invincibleTime=time;
			invincibleTimer=0;
			invincible=true;
		}
		public void takeDamage(int damage){
			this.hp-=damage;
			this.damageRenderer.renderDamage(damage);
		}
		
		/**
		 * Applies damage to character
		 * <br><br>
		 * <b>Inputs:</b>
		 * <br><b>value</b> - amount of damage
		 * <br><b>invincibleDuration</b> - time in which the character does not receive damage
		 * <br><b>target</b> - target object
		 */
		public void applyDamage(int value, int invincibleDuration, GameObject target){
			if(target.invincible) return;
			else{
				target.setInvincible(invincibleDuration);
				target.takeDamage(value);
			}
		}
		public void applyDamage(int value,int randomValue, int invincibleDuration, GameObject target){
			if(target.invincible) return;
			else{
				target.setInvincible(invincibleDuration);
				target.takeDamage(value+rand.nextInt(randomValue));
			}
		}
		
		
		
		public double getXAbsolute(){
			return this.x;
		}
		public double getYAbsolute(){
			return this.y;
		}
		public void placeBomb(int bombStrength,int bombLength,int duration){
			if(Physics.onTopOfBomb(this, game.getBombList())!=-1){
				return;
			}
			controller.addEntity(new Bomb(this.xGridNearest,this.yGridNearest,game,bombStrength,bombLength,duration));
		}
		public void kickBomb(){
			int kickedNum = Physics.behindBomb(this, game.getBombList());
			if(kickedNum!=-1){
				Bomb kickedBomb=game.getBombList().get(kickedNum);
				if(orientation==ORIENTATION.UP){
					kickedBomb.setVelY(-20);
				}
				else if(orientation==ORIENTATION.DOWN){
					kickedBomb.setVelY(20);
				}
				else if(orientation==ORIENTATION.LEFT){
					kickedBomb.setVelX(-20);
				}
				else if(orientation==ORIENTATION.RIGHT){
					kickedBomb.setVelX(20);
				}
			}
		}
		public void setSerialNumber(){
			this.serialNumber=GameSystem.serialNumber;
			GameSystem.serialNumber++;
		}
		
		public void stopChannelling(){
			this.channelling = false;
		}
		public abstract void remove();
		
		/*
		public void sendCommand(String s){
			GameSystem.sendCommand="!"+s+","+Integer.toString(this.serialNumber)+";";
		}
		*/
		public void sendCommand(String s){
			if(!GameSystem.twoPlayerMode){
				return;
			}
			if(GameSystem.sendCommand!=null){
				GameSystem.sendCommand=GameSystem.sendCommand.concat("!"+s+","+Integer.toString((this.serialNumber))+";");
			}
			else{
				GameSystem.sendCommand="!"+s+","+Integer.toString(this.serialNumber)+";";
			}
			
			if(GameSystem.sendCommandSelf!=null){
				GameSystem.sendCommandSelf=GameSystem.sendCommandSelf.concat("!"+s+","+Integer.toString((this.serialNumber))+";");
			}
			else{
				GameSystem.sendCommandSelf="!"+s+","+Integer.toString(this.serialNumber)+";";
			}
		}
		
		public void sendCommandToOther(String s){
			if(!GameSystem.twoPlayerMode){
				return;
			}
			if(GameSystem.sendCommand!=null){
				GameSystem.sendCommand=GameSystem.sendCommand.concat("!"+s+","+Integer.toString((this.serialNumber))+";");
			}
			else{
				GameSystem.sendCommand="!"+s+","+Integer.toString(this.serialNumber)+";";
			}
		}
}
