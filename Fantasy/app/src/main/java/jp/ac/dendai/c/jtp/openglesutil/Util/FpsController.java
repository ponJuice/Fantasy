package jp.ac.dendai.c.jtp.openglesutil.Util;

/**
 * FPSの制御だったり、表示だったりする
 * めちゃくちゃ作り掛け
 * @author
 *
 */
public class FpsController {
	/**
	 * 現在FPS
	 */
	private static short fps = 0;
	/**
	 * 固定したいFPS
	 */
	private static short settedFps;
	/**
	 * カウンター
	 */
	private static short fpsCounter = 0;
	/**
	 * FPS計測用
	 */
	private static long time;

	/**
	 * 初期化
	 * @param fps　固定したいFPS値
	 */
	public static void initFpsController(short fps){
		settedFps = fps;
	}

	/**
	 * FPS制御関数
	 */
	public static void updateFps(){
		if(fpsCounter > 0){	//二回目起動以降
			long differenceTime = System.currentTimeMillis()-time;
			long sleepTime = (fpsCounter*1000/settedFps)-differenceTime;
			if(sleepTime>0){
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
				}
			}
			if(differenceTime>=1000){
				fps = fpsCounter;
				fpsCounter = -1;
			}
		}
		else{
			time = System.currentTimeMillis();
		}
	    fpsCounter++;
	}
	/**
	 * FPSのゲッタ
	 * @return　FPS
	 */
	public static short getFps(){
		return fps;
	}

}
