/**
 * 
 */
package com.digitale.mygdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

/**
 * @author Richard
 *
 */
public class SoundManager {
	/** bg music **/
	static Music music ; 
	private static Sound fanfare ;
	static long enginepitch;
	/**  engine sound **/
	private static Sound engine ;
	/** alarm sound **/
	private static Sound alarm ;
	/** dock request sound **/
	private static Sound dockRequest ;
	/**playerjoined sound also used for playerleft  with double pitch **/
	private static Sound playerJoin ;
	/** dock request accepted sound **/
	private static Sound dockOK ;
	/** undock  sound **/
	private static Sound undock ;
	/** coins  sound **/
	private static Sound coins ;
	/** ui click sound **/
	public static Sound click;
	/** error sound, also used for confirm with double pitch **/
	public static Sound error ;
	/** whoosh ui sound for sliding actions **/
	private static Sound whoosh;
	/** explosion sound **/
	private  static Sound explosion;
	private  static Sound boom;
	/** shot sound **/
	private  static Sound shot;
	private  static Sound missile;
	private  static Sound repair;
	public static void playuiclick() {
		
		
		click.play(BattleBeasties3d.sfxVolume);
		
	}
	public static void playError() {
		error.play(BattleBeasties3d.sfxVolume);
		
	}
	public static void playmusic() {
		
		music.setLooping(true);
		music.setVolume(BattleBeasties3d.musicVolume);
		music.play();
	
	}
	public static void playconfirm() {
	
		long playing=error.play(BattleBeasties3d.sfxVolume);
		error.setPitch(playing, 2f);
	}
	public static void playEngine(float pitch) {
		enginepitch=engine.loop(BattleBeasties3d.sfxVolume/2);
		engine.setPitch(enginepitch, pitch);
		if(BattleBeasties3d.DEEPDEBUG)
			System.out.println(" soundman engine pitch "
					+ pitch + "play id "+enginepitch);
	}
	
	public static void changeEnginePitch(float pitch) {
		
		engine.setPitch(enginepitch, pitch);
	}
	
public static void stopEngine() {
		
		engine.stop();
		enginepitch=0;
	}
	public static void playwhoosh() {
		// TODO Auto-generated method stub
		whoosh.play(BattleBeasties3d.sfxVolume);
	
	}
	public static void setMusicVolume() {
		music.setVolume(BattleBeasties3d.musicVolume);
		
	}
	public static void setSFXVolume() {
		// TODO Auto-generated method stub
		if(enginepitch !=0)engine.setVolume(enginepitch, BattleBeasties3d.sfxVolume);
	}
	public static void dispose() {
		
	}
	public static void playshot() {
		shot.play(BattleBeasties3d.sfxVolume);
		
	}
	public static void playexplosion() {
		explosion.play(BattleBeasties3d.sfxVolume);
		
	}
	public static void playboom() {
		boom.play(BattleBeasties3d.sfxVolume);
		
	}
	public static void playDockRequest() {
		dockRequest.play(BattleBeasties3d.sfxVolume);
	}
	public static void playDockOK() {
		dockOK.play(BattleBeasties3d.sfxVolume);
	}
	public static void playUndock() {
		undock.play(BattleBeasties3d.sfxVolume);
	}
	public static void playAlarm() {
		alarm.play(BattleBeasties3d.sfxVolume);
		
	}
	public static void playCoins() {
		coins.play(BattleBeasties3d.sfxVolume);
	}
	public static void init() {
		
		/**  engine sound **/
		 engine = BattleBeasties3d.manager.get("sound/engine.mp3",Sound.class);
		/** dock request accepted sound **/
		 alarm = BattleBeasties3d.manager.get("sound/alarmloop.ogg",Sound.class);
		/** dock request sound **/
		 dockRequest = BattleBeasties3d.manager.get("sound/dockrequest.mp3",Sound.class);
		/** dock request accepted sound **/
		 dockOK = BattleBeasties3d.manager.get("sound/dockok.mp3",Sound.class);
		/** undock  sound **/
		 undock = BattleBeasties3d.manager.get("sound/undock.mp3",Sound.class);
		/** coins  sound **/
		 coins =BattleBeasties3d.manager.get("sound/coins.mp3",Sound.class);
		/** ui click sound **/
		 click = BattleBeasties3d.manager.get("sound/click.ogg",Sound.class);
		/** error sound, also used for confirm with double pitch **/
		 error = BattleBeasties3d.manager.get("sound/error.mp3",Sound.class);
		/** whoosh ui sound for sliding actions **/
		 whoosh = BattleBeasties3d.manager.get("sound/whoosh.mp3",Sound.class);
		/** explosion sound **/
		  explosion= BattleBeasties3d.manager.get("sound/explosion.mp3",Sound.class);
		  boom= BattleBeasties3d.manager.get("sound/boom.mp3",Sound.class);
		/** shot sound **/
		  shot= BattleBeasties3d.manager.get("sound/laser.mp3",Sound.class);
		  /** playerjoin sound **/
		  playerJoin= BattleBeasties3d.manager.get("sound/playerjoin.mp3",Sound.class);
		  fanfare=BattleBeasties3d.manager.get("sound/schmetterling.mp3",Sound.class);
		  missile= BattleBeasties3d.manager.get("sound/missile.mp3",Sound.class);
		  repair=  BattleBeasties3d.manager.get("sound/repair.mp3",Sound.class);
	}
	public static void playPlayerJoined() {
		
		playerJoin.play(BattleBeasties3d.sfxVolume);
	}
	public static void playFanfare() {
		
		fanfare.play(BattleBeasties3d.sfxVolume);
	}
	public static void playPlayerLeft() {
		long playing=playerJoin.play(BattleBeasties3d.sfxVolume);
		playerJoin.setPitch(playing, .5f);
	}
	public static void playRepair() {
		repair.play(BattleBeasties3d.sfxVolume);
		
	}
	public static void playmissile() {
		missile.play(BattleBeasties3d.sfxVolume);
		
	}
}
