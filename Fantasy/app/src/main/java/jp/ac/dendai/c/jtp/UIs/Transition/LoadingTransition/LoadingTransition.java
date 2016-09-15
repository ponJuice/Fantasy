package jp.ac.dendai.c.jtp.UIs.Transition.LoadingTransition;

import android.graphics.Bitmap;

import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.Math.Util.Clamp;
import jp.ac.dendai.c.jtp.UIs.Screen.Screenable;
import jp.ac.dendai.c.jtp.UIs.Transition.Transitionable;
import jp.ac.dendai.c.jtp.UIs.UI.Text.Text;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;
import jp.ac.dendai.c.jtp.openglesutil.graphic.blending_mode.GLES20COMPOSITIONMODE;

public class LoadingTransition implements Transitionable {
	private enum LOAD_STATE{
		START,
		LOAD_START,
		LOAD_STAY,
		END
	}
	private LOAD_STATE state = LOAD_STATE.START;
	private static LoadingTransition instance;
	private Class<?> nextScreenClass;
	private Screenable nextScreen;
	private int sleepTime = 0;
	private float alpha = 0;
	private float deltaAlpha =0;
	private int mode = 0;
	private Bitmap bitmap;
	private int transitionTime = 60;
	private float textx = 0.05f,texty = 0.05f;
	private int r = 255,g = 255, b = 255;
	private Text nowLoading;
	private LoadingThread thread;
	private Object lock;
	private int count = 0;
	public static LoadingTransition getInstance(){
		if(instance == null)
			instance = new LoadingTransition();
		return instance;
	}
	private LoadingTransition(){
		nowLoading = new Text("NOW LOADING...",0,0,0);
		nowLoading.setHorizontalTextAlign(Text.TextAlign.RIGHT);
		nowLoading.setVerticalTextAlign(Text.TextAlign.BOTTOM);
		bitmap = GLES20Util.createBitmap(r,g,b,255);
		thread = new LoadingThread(lock);
	}
	public void initTransition(Class<?> nextScreenClass){
		thread.initThread(nextScreenClass);
		count = 0;
	}
	@Override
	public boolean Transition() {
		if(state == LOAD_STATE.START){
			GameManager.nowScreen.freeze();
			//ロードの開始
			//ロード画面のトランジョン
			GLES20Util.DrawGraph(GLES20Util.getWidth_gl()/2f,GLES20Util.getHeight_gl()/2f,
					GLES20Util.getWidth_gl(),GLES20Util.getHeight_gl(),
					bitmap, Clamp.clamp(0,1,60,count),GLES20COMPOSITIONMODE.ALPHA);
			if(count > 60) {
				state = LOAD_STATE.LOAD_START;
				count = 0;
			}
		}else if(state == LOAD_STATE.LOAD_START){
			GLES20Util.DrawGraph(GLES20Util.getWidth_gl()/2f,GLES20Util.getHeight_gl()/2f,
					GLES20Util.getWidth_gl(),GLES20Util.getHeight_gl(),
					bitmap, 1f,GLES20COMPOSITIONMODE.ALPHA);
			//スレッド動作開始
			thread = new LoadingThread(lock);
			thread.start();
			state = LOAD_STATE.LOAD_STAY;
		}else if(state == LOAD_STATE.LOAD_STAY){
			GLES20Util.DrawGraph(GLES20Util.getWidth_gl()/2f,GLES20Util.getHeight_gl()/2f,
					GLES20Util.getWidth_gl(),GLES20Util.getHeight_gl(),
					bitmap, 1f,GLES20COMPOSITIONMODE.ALPHA);
			//ロード中
			if(thread.isEnd()){
				GameManager.nowScreen = thread.getScreen();
				GameManager.nowScreen.unFreeze();
				state = LOAD_STATE.END;
				count = 0;
			}
		}else if(state == LOAD_STATE.END){
			GLES20Util.DrawGraph(GLES20Util.getWidth_gl()/2f,GLES20Util.getHeight_gl()/2f,
					GLES20Util.getWidth_gl(),GLES20Util.getHeight_gl(),
					bitmap, Clamp.clamp(1,0,60,count),GLES20COMPOSITIONMODE.ALPHA);
			if(count >= 60) {
				//終了処理
				count = 0;
				return false;
			}
		}
		count++;
		return true;
	}

	public void setTransitionTime(int transitionTime) {
		this.transitionTime = transitionTime;
	}

}
