package menu;

import game.Game;

import java.awt.Color;
import java.awt.Graphics;

import menu.Menu.MENUSTATE;
import system.GameSystem;

public class MenuDifficulty implements GeneralMenu{
	private double easy = 0.5;
	private double medium = 1;
	private double hard = 2;
	private enum SELECTED{
		EASY,
		MEDIUM,
		HARD
	}
	
	private SELECTED selected = SELECTED.MEDIUM;
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawString("Easy",Menu.X_START,Menu.Y_START);
		g.drawString("Medium",Menu.X_START+Menu.SPACING,Menu.Y_START);
		g.drawString("Hard",Menu.X_START+2*Menu.SPACING,Menu.Y_START);
		
		renderSelected(g);
		
	}

	@Override
	public void renderSelected(Graphics g) {
		if(selected==SELECTED.EASY){
			g.drawImage(Menu.pointer, Menu.POINTER_X_START,Menu.POINTER_Y_START, null);
		}
		else if(selected==SELECTED.MEDIUM){
			g.drawImage(Menu.pointer, Menu.POINTER_X_START+Menu.SPACING,Menu.POINTER_Y_START, null);
		}
		else if(selected==SELECTED.HARD){
			g.drawImage(Menu.pointer, Menu.POINTER_X_START+2*Menu.SPACING,Menu.POINTER_Y_START, null);
		}
		
	}

	@Override
	public void keyPressed(int key) {
		if(key==GameSystem.RIGHT){
			if(selected==SELECTED.EASY){
				selected=SELECTED.MEDIUM;
			}
			else if(selected==SELECTED.MEDIUM){
				selected=SELECTED.HARD;
			}
			else if(selected==SELECTED.HARD){
				selected=SELECTED.EASY;
			}
			GameSystem.playSwitch();
		}
		else if(key==GameSystem.LEFT){
			if(selected==SELECTED.EASY){
				selected=SELECTED.HARD;
			}
			else if(selected==SELECTED.MEDIUM){
				selected=SELECTED.EASY;
			}
			else if(selected==SELECTED.HARD){
				selected=SELECTED.MEDIUM;
			}
			GameSystem.playSwitch();
		}
		else if(key==GameSystem.CONFIRM){
			if(selected==SELECTED.EASY){
				Game.DIFFICULTY = easy;
			}
			else if(selected==SELECTED.MEDIUM){
				Game.DIFFICULTY = medium;
			}
			else if(selected==SELECTED.HARD){
				Game.DIFFICULTY = hard;
			}
			GameSystem.playConfirm();
			Menu.toChooseChar();
		}
		else if(key==GameSystem.CANCEL){
			GameSystem.playCancel();
			Menu.mState=MENUSTATE.MAIN;
		}
		
	}

}
