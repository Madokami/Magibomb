package gameObject;

import java.util.Random;

import system.GameSystem;

/**
* <b>Description:</b>
* <br>
* Responsible for playing different sounds
* <br>ex. LevelUpSound, DeathSound
* @author Team 6
* @version 1.0
* @since 2014-03-31
*/
public class KyVoice implements PlayerVoice{
	Random rand;
	public KyVoice(){
		rand=new Random();
	}
	public void playDeathSound(){
		int x = rand.nextInt(3);
		String url = "/sound/kyDeath";
		url=url.concat(Integer.toString(x)).concat(".wav");
		GameSystem.musicPlayer.playVoice(url);
	}
	public void playUltimateSound(){
		int x = rand.nextInt(3);
		String url = "/sound/hoUlt";
		url=url.concat(Integer.toString(x)).concat(".wav");
		System.out.println(url);
		GameSystem.musicPlayer.playVoice(url);
	}
	public void playLevelUpSound(){
		GameSystem.musicPlayer.playVoice("/sound/kyLevel.wav");
	}
	public void playItemFoundSound(){
		GameSystem.musicPlayer.playVoice("/sound/kyItem.wav");
	}
	public void playDamagedMediumSound(){
		GameSystem.musicPlayer.playVoice("/sound/kyDamage0.wav");
	}
	public void playDamagedHeavySound(){
		GameSystem.musicPlayer.playVoice("/sound/kyDamage1.wav");
	}
	public void playSoulGemDarkSound(){
		GameSystem.musicPlayer.playVoice("/sound/kySoul1.wav");
	}
	public void playCdSound() {
		GameSystem.playVoice("/sound/kyCd.wav");
	}
}
