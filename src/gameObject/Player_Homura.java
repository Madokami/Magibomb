package gameObject;

import game.Game;
import game.Game.CHARACTER;

import java.awt.Graphics;
import java.awt.Image;

import menu.MenuChar;
import system.GameSystem;
import system.IntToImage;

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
public class Player_Homura extends Player{
	Image damaged;
	public Player_Homura(int x, int y, Game game) {
		
		super(x, y, game);
		playerBackground = MenuChar.hoBg;
		
		name="Homura";
		skillUltName="Time Stop";
		this.skillUlt=loader.loadImage("/image/skills/hoSkillUlt.png");
		
		//this.moveRightGif=loader.loadGif("/image/spriteSheet/actors/player/homura/run.gif");
		run=new ImageSequence("/image/spriteSheet/actors/player/homura/run",8);
		stand=new ImageSequence("/image/spriteSheet/actors/player/homura/stand",8);
		damage=new ImageSequence("/image/spriteSheet/actors/player/homura/damage",4);		
		dead=new ImageSequence("/image/spriteSheet/actors/player/homura/dead",14);
		ulty=new ImageSequence("/image/spriteSheet/actors/player/homura/ulty",7);
		ulty.setAnimationSpeed(0.3);
		sequence.startSequence(stand);
		
		pVoice=new HoVoice();
		
		soulGemSprite=SpriteData.gem_homura;
		soulGemImage=soulGemSprite.grabImage(1, 1, soulGemWidth, soulGemHeight);
		
		status = SpriteData.hoStatus;
		setStatusImages();
		
		if(Game.cChosen==CHARACTER.HOMURA){
			pData.loadPlayerStatus(this);
		}
		levelImage=IntToImage.toImageSmall(level);
		soulGemValueImage=IntToImage.toImageGriefSyndrome((int)soul);
		maxHp=hp;
		maxMp=mp;
		maxSoul=soul;

		ultyCd = 300;
		ultyTimer=300;
	}
	
	public void render(Graphics g){
		super.render(g);
	}
	public void useUltimate(){
		if(ultyTimer<ultyCd){
			GameSystem.playError();
			this.pVoice.playCdSound();
			return;
		}
		if(mp-50<0){
			return;
		}
		else{
			mp=mp-50;
			pVoice.playUltimateSound();
			sequence.startSequence(ulty, stand);
			setVelX(0);
			setVelY(0);
			//game.event1.startEvent(2000, "timeStopCutIn");
			game.event2.startEvent(5000, "timeStop");
			ultyTimer=0;
			//GameSystem.musicPlayer.playSwoosh();
		}
	}
	
	public void updatePlayerData(){
		pData.upDatePlayerData(this);
	}

	@Override
	public void useAbility2() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void useAbility3() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void useAbility1() {
		// TODO Auto-generated method stub
		
	}
	

}
