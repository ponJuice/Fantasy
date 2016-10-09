package jp.ac.dendai.c.jtp.Game.UIs.Screen;

import android.graphics.Bitmap;

import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.TouchUtil.Input;
import jp.ac.dendai.c.jtp.Game.UIs.Effects.Bitmap.AnimationBitmap;
import jp.ac.dendai.c.jtp.Game.UIs.Effects.Bitmap.Animator;
import jp.ac.dendai.c.jtp.Game.UIs.Transition.LoadingTransition.LoadingTransition;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.Button;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.ButtonListener;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Image.Image;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.StaticText;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.fantasy.R;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;
import jp.ac.dendai.c.jtp.openglesutil.graphic.blending_mode.GLES20COMPOSITIONMODE;

/**
 * Created by テツヤ on 2016/09/13.
 */
public class TownScreen implements Screenable {
    protected Bitmap background;
    protected Button button,itemShop,toDungeon;
    protected Image image;
    protected boolean freeze;
    protected StaticText loading;
    protected Animator anim;
    protected int counter = 1;
    public TownScreen(){
        background = GLES20Util.loadBitmap(R.mipmap.town_01);

        loading = new StaticText("Loading...", Constant.getBitmap(Constant.BITMAP.white));
        loading.setWidth(0.5f);
        loading.setHolizontal(UIAlign.Align.RIGHT);
        loading.setVertical(UIAlign.Align.BOTTOM);
        loading.setX(GLES20Util.getWidth_gl());
        loading.setY(0);
        loading.init();

        itemShop = new Button(0.05f,0.6f,0.8f,0.4f,"武器屋");
        itemShop.setPadding(0.08f);
        itemShop.setCriteria(Button.CRITERIA.HEIGHT);
        itemShop.setBackground(Constant.getBitmap(Constant.BITMAP.system_button));

        anim = new Animator(AnimationBitmap.createAnimation(R.mipmap.effect_attack_01,368,147,5,2,6));

        button = new Button(0,0.3f,0.3f,0,"Hello...");
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

        toDungeon = new Button(0,0.9f,0.8f,0.7f,"ダンジョンへ");
        toDungeon.setPadding(0.08f);
        toDungeon.setCriteria(Button.CRITERIA.HEIGHT);
        toDungeon.setBackground(Constant.getBitmap(Constant.BITMAP.system_button));
        toDungeon.setButtonListener(new ButtonListener() {
            @Override
            public void touchDown(Button button) {

            }

            @Override
            public void touchHover(Button button) {

            }

            @Override
            public void touchUp(Button button) {
                LoadingTransition lt = LoadingTransition.getInstance();
                lt.initTransition(BattleScreen.class);
                GameManager.transition = lt;
                GameManager.isTransition = true;
            }
        });

        image = new Image(anim.getBitmap());
        image.setMode(GLES20COMPOSITIONMODE.ADD);
        image.setHolizontal(UIAlign.Align.LEFT);
        image.setVertical(UIAlign.Align.TOP);
        image.setX(0);
        image.setY(GLES20Util.getHeight_gl());
        image.setHeight(0.3f);
    }
    @Override
    public void Proc() {
        if(freeze)
            return;
        if(counter % 5 == 0) {
            anim.next();
            image.setImage(anim.getBitmap());
        }
        button.proc();
        itemShop.proc();
        loading.proc();
        toDungeon.proc();
        counter++;
    }

    @Override
    public void Draw(float offsetX, float offsetY) {
        GLES20Util.DrawGraph(GLES20Util.getWidth_gl() / 2f, GLES20Util.getHeight_gl() / 2f
                , GLES20Util.getWidth_gl(), GLES20Util.getHeight_gl()
                , background, 1f, GLES20COMPOSITIONMODE.ALPHA);
        button.draw();
        itemShop.draw();
        image.draw();
        toDungeon.draw();
        //loading.draw();
    }

    @Override
    public void Touch() {
        if(freeze)
            return;
        for(int n = 0;n < Input.getMaxTouch();n++){
            button.touch(Input.getTouchArray()[n]);
            itemShop.touch(Input.getTouchArray()[n]);
            toDungeon.touch(Input.getTouchArray()[n]);
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