package jp.ac.dendai.c.jtp.Game.GameUI;

import android.graphics.Bitmap;

import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Image.Image;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by Goto on 2016/10/18.
 */

public class SkillText implements PatchingText{
    protected Image conjunction_no;
    protected Image skillTextImage;
    protected Image ownerTextImage;
    public SkillText(){
        conjunction_no = new Image(GLES20Util.stringToBitmap("の", Constant.fontName,25,255,255,255));
        conjunction_no.useAspect(true);
        conjunction_no.setHorizontal(UIAlign.Align.RIGHT);
        skillTextImage = new Image(conjunction_no.getImage());
        skillTextImage.setHorizontal(UIAlign.Align.LEFT);
        skillTextImage.useAspect(true);
        ownerTextImage = new Image(skillTextImage.getImage());
        ownerTextImage.setHorizontal(UIAlign.Align.RIGHT);
        ownerTextImage.useAspect(true);
    }
    public void setHeight(float height){
        skillTextImage.setHeight(height);
        ownerTextImage.setHeight(height);
        conjunction_no.setHeight(height);
    }
    public void setWidth(float width){
        skillTextImage.setWidth(width);
        ownerTextImage.setWidth(width);
        conjunction_no.setWidth(width);
    }
    public void setOwner(Bitmap bitmap){
        ownerTextImage.setImage(bitmap);
        ownerTextImage.setHeight(conjunction_no.getHeight());
    }
    public void setSkill(Bitmap bitmap){
        skillTextImage.setImage(bitmap);
        skillTextImage.setHeight(conjunction_no.getHeight());
    }
    public void draw(float offsetX,float offsetY){
        float _x = ownerTextImage.getWidth() - (skillTextImage.getWidth() + ownerTextImage.getWidth())/2f;
        ownerTextImage.draw(offsetX+_x-conjunction_no.getWidth(),offsetY);
        conjunction_no.draw(offsetX+_x,offsetY);
        skillTextImage.draw(offsetX+_x,offsetY);
    }
}
