package jp.ac.dendai.c.jtp.Game.GameUI;

import android.graphics.Color;

import jp.ac.dendai.c.jtp.Game.UIs.UI.Image.Image;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by Goto on 2016/10/13.
 */

public class Gage {
    protected Image back,front;
    protected float valueBuffer;
    protected boolean first = true;
    protected float max,min;
    protected float width,height;
    protected float value;
    public Gage(int backColor,int frontColor,float lengthX,float lengthY,float max,float min,float value){
        this.max = max;
        this.min = min;
        this.value = value;
        width = lengthX;
        height = lengthY;
        back = new Image(GLES20Util.createBitmap(Color.red(backColor)
                ,Color.green(backColor)
                ,Color.blue(backColor)
                ,Color.alpha(backColor)));
        front = new Image(GLES20Util.createBitmap(Color.red(frontColor)
                ,Color.green(frontColor)
                ,Color.blue(frontColor)
                ,Color.alpha(frontColor)));
        back.useAspect(false);
        back.setHorizontal(UIAlign.Align.LEFT);
        back.setVertical(UIAlign.Align.CENTOR);
        back.setWidth(lengthX);
        back.setHeight(lengthY);

        front.useAspect(false);
        front.setHorizontal(UIAlign.Align.LEFT);
        front.setVertical(UIAlign.Align.CENTOR);
        front.setHeight(lengthY);
        float _width = value / (max - min);
        _width = Math.min(Math.max(_width,0),1);    //0～1に制限
        front.setWidth(_width * width);
    }
    public void setX(float x){
        back.setX(x);
        front.setX(x);
    }
    public void setY(float y){
        back.setY(y);
        front.setY(y);
    }
    public void setValue(float value){
        this.value = value;
        float _width = value / (max - min);
        _width = Math.min(Math.max(_width,0),1);    //0～1に制限
        front.setWidth(_width * width);
    }
    public void draw(float offsetX,float offsetY){
        back.draw(offsetX,offsetY);
        front.draw(offsetX,offsetY);
    }
    public float getMax(){
        return max;
    }
    public float getMin(){
        return min;
    }
    public float getValue(){
        return value;
    }
    public boolean animation(float time,float effectTime,float damage){
        if(first) {
            valueBuffer = getValue();
            first = false;
        }
        if(time >= effectTime){
            setValue(valueBuffer - damage);
            first = true;
            return true;
        }
        float norm = 1f / effectTime * time;
        setValue(valueBuffer - damage * norm);
        return false;
    }
}
