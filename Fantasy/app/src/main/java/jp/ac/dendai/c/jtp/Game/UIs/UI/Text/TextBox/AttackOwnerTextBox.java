package jp.ac.dendai.c.jtp.Game.UIs.UI.Text.TextBox;

import android.graphics.Bitmap;
import android.opengl.GLES20;

import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;
import jp.ac.dendai.c.jtp.openglesutil.graphic.blending_mode.GLES20COMPOSITIONMODE;

/**
 * Created by テツヤ on 2016/10/16.
 */

public class AttackOwnerTextBox{
    protected UIAlign.Align horizontal = UIAlign.Align.CENTOR,vertical = UIAlign.Align.CENTOR;
    protected float x,y;
    protected float back_aspect;
    protected float owner_aspect;
    protected float text_aspect;

    protected float padding = 0;
    protected float width,height;
    protected float owner_width,owner_height;
    protected float text_width,text_height;
    protected Bitmap background;
    protected Bitmap ownerName;
    protected Bitmap attackText;
    public AttackOwnerTextBox(Bitmap back){
        background = back;
        back_aspect = (float)back.getWidth() / (float)back.getHeight();
        height = 1;
        width = back_aspect * height;

        attackText = GLES20Util.stringToBitmap("の攻撃!!", Constant.fontName,25,255,255,255);
        text_aspect = (float)attackText.getWidth() / (float)attackText.getHeight();
        text_height = height;
        text_width = text_height * text_aspect;
    }

    public void setPadding(float p){
        padding = p;
        text_height = height-padding;
        owner_height = height-padding;
        text_width = text_aspect * text_height;
        owner_width = owner_aspect * owner_height;
    }

    public void setX(float x){
        this.x = x;
    }
    public void setY(float y){
        this.y = y;
    }
    public void setWidth(float width){
        this.width = width;
        height = back_aspect / width;
        text_height = height-padding;
        owner_height = height-padding;
        text_width = text_aspect * text_height;
        owner_width = owner_aspect * owner_height;
    }
    public void setHeight(float height){
        this.height = height;
        this.text_height = height-padding;
        this.owner_height = height-padding;
        width = back_aspect * height;
        text_width = text_aspect * text_height;
        owner_width = owner_aspect * owner_height;
    }

    public void setOwner(Bitmap bitmap){
        ownerName = bitmap;
        owner_aspect = (float)ownerName.getWidth() / (float)ownerName.getHeight();
        owner_width = owner_height * owner_aspect;
    }

    public void draw(float offsetX,float offsetY){
        float _x = x + UIAlign.convertAlign(width,horizontal);
        float _y = y + UIAlign.convertAlign(height,vertical);
        GLES20Util.DrawGraph(_x,_y,width,height,background,1,GLES20COMPOSITIONMODE.ALPHA);
        float length = owner_width + text_width;
        float half_length = length / 2f;
        float owner_offset = owner_width - half_length - owner_width/2f;
        float text_offset = -(half_length - owner_width) + text_width/2f;
        GLES20Util.DrawGraph(_x + owner_offset,_y,owner_width,owner_height,ownerName,1,GLES20COMPOSITIONMODE.ALPHA);
        GLES20Util.DrawGraph(_x + text_offset,_y,text_width,text_height,attackText,1,GLES20COMPOSITIONMODE.ALPHA);
    }
}
