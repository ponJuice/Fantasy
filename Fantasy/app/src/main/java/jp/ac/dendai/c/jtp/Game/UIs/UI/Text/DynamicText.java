package jp.ac.dendai.c.jtp.Game.UIs.UI.Text;

import android.graphics.Bitmap;

import jp.ac.dendai.c.jtp.TouchUtil.Touch;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UI;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;
import jp.ac.dendai.c.jtp.openglesutil.graphic.blending_mode.GLES20COMPOSITIONMODE;

/**
 * Created by Goto on 2016/09/14.
 */
public class DynamicText implements UI {
    protected Character[] text;
    protected float x = 0,y = 0;
    protected int m = 0,n = 0;
    protected float aspect = 0;
    protected float textSize = 1;
    public void setText(Character[] c){
        text = c;
    }
    public void setTextSize(float n){
        textSize = n;
    }
    public void setX(float x){
        this.x = x;
    }
    public void setY(float y){
        this.y = y;
    }
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
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
        n = 0;
        m = 0;
        aspect = 0;
        for(int index = 0;index < text.length;index++) {
            if(text[index] == '\n') {
                m++;
                n = 0;
                continue;
            }
            Bitmap chara = CharactorsMap.getChara(text[n]);
            aspect = (float)chara.getWidth() / (float)chara.getHeight();
            GLES20Util.DrawGraph(x + n * aspect * textSize+offset_x, y - m * textSize+offset_y, aspect*textSize, textSize, chara, 1, GLES20COMPOSITIONMODE.ALPHA);
            n++;
        }
    }
}
