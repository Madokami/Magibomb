package gameObject;

import menu.MenuChar;
import system.GameSystem;
import system.IntToImage;
import game.Game;
import game.Game.CHARACTER;

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
public class Player_Mami extends Player{

	public Player_Mami(int x, int y, Game game) {
		super(x, y, game);
		playerBackground = MenuChar.maBg;
		
		pVoice=new MaVoice();
		
		name="  Mami";
		skill1 = loader.loadImage("/image/skills/maSkill1.png");
		skill2 = loader.loadImage("/image/skills/maSkill2.png");
		skillUlt = loader.loadImage("/image/skills/maSkillUlt.png");
		
		
		run=new ImageSequence("/image/spriteSheet/actors/player/mami/run",8);
		stand=new ImageSequence("/image/spriteSheet/actors/player/mami/stand",7);
		damage=new ImageSequence("/image/spriteSheet/actors/player/mami/damage",5);		
		dead=new ImageSequence("/image/spriteSheet/actors/player/mami/dead",13);
		sequence.startSequence(stand);
		
		soulGemSprite=SpriteData.gem_mami;
		soulGemImage=soulGemSprite.grabImage(1, 1, soulGemWidth, soulGemHeight);
		
		status = SpriteData.maStatus;
		setStatusImages();
		
		if(Game.cChosen==CHARACTER.MAMI){
			pData.loadPlayerStatus(this);
		}
		
		levelImage=IntToImage.toImageSmall(level);
		soulGemValueImage=IntToImage.toImageGriefSyndrome((int)soul);
		maxHp=hp;
		maxMp=mp;
		maxSoul=soul;
	}
	public void useUltimate(){
		if(channelling==true) {
			if(mp<=0){
				stopChannelling();
			}
			mp-=0;
			return;
		}
		
		if(ultyTimer<ultyCd){
			GameSystem.playError();
			this.pVoice.playCdSound();
			return;
		}
		
		if(mp<50){
			return;
		}
		else{
			mp-=5;
			channelling=true;
			game.getController().addEntity(new TiroFinale(this.xGridNearest,yGridNearest,game,this));
			//game.getController().addEntity(new Projectile_blackBeam(this.xGridNearest,yGridNearest,game,this));
			setVelX(0);
			setVelY(0);
		}
		//pVoice.playUltimateSound();
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
