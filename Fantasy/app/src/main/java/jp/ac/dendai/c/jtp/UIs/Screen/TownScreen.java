package jp.ac.dendai.c.jtp.UIs.Screen;

import android.graphics.Bitmap;
import android.view.MotionEvent;

import javax.microedition.khronos.opengles.GL;

import jp.ac.dendai.c.jtp.Game.Charactor.FaceReader;
import jp.ac.dendai.c.jtp.Game.Charactor.Hero;
import jp.ac.dendai.c.jtp.UIs.UI.Button;
import jp.ac.dendai.c.jtp.fantasy.R;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;
import jp.ac.dendai.c.jtp.openglesutil.graphic.blending_mode.GLES20COMPOSITIONMODE;

/**
 * Created by テツヤ on 2016/09/13.
 */
public class TownScreen implements Screenable {
    private Bitmap image,serihu_waku,namae_waku;
    private Hero hero;
    private Button bt;

    public TownScreen(){
        image = GLES20Util.loadBitmap(R.mipmap.town_01);
        serihu_waku = GLES20Util.loadBitmap(R.mipmap.serihu_waku);
        namae_waku = GLES20Util.loadBitmap(R.mipmap.namae_waku);
        FaceReader fr = new FaceReader();
        fr.length_x = 384;
        fr.length_y = 192;
        fr.x_count_max = 4;
        fr.y_count_max = 2;
        fr.id = R.mipmap.hero_2;
        hero = new Hero(fr);
        bt = new Button(GLES20Util.getWidth_gl()/2f-0.1f,GLES20Util.getHeight_gl()/2f,0.2f,0.1f,1,"TEST",255,0,255,0);
    }

    @Override
    public void Proc() {

    }

    @Override
    public void Draw(float offsetX, float offsetY) {
        GLES20Util.DrawGraph(GLES20Util.getWidth_gl()/2f,0.5f,GLES20Util.getWidth_gl(),1,image,1, GLES20COMPOSITIONMODE.ALPHA);
        float a = (float)serihu_waku.getHeight() / (float)serihu_waku.getWidth() * GLES20Util.getWidth_gl();
        GLES20Util.DrawGraph(GLES20Util.getWidth_gl()/2f,a/2f,GLES20Util.getWidth_gl(),a,serihu_waku,1, GLES20COMPOSITIONMODE.ALPHA);
        GLES20Util.DrawGraph(GLES20Util.getWidth_gl()/2f-0.65f,GLES20Util.getHeight_gl()/2-0.04f,0.4f,0.1046f,namae_waku,1,GLES20COMPOSITIONMODE.ALPHA);

        GLES20Util.DrawGraph(0.3f,a/2f,a-0.1f,a-0.1f,hero.getFace(5),1f,GLES20COMPOSITIONMODE.ALPHA);
        bt.draw(offsetX,offsetY);
        //GLES20Util.DrawGraph(GLES20Util.getWidth_gl()/2f+0.4f,GLES20Util.getHeight_gl()/2f,0.8f,0.8f,hero.getFace(0),1f,GLES20COMPOSITIONMODE.ALPHA);
        //GLES20Util.DrawGraph(GLES20Util.getWidth_gl()/2f+0.4f,GLES20Util.getHeight_gl()/2f,0.25f,0.25f,hero.getFace(2),1f,GLES20COMPOSITIONMODE.ALPHA);
        //GLES20Util.DrawGraph(GLES20Util.getWidth_gl()/2f+0.7f,GLES20Util.getHeight_gl()/2f,0.25f,0.25f,hero.getFace(3),1f,GLES20COMPOSITIONMODE.ALPHA);
    }

    @Override
    public void Touch(MotionEvent event) {

    }

    @Override
    public void death() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void freeze() {

    }

    @Override
    public void unFreeze() {

    }
}
