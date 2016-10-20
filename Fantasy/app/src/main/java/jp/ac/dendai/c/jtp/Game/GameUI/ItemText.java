package jp.ac.dendai.c.jtp.Game.GameUI;

import android.graphics.Bitmap;

import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Image.Image;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by Goto on 2016/10/19.
 */

public class ItemText implements PatchingText {
    protected Image conjunction_ha;
    protected Image endTextImage;
    protected Image itemTextImage;
    protected Image ownerTextImage;
    protected float length;
    public ItemText() {
        conjunction_ha = new Image(GLES20Util.stringToBitmap("は", Constant.fontName,25,255,255,255));
        conjunction_ha.useAspect(true);
        conjunction_ha.setHorizontal(UIAlign.Align.LEFT);
        itemTextImage = new Image(conjunction_ha.getImage());
        itemTextImage.setHorizontal(UIAlign.Align.LEFT);
        itemTextImage.useAspect(true);
        ownerTextImage = new Image(conjunction_ha.getImage());
        ownerTextImage.setHorizontal(UIAlign.Align.RIGHT);
        ownerTextImage.useAspect(true);
        endTextImage = new Image(GLES20Util.stringToBitmap("を使った",Constant.fontName,25,255,255,255));
        endTextImage.useAspect(true);
        endTextImage.setHorizontal(UIAlign.Align.LEFT);

    }

    public void setOwner(Bitmap bitmap){
        ownerTextImage.setImage(bitmap);
        ownerTextImage.setHeight(conjunction_ha.getHeight());
        length = ownerTextImage.getWidth() + conjunction_ha.getWidth() + itemTextImage.getWidth() + endTextImage.getWidth();
    }

    public void setItem(Bitmap bitmap){
        itemTextImage.setImage(bitmap);
        itemTextImage.setHeight(conjunction_ha.getHeight());
        length = ownerTextImage.getWidth() + conjunction_ha.getWidth() + itemTextImage.getWidth() + endTextImage.getWidth();
    }

    @Override
    public void setHeight(float height){
        itemTextImage.setHeight(height);
        ownerTextImage.setHeight(height);
        conjunction_ha.setHeight(height);
        endTextImage.setHeight(height);
        length = ownerTextImage.getWidth() + conjunction_ha.getWidth() + itemTextImage.getWidth() + endTextImage.getWidth();
    }
    @Override
    public void setWidth(float width) {
        itemTextImage.setWidth(width);
        ownerTextImage.setWidth(width);
        conjunction_ha.setWidth(width);
        endTextImage.setWidth(width);
        length = ownerTextImage.getWidth() + conjunction_ha.getWidth() + itemTextImage.getWidth() + endTextImage.getWidth();
    }

    @Override
    public void draw(float offsetX, float offsetY) {
        float _x = ownerTextImage.getWidth() - length/2f;
        ownerTextImage.draw(offsetX + _x,offsetY);
        conjunction_ha.draw(offsetX+_x,offsetY);
        itemTextImage.draw(offsetX+_x+conjunction_ha.getWidth(),offsetY);
        endTextImage.draw(offsetX+_x+conjunction_ha.getWidth()+itemTextImage.getWidth(),offsetY);
    }
}
