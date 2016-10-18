package jp.ac.dendai.c.jtp.Game.UIs.UI.Text;

import android.graphics.Bitmap;

import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.TouchUtil.Touch;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Image.Image;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by wark on 2016/09/16.
 */
public class StaticText extends Image {
    protected Bitmap effect_mask;
    protected float delta_u = 0.066666f,delta_v = 0;
    protected float counter = 0;
    public StaticText(String text,Bitmap effect_mask){
        super(GLES20Util.stringToBitmap(text,"メイリオ",25,255,255,255));
        if(effect_mask == null)
            this.effect_mask = Constant.getBitmap(Constant.BITMAP.white);
        else
            this.effect_mask = effect_mask;
    }
    public StaticText(Bitmap text){
        super(text);
        if(effect_mask == null)
            this.effect_mask = Constant.getBitmap(Constant.BITMAP.white);
        else
            this.effect_mask = effect_mask;
    }
    public void setDelta_u(float u){
        delta_u = u;
    }
    public void setDelta_v(float v){
        delta_v = v;
    }

    public void init(){
        counter = 0;
    }

    @Override
    public boolean touch(Touch touch) {
        return false;
    }

    @Override
    public void proc() {
        counter++;
        if(counter*delta_u >= 2)
            counter = -2f / delta_u;
    }

    @Override
    public void draw(float offset_x,float offset_y) {
        GLES20Util.DrawGraph(x + UIAlign.convertAlign(width, horizontal) + offset_x, y + UIAlign.convertAlign(height, vertical) + offset_y, width, height, 0, 0, 1, 1, delta_u * counter, delta_v, 0, image,effect_mask, alpha, mode);
        //GLES20Util.DrawGraph(x + UIAlign.convertAlign(width, horizontal), y + UIAlign.convertAlign(height, vertical), width, height,delta_u*counter,delta_v,1,1,0,image, alpha, mode);
    }
}
