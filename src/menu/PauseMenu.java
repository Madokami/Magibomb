package menu;

import game.Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import system.BufferedImageLoader;
import system.GameSystem;
import system.GameSystem.STATE;

public class PauseMenu implements GeneralMenu{
	private BufferedImageLoader loader;
	private BufferedImage background;
	private BufferedImage pointer;
		
	public static final int PAUSE_MENU_WIDTH = GameSystem.GAME_WIDTH/3 - 50;
	public static final int PAUSE_MENU_HEIGHT = GameSystem.GAME_HEIGHT/4;
	
	public static final int RESUME = 1;
	public static final int SETTINGS = 2;
	public static final int QUIT = 3;
	
	public static int pauseState = RESUME; 
	
	
	public static final int CONTROLS = 1;
	public static final int MUTE = 2;
	public static final int BACK = 3;
	
	public static int settingState = CONTROLS;
	
	
	public static enum PauseState {
		PAUSE, SETTINGS, CONTROLS,
	};
	
	public static PauseState pState = PauseState.PAUSE;
	
	Game game;
	
	public PauseMenu(Game game){
		loader = new BufferedImageLoader();
		this.game=game;
		pointer = loader.loadImage("/image/menu/pointer.png");
		background = loader.loadImage("/image/white.png");
	}
	
	public void render(Graphics g) {
		g.drawImage(background, 25, 10, GameSystem.GAME_WIDTH - 45, GameSystem.GAME_HEIGHT - 35, null);
		g.setFont(new Font("Arial", Font.BOLD, 64));
		if (pState == PauseState.PAUSE){
			if(pauseState == RESUME){
				g.setColor(Color.RED);
				g.drawImage(pointer, PAUSE_MENU_WIDTH - 80, PAUSE_MENU_HEIGHT - 31, null);
			} else {
				g.setColor(Color.BLACK);
			}
			g.drawString("RESUME", PAUSE_MENU_WIDTH, PAUSE_MENU_HEIGHT + 25);
			
			if(pauseState == SETTINGS){
				g.setColor(Color.RED);
				g.drawImage(pointer, PAUSE_MENU_WIDTH - 80, PAUSE_MENU_HEIGHT*2 - 31, null);
			} else {
				g.setColor(Color.BLACK);
			}
			g.drawString("SETTINGS", PAUSE_MENU_WIDTH, PAUSE_MENU_HEIGHT*2 + 25);
			
			if(pauseState == QUIT){
				g.setColor(Color.RED);
				g.drawImage(pointer, PAUSE_MENU_WIDTH - 80, PAUSE_MENU_HEIGHT*3 - 31, null);
			} else {
				g.setColor(Color.BLACK);
			}
			g.drawString("QUIT", PAUSE_MENU_WIDTH, PAUSE_MENU_HEIGHT*3 + 25);
			
		} else if (pState == PauseState.SETTINGS){
			if (settingState == CONTROLS){
				g.setColor(Color.RED);
				g.drawImage(pointer, PAUSE_MENU_WIDTH - 80, PAUSE_MENU_HEIGHT - 31, null);
			} else {
				g.setColor(Color.BLACK);
			}
			g.drawString("CONTROLS", PAUSE_MENU_WIDTH, PAUSE_MENU_HEIGHT + 25);
			
			if (settingState == MUTE){
				g.setColor(Color.RED);
				g.drawImage(pointer, PAUSE_MENU_WIDTH - 80, PAUSE_MENU_HEIGHT*2 - 31, null);
			} else {
				g.setColor(Color.BLACK);
			}
			if (GameSystem.mute)
				g.drawString("MUSIC: OFF", PAUSE_MENU_WIDTH, PAUSE_MENU_HEIGHT*2 + 25);
			else
				g.drawString("MUSIC: ON", PAUSE_MENU_WIDTH, PAUSE_MENU_HEIGHT*2 + 25);
			
			if (settingState == BACK){
				g.setColor(Color.RED);
				g.drawImage(pointer, PAUSE_MENU_WIDTH - 80, PAUSE_MENU_HEIGHT*3 - 31, null);
			} else {
				g.setColor(Color.BLACK);
			}
			g.drawString("BACK", PAUSE_MENU_WIDTH, PAUSE_MENU_HEIGHT*3 + 25);
			
		} else if (pState == PauseState.CONTROLS){
			g.setColor(Color.BLACK);
			g.drawString("CONTROLS", GameSystem.GAME_WIDTH/3 - 50, GameSystem.GAME_HEIGHT/5 + 15);
			g.drawString("\u2191", GameSystem.GAME_WIDTH/4 - 50, GameSystem.GAME_HEIGHT*3/10 - 15);
			g.drawString("\u2192", GameSystem.GAME_WIDTH/4 -5, GameSystem.GAME_HEIGHT*3/10 + 45);
			g.drawString("\u2193", GameSystem.GAME_WIDTH/4 - 50, GameSystem.GAME_HEIGHT*3/10 + 50);
			g.drawString("\u2190", GameSystem.GAME_WIDTH/4 - 125, GameSystem.GAME_HEIGHT*3/10 + 45);
			g.drawString("-", GameSystem.GAME_WIDTH/4 + 75, GameSystem.GAME_HEIGHT*4/10 - 15);
			g.drawString("MOVEMENT", GameSystem.GAME_WIDTH/4 + 100, GameSystem.GAME_HEIGHT*4/10 - 10);
			g.drawString("Z", GameSystem.GAME_WIDTH/4 - 50, GameSystem.GAME_HEIGHT*5/10 + 20);
			g.drawString("-", GameSystem.GAME_WIDTH/4, GameSystem.GAME_HEIGHT*5/10 + 15);
			g.drawString("PLACE BOMB", GameSystem.GAME_WIDTH/4 + 25, GameSystem.GAME_HEIGHT*5/10 + 20);
			g.drawString("C", GameSystem.GAME_WIDTH/4 - 50, GameSystem.GAME_HEIGHT*6/10 + 50);
			g.drawString("-", GameSystem.GAME_WIDTH/4, GameSystem.GAME_HEIGHT*6/10 + 45);
			g.drawString("SPECIAL ABILITY", GameSystem.GAME_WIDTH/4 + 25, GameSystem.GAME_HEIGHT*6/10 + 50);
			g.setColor(Color.RED);
			g.drawImage(pointer, GameSystem.GAME_WIDTH/3 - 80, GameSystem.GAME_HEIGHT*4/5 + 30 - 56, null);
			g.drawString("BACK", GameSystem.GAME_WIDTH/3, GameSystem.GAME_HEIGHT*4/5 + 30);
		}
	}
	public void tick(){
		this.pointer=game.getPlayer().soulGemImage;
	}
	public void keyPressed(int key) {
		if (key == KeyEvent.VK_P) {
			GameSystem.state = STATE.GAME;
			pauseState = RESUME;
			settingState = CONTROLS;
			pState = PauseState.PAUSE;
		}
		if (key == KeyEvent.VK_DOWN){
			if (pState == PauseState.PAUSE){
				pauseState += 1;
				if (pauseState > 4)
					pauseState = 1;
			} else if (pState == PauseState.SETTINGS){
				settingState += 1;
				if (settingState > 3)
					settingState = 1;
			}
		}
		if (key == KeyEvent.VK_UP){
			if (pState == PauseState.PAUSE){
				pauseState -= 1;
				if (pauseState == 0)
					pauseState = 3;
			} else if (pState == PauseState.SETTINGS){
				settingState -= 1;
				if (settingState < 1)
					settingState = 3;
			}
		}
		if (key == KeyEvent.VK_Z){
			if (pState == PauseState.PAUSE){
				if (pauseState == RESUME){
					GameSystem.state = STATE.GAME;
				} else if (pauseState == SETTINGS){
					pState = PauseState.SETTINGS;
				} else if (pauseState == QUIT){
					System.exit(0);
				}
			} else if (pState == PauseState.SETTINGS){
				if (settingState == CONTROLS){
					pState = PauseState.CONTROLS;
				} else if (settingState == MUTE){
					if (GameSystem.mute){
						GameSystem.mute = false;
						//GameSystem.turnOnBgm("/sound/delusio_summa.wav");
						GameSystem.setMusicVolume(-10);
					} else {
						GameSystem.mute = true;
						//GameSystem.turnOffBgm();
						GameSystem.setMusicVolume(-80);
					}
				} else if (settingState == BACK){
					pState = PauseState.PAUSE; 
					settingState = CONTROLS;
				}
			} else if (pState == PauseState.CONTROLS) {
				pState = PauseState.SETTINGS;
			}
		}
	}

	@Override
	public void renderSelected(Graphics g) {
		// TODO Auto-generated method stub
		
	}
}
