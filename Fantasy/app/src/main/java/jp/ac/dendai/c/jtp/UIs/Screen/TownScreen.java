package jp.ac.dendai.c.jtp.UIs.Screen;

import android.graphics.Bitmap;
import android.view.MotionEvent;

import jp.ac.dendai.c.jtp.Game.Charactor.FaceReader;
import jp.ac.dendai.c.jtp.Game.Charactor.Hero;
import jp.ac.dendai.c.jtp.UIs.UI.Text.CharactorsMap;
import jp.ac.dendai.c.jtp.UIs.UI.Text.DynamicText;
import jp.ac.dendai.c.jtp.UIs.UI.Text.NumberText;
import jp.ac.dendai.c.jtp.UIs.UI.Text.StreamText;
import jp.ac.dendai.c.jtp.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.fantasy.R;
import jp.ac.dendai.c.jtp.openglesutil.Util.FpsController;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;
import jp.ac.dendai.c.jtp.openglesutil.graphic.blending_mode.GLES20COMPOSITIONMODE;

/**
 * Created by テツヤ on 2016/09/13.
 */
public class TownScreen implements Screenable {
    protected Bitmap background;
    public TownScreen(){
        background = GLES20Util.loadBitmap(R.mipmap.town_01);

    }
    @Override
    public void Proc() {

    }

    @Override
    public void Draw(float offsetX, float offsetY) {
        GLES20Util.DrawGraph(GLES20Util.getWidth_gl()/2f,GLES20Util.getHeight_gl()/2f
                ,GLES20Util.getWidth_gl(),GLES20Util.getHeight_gl()
                ,background,1f,GLES20COMPOSITIONMODE.ALPHA);
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
