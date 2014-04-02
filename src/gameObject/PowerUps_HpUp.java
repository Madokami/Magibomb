package gameObject;

import game.Game;

public class PowerUps_HpUp extends PowerUps{

	public PowerUps_HpUp(int x, int y, Game game) {
		super(x, y, game);
		ss = SpriteData.upgrades;
		image = ss.grabImage(10,14,ssWidth,ssHeight);
	}

	@Override
	public void applyEffect(Player player) {
		player.hp+=20;
	}

}
