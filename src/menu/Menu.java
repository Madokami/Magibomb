package menu;

import game.Game;
import game.Game.GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import system.BufferedImageLoader;
import system.Client;
import system.ClientSelf;
import system.GameSystem;
import system.GameSystem.STATE;
import system.StartClient;
//Note: if the Menu class get too fat with information and gets confusing, you should create
//additional classes
public class Menu {
	//you can delete these 3 Rectangle objects. They are only used to track the position of text
	//However, I have changed menu input from mouse to keyboard because keyboard is a lot easier to code.
	public MenuDeath mDeath;
	public MenuChar mChar;
	public MenuScore mScore;
	public MenuMultiplayer mMult;
	public static BufferedImage logo;
	
	public static int X_START = GameSystem.WIDTH/2+120;
	public static int Y_START = 150;
	public static int SPACING = 47;
	
	
	private static String myIp="192.168.0.101";
	
	private static String self="127.0.0.1";
	
	Thread client;
	Thread clientSelf;
	
	//the bufferedImageLoader is something that I made to save steps in loading images.
	BufferedImage start;
	BufferedImage menu;
	BufferedImage help;
	public static BufferedImage pointer;
	
	public Game game;
	Image gif;
	
	//enum: only 1 state can be true at a time.
	//used to track state of game and change what gets rendered;
	public static enum MENUSTATE{
		MAIN,
		CHOOSECHAR,
		SAVE,
		LOAD,
		SETTING,
		DEATH,
		SCORE,
		TWOPLAYER
	};
	
	//determines what menu option is currently selected by the user
	//"selected" options will light up
	public static enum SELECTED{
		STORY,
		ARCADE,
		TWOPLAYER,
	};
	
	//Initializes the two states.
	public static SELECTED selected = SELECTED.STORY;
	public static MENUSTATE mState = MENUSTATE.MAIN;
	
	public Menu(Game game){
		this.game=game;
		
		mChar = new MenuChar(game);
		mDeath = new MenuDeath();
		mScore = new MenuScore();
		mMult = new MenuMultiplayer();
		//sample usage
		//NOTE: use loader.loadGif(path) to load .gif, or else doesn't work.
		start = GameSystem.loader.loadImage("/homu.png");
		//menu = loader.loadImage("/menu.png");
		menu=GameSystem.loader.loadImage("/image/menu/test.png");
		help = GameSystem.loader.loadImage("/help.png");
		pointer=GameSystem.loader.loadImage("/image/menu/griefSeed2.png");
		logo=GameSystem.loader.loadImage("/image/menu/magibomb.png");
		//example .gif loading
		gif = GameSystem.loader.loadGif("/homura.gif");
	}
	
	//this method is called automatically by GameSystem
	//use this method to update variables continuously
	//this method will be called exactly 30 times per second.
	public void tick(){
		X_START=70;
		Y_START=GameSystem.ABSHEIGHT-30;
		SPACING=300;
		if(mState==MENUSTATE.CHOOSECHAR){
			mChar.tick();
		}
		else if(mState==MENUSTATE.DEATH){
			mDeath.tick();
		}
		else if(mState==MENUSTATE.TWOPLAYER){
			mMult.tick();
		}
	}
	
	//this method draws stuff. this method will only activate when GameSystem.state is set to MENU
	//this method will be called continuously. 
	//NOTE: if you need to update a variable for example: update a position variable every time the game loop finish, do it in tick()
	// because tick() is designed to be ran 60 times per second, and if it lags during intense times, 
	//it will be ran bonus times later to make up for the lack of update.
	// on the other hand, the render method has no restrictions on how many times it's ran per second.
	public void render(Graphics g){
		Font f1 = new Font("arial",Font.BOLD,50);
		g.setFont(f1);
		g.setColor(Color.BLACK);
		g.drawImage(menu, 0, 0,GameSystem.ABSWIDTH+10,GameSystem.ABSHEIGHT+10, null);
		//could omit the width and height in drawString or drawImage
		//g.drawString(GameSystem.TITLE, GameSystem.ABSWIDTH/3, 100);
		g.drawImage(logo, GameSystem.ABSWIDTH/18, GameSystem.ABSHEIGHT/20, null);
		if(mState==MENUSTATE.MAIN){
			
			g.drawString("Story", X_START-5, Y_START);
			g.drawString("Arcade", X_START-2+SPACING, Y_START);
			g.drawString("Network", X_START-2+2*SPACING, Y_START);
			//g.drawString("Quit", X_START-2+3*SPACING, Y_START);
		}
		//make sure renderSelected is under renderCurrentState or else you won't see the changes
		renderCurrentState(g);
		renderSelected(g);
	}
	
	//this method simply filters what to draw out based on the current Menu.SELECTED state
	public void renderSelected(Graphics g){
		if(mState==MENUSTATE.MAIN){
			if(selected==SELECTED.STORY){
				//g.setColor(Color.LIGHT_GRAY);
				g.drawString("Story", X_START-5, Y_START);
				g.drawImage(pointer, X_START-5-pointer.getWidth(), Y_START-pointer.getHeight(), null);
			}
			else if(selected==SELECTED.ARCADE){
				//g.setColor(Color.LIGHT_GRAY);
				g.drawString("Arcade", X_START-2+SPACING, Y_START);
				g.drawImage(pointer, X_START-5-pointer.getWidth()+SPACING, Y_START-pointer.getHeight(), null);
			}
			else if(selected==SELECTED.TWOPLAYER){
				//g.setColor(Color.LIGHT_GRAY);
				g.drawString("Network", X_START-2+2*SPACING, Y_START);
				g.drawImage(pointer, X_START-5-pointer.getWidth()+2*SPACING, Y_START-pointer.getHeight(), null);
			}
		}
		else if(mState==MENUSTATE.CHOOSECHAR){
			mChar.renderSelected(g);
		}
	}
	//change what is render based on the current MENUSTATE
	public void renderCurrentState(Graphics g){
		if(mState == MENUSTATE.MAIN){
			return;
		}
		else if(mState == MENUSTATE.CHOOSECHAR){
			//draws a black background for now. Change it to something nicer :D
			mChar.render(g);
		}
		else if(mState == MENUSTATE.DEATH){
			mDeath.render(g);
		}
		else if(mState == MENUSTATE.SCORE){
			mScore.render(g);
		}
		else if(mState == MENUSTATE.TWOPLAYER){
			mMult.render(g);
		}
	}
	
	//detects user keyboard input. the parameter "key" is passed down from GameSystem.keyPressed
	public void keyPressed(int key) {
		if(mState==MENUSTATE.CHOOSECHAR){
			mChar.keyPressed(key);
		}
		else if(mState==MENUSTATE.DEATH){
			mDeath.keyPressed(key);
		}
		else if(mState==MENUSTATE.SCORE){
			mScore.keyPressed(key);
		}
		else if(mState==MENUSTATE.TWOPLAYER){
			mMult.keyPressed(key);
		}
		//when "down" is pressed
		else if(key==GameSystem.RIGHT){
			//if the current selected mode is story
			if(mState==MENUSTATE.MAIN){
				if(Menu.selected==Menu.SELECTED.STORY){
					//change the current selected mode to arcade
					Menu.selected=Menu.SELECTED.ARCADE;
					//hopefully find a better sound effect. this one is too long
					GameSystem.playSwitch();
				}
				else if(Menu.selected==Menu.SELECTED.ARCADE){
					Menu.selected=Menu.SELECTED.TWOPLAYER;
					//hopefully find a better sound effect. this one is too long
					GameSystem.playSwitch();
				}
			}
		}
		else if(key==GameSystem.LEFT){
			if(mState==MENUSTATE.MAIN){
				if(Menu.selected==Menu.SELECTED.ARCADE){
					Menu.selected=Menu.SELECTED.STORY;
					GameSystem.playSwitch();
				}
				else if(Menu.selected==Menu.SELECTED.TWOPLAYER){
					Menu.selected=Menu.SELECTED.ARCADE;
					//hopefully find a better sound effect. this one is too long
					GameSystem.playSwitch();
				}
			}
		}
		
		//user has pressed "z"
		else if(key==KeyEvent.VK_Z){
			if(mState==MENUSTATE.MAIN){
				//if currently story mode is selected
				if(Menu.selected==Menu.SELECTED.STORY){
					GameSystem.playConfirm();
					toStoryMode();
				}
				//similarly if arcade mode is selected
				else if(Menu.selected==Menu.SELECTED.ARCADE){
					//change Menu state to CHOOSECHAR state.
					GameSystem.playConfirm();
					mChar.setChooseChar();
					mChar.yShift=1;
					Menu.mState=Menu.MENUSTATE.CHOOSECHAR;
				}
				else if(Menu.selected==Menu.SELECTED.TWOPLAYER){
					//change Menu state to CHOOSECHAR state.
					//StartClient.init();
					String fdaIp="132.216.48.176";
					String homeIp="192.168.0.101";
					String mcgillIp="142.157.59.166";
					if(client==null){
						client = new Thread(new Client("142.157.164.180"));
						client.start();
					}
					/*
					if(clientSelf==null){
						clientSelf = new Thread(new ClientSelf(Menu.myIp));
						clientSelf.start();
					}
					*/
					
					/*
					if(!GameSystem.twoPlayerMode){
						GameSystem.twoPlayerMode=true;
						//GameSystem.sendCommand("twoPlayer");
					}
					else{
						GameSystem.isPlayerOne=false;
					}
					*/
					GameSystem.playConfirm();
					toMultiPlayer();
				}
			}
			
		}
		
	}
	public static void toStoryMode() {
		GameSystem.turnOffBgm();
		GameSystem.state=STATE.STORY;
	}
	
	public static void toChooseChar(){
		MenuChar.setChooseChar();
		MenuChar.yShift=1;
		Menu.mState=Menu.MENUSTATE.CHOOSECHAR;
	}
	public static void toMultiPlayer(){
		Menu.mState=Menu.MENUSTATE.TWOPLAYER;
	}
	
	public static void toGameMode() {
		GameSystem.turnOffBgm();
		Game.gState=GameState.WAIT;
		GameSystem.state=STATE.GAME;
	}
	//2 ways to play bgm
	//first one plays the default bgm
	public static void backToMenu() {
		GameSystem.turnOffBgm();
		System.gc();
		GameSystem.twoPlayerMode=false;
		GameSystem.turnOnBgm("/sound/music/title.wav");
		Menu.mState=MENUSTATE.MAIN;
	}
}
