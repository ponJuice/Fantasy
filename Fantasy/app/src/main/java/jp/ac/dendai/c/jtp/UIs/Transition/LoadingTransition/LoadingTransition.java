package jp.ac.dendai.c.jtp.UIs.Transition.LoadingTransition;

import android.graphics.Bitmap;

import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.Math.Util.Clamp;
import jp.ac.dendai.c.jtp.UIs.Screen.Screenable;
import jp.ac.dendai.c.jtp.UIs.Transition.Transitionable;
import jp.ac.dendai.c.jtp.UIs.UI.Text.StaticText;
import jp.ac.dendai.c.jtp.UIs.UI.Text.Text;
import jp.ac.dendai.c.jtp.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;
import jp.ac.dendai.c.jtp.openglesutil.graphic.blending_mode.GLES20COMPOSITIONMODE;

public class LoadingTransition implements Transitionable {
	private enum LOAD_STATE{
		NON,
		START,
		LOAD_START,
		LOAD_STAY,
		END
	}
	private LOAD_STATE state = LOAD_STATE.NON;
	private static LoadingTransition instance;
	private Class<?> nextScreenClass;
	private Bitmap bitmap;
	private StaticText loading;
	private int r = 0,g = 0, b = 0;
	private LoadingThread thread;
	private Object lock;
	private int count = 0;
	public static LoadingTransition getInstance(){
		if(instance == null)
			instance = new LoadingTransition();
		return instance;
	}
	private LoadingTransition(){
		bitmap = GLES20Util.createBitmap(r,g,b,255);
		lock = new Object();
		thread = new LoadingThread(lock);
		loading = new StaticText("Loading...");
		loading.setWidth(0.5f);
		loading.setHolizontal(UIAlign.Align.RIGHT);
		loading.setVertical(UIAlign.Align.BOTTOM);
		loading.setX(GLES20Util.getWidth_gl());
		loading.setY(0);
	}
	public void initTransition(Class<?> nextScreenClass){
		thread.initThread(nextScreenClass);
		count = 0;
		loading.init();
		state = LOAD_STATE.START;
	}
	@Override
	public boolean Transition() {
		loading.proc();
		if(state == LOAD_STATE.START){
			GameManager.nowScreen.freeze();
			GameManager.nowScreen.Draw(0,0);
			//ロードの開始
			//ロード画面のトランジョン
			float a = Clamp.clamp(0f, 1f, 60f, (float) count);
			GLES20Util.DrawGraph(GLES20Util.getWidth_gl()/2f,GLES20Util.getHeight_gl()/2f,
					GLES20Util.getWidth_gl(),GLES20Util.getHeight_gl(),
					bitmap, a ,GLES20COMPOSITIONMODE.ALPHA);
			loading.setAlpha(a);
			loading.draw();
			if(count > 60) {
				state = LOAD_STATE.LOAD_START;
				count = 0;
			}
		}else if(state == LOAD_STATE.LOAD_START){
			GLES20Util.DrawGraph(GLES20Util.getWidth_gl() / 2f, GLES20Util.getHeight_gl() / 2f,
					GLES20Util.getWidth_gl(), GLES20Util.getHeight_gl(),
					bitmap, 1f, GLES20COMPOSITIONMODE.ALPHA);
			loading.setAlpha(1);
			loading.draw();
			thread.start();
			state = LOAD_STATE.LOAD_STAY;
		}else if(state == LOAD_STATE.LOAD_STAY){
			GLES20Util.DrawGraph(GLES20Util.getWidth_gl() / 2f, GLES20Util.getHeight_gl() / 2f,
					GLES20Util.getWidth_gl(), GLES20Util.getHeight_gl(),
					bitmap, 1f, GLES20COMPOSITIONMODE.ALPHA);
			loading.draw();
			if(count > 180) {
				//ロード中
				if (thread.isEnd()) {
					GameManager.nowScreen = thread.getScreen();
					GameManager.nowScreen.freeze();
					state = LOAD_STATE.END;
					count = 0;
				}
			}
		}else if(state == LOAD_STATE.END){
			GameManager.nowScreen.Draw(0, 0);
			float a = Clamp.clamp(1f, 0f, 60f, (float) count);
			GLES20Util.DrawGraph(GLES20Util.getWidth_gl()/2f,GLES20Util.getHeight_gl()/2f,
					GLES20Util.getWidth_gl(),GLES20Util.getHeight_gl(),
					bitmap, Clamp.clamp(1f,0f,60f,(float)count),GLES20COMPOSITIONMODE.ALPHA);
			if(count >= 60) {
				//終了処理
				state = LOAD_STATE.NON;
				GameManager.nowScreen.unFreeze();
				count = 0;
				return false;
			}
		}
		count++;
		return true;
	}

}
