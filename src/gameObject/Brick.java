package gameObject;

import system.GameSystem;
import game.Game;

public abstract class Brick extends GameObject{

	public Brick(int x, int y, Game game) {
		super(x, y, game);
		hp=30;
		image = GameSystem.loader.loadImage("/image/stage/stage1/brick1.png");
		imageWidth=GameSystem.GRID_SIZE;
		imageHeight=GameSystem.GRID_SIZE*5/4;
		this.renderYShift=-GameSystem.GRID_SIZE*1/4;
		/*
		ss=SpriteData.bricks;
		ssWidth=GameSystem.GRID_SIZE;
		ssHeight=GameSystem.GRID_SIZE;
		imageWidth=GameSystem.GRID_SIZE;
		imageHeight=GameSystem.GRID_SIZE;
		super.image=ss.grabImage(1,1,32,32);
		*/
	}
	public void tick(){
		super.tick();
		if(hp<=0){
			int type = rand.nextInt(6);
			game.getController().removeEntity(this);
			if(type==3){
				game.getController().addEntity(new PowerUps_SpeedUp(this.xGridNearest,yGridNearest,game));
			}
			else if(type==4){
				game.getController().addEntity(new PowerUps_DamageUp(this.xGridNearest,yGridNearest,game));
			}
			else if(type==5){
				game.getController().addEntity(new PowerUps_HpUp(this.xGridNearest,yGridNearest,game));
			}
		}
	}
	public void remove(){
		game.getController().removeEntity(this);
	}
}
