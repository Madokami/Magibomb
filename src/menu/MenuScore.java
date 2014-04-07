package menu;
/**
* Description:
* Game score menu
* @author Team 6
* @version 1.4
* @since 2014-04-06
*/
import game.Game;
import gameObject.Player;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;


import system.GameSystem;

public class MenuScore implements GeneralMenu{
	private enum SELECTED{
		CONTINUE,
		CHANGE_CHARACTER,
		STATUS,
	}
	
	private SELECTED selected = SELECTED.CONTINUE;
	
	
	
	
	public void tick(){
		
	}
	
	/**
	 * Draw menu button onto screen
	 */
	public void render(Graphics g){
		g.setColor(Color.WHITE);
		
		g.fillRect(0, 0, GameSystem.ABSWIDTH+10, GameSystem.ABSHEIGHT+10);
		g.drawImage(Game.getPlayer().getPlayerBackground(), 0, 0, GameSystem.ABSWIDTH+10,GameSystem.ABSHEIGHT+10,null);
		g.setColor(Color.BLACK);
		g.setFont(new Font("arial", Font.BOLD,30));
		g.drawString("press 'space' go to next level", 200, 100);
		g.drawString("please make this page look nicer", 180,200);
		g.drawString("Current score: "+Player.SCORE, 180,300);
		
		g.drawString("Continue", Menu.X_START, Menu.Y_START);
		g.drawString("change character", Menu.X_START+Menu.SPACING, Menu.Y_START);
		renderSelected(g);
	}
	@Override
	/**
	 * Draw "selected" menu button
	 */
	public void renderSelected(Graphics g) {
		if(selected==SELECTED.CONTINUE){
			g.drawImage(Menu.pointer, Menu.POINTER_X_START,Menu.POINTER_Y_START, null);
		}
		else if(selected==SELECTED.CHANGE_CHARACTER){
			g.drawImage(Menu.pointer, Menu.POINTER_X_START+Menu.SPACING,Menu.POINTER_Y_START, null);
		}
		
	}
	/**
	 * Execute keyboard key functions
	 * @param key keyboard key pressed
	 */
	public void keyPressed(int key) {
		if(key==GameSystem.CONFIRM){
			if(selected==SELECTED.CONTINUE){
				MenuChar.handler.refreshAll();
				Menu.toCharStats();
			}
			else if(selected==SELECTED.CHANGE_CHARACTER){
				GameSystem.PLAYER_ONE_CHOSEN=false;
				Menu.toChooseChar();
			}
			
		}
		else if(key==GameSystem.RIGHT){
			if(selected==SELECTED.CONTINUE){
				selected=SELECTED.CHANGE_CHARACTER;
			}
			else if(selected==SELECTED.CHANGE_CHARACTER){
				selected=SELECTED.CONTINUE;
			}
			GameSystem.playSwitch();
		}
		else if(key==GameSystem.LEFT){
			if(selected==SELECTED.CONTINUE){
				selected=SELECTED.CHANGE_CHARACTER;
			}
			else if(selected==SELECTED.CHANGE_CHARACTER){
				selected=SELECTED.CONTINUE;
			}
			GameSystem.playSwitch();
		}
		
	}


	
	
}
