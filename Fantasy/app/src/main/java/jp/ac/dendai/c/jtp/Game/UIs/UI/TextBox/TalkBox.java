package jp.ac.dendai.c.jtp.Game.UIs.UI.TextBox;

import android.graphics.Bitmap;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Enum.FaceType;
import jp.ac.dendai.c.jtp.Game.Charactor.Face;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Image.Image;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.StreamText.OneSentenceStreamText;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.StreamText.StreamText;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UI;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.TouchUtil.Touch;

/**
 * Created by Goto on 2016/10/07.
 */

public class TalkBox implements UI{
    protected UIAlign.Align vertical = UIAlign.Align.BOTTOM;
    protected UIAlign.Align horizontal = UIAlign.Align.LEFT;
    protected float x,y;
    protected float width,height;
    protected Face face;
    protected Image faceImage;
    protected Image nameImage;
    protected Image background;
    protected OneSentenceStreamText streamText;
    protected boolean faceEnabled,nameEnabled,backgroundEnabled,textEnabled;
    protected float faceOffsetX = 0.12f , faceOffsetY = 0.07f;
    protected float faceWidth   = 0.3f  , faceHeight = 0.3f;
    protected float nameOffsetX = 0.44f  , nameOffsetY = 0.28f;
    protected float nameHeight = 0.1f;
    protected float textOffsetX = 0.44f  , textOffsetY = 0.07f;

    public TalkBox(float x,float y,float width,float height){
        this.width = width;
        this.height = height;
        faceImage = new Image();
        faceImage.setHolizontal(UIAlign.Align.LEFT);
        faceImage.setVertical(UIAlign.Align.BOTTOM);
        faceImage.setWidth(faceWidth);
        faceImage.setHeight(faceHeight);
        nameImage = new Image();
        nameImage.setHolizontal(UIAlign.Align.LEFT);
        nameImage.setVertical(UIAlign.Align.BOTTOM);
        background = new Image();
        background.setHolizontal(UIAlign.Align.LEFT);
        background.setVertical(UIAlign.Align.BOTTOM);
        background.setWidth(width);
        setX(x);
        setY(x);
    }

    public void setX(float x){
        this.x = x;// + UIAlign.convertAlign(width,horizontal);
        background.setX(this.x);
        faceImage.setX(this.x + faceOffsetX);
        nameImage.setX(this.x + nameOffsetX);
        if(streamText != null)
            streamText.setX(this.x + textOffsetX);
    }

    public void setY(float y){
        this.y = y;// + UIAlign.convertAlign(width,vertical);
        background.setY(this.y);
        faceImage.setY(this.y + faceOffsetY);
        nameImage.setY(this.y + nameOffsetY);
        if(streamText != null)
            streamText.setY(this.y + textOffsetY);
    }

    public boolean isTextEnd(){
        return streamText.isEnd();
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
        nameImage.setImage(face.getNameImage());
        nameImage.setHeight(nameHeight);

    }

    public void setFaceType(FaceType faceType){
        faceEnabled = true;
        nameEnabled = true;
        faceImage.setImage(face.getFace(faceType));
        faceImage.setHeight(faceHeight);
        faceImage.setWidth(faceWidth);
    }

    public void setBackground(Bitmap back){
        backgroundEnabled = true;
        background.setImage(back);
        background.setWidth(width);
        //background.setHeight(height);
    }

    public void setText(OneSentenceStreamText text){
        textEnabled = true;
        streamText = text;
        streamText.setX(x + textOffsetX);
        streamText.setY(y + textOffsetY);
        streamText.setHeight(Constant.talk_text_height);
        streamText.setWidth(Constant.talk_text_width);
        streamText.setHolizontal(UIAlign.Align.LEFT);
        streamText.setVertical(UIAlign.Align.BOTTOM);
    }

    @Override
    public boolean touch(Touch touch) {
        return false;
    }

    @Override
    public void proc() {
        streamText.nextCharX();
    }

    @Override
    public void draw(float offset_x,float offset_y) {
        if(backgroundEnabled)
            background.draw(offset_x,offset_y);
        if(faceEnabled)
            faceImage.draw(offset_x,offset_y);
        if(nameEnabled)
            nameImage.draw(offset_x,offset_y);
        if(streamText != null && textEnabled)
            streamText.draw(offset_x,offset_y);
    }
}
