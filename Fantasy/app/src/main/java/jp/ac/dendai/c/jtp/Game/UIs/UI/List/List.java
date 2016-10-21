package jp.ac.dendai.c.jtp.Game.UIs.UI.List;

import android.opengl.GLES20;
import android.util.Log;

import java.util.ArrayList;

import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.Button;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UI;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Util.Time;
import jp.ac.dendai.c.jtp.TouchUtil.Touch;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;
import jp.ac.dendai.c.jtp.openglesutil.graphic.blending_mode.GLES20COMPOSITIONMODE;

/**
 * Created by テツヤ on 2016/10/10.
 */

public class List implements UI {
    protected ArrayList<Button> listItem;
    protected UIAlign.Align horizontal = UIAlign.Align.LEFT;
    protected UIAlign.Align vertical = UIAlign.Align.BOTTOM;
    protected float width,height;
    protected float content_width = 0.5f,content_height = 0.2f;
    protected float text_padding = 0.08f;
    protected float item_padding = 0.01f;
    protected float offset_y = 0;
    protected float inertia = 0,decline = 0.01f,decline_buffer = 0,inertia_sensitivity = 0.001f;
    protected float x,y;
    protected boolean scrollable = true;
    protected boolean touchable = true;
    protected boolean drawable = true;
    protected boolean isTouch = false;

    protected float sensitivity = 0.001f;
    public List(float x,float y,float width,float height){
        listItem = new ArrayList<>();
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
    }
    public void addItem(Button bt){
        itemInit(bt,listItem.size(),0,0);
        listItem.add(bt);
    }

    public float getTextPadding(){
        return text_padding;
    }

    public float getContentHeight(){
        return content_height;
    }

    public float getContentLeft(){
        return x;
    }

    public float getContentRight(){
        return x+content_width;
    }

    public float getContentWidth(){
        return content_width;
    }
    public ArrayList<Button> getList(){
        return listItem;
    }

    public void setDrawable(boolean flag){
        drawable  = flag;
    }

    public void setTouchable(boolean flag){
        touchable = flag;
    }

    public void setX(float x){
        this.x = x + UIAlign.convertAlign(width,horizontal) - width/2f;
        initItem();
    }
    public void setY(float y){
        this.y = y + UIAlign.convertAlign(height,vertical) + height / 2f;
        initItem();
    }
    public void setWidth(float width){
        this.width = width;
        initItem();
    }
    public void setHeight(float height){
        this.height = height;
        initItem();
    }
    public void setHorizontal(UIAlign.Align horizontal){
        this.horizontal = horizontal;
        setX(x);
    }
    public void setVertical(UIAlign.Align vertical){
        this.vertical = vertical;
        setY(y);
    }
    public void setItemPadding(float padding){
        item_padding = padding;
    }
    public void setContentWidth(float width){
        content_width = width;
    }
    public void setContentHeight(float height){
        content_height = height;
    }
    public void setTextPaddint(float padding){
        text_padding = padding;
        initItem();
    }

    protected float calcPosY(int num){
        return height - (num*content_height + item_padding * num);
    }

    protected void itemInit(Button bt,int num,float offset_x,float offset_y){
        bt.useAspect(false);
        bt.setPadding(text_padding);
        bt.setWidth(content_width);
        bt.setHeight(content_height);
        bt.setHorizontal(UIAlign.Align.LEFT);
        bt.setVertical(UIAlign.Align.TOP);
        bt.setY(calcPosY(num)+offset_y+y);
        bt.setX(x+offset_x);
    }

    protected void initItem(){
        for(int n = 0;n < listItem.size();n++){
            itemInit(listItem.get(n),n,0,0);
        }
    }

    public void removeItem(Button bt){
        listItem.remove(bt);
        for(int n = 0;n < listItem.size();n++){
            itemInit(listItem.get(n),n,0,offset_y);
        }
    }

    public void setScrollable(boolean flag){
        scrollable = flag;
    }
    @Override
    public boolean touch(Touch touch) {
        if(!touchable)
            return false;
        float _x = GLES20Util.convertTouchPosToGLPosX(touch.getPosition(Touch.Pos_Flag.X));
        float _y = GLES20Util.convertTouchPosToGLPosY(touch.getPosition(Touch.Pos_Flag.Y));

        if (_x < x || _x >= (x + width) || _y < y || _y >= (y + height)) {
            for (int n = 0; n < listItem.size(); n++) {
                listItem.get(n).touchReset();
            }
            return false;
        }

        if(scrollable) {
            if (inertia != 0) {
                decline_buffer += decline * Time.getDeltaTime();
                Log.d("List", "Inertia : " + inertia + " decline_buffer : " + decline_buffer);
                if (inertia > 0) {
                    inertia -= decline_buffer;
                    if (inertia <= 0)
                        inertia = 0;
                } else {
                    //inertia < 0
                    inertia += decline_buffer;
                    if (inertia >= 0)
                        inertia = 0;
                }
                offset_y += inertia;
            }

            if (touch == null)
                return false;
            float _delta_x = touch.getDelta(Touch.Pos_Flag.Y) * sensitivity;
            if (touch.getTouchID() != -1 && Math.abs(_delta_x) >= 0.001f) {
                offset_y += _delta_x;
                for (int n = 0; n < listItem.size(); n++) {
                    listItem.get(n).touchReset();
                }
                isTouch = true;
                return false;
            }

            if (isTouch && touch.getTouchID() == -1) {
                //慣性を求める
                inertia = touch.getDelta(Touch.Pos_Flag.Y) * inertia_sensitivity;
                decline_buffer = 0;
                isTouch = false;
            }
        }
        boolean flag = true;
        for(int n = 0;n < listItem.size();n++){
            if(!isTouch)
                flag = listItem.get(n).touch(touch);
        }

        return false;
    }

    public void init(){
        isTouch = false;
        touchable = true;
        drawable = true;
    }

    @Override
    public void proc() {
        clampItemPos();
        for(int n = 0;n < listItem.size();n++) {
            if(isTouch || inertia != 0) {
                itemInit(listItem.get(n), n, 0, offset_y);
            }
            listItem.get(n).proc();
        }
    }

    public void clampItemPos(){
        float length_y = (float)(listItem.size()) * content_height + (float)(listItem.size()) * item_padding;

        if((height -(length_y - offset_y)) <= 0){
            offset_y = length_y - height;
        }else if(offset_y >= 0){
            offset_y =0;// y - (float)(listItem.size() -1)*content_height - (float)(listItem.size()-1)*item_padding;
        }
    }

    @Override
    public void draw(float offset_x,float offset_y) {
        if(!drawable)
            return;
        if(!GLES20.glIsEnabled(GLES20.GL_STENCIL_TEST)){
            GLES20.glEnable(GLES20.GL_STENCIL_TEST);
        }
        GLES20.glStencilMask(~0);
        GLES20.glClear(GLES20.GL_STENCIL_BUFFER_BIT);

        GLES20.glClearStencil(0);

        GLES20.glColorMask(false,false,false,false);
        GLES20.glStencilOp(GLES20.GL_KEEP,GLES20.GL_REPLACE,GLES20.GL_REPLACE);
        GLES20.glStencilFunc(GLES20.GL_ALWAYS,1,~0);
        GLES20Util.DrawGraph(x + width/2f+offset_x,y + height/2f+offset_y,width,height, Constant.getBitmap(Constant.BITMAP.black), 1f, GLES20COMPOSITIONMODE.ALPHA);

        GLES20.glColorMask(true, true, true, true);
        GLES20.glStencilOp(GLES20.GL_KEEP, GLES20.GL_KEEP, GLES20.GL_KEEP);
        GLES20.glStencilFunc(GLES20.GL_EQUAL,1, ~0);
        for(int n = 0;n < listItem.size();n++){
            if(listItem.get(n).getY() <= y + height && listItem.get(n).getY() >= y)
                listItem.get(n).draw(offset_x,offset_y);
        }

        GLES20.glDisable(GLES20.GL_STENCIL_TEST);
    }
}
