package jp.ac.dendai.c.jtp.Game.UIs.UI.Image;

import android.graphics.Bitmap;

import jp.ac.dendai.c.jtp.TouchUtil.Touch;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UI;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;
import jp.ac.dendai.c.jtp.openglesutil.graphic.blending_mode.GLES20COMPOSITIONMODE;

/**
 * Created by wark on 2016/09/16.
 */
public class Image implements UI {
    protected Criteria criteria;
    protected Bitmap image;
    protected float aspect;
    protected UIAlign.Align holizontal = UIAlign.Align.CENTOR,vertical = UIAlign.Align.CENTOR;
    protected float width,height;
    protected float alpha = 1;
    protected boolean useAspect = true;
    protected float x = 0,y = 0;
    protected GLES20COMPOSITIONMODE mode = GLES20COMPOSITIONMODE.ALPHA;
    public Image(){}
    public Image(Bitmap bitmap){
        image = bitmap;
        aspect = (float)image.getWidth()/(float)image.getHeight();
    }
    public Image(Image image){
        this.image = image.getImage();
        aspect =image.aspect;

        holizontal = image.holizontal;
        vertical = image.vertical;
        width = image.width;
        height = image.height;
        alpha = image.alpha;
        x = image.x;
        y = image.y;
        mode = image.mode;
    }
    public void setWidth(float width){
        this.width = width;
        if(useAspect)
            this.height = width/aspect;
    }
    public void setHeight(float height){
        this.height = height;
        if(useAspect)
            this.width = height*aspect;
    }
    public void setAlpha(float a){
        alpha = a;
    }

    public void useAspect(boolean flag){
        useAspect = flag;
        if(useAspect){
            if(criteria == Criteria.Height){
                width = height*aspect;
            }else{
                height = width/aspect;
            }
        }
    }
    @Override
    public boolean touch(Touch touch) {
        return false;
    }

    @Override
    public void proc() {

    }

    @Override
    public void draw(float offset_x,float offset_y) {
        if(image != null)
            GLES20Util.DrawGraph(x + UIAlign.convertAlign(width,holizontal)+offset_x,y + UIAlign.convertAlign(height,vertical)+offset_y,width,height,image,alpha, mode);
    }

    public float getAlpha() {
        return alpha;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public GLES20COMPOSITIONMODE getMode() {
        return mode;
    }

    public void setMode(GLES20COMPOSITIONMODE mode) {
        this.mode = mode;
    }

    public UIAlign.Align getVertical() {
        return vertical;
    }

    public void setVertical(UIAlign.Align vertical) {
        this.vertical = vertical;
    }

    public UIAlign.Align getHolizontal() {
        return holizontal;
    }

    public void setHolizontal(UIAlign.Align holizontal) {
        this.holizontal = holizontal;
    }

    public void setImage(Bitmap image) {
        this.image = image;
        aspect = (float)image.getWidth()/(float)image.getHeight();
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Bitmap getImage(){
        return image;
    }
}
