package game;

import gameObject.Bomb;
import gameObject.Brick;
import gameObject.CommandHandler;
import gameObject.Controller;
import gameObject.Enemy;
import gameObject.Fire;
import gameObject.GameObject.ORIENTATION;
import gameObject.HitableBrick;
import gameObject.PlaceHolder;
import gameObject.Player;
import gameObject.PowerUps;
import gameObject.Projectile;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

import menu.Menu;
import system.BufferedImageLoader;
import system.Client;
import system.GameData;
import system.GameSystem;
import system.GameSystem.STATE;
import system.GameTimer;
import system.Server;
import system.StartClient;

public class Game {
	public GameTimer timer;
	//private CommandHandler commandHandler;
	public static boolean TITLE_DONE;
	
	
	private GameData gameData;
	private Player player;
	private BufferedImage background;
	private boolean playing;
	private boolean musicOn;
	private int duration = 3000;
	private BufferedImage hpGauge;
	private BufferedImage bottomPanel;

	private boolean playerIsAlive = true;
	private LinkedList<Bomb> bombList;
	private LinkedList<Enemy> enemyList;
	private LinkedList<HitableBrick> brickList;
	private LinkedList<PlaceHolder> placeHolderList;
	private LinkedList<Fire> fireList;
	private LinkedList<PowerUps> powerUpList;
	private LinkedList<Projectile> projectileList;
	private boolean[][] wallArray;
	private boolean[][] bombArray;
	
	private Controller controller; 
	public static int curLevel;
	private LevelLoader levelLoader;
	public BufferedImageLoader loader;
	private int lastStage=4;
	private int enemyCount;
	private boolean victory;
	
	private int shift = 428;
	
	private PlayerData playerData;
	private StageRenderer stageRenderer = new StageRenderer();
	
	
	//handles ticking problems with respect to bomb explosion sound.
	//else "time stop" ability + mass bombing leads to serious lag
	public int timePastSinceLastExplode=0;
	public static boolean explosionPlayed;
	
	
	public long renderStageStart;
	
	private boolean timeStop;
	private boolean stopTick;
	
	public TimedEvent event1; 
	public TimedEvent event2;
	
	GameSystem sys;
	BufferedImage cutIn;
	
	public enum CHARACTER{
		MADOKA,
		HOMURA,
		SAYAKA,
		MAMI,
		KYOUKO,
	};
	
	
	
	public static enum GameState{
		PLAY,
		WAIT,
		LOAD,
	};
	public static GameState gState = GameState.WAIT;
	public static CHARACTER cChosen = CHARACTER.HOMURA;
	public static CHARACTER cChosenP2 = null;
	
	
	public Game(GameSystem sys){
		
	
		timer = new GameTimer();
		this.sys = sys;
		playing = false;
		levelLoader = new LevelLoader(this);
		controller = new Controller(this);
		curLevel=1;
		musicOn=false;
		event1 = new TimedEvent(this);
		event2= new TimedEvent(this);
		loader = new BufferedImageLoader();
		//car = l.loadImage("/car.gif");
		cutIn = loader.loadImage("/homuraCutIn.png");
		hpGauge = loader.loadImage("/image/hpGauge.png");
		
		playerData = new PlayerData();
		playerData.loadDefaultValues();
		gameData = new GameData();
		bottomPanel=loader.loadImage("/image/stage/bottomPanel.png");

	}

	public void loadLevel(){
		levelLoader.load();
	}
	public void tick(){
		/*
		System.out.println(MultiplayerStats.HP);
		System.out.println(MultiplayerStats.MP);
		System.out.println(MultiplayerStats.BOMBS);
		System.out.println(MultiplayerStats.BOMBL);
		System.out.println(MultiplayerStats.BP);
		System.out.println(MultiplayerStats.SOUL);
		*/
		//System.out.println(MultiplayerStats.CURSTAGE);
		if(isWaiting()){
			if(GameSystem.twoPlayerMode){
				if(Game.cChosenP2==null){
					System.out.println("player 2 data is still not sent");
					return;
				}
			}
			loadLevel();
			renderStageTitle(duration);
			//Game.cChosenP2=null;
			
			
		}
		if(!isWaiting()){
			if(!playerIsAlive){
				setWait();
				goToDeath();
				return;
			}
			if(victory){
				curLevel++;
				if(curLevel>lastStage){
					curLevel=1;
				}
				player.updatePlayerData();
				gameData.updateGameData(this);
				goToScore();
				saveGame();
				return;
			}
			
		}
		if(Game.gState==Game.GameState.LOAD){
			if(GameSystem.twoPlayerMode){
				if(GameSystem.otherPlayerIsReady){
					if(Game.TITLE_DONE){
						gState=GameState.PLAY;
						playing=true;
					}
				}
				else{
					System.out.println("waiting for other player.....");
				}
			}
			else{
				if(Game.TITLE_DONE){
					gState=GameState.PLAY;
					playing=true;
				}
			}
		}
		if(Game.gState==Game.GameState.PLAY){
			System.out.println(this.bombList.size());
			timer.tick();
			event1.tick();
			event2.tick();
			GameSystem.getCommand=GameSystem.sendCommand;
			checkVictoryCondition();
			if(stopTick){
				return;
			}
			if(timeStop){
				player.tick();
				for(int i=0;i<this.bombList.size();i++){
					bombList.get(i).tick();
				}
				return;
			}			
			controller.tick();
		}
		if(explosionPlayed){
			if(timePastSinceLastExplode<10){
				timePastSinceLastExplode++;
				
			}
			else{
				GameSystem.musicPlayer.reloadExplosion();
				explosionPlayed=false;
				timePastSinceLastExplode=0;
			}
		}

		
	}
	public void renderStageTitle(int duration){
		levelLoader.renderStart(duration);
		gState=GameState.LOAD;
	}
	public void updatePlaying(){
		if(!playerIsAlive){
			playing=false;
		}
	}
	public boolean isWaiting(){
		if(gState==GameState.WAIT){
			return true;
		}
		return false;
	}
	public void setWait(){
		gState=GameState.WAIT;
	}
	public void render(Graphics g){
	
			if(Game.gState==Game.GameState.LOAD){
				levelLoader.render(g);
			}
			else if(Game.gState==Game.GameState.PLAY){
				g.drawImage(background, 0, 0,GameSystem.GAME_WIDTH+10,GameSystem.GAME_HEIGHT, null);
				g.setColor(Color.WHITE);
				for(int i=0;i<GameSystem.GAME_WIDTH;i+=GameSystem.GRID_SIZE){
					g.drawLine(i, 0, i, GameSystem.GAME_HEIGHT);
				}
				for(int i=0;i<GameSystem.GAME_HEIGHT;i+=GameSystem.GRID_SIZE){
					g.drawLine(0, i, GameSystem.GAME_WIDTH, i);
				}
				
				controller.render(g);
				g.setColor(Color.DARK_GRAY);
				g.fillRect(0, GameSystem.GAME_HEIGHT, GameSystem.GAME_WIDTH+10, 106);
				event1.render(g);
				event2.render(g);
				
				player.renderPlayerStatus(g);
				//draws the thing at the bottom which acts as background for player stats
				g.drawImage(bottomPanel, 0, GameSystem.ABSHEIGHT-148,910,160, null);
				
				renderPlayerHealth(g);
				renderPlayerMana(g);
				player.renderExp(g);;
				player.renderSkills(g);
				player.renderPlayerLevel(g);
				player.renderSoulGem(g);
				
				timer.render(g);
			}
		
	}
	public void timeStop(){
		timeStop = true;
	}
	public void stopTick(){
		stopTick = true;
	}
	public void removeTimedEvents(){
		stopTick = false;
		timeStop = false;
	}

	public void keyPressed(int key) {
		if(key==KeyEvent.VK_P){
			GameSystem.state=STATE.PAUSE;
		}
		if(gState==GameState.PLAY){
			
			if(key==GameSystem.ULT){
				if(player.damaged){
					return;
				}
				if(cChosen==CHARACTER.MAMI){
					if(player.isChannelling()){
						return;
					}
				}
				player.sendCommand("ult");
				player.useUltimate();
				
			}
			else if(key==GameSystem.CONFIRM){
				player.sendCommand("placeBomb");
				player.placeBomb(player.bombStrength,player.bombLength,45);
				
				
			}
			else if(key==GameSystem.CANCEL){
				player.kickBomb();
			}
				if(key==GameSystem.RIGHT){
					player.sendCommand("moveRight");
					player.moveRight();
				}
				else if(key==GameSystem.LEFT){
					player.sendCommand("moveLeft");
					player.moveLeft();
				}
				else if(key==GameSystem.UP){
					player.sendCommand("moveUp");
					player.moveUp();
				}
				else if(key==GameSystem.DOWN){
					player.sendCommand("moveDown");
					player.moveDown();
				}
			
		}
		
	}

	public void keyReleased(int key) {
		if(key==GameSystem.RIGHT&&player.orientation==ORIENTATION.RIGHT){
			
			player.sendCommand("moveStop");
			player.moveStop();
		}
		else if(key==GameSystem.LEFT&&player.orientation==ORIENTATION.LEFT){
		
			player.sendCommand("moveStop");
			
			player.moveStop();
		}
		else if(key==GameSystem.UP&&player.orientation==ORIENTATION.UP){

			player.sendCommand("moveStop");
			player.moveStop();
		}
		else if(key==GameSystem.DOWN&&player.orientation==ORIENTATION.DOWN){
			if(GameSystem.twoPlayerMode){
				player.sendCommand("moveStop");
			}
			player.moveStop();
		}
		else if(key==GameSystem.ULT){
			if(cChosen==CHARACTER.MAMI){
				player.stopChannelling();
			}
		}
	
	}
	
	public void decreaseEnemyCount() {
		enemyCount--;
		
	}
	
	public void goToMenu(){
		GameSystem.turnOffBgm();
		GameSystem.turnOnBgm("/sound/music/theme1.wav");	
		Menu.mState=Menu.MENUSTATE.MAIN;
		GameSystem.state=GameSystem.STATE.MENU;
	}
	public void goToDeath(){
		GameSystem.turnOffBgm();
		GameSystem.turnOnBgm("/sound/music/theme2.wav");
		Menu.mState=Menu.MENUSTATE.DEATH;
		GameSystem.state=GameSystem.STATE.MENU;
	}
	public void goToScore(){
		GameSystem.turnOffBgm();
		GameSystem.turnOnBgm("/sound/music/theme1.wav");	
		Menu.mState=Menu.MENUSTATE.SCORE;
		GameSystem.state=GameSystem.STATE.MENU;
	}
	public void renderPlayerHealth(Graphics g){
		int x = 241;
		int y = 565;
		int width = 645;
		int height = 15;
		//g.drawImage(hpGauge, 250, GameSystem.ABSHEIGHT-75, null);
		g.setColor(Color.GREEN);
		if(player.hp>=0){
			g.fillRect(x, y, (int) (player.hp/player.maxHp*width), height);
		}
		g.setColor(Color.WHITE);
		g.drawRect(x, y, width,height);
	}
	public void renderPlayerMana(Graphics g){
		int x = 241;
		int y = 581;
		int width = 645;
		int height = 10;
		g.setColor(Color.BLUE);
		g.fillRect(x, y, (int) (player.mp/player.maxMp*width), height);
		g.setColor(Color.WHITE);
		g.drawRect(x, y,width,height);
	}
	
	public void checkVictoryCondition(){
		if(enemyCount<=0){
			victory=true;
		}
	}
	
	public void saveGame(){
		try
	      {
			//String path = getClass().getResource("bin/save/game.ser").toString();
			//path = URLDecoder.decode(path);
			//File newFile = new File(path);"C:/Users/Attack on Majou/workspace/Java2DGame/res/save/game.ser"
			 GameData saveData = gameData;
			 saveData.updateGameData(this);
			//game.p.pData.upDatePlayerData(game.p);
	         FileOutputStream fileOut = new FileOutputStream("system/save/game.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         //game.pData.upDatePlayerData(game.p);
	         out.writeObject(saveData);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data is saved in /save/game.ser");
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}
	
	public void renderStageObjects(Graphics g){
		if(curLevel==1){
			stageRenderer.render1(g);
		}
		else if(curLevel==2){
			stageRenderer.render2(g);
		}
		else if(curLevel==3){
			stageRenderer.render3(g);
		}
		else if(curLevel==4){
			stageRenderer.render4(g);
		}
		else if(curLevel==5){
			stageRenderer.render5(g);
		}
		else if(curLevel==6){
			stageRenderer.render6(g);
		}
		else if(curLevel==7){
			stageRenderer.render7(g);
		}
		else if(curLevel==8){
			stageRenderer.render8(g);
		}
		
	}

	//getters and setters

	public GameData getGameData() {
		return gameData;
	}

	public void setGameData(GameData gData) {
		this.gameData = gData;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player p) {
		this.player = p;
	}

	public BufferedImage getBackground() {
		return background;
	}

	public void setBackground(BufferedImage background) {
		this.background = background;
	}

	public boolean isPlaying() {
		return playing;
	}

	public void setPlaying(boolean playing) {
		this.playing = playing;
	}

	public boolean isMusicOn() {
		return musicOn;
	}

	public void setMusicOn(boolean musicOn) {
		this.musicOn = musicOn;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public BufferedImage getHpGauge() {
		return hpGauge;
	}

	public void setHpGauge(BufferedImage hpGauge) {
		this.hpGauge = hpGauge;
	}

	public boolean isPlayerIsAlive() {
		return playerIsAlive;
	}

	public void setPlayerIsAlive(boolean playerIsAlive) {
		this.playerIsAlive = playerIsAlive;
	}

	public LinkedList<Bomb> getBombList() {
		return bombList;
	}

	public void setBombList(LinkedList<Bomb> bombList) {
		this.bombList = bombList;
	}

	public LinkedList<Enemy> getEnemyList() {
		return enemyList;
	}

	public void setEnemyList(LinkedList<Enemy> eList) {
		this.enemyList = eList;
	}

	public LinkedList<HitableBrick> getBrickList() {
		return brickList;
	}

	public void setBrickList(LinkedList<HitableBrick> brickList) {
		this.brickList = brickList;
	}
	
	public LinkedList<PlaceHolder> getPlaceHolderList() {
		return placeHolderList;
	}

	public void setPlaceHolderList(LinkedList<PlaceHolder> list) {
		this.placeHolderList = list;
	}

	public LinkedList<Fire> getFireList() {
		return fireList;
	}

	public void setFireList(LinkedList<Fire> fireList) {
		this.fireList = fireList;
	}

	public LinkedList<PowerUps> getPowerUpList() {
		return powerUpList;
	}

	public void setPowerUpList(LinkedList<PowerUps> powerUpList) {
		this.powerUpList = powerUpList;
	}

	public LinkedList<Projectile> getProjectileList() {
		return projectileList;
	}

	public void setProjectileList(LinkedList<Projectile> projectileList) {
		this.projectileList = projectileList;
	}

	public boolean[][] getWallArray() {
		return wallArray;
	}

	public void setWallArray(boolean[][] wallArray) {
		this.wallArray = wallArray;
	}

	public boolean[][] getBombArray() {
		return bombArray;
	}

	public void setBombArray(boolean[][] bombArray) {
		this.bombArray = bombArray;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller c) {
		this.controller = c;
	}

	public int getCurLevel() {
		return curLevel;
	}

	public void setCurLevel(int curLevel) {
		this.curLevel = curLevel;
	}

	

	public int getLastStage() {
		return lastStage;
	}

	public void setLastStage(int lastStage) {
		this.lastStage = lastStage;
	}

	public int getEnemyCount() {
		return enemyCount;
	}

	public void setEnemyCount(int enemyCount) {
		this.enemyCount = enemyCount;
	}

	public boolean isVictory() {
		return victory;
	}

	public void setVictory(boolean victory) {
		this.victory = victory;
	}

	public int getShift() {
		return shift;
	}

	public void setShift(int shift) {
		this.shift = shift;
	}

	public PlayerData getPlayerData() {
		return playerData;
	}

	public void setPlayerData(PlayerData pData) {
		this.playerData = pData;
	}

	public int getTimePastSinceLastExplode() {
		return timePastSinceLastExplode;
	}

	public void setTimePastSinceLastExplode(int timePastSinceLastExplode) {
		this.timePastSinceLastExplode = timePastSinceLastExplode;
	}

	public static boolean isExplosionPlayed() {
		return explosionPlayed;
	}

	public static void setExplosionPlayed(boolean explosionPlayed) {
		Game.explosionPlayed = explosionPlayed;
	}

	public long getRenderStageStart() {
		return renderStageStart;
	}

	public void setRenderStageStart(long renderStageStart) {
		this.renderStageStart = renderStageStart;
	}

	public boolean isTimeStop() {
		return timeStop;
	}

	public void setTimeStop(boolean timeStop) {
		this.timeStop = timeStop;
	}

	public boolean isStopTick() {
		return stopTick;
	}

	public void setStopTick(boolean stopTick) {
		this.stopTick = stopTick;
	}

	public TimedEvent getEvent1() {
		return event1;
	}

	public void setEvent1(TimedEvent event1) {
		this.event1 = event1;
	}

	public TimedEvent getEvent2() {
		return event2;
	}

	public void setEvent2(TimedEvent event2) {
		this.event2 = event2;
	}

	public GameSystem getSys() {
		return sys;
	}

	public void setSys(GameSystem sys) {
		this.sys = sys;
	}

	public BufferedImage getCutIn() {
		return cutIn;
	}

	public void setCutIn(BufferedImage cutIn) {
		this.cutIn = cutIn;
	}

	public static GameState getgState() {
		return gState;
	}

	public static void setgState(GameState gState) {
		Game.gState = gState;
	}

	public static CHARACTER getcChosen() {
		return cChosen;
	}

	public static void setcChosen(CHARACTER cChosen) {
		Game.cChosen = cChosen;
	}

	
	

	
}
