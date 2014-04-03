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
public class PowerUps_DamageUp extends PowerUps{

	public PowerUps_DamageUp(int x, int y, Game game) {
		super(x, y, game);
		ss = SpriteData.upgrades;
		image = ss.grabImage(9,19,ssWidth,ssHeight);
	}

	@Override
	public void applyEffect(Player player) {
		player.bombStrength+=5;
	}
	
}
