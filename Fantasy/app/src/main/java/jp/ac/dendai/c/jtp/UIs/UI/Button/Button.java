package jp.ac.dendai.c.jtp.UIs.UI.Button;

import android.graphics.Bitmap;

import jp.ac.dendai.c.jtp.TouchUtil.Touch;


import jp.ac.dendai.c.jtp.UIs.UI.Listener.ButtonListener;
import jp.ac.dendai.c.jtp.UIs.UI.UI;
import jp.ac.dendai.c.jtp.UIs.UI.Util.Rect;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by Goto on 2016/09/06.
 */
public class Button implements UI {
    private enum BUTTON_STATE{
        NON,
        DOWN,
        HOVER,
        UP,
    }
    protected BUTTON_STATE state = BUTTON_STATE.NON;
    protected ButtonListener listener;
    protected Touch touch;
    protected float hover_alpha = 0.5f;
    protected float non_hover_alpha = 1f;
    protected Rect rect;
    protected Bitmap tex;
    public Button(float left,float top,float right,float bottom){
        rect = new Rect(left,top,right,bottom);
    }
    public void setButtonListener(ButtonListener listener){
        this.listener = listener;
    }
    public void setTop(float value){
        rect.setTop(value);
    }

    public void setLeft(float value){
        rect.setLeft(value);
    }

    public void setBottom(float value){
        rect.setBottom(value);
    }

    public void setRight(float value){
        rect.setRight(value);
    }
    public void setBackground(Bitmap tex){
        this.tex = tex;
    }

    @Override
    public void touch(Touch touch) {
        if(this.touch != null && this.touch != touch)
            return;
        float x = .convertTouchPosToGLPosX(touch.getPosition(Touch.Pos_Flag.X));
        float y = camera.convertTouchPosToGLPosY(touch.getPosition(Touch.Pos_Flag.Y));
        if(touch.getTouchID() == -1){
            //指が離された
            if(state != BUTTON_STATE.NON && rect.contains(x,y)){
                state = BUTTON_STATE.UP;
            }else{
                this.touch = null;
            }
            return;
        }
        //Log.d("button touch pos","device pos:"+"("+touch.getPosition(Touch.Pos_Flag.X)+","+ touch.getPosition(Touch.Pos_Flag.Y)+")"+"camera pos:("+x+","+y+")");
        if(touch.getTouchID() != -1 && rect.contains(x,y)){
            if(state == BUTTON_STATE.NON) {
                state = BUTTON_STATE.DOWN;
                this.touch = touch;
            }
            else if(state == BUTTON_STATE.DOWN)
                state = BUTTON_STATE.HOVER;
        }else{
            if(state == BUTTON_STATE.DOWN || state == BUTTON_STATE.HOVER) {
                state = BUTTON_STATE.NON;
                this.touch = null;
            }
            else if(state == BUTTON_STATE.UP) {
                state = BUTTON_STATE.NON;
                this.touch = touch;
            }
        }
    }

    @Override
    public void proc() {
        if(state == BUTTON_STATE.UP){
            listener.touchUp(this);
            state = BUTTON_STATE.NON;
        }else if(state == BUTTON_STATE.DOWN){
            listener.touchDown(this);
            state = BUTTON_STATE.HOVER;
        }else if(state == BUTTON_STATE.HOVER){
            listener.touchHover(this);
        }
    }

    @Override
    public void draw() {
        if(state == BUTTON_STATE.NON)
            GLES20Util.DrawGraph(tex,rect.getLeft(),rect.getBottom(),rect.getRight()-rect.getLeft(),rect.getTop() - rect.getBottom(),0,non_hover_alpha);
        else
            GLES20Util.DrawGraph(tex,rect.getLeft(),rect.getBottom(),rect.getRight()-rect.getLeft(),rect.getTop() - rect.getBottom(),0,hover_alpha);
    }
}
