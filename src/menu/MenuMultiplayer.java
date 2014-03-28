package menu;

import game.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import menu.Menu.MENUSTATE;
import system.Client;
import system.ClientSelf;
import system.GameSystem;

public class MenuMultiplayer implements GeneralMenu{
	//private BufferedImage backGround;
	public String stateChosen="hostGame";
	public MenuMultiplayer(){
		//backGround=GameSystem.loader.loadImage("/image/menu/multBg.png");
	}
	
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	public void render(Graphics g) {
		//g.drawImage(backGround, 0, 0, GameSystem.ABSWIDTH+10,GameSystem.ABSHEIGHT+10,null);
		//g.drawImage(backGround, -400, 0,null);
		g.drawString("Host Game", Menu.X_START-5, Menu.Y_START+Menu.SPACING);
		g.drawString("Join Game", Menu.X_START-2, Menu.Y_START+2*Menu.SPACING);
		renderSelected(g);
	}

	
	public void renderSelected(Graphics g) {
		g.setColor(Color.RED);
		if(stateChosen=="hostGame"){
			g.drawString("Host Game", Menu.X_START-5, Menu.Y_START+Menu.SPACING);
		}
		else if(stateChosen=="joinGame"){
			g.drawString("Join Game", Menu.X_START-2, Menu.Y_START+2*Menu.SPACING);
		}
		
	}

	public void keyPressed(int key) {
		// TODO Auto-generated method stub
		if(key==GameSystem.CONFIRM){
			GameSystem.twoPlayerMode=true;
			
			if(stateChosen=="hostGame"){
				GameSystem.isPlayerOne=true;
				GameSystem.sendCommandToOther("setCurrentStage"+Integer.toString(Game.curLevel));
				
			}
			else if(stateChosen=="joinGame"){
				GameSystem.isPlayerOne=false;
			}
				GameSystem.playConfirm();
				Menu.toChooseChar();
		}
		else if(key==GameSystem.CANCEL){
			Menu.mState=MENUSTATE.MAIN;
			GameSystem.playCancel();
		}
		else if(key==GameSystem.DOWN){
			if(stateChosen=="hostGame"){
				setState("joinGame");
			}
		}
		else if(key==GameSystem.UP){
			if(stateChosen=="joinGame"){
				setState("hostGame");
			}
		}
	}
	
	public void setState(String state){
		this.stateChosen=state;
	}
}
