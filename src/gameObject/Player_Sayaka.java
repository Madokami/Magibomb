package gameObject;

import menu.MenuChar;
import system.GameSystem;
import system.IntToImage;
import game.Game;

/**
* <b>Description:</b>
* <br>
* Defines new individual player
* <br>Utilizes unique image to display player
* <br>Corresponds to input coordinates of grid map
* <br>Contains features such as sound and abilites
* @author Team 6
* @version 1.0
* @since 2014-03-31
*/
public class Player_Sayaka extends Player{
	
	
	public Player_Sayaka(int x, int y, Game game) {
		super(x, y, game);
		playerBackground = MenuChar.saBg;
		
		this.skillUlt=loader.loadImage("/image/skills/saSkillUlt.png");
		this.skillUltName="Dash";
		pVoice=new SaVoice();
		status = SpriteData.saStatus;
		setStatusImages();
		
		dash= new ImageSequence("/image/spriteSheet/actors/player/sayaka/dash",3);
		
		name="Sayaka";
		
		run=new ImageSequence("/image/spriteSheet/actors/player/sayaka/run",8);
		stand=new ImageSequence("/image/spriteSheet/actors/player/sayaka/stand",5);
		damage=new ImageSequence("/image/spriteSheet/actors/player/sayaka/damage",4);		
		dead=new ImageSequence("/image/spriteSheet/actors/player/sayaka/dead",9);
		sequence.startSequence(stand);
		
		soulGemSprite=SpriteData.gem_sayaka;
		soulGemImage=soulGemSprite.grabImage(1, 1, soulGemWidth, soulGemHeight);
		
		
		pData.loadPlayerStatus(this);
		
		
		levelImage=IntToImage.toImageSmall(level);
		soulGemValueImage=IntToImage.toImageGriefSyndrome((int)soul);
		maxHp=hp;
		maxMp=mp;
		maxSoul=soul;
		
		ultyCd=180;
		ultyTimer=180;
	}
	public void useUltimate(){
		/*
		game.event1.startEvent(1000, "sayakaCutIn");
		//game.event2.startEvent(5000, "timeStop");
		playUltimateSound();
		GameSystem.musicPlayer.playSwoosh();
		*/
		if(ultyTimer<ultyCd){
			GameSystem.playError();
			this.pVoice.playCdSound();
			return;
		}
		if(mp>skillUltCost){
			mp-=skillUltCost;
			int time = 30;
			int chargeSpd = 40;
			GameSystem.musicPlayer.playSwoosh();
			this.setInvincible(time);
			controller.addEntity(new SaDash(xGridNearest,yGridNearest,game,this,time));
			
			startCharge(chargeSpd,time);
			ultyTimer=0;
		}
	}
	
	public void updatePlayerData(){
		pData.upDatePlayerData(this);
	}
}
