package jp.ac.dendai.c.jtp.fantasy;

import android.app.Activity;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.Game.UIs.Screen.DebugEventSelectScreen;
import jp.ac.dendai.c.jtp.Game.UIs.Screen.StartScreen;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Util.Time;
import jp.ac.dendai.c.jtp.TouchUtil.Input;
import jp.ac.dendai.c.jtp.TouchUtil.Touch;
import jp.ac.dendai.c.jtp.Game.UIs.Screen.TownScreen;
import jp.ac.dendai.c.jtp.openglesutil.Util.FileManager;
import jp.ac.dendai.c.jtp.openglesutil.Util.FpsController;
import jp.ac.dendai.c.jtp.openglesutil.Util.ImageReader;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

public class MainActivity extends Activity implements GLSurfaceView.Renderer{
    protected Bitmap image;
    protected boolean firstStart = true;

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int eventAction = event.getActionMasked();
        int pointerIndex = event.getActionIndex();
        int pointerId = event.getPointerId(pointerIndex);
        int ptrIndex = event.findPointerIndex(pointerId);
        Touch temp;

        switch (eventAction) {
            case MotionEvent.ACTION_DOWN:
                Input.addTouchCount();
                (Input.getTouch()).setTouch(event.getX(ptrIndex), event.getY(ptrIndex), pointerId);
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                Input.addTouchCount();
                if(Input.getTouchCount() <= Input.getMaxTouch()){
                    (Input.getTouch()).setTouch(event.getX(ptrIndex), event.getY(ptrIndex), pointerId);
                }
                break;

            case MotionEvent.ACTION_POINTER_UP:
                Input.subTouchCount();
                if((temp = Input.getTouch(pointerId)) != null){
                    temp.removeTouch();
                }
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                Input.subTouchCount();
                if((temp = Input.getTouch(pointerId)) != null){
                    temp.removeTouch();
                }
                break;

            case MotionEvent.ACTION_MOVE:
                //どれか一つでも移動された場合、全てのタッチ位置を更新する
                for(int n=0;n < Input.getMaxTouch();n++){
                    if((temp = Input.getTouchArray()[n]).getTouchID() != -1){
                        temp.updatePosition(event.getX(event.findPointerIndex(temp.getTouchID())),event.getY(event.findPointerIndex(temp.getTouchID())));
                    }
                }
                break;
        }
        //pointerInfo.setText("pointerID:"+pointerId+" pointerIndex:"+pointerIndex+" ptrIndex:"+ptrIndex);
        //count.setText("count : " + Input.getTouchCount());
        //text1.setText(Input.getTouchArray()[0].toString());
        //text2.setText(Input.getTouchArray()[1].toString());
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // OpenGL ES 2.0が使用できるように初期化する
        GLSurfaceView glSurfaceView = GLES20Util.initGLES20(this, this);

        // GLSurfaceViewをこのアプリケーションの画面として使用する
        setContentView(glSurfaceView);

        //ファイルマネージャを使えるようにする
        FileManager.initFileManager(this);

        //イメージリーダーを使えるようにする
        ImageReader.initImageReader(this);

        //タッチマネージャーを使えるようにする
        Input.setMaxTouch(1);
        Input.setOrientation(getResources().getConfiguration().orientation);

        FpsController.initFpsController((short) GameManager.fps);
        GameManager.init(this);

        Log.d("onCreate", "onCreate finished");
    }

    @Override
    protected void onResume() {
        super.onResume();
        GameManager.resume();
        if(GameManager.nowScreen != null)
            GameManager.nowScreen.resume();
    }

    @Override
    public void onPause(){
        super.onPause();
        GameManager.pause();
        if(GameManager.nowScreen != null) {
            GameManager.nowScreen.pause();
        }
    }

    @Override
    public void onDrawFrame(GL10 arg0) {
        // TODO 自動生成されたメソッド・スタブ
        process();
        draw();
    }

    @Override
    public void onSurfaceChanged(GL10 arg0, int width, int height) {
        // 表示領域を設定する
        GLES20Util.initDrawErea(width, height, false);
        GLES20Util.initTextures();
        image = GLES20Util.createBitmap(255,0,0,255);
        Log.d("onSurfaceCreated", "initShader");
    }

    @Override
    public void onSurfaceCreated(GL10 arg0, EGLConfig arg1) {
        String vertexShader = new String(FileManager.readShaderFile(this, "VSHADER.txt"));
        String fragmentShader = new String(FileManager.readShaderFile(this,"FSHADER.txt"));
        GLES20Util.initGLES20Util(vertexShader, fragmentShader);
    }

    private void process(){
        if(firstStart){
            GameManager.nowScreen = new StartScreen();//TownScreen();
            GameManager.nowScreen.unFreeze();
            firstStart = false;
        }
        Time.tick();
        FpsController.updateFps();
        GameManager.touch();
        GameManager.proc();
    }

    private void draw(){
        // 描画領域をクリアする
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // 画面をクリアする色を設定する
        GameManager.draw();
    }

}
