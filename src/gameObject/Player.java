package gameObject;


import game.Game;
import game.MultiplayerStats;
import game.PlayerData;
import gameObject.GameObject.ORIENTATION;
import gameObject.MovableObject.ANIMATION;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import system.BufferedImageLoader;
import system.GameSystem;
import system.IntToImage;

public abstract class Player extends MovableObject{
	public BufferedImage soulGemImage;
	public SpriteSheet soulGemSprite;
	public static final int soulGemWidth=64;
	public static final int soulGemHeight=96;
	public double soulGemCounter=0;
	public final double soulGemAnimationSpeed = 0.3;
	protected String name;
	
	protected int bombCount = 2;
	
	public BufferedImage expBar;
	protected BufferedImage skillPlaceholder;
	protected BufferedImage skill1;
	protected BufferedImage skill2;
	protected BufferedImage skill3;
	protected BufferedImage skillUlt;
	
	protected String tempName = "tempName";
	protected String skill1Name,skill2Name,skill3Name,skillUltName;
	
	public BufferedImageLoader loader;
	public double maxSoul;
	public double soul;
	public double mp,maxMp,maxHp;
	
	public SpriteSheet status;
	public BufferedImage okStatus,midDamageStatus,highDamageStatus,despairStatus,statusBg;
	public final int W_STATUS = 128;
	public final int H_STATUS = 128;
	
	
	public PlayerData pData;
	
	public int level;
	public BufferedImage[] levelImage;
	public BufferedImage[] soulGemValueImage;
	
	public double expCurrent;
	public int BP;
	public int score;
	
	public LevelUp levelUpdater;
	
	public int speedChangeDuration;
	public int speedChangeTimer;
	public int spdOriginal;
	
	public boolean damageMediumPlayed,damageHeavyPlayed,soulGemDarkSoundPlayed;
	
	protected PlayerVoice pVoice;
	
	public boolean dying;
	public int dyingDuration;
	public int dyingDurationTimer;

	public Player(int x, int y, Game game) {
		super(x, y, game);
		
		skill1Name=skill2Name=skill3Name=skillUltName=tempName;
		
		loader = new BufferedImageLoader();
		bombStrength = 50;
		bombLength=3;
		hp=100;
		mp=100;
		maxHp=hp;
		maxMp=mp;
		maxSoul=500;
		soul=maxSoul;
		spd=6;
		spdOriginal=spd;
		soulGemDarkSoundPlayed=false;
		skillPlaceholder=loader.loadImage("/image/skills/skillPlaceholder.png");
		skill1=skill2=skill3=skillUlt=skillPlaceholder;
		
		
		
		expBar=loader.loadImage("/image/expBar.png");
		
		statusBg=loader.loadImage("/image/statusBg.png");
		
		pData = game.getPlayerData();
		levelUpdater = new LevelUp();
		// TODO Auto-generated constructor stub
		collisionWidth=56;
		collisionHeight=56;
		
		if(GameSystem.twoPlayerMode){
			loadFromMultiplayerData();
		}
		
	}


	public void tick(){
		super.tick();
		
		if(GameSystem.twoPlayerMode){
			if(this==game.getPlayer()){
				if(positionUpdateTimer>15){
					positionUpdateTimer=0;
					sendCommand("updateX"+Double.toString(x));
					sendCommand("updateY"+Double.toString(y));
				}
			}
		}
		/*
		if(direction.equals("stand")){
			image=this.standGif;
		}*/
	
		playPlayerHpMissingVoices();
		searchForPowerups();
		dyingDurationTimer++;
		if(dyingDurationTimer>dyingDuration){
			stopDying();
		}
		if(dying){
			animation=ANIMATION.DYING;
			setVelX(0);
			setVelY(0);
		}
		
			if(hp<=0){
				hp=0;
				startDying(160);
				
			}
		if(animation!=ANIMATION.DYING){
			if(soul>0){
				if(hp<maxHp){
					soul--;
					hp=hp+0.2;
					soulGemValueImage=IntToImage.toImageGriefSyndrome((int)soul);
				}
			}
			if(soul>0){
				if(mp<maxMp){
					soul--;
					mp=mp+0.2;
					soulGemValueImage=IntToImage.toImageGriefSyndrome((int)soul);
				}
			}
		}
		
		
	
		//changes the player's "playerImage" depending on movement orientation
		levelUpdater.checkIfLevelUp(this);
		
		//Animate.animate(this);
		Animate.animateGem(this);
		//Animate.animateWithGif(this);
	}
	
	public void render(Graphics g){
		super.render(g);
	}

	public abstract void useUltimate();
	public void setStatusImages() {
		okStatus = status.grabImage(1, 1, W_STATUS, H_STATUS);
		midDamageStatus = status.grabImage(2, 1, W_STATUS, H_STATUS);
		highDamageStatus = status.grabImage(3, 1, W_STATUS, H_STATUS);
		despairStatus = status.grabImage(4, 1, W_STATUS, H_STATUS);
		
	}
	public void renderSkills(Graphics g){
		int spacing = 100;
		int size = 70;
		int x = GameSystem.ABSWIDTH/2+10;
		int y = GameSystem.GAME_HEIGHT+25;
		
		g.drawImage(skill1, x,y,size,size,null);
		g.drawImage(skill2, x+spacing,y,size,size,null);
		g.drawImage(skill3, x+2*spacing,y,size,size,null);
		g.drawImage(skillUlt, x+3*spacing,y,size,size,null);
		
		g.setColor(Color.WHITE);
		g.drawString(skill1Name, x, y+spacing/10);
		g.drawString(skill2Name, x+spacing, y+spacing/10);
		g.drawString(skill3Name, x+2*spacing, y+spacing/10);
		g.drawString(skillUltName, x+3*spacing, y+spacing/10);
		
		g.setColor(new Color(0f,0f,0f,.8f));
		
		if(this.ultyCd-this.ultyTimer>0){
			int time = (this.ultyCd-this.ultyTimer)/30;
			BufferedImage[] cd = IntToImage.toImageSmall(time);						
			g.fillRect(x+3*spacing, y, size, (this.ultyCd-this.ultyTimer)*size/this.ultyCd);
			
			
			for(int i=0;i<cd.length;i++){
				g.drawImage(cd[i], x+3*spacing+spacing/2+i*12, y+spacing/2, null);
			}
		}
	}
	
	public void renderPlayerStatus(Graphics g){
		//g.drawImage(statusBg,0, GameSystem.ABSHEIGHT-H_STATUS+28,null);
		
		g.setFont(new Font("arial",Font.BOLD,11));
		if(soul==0){
				g.drawImage(despairStatus,135, GameSystem.ABSHEIGHT-H_STATUS-6,null);
				return;
		}
		if(hp/maxHp>0.6){
			g.drawImage(okStatus,135, GameSystem.ABSHEIGHT-H_STATUS-6,null);
		}
		else if(hp/maxHp<=0.6&&hp/maxHp>0.3){
			g.drawImage(midDamageStatus,135, GameSystem.ABSHEIGHT-H_STATUS-6,null);
		}
		else if(hp/maxHp<=0.3){
			g.drawImage(highDamageStatus,135, GameSystem.ABSHEIGHT-H_STATUS-6,null);
		}
	}
	
	public void renderSoulGem(Graphics g){
		g.setColor(Color.LIGHT_GRAY);
		g.drawString(name, 160, GameSystem.ABSHEIGHT-H_STATUS+3);
		g.drawImage(soulGemImage,GameSystem.GAME_WIDTH/2-430,GameSystem.GAME_HEIGHT-10,null);
		for(int i=0;i<soulGemValueImage.length;i++){
			g.drawImage(soulGemValueImage[i],GameSystem.GAME_WIDTH/2-430+i*20,GameSystem.GAME_HEIGHT+60,null);
		}
	}
	public void renderExp(Graphics g){
		g.drawImage(expBar, 115+34, GameSystem.GAME_HEIGHT+93, null);
		double ratio = this.expCurrent/this.levelUpdater.expRequired;
		g.setColor(Color.YELLOW);
		g.fillRect(120+34, GameSystem.GAME_HEIGHT+96, (int)(ratio*67), 4);
	}
	public void renderPlayerLevel(Graphics g){
		for(int i=0;i<levelImage.length;i++){
			g.drawImage(levelImage[i],131+i*12,GameSystem.GAME_HEIGHT+89,null);
		}
	}
	
	
	public abstract void updatePlayerData();
	private void playPlayerHpMissingVoices(){
		if(animation==ANIMATION.DYING){
			return;
		}
		
		String hpRange = checkPlayerHpRange();
		if(hpRange.equals("good")){
			damageMediumPlayed=false;
			damageHeavyPlayed=false;
			return;
		}
		else if(hpRange.equals("damaged")){
			damageHeavyPlayed=false;
			if(!damageMediumPlayed) pVoice.playDamagedMediumSound();
			damageMediumPlayed=true;
		}
		else if(hpRange.equals("severe")){
			damageMediumPlayed=true;
			if(!damageHeavyPlayed) pVoice.playDamagedHeavySound();
			damageHeavyPlayed=true;
		}
		if(soul<=0){
			if(!soulGemDarkSoundPlayed) pVoice.playSoulGemDarkSound();
			soulGemDarkSoundPlayed=true;
		}
	}
	private String checkPlayerHpRange(){
		String ret=null;
		if(hp/maxHp>0.6){
			ret="good";
		}
		else if(hp/maxHp<=0.6&&hp/maxHp>0.3){
			ret="damaged";
		}
		else if(hp/maxHp<=0.3){
			ret="severe";
		}
		return ret;
	}
	public void changeSpeed(int value, int duration){
		speedChangeDuration=duration;
		speedChangeTimer=0;
		spdOriginal=spd;
		this.spd=value;
		refreshMovementSpeed();
	}
	public void restoreSpeed(){
		spd=spdOriginal;
	}
	private void searchForPowerups(){
		if(animation==ANIMATION.DYING){
			return;
		}
		int tempNum = Physics.collision(this, game.getPowerUpList());
		if(tempNum!=-1){
			PowerUps temp=game.getPowerUpList().get(tempNum);
			temp.applyEffect(this);
			temp.remove();
			pVoice.playItemFoundSound();
		}
	}


	public void increaseSpeed(int i) {
		if(spd<25) spd++;
	}
	public void stopDying(){
		if(!dying) return;
		game.getController().removePlayer(this);
		if(this==game.getPlayer()){
			game.setPlayerIsAlive(false);
		}
	}
	public void startDying(int duration){
		if(dying) return;
		if(animation!=ANIMATION.DYING) {
			animation=ANIMATION.DYING;
			if(dead!=null) sequence.startOneTimeSequence(dead);
		}
		pVoice.playDeathSound();
		GameSystem.turnOffBgm();
		dying=true;
		dyingDuration=duration;
		dyingDurationTimer=0;
	}
	public void remove(){
		this.game.getController().removePlayer(this);
	}
	public void loadFromMultiplayerData(){
		hp=MultiplayerStats.HP;
		mp=MultiplayerStats.MP;
		bombStrength=MultiplayerStats.BOMBS;
		bombLength=MultiplayerStats.BOMBL;
		//this.level=MultiplayerStats.LEVEL;
		this.spd=MultiplayerStats.SPD;
		BP=MultiplayerStats.BP;
		soul=MultiplayerStats.SOUL;
	}
	public void placeBomb(int bombStrength,int bombLength,int duration){
		if(Physics.onTopOfBomb(this, game.getBombList())!=-1||animation==ANIMATION.DYING||animation==ANIMATION.DAMAGED){
			return;
		}
		if(this.bombCount>=0){
			controller.addEntity(new Bomb(this.xGridNearest,this.yGridNearest,game,bombStrength,bombLength,duration));
			this.bombCount--;
		}
		else{
			GameSystem.playError();
		}
	}
	
	public abstract void useAbility1();
	public abstract void useAbility2();
	public abstract void useAbility3();
}
