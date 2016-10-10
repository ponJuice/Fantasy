package jp.ac.dendai.c.jtp.Game.UIs.Screen;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.util.Log;

import java.io.IOException;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Event.Event;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.ADVEventParser;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.Game.UIs.Transition.LoadingTransition.LoadingTransition;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.Button;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.ButtonListener;
import jp.ac.dendai.c.jtp.Game.UIs.UI.List.List;
import jp.ac.dendai.c.jtp.TouchUtil.Input;
import jp.ac.dendai.c.jtp.fantasy.R;
import jp.ac.dendai.c.jtp.openglesutil.Util.ImageReader;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;
import jp.ac.dendai.c.jtp.openglesutil.graphic.blending_mode.GLES20COMPOSITIONMODE;

import static jp.ac.dendai.c.jtp.Game.GameManager.args;

/**
 * Created by テツヤ on 2016/10/10.
 */

public class DebugEventSelectScreen implements Screenable{
    protected boolean freeze;
    protected Bitmap image,black,red,green;
    protected Event event;
    protected List list;

    protected float black_x = 0f,black_y = 0f;
    protected float black_lx = 0.4f,black_ly = 0.4f;
    protected float red_x = 0.0f,red_y = 0.0f;
    protected float red_lx = 0.2f,red_ly = 0.2f;
    protected float green_x = 0.4f,green_y = 0.4f;
    protected float green_lx = 0.2f,green_ly = 0.2f;

    public DebugEventSelectScreen(){
        //event = ADVEventParser.createEvent("event_text.event");
        //event.preparation();
        image = GLES20Util.loadBitmap(R.mipmap.town_01);
        black = GLES20Util.createBitmap(0,0,0,255);
        red = GLES20Util.createBitmap(255,0,0,255);
        green = GLES20Util.createBitmap(0,255,0,255);

        list = new List(GLES20Util.getWidth_gl()/2f,0);
        AssetManager assetMgr = GameManager.act.getResources().getAssets();
        try {
            String files[] = assetMgr.list("Event");
            for(int i = 0; i < files.length; i++) {
                Button bt = new Button(0,0,0.5f,0.25f,files[i]);
                bt.setBitmap(Constant.getBitmap(Constant.BITMAP.system_button));
                bt.setButtonListener(new SelectEventButtonListener(files[i]));
                list.addItem(bt);
            }
        } catch (IOException e) {
        }

    }

    @Override
    public void constract(Object[] args) {

    }

    @Override
    public void Proc() {
        if(freeze)
            return;
        //event.proc(null);
        list.proc();
    }

    @Override
    public void Draw(float offsetX, float offsetY) {
        //event.draw();
        GLES20Util.DrawGraph(GLES20Util.getWidth_gl()/2f,GLES20Util.getHeight_gl()/2f,GLES20Util.getWidth_gl(),GLES20Util.getHeight_gl(), image,1, GLES20COMPOSITIONMODE.ALPHA);


        list.draw(offsetX,offsetY);
    }

    @Override
    public void Touch() {
        if(freeze)
            return;
        list.touch(Input.getTouchArray()[0]);
        //Input.getTouchArray()[0].resetDelta();
        //event.touch();
    }

    @Override
    public void death() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void freeze() {
        freeze = true;
    }

    @Override
    public void unFreeze() {
        freeze = false;
    }

    protected class SelectEventButtonListener implements ButtonListener{
        String fileName;
        public SelectEventButtonListener(String file){
            fileName = file;
        }
        @Override
        public void touchDown(Button button) {

        }

        @Override
        public void touchHover(Button button) {

        }

        @Override
        public void touchUp(Button button) {
            LoadingTransition lt = LoadingTransition.getInstance();
            lt.initTransition(TalkScreen.class);
            GameManager.args = new Object[1];
            GameManager.args[0] = fileName;
            GameManager.transition = lt;
            GameManager.isTransition = true;
        }
    }
}
