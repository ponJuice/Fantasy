package jp.ac.dendai.c.jtp.Game.UIs.UI.TextBox;

import android.graphics.Bitmap;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Enum.FaceType;
import jp.ac.dendai.c.jtp.Game.Charactor.Face;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Image.Image;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.StaticText;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.StreamText.OneSentenceStreamText;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UI;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.TouchUtil.Touch;

/**
 * Created by テツヤ on 2016/10/09.
 */

public class TextBox implements UI {
    protected UIAlign.Align vertical = UIAlign.Align.BOTTOM;
    protected UIAlign.Align horizontal = UIAlign.Align.LEFT;
    protected float x,y;
    protected float width,height;
    protected Face face;
    protected StaticText staticText;
    protected Image background;
    protected boolean faceEnabled,nameEnabled,backgroundEnabled,textEnabled;
    protected float padding = 0.1f;

    public TextBox(float x,float y,float width,float height){
        this.width = width;
        this.height = height;
        background = new Image();
        background.setHolizontal(UIAlign.Align.CENTOR);
        background.setVertical(UIAlign.Align.CENTOR);
        background.setWidth(width);
        setX(x);
        setY(y);
    }

    public void setX(float x){
        this.x = x;// + UIAlign.convertAlign(width,horizontal);
        background.setX(this.x);
        if(staticText != null)
            staticText.setX(this.x);
    }

    public void setY(float y){
        this.y = y;// + UIAlign.convertAlign(width,vertical);
        background.setY(this.y);
        if(staticText != null)
            staticText.setY(this.y);
    }

    public void useAspect(boolean flag){
        background.useAspect(flag);
    }

    public void setHeight(float height){
        background.setHeight(height+padding);
    }

    public void setWidth(float width){
        background.setWidth(width+padding);
    }

    public float getHeight(){
        return background.getHeight();
    }

    public float getWidth(){
        return background.getWidth();
    }

    public void faceEnebled(boolean flag){
        faceEnabled = flag;
    }

    public void nameEnabled(boolean flag){
        nameEnabled = flag;
    }

    public void backgroundEnabled(boolean flag){
        backgroundEnabled = flag;
    }

    public void textEnabled(boolean flag){
        textEnabled = flag;
    }

    public void setFace(Face face){
        faceEnabled = false;
        nameEnabled = false;
        this.face = face;

    }

    public void setFaceType(FaceType faceType){
        faceEnabled = true;
        nameEnabled = true;
    }

    public void setBackground(Bitmap back){
        backgroundEnabled = true;
        background.setImage(back);
        background.setWidth(width);
        //background.setHeight(height);
    }

    public void setText(StaticText text){
        textEnabled = true;
        staticText = text;
        staticText.setHolizontal(UIAlign.Align.CENTOR);
        staticText.setVertical(UIAlign.Align.CENTOR);
        staticText.setX(background.getY());
        staticText.setY(background.getX());
        staticText.setHeight(Constant.message_box_line_height);
    }

    @Override
    public void touch(Touch touch) {

    }

    @Override
    public void proc() {
    }

    @Override
    public void draw() {
        if(backgroundEnabled)
            background.draw();
        if(staticText != null && textEnabled)
            staticText.draw();
    }
}
