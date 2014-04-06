package menu;

import java.awt.Graphics;

import system.GameSystem;

public class MenuTwoPlayer implements GeneralMenu{
	private enum SELECTED{
		ONE_PLAYER,
		TWO_PLAYERS,
	}
	
	private SELECTED selected = SELECTED.ONE_PLAYER;
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		g.drawString("1 Player", Menu.X_START, Menu.Y_START);
		g.drawString("2 Players", Menu.X_START+2*Menu.SPACING, Menu.Y_START);
		
		this.renderSelected(g);
	}

	@Override
	public void renderSelected(Graphics g) {
		if(selected==SELECTED.ONE_PLAYER){
			g.drawImage(Menu.pointer, Menu.POINTER_X_START, Menu.POINTER_Y_START, null);
		}
		else if(selected==SELECTED.TWO_PLAYERS){
			g.drawImage(Menu.pointer, Menu.POINTER_X_START+2*Menu.SPACING, Menu.POINTER_Y_START, null);
		}
		
	}

	@Override
	public void keyPressed(int key) {
		if(key==GameSystem.CONFIRM){
			if(selected==SELECTED.ONE_PLAYER){
				GameSystem.setDefaultKeyLayout();
				Menu.toChooseChar();
			}
			else if(selected==SELECTED.TWO_PLAYERS){
				GameSystem.TWO_PLAYER_MODE=true;
				Menu.toChooseChar();
			}
			GameSystem.playConfirm();
		}
		else if(key==GameSystem.CANCEL){
			GameSystem.playCancel();
			GameSystem.TWO_PLAYER_MODE=false;
			Menu.toDifficulty();
			GameSystem.setDefaultKeyLayout();
		}
		else if(key==GameSystem.RIGHT){
			if(selected==SELECTED.ONE_PLAYER){
				selected=SELECTED.TWO_PLAYERS;
			}
			else if(selected==SELECTED.TWO_PLAYERS){
				selected=SELECTED.ONE_PLAYER;
			}
			GameSystem.playSwitch();
		}
		else if(key==GameSystem.LEFT){
			if(selected==SELECTED.ONE_PLAYER){
				selected=SELECTED.TWO_PLAYERS;
			}
			else if(selected==SELECTED.TWO_PLAYERS){
				selected=SELECTED.ONE_PLAYER;
			}
			GameSystem.playSwitch();
		}
		
	}

}
