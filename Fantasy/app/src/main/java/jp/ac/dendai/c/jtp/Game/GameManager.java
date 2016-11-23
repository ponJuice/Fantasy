package jp.ac.dendai.c.jtp.Game;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.SoundPool;

import java.util.ArrayDeque;

import jp.ac.dendai.c.jtp.Game.BattleSystem.Player.Player;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Player.PlayerData;
import jp.ac.dendai.c.jtp.Game.UIs.Screen.Screenable;
import jp.ac.dendai.c.jtp.Game.UIs.Screen.TalkScreen;
import jp.ac.dendai.c.jtp.Game.UIs.Transition.Transitionable;

public class GameManager {
	public static int fps = 60;
	public static boolean debug = false;
	public static Screenable nowScreen;
	public static Screenable nextScreen;
	public static boolean isTransition = false;
	public static Transitionable transition;
	public static Activity act;
	public static SoundPool sp;
	public static int button;
	public static Object[] args;
	public static ArrayDeque<Screenable> stack = new ArrayDeque<>();
	protected static PlayerData playerData;
	protected static DataBase db;
	protected static MediaPlayer mp;
	protected static boolean play = false;
	public static void init(Activity _act){
		act = _act;
		Constant.init();
		db = new DataBase();
		playerData = new PlayerData(Constant.player_init_hp,Constant.player_init_atk,Constant.player_init_def,25);
	}

	public static DataBase getDataBase(){
		return db;
	}

	public static void draw(){
		if(isTransition && transition != null){
			isTransition = transition.Transition();
		}
		else {
			nowScreen.Draw(0, 0);
		}
	}

	public static void pause(){
		if(mp != null && mp.isPlaying()){
			mp.pause();
			play = true;
		}
	}

	public static void resume(){
		if(mp != null && play){
			mp.start();
			play = false;
		}
	}

	public static void startBGM(int res_id,boolean roop){
		if(mp != null) {
			mp.stop();
			mp.release();
		}
		mp = MediaPlayer.create(act,res_id);
		mp.setLooping(roop);
		mp.start();
		/*mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.start();
			}
		});*/
	}

	public static void stopBGM(){
		if(mp != null)
			mp.stop();
	}

	public static PlayerData getPlayerData(){
		return playerData;
	}

	public static Screenable popStackScreen(){
		return stack.pop();
	}
	public static void proc(){
		if(isTransition && nextScreen != null)
			nextScreen.Proc();
		nowScreen.Proc();
	}
	public static void touch(){
		if(!isTransition){
			nowScreen.Touch();
		}
	}

}
