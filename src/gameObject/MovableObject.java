package gameObject;

import game.Game;
import system.BufferedImageLoader;
import system.GameSystem;

/**
* <b>Description:</b>
* <br>
* Overall MoveableObject that extends GameObject
* <br>Utilizes methods that determines whether an object is capable of moving across a certain region
* <br>Denotes the length and direction of an object's movement
* @author Team 6
* @version 1.0
* @since 2014-03-31
*/
public abstract class MovableObject extends GameObject{
	public int spd=4;
	public boolean[][] map = game.getWallArray();
	//public int damage;
	public int bombStrength = 20;
	public int bombLength=2;
	
	public boolean nextLocationSet;
	public boolean moving=false;
	public boolean buttonReleased;
	public String nextMove="null";
	public int toleranceX = GameSystem.GRID_SIZE/3;
	public int toleranceY = GameSystem.GRID_SIZE/3;
	private double velX = 0;
	private double velY = 0;
	
	protected boolean dontFlip=false;
	
	/////
	public ImageSequence run,stand,damage,dead,ulty,dash;
	
	/////
	
	public int nextXCrude,nextYCrude;
	protected BufferedImageLoader loader=new BufferedImageLoader();
	protected AnimationParameters animationParameters = new AnimationParameters();
	
	protected Animation sequence;
	
	//starting from here is some special variables for active abilities
	protected boolean charging=false;
	public int chargeSpeed;
	public int chargeDuration;
	public int chargeDurationTimer;
	
	public boolean damaged;
	public int damagedDuration;
	public int damagedDurationTimer;
	
	public int positionUpdateTimer;
	
	public int ultyTimer;
	public int ultyCd;
	protected int abi1Timer;
	protected int abi1Cd;
	protected int abi2Timer;
	protected int abi2Cd;
	protected int abi3Timer;
	protected int abi3Cd;
	
	
	public enum ANIMATION{
		MOVELEFT,
		MOVERIGHT,
		MOVEUP,
		MOVEDOWN,
		STAND,
		JUMPUP,
		JUMPDOWN,
		JUMPLEFT,
		JUMPRIGHT,
		DYING,
		DAMAGED,
		UPATTACK,
	};
	public enum FACING{
		LEFT,
		RIGHT
	};
	
	protected ANIMATION animation = ANIMATION.STAND;
	protected FACING facing = FACING.RIGHT;
	
	/**
	 * Defines MoveableObject
	 * <br><br>
	 * <b>Inputs:</b>
	 * <br><b>x</b>,<b>y</b> - coordinates
	 * <br><b>game</b> - Game object
	 */
	public MovableObject(int x, int y, Game game) {
		super(x, y, game);
		hp=100;
		xTemp=this.x;
		yTemp=this.y;
		targetX=xGridNearest;
		targetY=yGridNearest;
		finishingMove=false;
		moving=false;
		nextLocationSet=false;
		sequence=new Animation(this);
	}
	/**
	 * checks current conditions to determine following procedure
	 */
	public void tick(){
		//first check if blocked
		super.tick();
		tickTimers();
		applySpecialAbilitiesWithinDuration();
		if(sequence!=null){
			sequence.tick();
		}
		if(adjustToBlockageAndReturnTrueIfBlocked()||atEdge) {
			if(atEdge){
				checkIfAtEdge();
			}
			if(charging) stopCharge();
			if(animation!=ANIMATION.DYING&&animation!=ANIMATION.DAMAGED) {
				if(animation!=ANIMATION.STAND) {
					animation=ANIMATION.STAND;
					if(stand!=null) sequence.startSequence(stand);
				}
			}
			return;
		}
		if(buttonReleased){
			if(targetPositionReached()){
				velX=0;
				velY=0;
				moving=false;
				if(animation!=ANIMATION.DYING&&animation!=ANIMATION.DAMAGED) {
					if(animation!=ANIMATION.STAND) {
						animation=ANIMATION.STAND;
						if(stand!=null) sequence.startSequence(stand);
					}
				}
				direction="stand";
			}
		}
		if(damaged){
			if(animation!=ANIMATION.DYING) {
				if(animation!=ANIMATION.DAMAGED) {
					animation=ANIMATION.DAMAGED;
					if(damage!=null) sequence.startSequence(damage,stand);
				}
			}
			setVelX(0);
			setVelY(0);
		}
		
		x+=velX;
		y+=velY;
		updatePosition();
		checkIfAtEdge();
		
		
	}
	private void tickTimers() {
		chargeDurationTimer++;
		damagedDurationTimer++;
		ultyTimer++;
		abi1Timer++;
		abi2Timer++;
		abi3Timer++;
		if(GameSystem.LAN_TWO_PLAYER_MODE){
			if(GameSystem.isPlayerOne){
				this.positionUpdateTimer++;
			}
		}
		//damage.tick();
		
	}
	private void applySpecialAbilitiesWithinDuration(){
		if(damagedDurationTimer>damagedDuration){
			stopDamaged();
		}
		
		
		
		if(chargeDurationTimer<chargeDuration) {
			charge();
			charging=true;
		}
		
		else {
			stopCharge();
		}
	}
	//moveUp shall move 1 grid up only
	public void moveUp(){
		if(GameSystem.TWO_PLAYER_MODE){
			if(Game.getPlayer2().dying){
				return;
			}
		}
		if(animation==ANIMATION.DAMAGED||Game.getPlayer().dying||channelling||charging) return;
		moving=true;
		buttonReleased=false;
		orientation=ORIENTATION.UP;
		if(animation!=ANIMATION.MOVEUP) {
			animation=ANIMATION.MOVEUP;
			if(run!=null) sequence.startSequence(run);
		}
		direction="up";
		setNextXY();
		setDestination(nextX,nextY);
		velY=-1*spd/2;
		velX=0;
	}
	//moveDown shall move 1 grid down only
	public void moveDown(){
		if(GameSystem.TWO_PLAYER_MODE){
			if(Game.getPlayer2().dying){
				return;
			}
		}
		if(animation==ANIMATION.DAMAGED||Game.getPlayer().dying||channelling||charging) return;
		moving=true;
		buttonReleased=false;
		orientation=ORIENTATION.DOWN;
		if(animation!=ANIMATION.MOVEDOWN) {
			animation=ANIMATION.MOVEDOWN;
			if(run!=null) sequence.startSequence(run);
		}
		direction="down";
		setNextXY();
		setDestination(nextX,nextY);
		velY=spd/2;
		velX=0;
	}
	//moveRight shall move 1 grid right only
	public void moveRight(){
		if(GameSystem.TWO_PLAYER_MODE){
			if(Game.getPlayer2().dying){
				return;
			}
		}
		if(animation==ANIMATION.DAMAGED||Game.getPlayer().dying||channelling||charging) return;
		moving=true;
		buttonReleased=false;
		orientation=ORIENTATION.RIGHT;
		if(animation!=ANIMATION.MOVERIGHT) {
			animation=ANIMATION.MOVERIGHT;
			if(run!=null) sequence.startSequence(run);
		}
		facing=FACING.RIGHT;
		direction="right";
		setNextXY();
		setDestination(nextX,nextY);
		velX=spd/2;
		velY=0;
	}
	//moveLeft shall move 1 grid left only
	public void moveLeft(){
		if(GameSystem.TWO_PLAYER_MODE){
			if(Game.getPlayer2().dying){
				return;
			}
		}
		if(animation==ANIMATION.DAMAGED||Game.getPlayer().dying||channelling||charging) return;
		moving=true;
		buttonReleased=false;
		orientation=ORIENTATION.LEFT;
		if(animation!=ANIMATION.MOVELEFT) {
			animation=ANIMATION.MOVELEFT;
			if(run!=null) sequence.startSequence(run);
		}
		facing=FACING.LEFT;
		direction="left";
		setNextXY();
		setDestination(nextX,nextY);
		velX=-1*spd/2;
		velY=0;
	}
	public void moveStop(){
		if(!charging){
			buttonReleased=true;
		}
	}
	
	
	public void setDestination(int nextX, int nextY) {
		targetX=nextX;
		targetY=nextY;
	}
	
	// determines whether or not object has reached specific position
	public boolean targetPositionReached(){
		
		if(orientation==ORIENTATION.UP&&targetY>=lastY){

			return true;
		}
		else if(orientation==ORIENTATION.DOWN&&targetY<=lastY){
			return true;
		}
		else if(orientation==ORIENTATION.LEFT&&targetX>=lastX){

			return true;
		}
		else if(orientation==ORIENTATION.RIGHT&&targetX<=lastX){

			return true;
		}
		
		return false;
	}
	public void updatePosition(){
		//maps the position to the closest "grid"
		if(y-curY>=GameSystem.GRID_SIZE/2){
			curY=curY+GameSystem.GRID_SIZE;
			yGridNearest++;
		}
		else if(curX-x>=GameSystem.GRID_SIZE/2){
			curX=curX-GameSystem.GRID_SIZE;
			xGridNearest--;
		}
		else if(x-curX>=GameSystem.GRID_SIZE/2){
			curX=curX+GameSystem.GRID_SIZE;
			xGridNearest++;
		}
		else if(curY-y>=GameSystem.GRID_SIZE/2){
			curY=curY-GameSystem.GRID_SIZE;
			yGridNearest--;
		}
		//sets the last completely arrived location grid
		if(y-yTemp>=GameSystem.GRID_SIZE){
			yTemp=yTemp+GameSystem.GRID_SIZE;
			y=yTemp;
			lastY++;
		}
		else if(xTemp-x>=GameSystem.GRID_SIZE){
			xTemp=xTemp-GameSystem.GRID_SIZE;
			x=xTemp;
			lastX--;
		}
		else if(x-xTemp>=GameSystem.GRID_SIZE){
			xTemp=xTemp+GameSystem.GRID_SIZE;
			x=xTemp;
			lastX++;
		}
		else if(yTemp-y>=GameSystem.GRID_SIZE){
			yTemp=yTemp-GameSystem.GRID_SIZE;
			y=yTemp;
			lastY--;
		}
		//only updates nextX and nextY when the move buttons are being pressed down
		/*
		if(movable){
			if(direction.equals("right")){
					nextX=lastX+1;
					nextY=lastY;
			}
			else if(direction.equals("left")){
				nextX=lastX-1;
				nextY=lastY;
			}
			else if(direction.equals("up")){
				nextY=lastY-1;
				nextX=lastX;
			}
			else if(direction.equals("down")){
				nextY=lastY+1;
				nextX=lastX;
			}
		}
		*/
		
	}
	
	public void setNextXY(){
		if(orientation==ORIENTATION.DOWN){
			nextX=lastX;
			nextY=lastY+1;
		}
		else if(orientation==ORIENTATION.UP){
			nextX=lastX;
			nextY=lastY-1;
		}
		else if(orientation==ORIENTATION.LEFT){
			nextX=lastX-1;
			nextY=lastY;
		}
		else if(orientation==ORIENTATION.RIGHT){
			nextX=lastX+1;
			nextY=lastY;
		}
		/*
		else if(orientation==ORIENTATION.STAND){
			nextX=lastX;
			nextY=lastY;
		}
		*/
	}
	public boolean checkIfBlocked(int lastX,int lastY,int nextX, int nextY){
		try{
			if(game.getController().wallArray[lastX][lastY]||game.getController().wallArray[nextX][nextY]){
				return true;
			}	
			if(game.getController().bombArray[lastX][lastY]||game.getController().bombArray[nextX][nextY]){
				return true;
			}	
		}catch(IndexOutOfBoundsException x){
			
		
		}
		return false;
	}
	public boolean checkIfBlocked(){
		setNextXY();
		/*if(Physics.hitWall(this, game.wi)){
			blocked=true;
		}
		else{
			blocked=false;
		}
		*/
		
		try{
			if(game.getController().wallArray[lastX][lastY]||game.getController().wallArray[nextX][nextY]){
				return true;
			}	
		}catch(IndexOutOfBoundsException x){
			
		
		}
		return false;
		
	}
	
	public boolean checkWallCollision(){
		if(Physics.hitWall(this, game.getBrickList())!=-1){
			return true;
		}
		if(Physics.hitPlaceHolder(this, game.getPlaceHolderList())!=-1){
			return true;
		}
		return false;
	}
	public boolean checkBombCollision(){
		int tempNum=Physics.hitBomb(this, game.getBombList());
		if(tempNum!=-1){
			for(int i=0;i<game.getBombList().get(tempNum).initiallyOnBomb.size();i++){
				if(game.getBombList().get(tempNum).initiallyOnBomb.get(i)==this){
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	public void checkIfAtEdge() {
		if(x<=0){
			x=1;
			atEdge=true;
			moving=false;
		}
		if(y<=0){
			y=1;
			atEdge=true;
			moving=false;
		}
		if(x>=GameSystem.GAME_WIDTH-collisionWidth){
			x=GameSystem.GAME_WIDTH-collisionHeight-1;
			atEdge=true;
			moving=false;
		}
		if(y>=GameSystem.GAME_HEIGHT-collisionWidth){
			y=GameSystem.GAME_HEIGHT-collisionHeight-1;
			atEdge=true;
			moving=false;
		}
		else{
			atEdge=false;
		}
		
	}
	public void moveToLastAcceptableLocation(){
		this.x=this.xTemp;
		this.y=this.yTemp;
	}
	
	
	public void startCharge(int value, int duration){
		chargeSpeed=value;
		chargeDuration=duration;
		chargeDurationTimer=0;
	}
	public void stopCharge(){
		if(!charging){
			return;
		}
		System.out.println("stopCharge is called.");
		charging=false;
		chargeDurationTimer=chargeDuration+1;
		refreshMovementSpeed();
		moveStop();
		
	}
	private void charge(){
		if(charging) return;
		
	
		if(orientation==ORIENTATION.LEFT){
			moveLeft();
			setVelX(-chargeSpeed);
			animation=ANIMATION.JUMPLEFT;	
		}
		else if(orientation==ORIENTATION.RIGHT){
			moveRight();
			setVelX(chargeSpeed);
			animation=ANIMATION.JUMPRIGHT;
			
		}
		else if(orientation==ORIENTATION.UP){
			moveUp();
			setVelY(-chargeSpeed);
			animation=ANIMATION.JUMPUP;
			
		}
		else if(orientation==ORIENTATION.DOWN){
			moveDown();
			setVelY(chargeSpeed);
			animation=ANIMATION.JUMPRIGHT;			
		}
		if(dash!=null) this.sequence.startSequence(dash);
	}
	private void startDamaged(int duration){
		damaged=true;
		damagedDuration=duration;
		damagedDurationTimer=0;
	}
	
	private void stopDamaged(){
		if(!damaged) return;
		damaged=false;
		animation=ANIMATION.STAND;
	}
	
	
	public void setVelX(double value){
		velX=value/2;
		//velY=0;
	}
	public void setVelY(double value){
		velY=value/2;
		//velX=0;
	}
	public double getVelX(){
		return this.velX;
	}
	public double getVelY(){
		return this.velY;
	}
	public void refreshMovementSpeed(){
		if(orientation==ORIENTATION.RIGHT) moveRight();
		else if(orientation==ORIENTATION.LEFT) moveLeft();
		else if(orientation==ORIENTATION.UP) moveUp();
		else if(orientation==ORIENTATION.DOWN) moveDown();
	}
	public void refreshMapPostion(){
		
	}
	private boolean adjustToBlockageAndReturnTrueIfBlocked(){
		if(checkIfBlocked()){
			if(this.checkBombCollision()){
				this.moveToLastAcceptableLocation();
			}
			//checks the orientation
			if(orientation==ORIENTATION.UP||orientation==ORIENTATION.DOWN){
				//sets it so that the y position is at right place
				//update position so variables catch up to the explicit change
				y=yTemp;
				updatePosition();
				//if the right side is not blocked, and the player is close enough to right, shift him to right;
				if(!checkIfBlocked(lastX+1,lastY,nextX+1,nextY)){
					if(xTemp+this.collisionWidth-x<=toleranceX){
						x=xTemp+collisionWidth;
						return false;
					}
			
				}
				
				if(!checkIfBlocked(lastX-1,lastY,nextX-1,nextY)){
					if((x+collisionWidth-xTemp)<=toleranceX){
						x=xTemp-collisionWidth;
						return false;
					}
					
				}
			}
			else if(orientation==ORIENTATION.LEFT||orientation==ORIENTATION.RIGHT){
				x=xTemp;
				updatePosition();
				if(!checkIfBlocked(lastX,lastY+1,nextX,nextY+1)){
					if(yTemp+this.collisionHeight-y<=toleranceY){
						y=yTemp+collisionHeight;
						return false;
					}
				
				}
				if(!checkIfBlocked(lastX,lastY-1,nextX,nextY-1)){
					if(y+collisionHeight-yTemp<=toleranceY){
						y=yTemp-collisionHeight;
						return false;
					}
				
				}
			}
			return true;
			
		}
		else{
			if(checkWallCollision()){
				this.moveToLastAcceptableLocation();
			}
			if(this.checkBombCollision()){
				this.moveToLastAcceptableLocation();
			}
		}
		return false;
	}
	
	public abstract void useUltimate();
	public abstract void useAbility1();
	public abstract void useAbility2();
	public abstract void useAbility3();
	
	public void takeDamage(int damage){
		super.takeDamage(damage);
		this.startDamaged(8);
	}
	
	public void setXPosition(double x){
		this.x=x;
	}
	public void setYPosition(double y){
		this.y=y;
	}
	public boolean isChannelling(){
		return this.channelling;
	}
}
