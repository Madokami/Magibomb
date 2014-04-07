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
public class Player_Kyouko extends Player{
	public Player_Kyouko(int x, int y, Game game) {
		super(x, y, game);
		playerBackground = MenuChar.kyBg;
		this.skillUlt=loader.loadImage("/image/skills/kySkillUlt.png");
		this.skillUltName="Flame Guard";
		
		name="Kyouko";
		
		run=new ImageSequence("/image/spriteSheet/actors/player/kyouko/run",8);
		stand=new ImageSequence("/image/spriteSheet/actors/player/kyouko/stand",8);
		damage=new ImageSequence("/image/spriteSheet/actors/player/kyouko/damage",4);		
		dead=new ImageSequence("/image/spriteSheet/actors/player/kyouko/dead",7);
		dead.scale(1.3);
		dead.setX(dead.getX()+15);
		dead.setY(dead.getY()-10);
		sequence.startSequence(stand);
		//moveUpGif=loader.loadGif("/image/spriteSheet/actors/player/kyouko/ky_jump.gif");
		//moveDownGif=loader.loadGif("/image/spriteSheet/actors/player/kyouko/ky_jump.gif");
		
		ssWidth=120;
		ssHeight=60;
		
		pVoice=new KyVoice();
		ssX=4;
		ssY=1;
		ss=SpriteData.char3;
		//image=ss.grabImage(ssX, ssY, ssWidth, ssHeight);
	
		soulGemSprite=SpriteData.gem_kyouko;
		soulGemImage=soulGemSprite.grabImage(1, 1, soulGemWidth, soulGemHeight);
		
		status = SpriteData.kyStatus;
		setStatusImages();
		
		
		pData.loadPlayerStatus(this);
		
		
		levelImage=IntToImage.toImageSmall(level);
		soulGemValueImage=IntToImage.toImageGriefSyndrome((int)soul);
		maxHp=hp;
		maxMp=mp;
		maxSoul=soul;
		
		skillUltCost = 75;
		ultyCd = 100;
		ultyTimer=100;
	}
	
	public void useUltimate(){
		if(ultyTimer<ultyCd){
			GameSystem.playError();
			this.pVoice.playCdSound();
			return;
		}
		if(mp>skillUltCost){
			mp-=skillUltCost;
			controller.addEntity(new Projectile_Fire(xGridNearest-1,yGridNearest-1,game,this));
			ultyTimer=0;
		}
		
		
	}
	public void updatePlayerData(){
		pData.upDatePlayerData(this);
	}

}
