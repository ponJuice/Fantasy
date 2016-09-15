package jp.ac.dendai.c.jtp.UIs.Screen;

import android.graphics.Bitmap;
import android.view.MotionEvent;

import jp.ac.dendai.c.jtp.Game.Charactor.FaceReader;
import jp.ac.dendai.c.jtp.Game.Charactor.Hero;
import jp.ac.dendai.c.jtp.UIs.UI.Text.CharactorsMap;
import jp.ac.dendai.c.jtp.UIs.UI.Text.DynamicText;
import jp.ac.dendai.c.jtp.UIs.UI.Text.NumberText;
import jp.ac.dendai.c.jtp.fantasy.R;
import jp.ac.dendai.c.jtp.openglesutil.Util.FpsController;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;
import jp.ac.dendai.c.jtp.openglesutil.graphic.blending_mode.GLES20COMPOSITIONMODE;

/**
 * Created by テツヤ on 2016/09/13.
 */
public class TownScreen implements Screenable {
    private Bitmap image,serihu_waku,namae_waku;
    private Hero hero;
    protected String text_string = "親譲の無鉄砲で小供の時から損ばかりしている。";
    protected NumberText nt;
    protected Bitmap text_bitmap,mask;
    protected DynamicText d_text;

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
        nt = new NumberText("custom_font.ttf");
        nt.x = GLES20Util.getWidth_gl()/2f;
        nt.y = GLES20Util.getHeight_gl()/2f;
        nt.textSize = 0.5f;
        text_bitmap = GLES20Util.stringToBitmap(text_string, "custom_font.ttf",100,255,255,255);
        mask = GLES20Util.loadBitmap(R.mipmap.text_mask);

        d_text = new DynamicText();
        d_text.setTextSize(0.068f);
        d_text.setText(CharactorsMap.loadText(text_string));
        d_text.setX(0.5f);
        d_text.setY(0.33f);
    }

    @Override
    public void Proc() {
        nt.setNumber(FpsController.getFps());
    }

    @Override
    public void Draw(float offsetX, float offsetY) {
        GLES20Util.DrawGraph(GLES20Util.getWidth_gl()/2f,0.5f,GLES20Util.getWidth_gl(),1,image,1, GLES20COMPOSITIONMODE.ALPHA);
        float a = (float)serihu_waku.getHeight() / (float)serihu_waku.getWidth() * GLES20Util.getWidth_gl();
        GLES20Util.DrawGraph(GLES20Util.getWidth_gl()/2f,a/2f,GLES20Util.getWidth_gl(),a,serihu_waku,1, GLES20COMPOSITIONMODE.ALPHA);
        GLES20Util.DrawGraph(GLES20Util.getWidth_gl()/2f-0.7f,GLES20Util.getHeight_gl()/2-0.04f,0.4f,0.1046f,namae_waku,1,GLES20COMPOSITIONMODE.ALPHA);

        GLES20Util.DrawGraph(0.28f, a / 2f, a - 0.1f, a - 0.1f, hero.getFace(8), 1f, GLES20COMPOSITIONMODE.ALPHA);
        //GLES20Util.DrawGraph(0.5f,0.33f,0.5f,0.5f,mask,1,GLES20COMPOSITIONMODE.ALPHA);
        //GLES20Util.DrawGraph(0.4f,0.23f,1f,0.4f,text_bitmap,1,GLES20COMPOSITIONMODE.ALPHA);
        float tes_a = (float)text_bitmap.getHeight()/(float)text_bitmap.getWidth();
        float pos_x = (float)text_bitmap.getWidth()/(float)22/(float)text_bitmap.getWidth() * 2f;
        GLES20Util.DrawString(0.5f,0.33f,0.5f,0.1f,0,0,1,1,pos_x,(float)text_bitmap.getHeight()/2f,0,text_bitmap,mask,1,GLES20COMPOSITIONMODE.ALPHA);
        //GLES20Util.DrawGraph(0.5f,0.33f,1f,0.4f,text_bitmap,1,GLES20COMPOSITIONMODE.ALPHA);
        d_text.draw();
        nt.draw();
        //GLES20Util.DrawGraph((float)text_bitmap.getWidth(), GLES20Util.getHeight_gl() / 2f, 0.5f, 0.1f, text_bitmap, 1, GLES20COMPOSITIONMODE.ALPHA);
        //nt.draw(FpsController.getFps(), 3, GLES20Util.getWidth_gl() / 2f - 0.3f, GLES20Util.getHeight_gl() / 2f,1,1,GLES20COMPOSITIONMODE.ALPHA);
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
