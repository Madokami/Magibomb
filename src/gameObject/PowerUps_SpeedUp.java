package gameObject;

import game.Game;

/**
* <b>Description:</b>
* <br>
* Defines PowerUp based on coordinate and effect on player
* @author Team 6
* @version 1.0
* @since 2014-03-31
*/
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
