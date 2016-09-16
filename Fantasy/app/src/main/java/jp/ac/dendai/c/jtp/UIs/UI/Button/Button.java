package jp.ac.dendai.c.jtp.UIs.UI.Button;

import android.graphics.Bitmap;
import android.util.Log;

import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.TouchUtil.Touch;


import jp.ac.dendai.c.jtp.UIs.UI.Text.StaticText;
import jp.ac.dendai.c.jtp.UIs.UI.UI;
import jp.ac.dendai.c.jtp.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.UIs.UI.Util.Figure.Rect;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;
import jp.ac.dendai.c.jtp.openglesutil.graphic.blending_mode.GLES20COMPOSITIONMODE;

/**
 * Created by Goto on 2016/09/06.
 */
public class Button implements UI {
    public UIAlign.Align getVertical() {
        return vertical;
    }


    public UIAlign.Align getHolizontal() {
        return holizontal;
    }

    private enum BUTTON_STATE{
        NON,
        DOWN,
        HOVER,
        UP,
    }
    public enum CRITERIA{
        NON,
        WIDTH,
        HEIGHT
    }
    protected CRITERIA criteria = CRITERIA.WIDTH;
    protected BUTTON_STATE state = BUTTON_STATE.NON;
    protected ButtonListener listener;
    protected Touch touch;
    protected StaticText text;
    protected float hover_alpha = 0.5f;
    protected float non_hover_alpha = 1f;
    protected float padding = 0;
    protected Rect rect;
    protected Bitmap tex;
    protected UIAlign.Align holizontal = UIAlign.Align.LEFT,vertical = UIAlign.Align.TOP;
    public Button(float left,float top,float right,float bottom,String text){
        rect = new Rect(left,top,right,bottom);
        if(text != null) {
            this.text = new StaticText(text, Constant.getBitmap(Constant.BITMAP.white));
            this.text.setVertical(UIAlign.Align.CENTOR);
            this.text.setHolizontal(UIAlign.Align.CENTOR);
            updateTextPos();
        }
        tex = Constant.getBitmap(Constant.BITMAP.white);
    }
    public void setButtonListener(ButtonListener listener){
        this.listener = listener;
    }
    public void setTop(float value){
        rect.setTop(value);
        updateTextPos();
    }

    public void setLeft(float value){
        rect.setLeft(value);
        updateTextPos();
    }

    public void setBottom(float value){
        rect.setBottom(value);
        updateTextPos();
    }

    public void setRight(float value){
        rect.setRight(value);
        updateTextPos();
    }
    private void updateTextPos(){
        if(text != null){
            text.setX(rect.getCx());
            text.setY(rect.getCy());
            if(criteria == CRITERIA.WIDTH)
                text.setWidth(rect.getWidth()-padding);
            else if(criteria == CRITERIA.HEIGHT)
                text.setHeight(rect.getHeight() - padding);
        }
    }

    public void setCriteria(CRITERIA c){
        this.criteria = c;
        updateTextPos();
    }

    public void setPadding(float n){
        padding = n;
        updateTextPos();
    }

    public void setBackground(Bitmap tex){
        this.tex = tex;
    }

    public void setX(float x){
        rect.setCx(x);
        if(text != null)
            text.setX(x);
    }

    public void setY(float y){
        rect.setCy(y);
        if(text != null)
            text.setY(y);
    }

    @Override
    public void touch(Touch touch) {
        if(this.touch != null && this.touch != touch)
            return;
        float x = GLES20Util.convertTouchPosToGLPosX(touch.getPosition(Touch.Pos_Flag.X));
        float y = GLES20Util.convertTouchPosToGLPosY(touch.getPosition(Touch.Pos_Flag.Y));
        if(touch.getTouchID() == -1){
            //指が離された
            if(state != BUTTON_STATE.NON && rect.contains(x,y)){
                state = BUTTON_STATE.UP;
            }else{
                this.touch = null;
            }
            return;
        }
        Log.d("button touch pos", "device pos:" + "(" + touch.getPosition(Touch.Pos_Flag.X) + "," + touch.getPosition(Touch.Pos_Flag.Y) + ")" + "camera pos:(" + x + "," + y + ")");
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
            if(listener != null)
                listener.touchUp(this);
            state = BUTTON_STATE.NON;
        }else if(state == BUTTON_STATE.DOWN){
            if(listener != null)
                listener.touchDown(this);
            state = BUTTON_STATE.HOVER;
        }else if(state == BUTTON_STATE.HOVER){
            if(listener != null)
                listener.touchHover(this);
        }
    }

    @Override
    public void draw() {
        if (state == BUTTON_STATE.NON) {
            GLES20Util.DrawGraph(rect.getLeft() + UIAlign.convertAlign(rect.getWidth(), holizontal), rect.getTop() + UIAlign.convertAlign(rect.getHeight(), vertical), rect.getWidth(), rect.getHeight(), tex, non_hover_alpha, GLES20COMPOSITIONMODE.ALPHA);
        } else {
            GLES20Util.DrawGraph(rect.getLeft() + UIAlign.convertAlign(rect.getWidth(), holizontal), rect.getTop() + UIAlign.convertAlign(rect.getHeight(), vertical), rect.getWidth(), rect.getHeight(), tex, hover_alpha, GLES20COMPOSITIONMODE.ALPHA);
        }
        if(text != null)
            text.draw();
    }
}
