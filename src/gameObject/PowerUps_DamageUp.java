package gameObject;

import game.Game;

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
