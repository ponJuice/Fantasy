package jp.ac.dendai.c.jtp.Game.GameUI;

import android.graphics.Bitmap;

import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Image.Image;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by Goto on 2016/10/18.
 */

public class AttackText implements PatchingText{
    protected Image attackTextImage;
    protected Image ownerTextImage;
    public AttackText(){
        attackTextImage = new Image(GLES20Util.stringToBitmap("の攻撃", Constant.fontName,25,255,255,255));
        attackTextImage.setHorizontal(UIAlign.Align.LEFT);
        attackTextImage.useAspect(true);
        ownerTextImage = new Image(attackTextImage.getImage());
        ownerTextImage.setHorizontal(UIAlign.Align.RIGHT);
        ownerTextImage.useAspect(true);
    }
    public void setHeight(float height){
        attackTextImage.setHeight(height);
        ownerTextImage.setHeight(height);
    }
    public void setWidth(float width){
        attackTextImage.setWidth(width);
        ownerTextImage.setWidth(width);
    }
    public void setOwner(Bitmap bitmap){
        ownerTextImage.setImage(bitmap);
        ownerTextImage.setHeight(attackTextImage.getHeight());
    }
    public void draw(float offsetX,float offsetY){
        float _x = ownerTextImage.getWidth() - (attackTextImage.getWidth() + ownerTextImage.getWidth())/2f;
        attackTextImage.draw(offsetX+_x,offsetY);
        ownerTextImage.draw(offsetX+_x,offsetY);
    }
}
