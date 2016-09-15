package jp.ac.dendai.c.jtp.UIs.UI.Text;

import android.graphics.Bitmap;
import android.widget.AlphabetIndexer;

import jp.ac.dendai.c.jtp.TouchUtil.Touch;
import jp.ac.dendai.c.jtp.UIs.UI.Image.Image;
import jp.ac.dendai.c.jtp.UIs.UI.UI;
import jp.ac.dendai.c.jtp.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.fantasy.R;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by wark on 2016/09/16.
 */
public class StaticText extends Image {
    protected static Bitmap effect_mask;
    protected float delta_u = 0.066666f,delta_v = 0;
    protected float counter = 0;
    public StaticText(String text){
        super(GLES20Util.stringToBitmap(text,"メイリオ",25,255,255,255));
        if(effect_mask == null)
            effect_mask = GLES20Util.loadBitmap(R.mipmap.text_effect_mask);
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
    public void touch(Touch touch) {

    }

    @Override
    public void proc() {
        counter++;
        if(counter*delta_u >= 2)
            counter = -2f / delta_u;
    }

    @Override
    public void draw() {
        GLES20Util.DrawGraph(x + UIAlign.convertAlign(width, holizontal), y + UIAlign.convertAlign(height, vertical), width, height, 0, 0, 1, 1, delta_u * counter, delta_v, 0, image,effect_mask, alpha, mode);
        //GLES20Util.DrawGraph(x + UIAlign.convertAlign(width, holizontal), y + UIAlign.convertAlign(height, vertical), width, height,delta_u*counter,delta_v,1,1,0,image, alpha, mode);
    }
}
