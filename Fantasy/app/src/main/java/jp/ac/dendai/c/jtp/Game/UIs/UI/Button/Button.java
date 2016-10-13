package jp.ac.dendai.c.jtp.Game.UIs.UI.Button;

import android.graphics.Bitmap;

import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.TouchUtil.Touch;


import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.StaticText;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UI;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Util.Figure.Rect;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;
import jp.ac.dendai.c.jtp.openglesutil.graphic.blending_mode.GLES20COMPOSITIONMODE;

/**
 * Created by Goto on 2016/09/06.
 */
public class Button implements UI{
    private enum BUTTON_STATE{
        NON,
        DOWN,
        HOVER,
        UP,
    }
    protected UI.Criteria criteria = UI.Criteria.NON;
    protected UI.Criteria backimage_criteria = Criteria.NON;
    protected BUTTON_STATE state = BUTTON_STATE.NON;
    protected ButtonListener listener;
    protected Touch touch;
    protected StaticText text;
    protected float hover_alpha = 0.5f;
    protected float non_hover_alpha = 1f;
    protected float padding = 0;
    protected Rect rect;
    protected boolean useAspect = true;
    protected float x,y;
    protected float width,height;
    protected float aspect;
    protected boolean through = false;
    protected Bitmap bitmap;
    protected UIAlign.Align horizontal = UIAlign.Align.CENTOR,vertical = UIAlign.Align.CENTOR;

    public Button(float cx,float cy,float width,float height,String string){
        useAspect(false);
        rect = new Rect(cx-width/2f,cy+height/2f,cx+width/2f,cy-height/2f);
        this.x = cx;
        this.y = cy;
        this.width = rect.getWidth();
        this.height = rect.getHeight();
        aspect = width / height;
        setX(cx);
        setY(cy);
        setBitmap(Constant.getBitmap(Constant.BITMAP.white));
        if(string != null) {
            this.text = new StaticText(string,Constant.getBitmap(Constant.BITMAP.white));
            this.text.setVertical(UIAlign.Align.CENTOR);
            this.text.setHorizontal(UIAlign.Align.CENTOR);
            updateTextPos();
        }
        updateBackimage();
    }
    public void setButtonListener(ButtonListener listener){
        this.listener = listener;
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public void setBackImageCriteria(Criteria c){
        backimage_criteria = c;
        updateBackimage();
        updateTextPos();
    }

    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
        aspect = calcAspect(bitmap);

        updateTextPos();
        updateBackimage();
    }

    private void updateTextPos(){
        if(text != null){
            text.setX(rect.getCx());
            text.setY(rect.getCy());
            if(criteria == Criteria.Width)
                text.setWidth(rect.getWidth() - padding);
            else if(criteria == Criteria.Height)
                text.setHeight(rect.getHeight() - padding);
        }
    }

    private void updateBackimage(){
        if(!useAspect)
            return;
        if(backimage_criteria == Criteria.Height){
            rect.setWidth(rect.getHeight() * aspect);
        }else{
            rect.setHeight(rect.getWidth() / aspect);
        }
    }

    public void useAspect(boolean flag){
        useAspect = flag;
        if(useAspect) {
            aspect = calcAspect(getBitmap());
            updateBackimage();
        }
        updateTextPos();
    }

    public void setCriteria(Criteria c){
        this.criteria = c;
        if(criteria == Criteria.NON) {
            text.useAspect(false);
        }else{
            text.useAspect(true);
        }
        updateTextPos();
    }

    public void setPadding(float n){
        padding = n;
        updateTextPos();
    }

    public void setHorizontal(UIAlign.Align align){
        horizontal = align;
        setX(getX());
    }

    public void setVertical(UIAlign.Align align){
        vertical = align;
        setY(getY());
    }

    public void setX(float x){
        this.x = x;
        x = x + UIAlign.convertAlign(rect.getWidth(),horizontal);
        rect.setCx(x);
        //this.x = rect.getCx();
        if(text != null)
            text.setX(x);
    }

    public float getX(){return x;}
    public float getY(){return y;}

    public void setY(float y){
        this.y = y;
        y = y + UIAlign.convertAlign(rect.getHeight(),vertical);
        rect.setCy(y);
        //this.y = rect.getCy();
        if(text != null)
            text.setY(y);
    }

    public void setWidth(float width){
        //rect.setCx((width - rect.getWidth())/2f);
        rect.setWidth(width);
        if(useAspect){
            //rect.setCy((width/aspect - rect.getHeight()/2f));
            rect.setHeight(width/aspect);
        }
        if(text != null) {
            text.setX(rect.getCx());
            text.setY(rect.getCy());
            if(criteria == Criteria.Width){
                text.setWidth(width - padding);
            }else if(criteria == Criteria.Height){
                text.setHeight(rect.getHeight()-padding);
            }
        }
        setX(x);
        setY(y);
    }

    public void setHeight(float height){
        //rect.setCy((height - rect.getHeight())/2f);
        rect.setHeight(height);
        if(useAspect){
            //rect.setCy((height*aspect - rect.getWidth()/2f));
            rect.setWidth(height*aspect);
        }
        if(text != null) {
            text.setY(rect.getCy());
            text.setX(rect.getCx());
            if(criteria == Criteria.Height){
                text.setHeight(height - padding);
            }else if(criteria == Criteria.Width){
                text.setWidth(rect.getWidth() - padding);
            }
        }
        setX(x);
        setY(y);
    }

    public void touchReset(){
        this.touch = null;
        state = BUTTON_STATE.NON;
    }

    public boolean touch(Touch touch) {
        if(this.touch != null && this.touch != touch)
            return true;
        float x = GLES20Util.convertTouchPosToGLPosX(touch.getPosition(Touch.Pos_Flag.X));
        float y = GLES20Util.convertTouchPosToGLPosY(touch.getPosition(Touch.Pos_Flag.Y));
        if(state == BUTTON_STATE.UP){
            state = BUTTON_STATE.NON;
            return true;
        }
        if(touch.getTouchID() == -1){
            //指が離された
            if(state != BUTTON_STATE.NON && rect.contains(x,y)){
                state = BUTTON_STATE.UP;
            }else{
                state = BUTTON_STATE.NON;
                this.touch = null;
            }
            return true;
        }
        //Log.d("button touch pos", "device pos:" + "(" + touch.getPosition(Touch.Pos_Flag.X) + "," + touch.getPosition(Touch.Pos_Flag.Y) + ")" + "camera pos:(" + x + "," + y + ") delta:"+touch.getDelta(Touch.Pos_Flag.X)+","+touch.getDelta(Touch.Pos_Flag.Y)+")");
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
        if(state == BUTTON_STATE.NON)
            return true;
        else if(touch.getTouchID() == -1)
            return true;
        else
            return through;
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
    public void draw(float offset_x,float offset_y) {
        if (state == BUTTON_STATE.NON) {
            GLES20Util.DrawGraph(rect.getCx()+offset_x, rect.getCy()+offset_y, rect.getWidth(), rect.getHeight(), bitmap, non_hover_alpha, GLES20COMPOSITIONMODE.ALPHA);
        } else {
            GLES20Util.DrawGraph(rect.getCx()+offset_y, rect.getCy()+offset_y, rect.getWidth(), rect.getHeight(), bitmap, hover_alpha, GLES20COMPOSITIONMODE.ALPHA);
        }
        if(text != null)
            text.draw(offset_x,offset_y);
    }

    protected static float calcAspect(Bitmap image){
        return (float)image.getWidth()/(float)image.getHeight();
    }
}
