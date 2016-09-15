package jp.ac.dendai.c.jtp.UIs.Screen;

import android.graphics.Bitmap;
import android.view.MotionEvent;

import jp.ac.dendai.c.jtp.Game.Charactor.FaceReader;
import jp.ac.dendai.c.jtp.Game.Charactor.Hero;
import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.TouchUtil.Input;
import jp.ac.dendai.c.jtp.UIs.Transition.LoadingTransition.LoadingTransition;
import jp.ac.dendai.c.jtp.UIs.UI.Button.Button;
import jp.ac.dendai.c.jtp.UIs.UI.Button.ButtonListener;
import jp.ac.dendai.c.jtp.UIs.UI.Image.Image;
import jp.ac.dendai.c.jtp.UIs.UI.Text.CharactorsMap;
import jp.ac.dendai.c.jtp.UIs.UI.Text.DynamicText;
import jp.ac.dendai.c.jtp.UIs.UI.Text.NumberText;
import jp.ac.dendai.c.jtp.UIs.UI.Text.StaticText;
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
    protected Button button;
    protected Image image;
    protected boolean freeze;
    protected StaticText loading;
    public TownScreen(){
        background = GLES20Util.loadBitmap(R.mipmap.town_01);
        button = new Button(0,0.3f,0.3f,0);
        button.setBackground(background);
        button.setButtonListener(new ButtonListener() {
            @Override
            public void touchDown(Button button) {

            }

            @Override
            public void touchHover(Button button) {

            }

            @Override
            public void touchUp(Button button) {
                LoadingTransition lt = LoadingTransition.getInstance();
                lt.initTransition(TalkScreen.class);
                GameManager.transition = lt;
                GameManager.isTransition = true;
            }
        });
        loading = new StaticText("Loading...");
        loading.setWidth(0.5f);
        loading.setHolizontal(UIAlign.Align.RIGHT);
        loading.setVertical(UIAlign.Align.BOTTOM);
        loading.setX(GLES20Util.getWidth_gl());
        loading.setY(0);
        loading.init();
    }
    @Override
    public void Proc() {
        if(freeze)
            return;
        button.proc();
        loading.proc();
    }

    @Override
    public void Draw(float offsetX, float offsetY) {
        GLES20Util.DrawGraph(GLES20Util.getWidth_gl() / 2f, GLES20Util.getHeight_gl() / 2f
                , GLES20Util.getWidth_gl(), GLES20Util.getHeight_gl()
                , background, 1f, GLES20COMPOSITIONMODE.ALPHA);
        button.draw();
        //loading.draw();
    }

    @Override
    public void Touch() {
        if(freeze)
            return;
        for(int n = 0;n < Input.getMaxTouch();n++){
            button.touch(Input.getTouchArray()[n]);
        }
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
        freeze = true;
    }

    @Override
    public void unFreeze() {
        freeze = false;
    }
}
