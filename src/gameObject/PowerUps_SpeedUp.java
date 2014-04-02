package gameObject;

import game.Game;

public class PowerUps_SpeedUp extends PowerUps{
	public PowerUps_SpeedUp(int x, int y, Game game) {
		super(x, y, game);
		ss = SpriteData.upgrades;
		image = ss.grabImage(11,19,ssWidth,ssHeight);
		type = "speed";
	}
	@Override
	public void applyEffect(Player player) {
		player.increaseSpeed(1);
		
	}

}
