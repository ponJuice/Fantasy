package jp.ac.dendai.c.jtp.Game.UIs.Screen;

import android.graphics.Bitmap;
import android.util.DebugUtils;
import android.util.Log;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Enum.FaceType;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Event.Event;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Event.EventDebuger;
import jp.ac.dendai.c.jtp.Game.Charactor.Face;
import jp.ac.dendai.c.jtp.Game.Charactor.FaceReader;
import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.StreamText.OneSentenceStreamText;
import jp.ac.dendai.c.jtp.Game.UIs.UI.TextBox.TalkBox;
import jp.ac.dendai.c.jtp.TouchUtil.Input;
import jp.ac.dendai.c.jtp.Game.UIs.Transition.LoadingTransition.LoadingTransition;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.Button;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.ButtonListener;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.NumberText;
import jp.ac.dendai.c.jtp.fantasy.R;
import jp.ac.dendai.c.jtp.openglesutil.Util.FpsController;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;
import jp.ac.dendai.c.jtp.openglesutil.graphic.blending_mode.GLES20COMPOSITIONMODE;

/**
 * Created by Goto on 2016/09/14.
 */
public class TalkScreen implements Screenable {
    private Bitmap image,serihu_waku,namae_waku;
    private Face hero;
    protected String text_string = "背中から腋の下へ斜はすに、渋段々染";
    protected NumberText nt;
    protected Bitmap text_bitmap,mask;
    protected OneSentenceStreamText s_text;
    protected boolean freeze;
    protected Button backButton;
    protected TalkBox talkBox;
    protected Event event;

    public TalkScreen(){
        image = GLES20Util.loadBitmap(R.mipmap.town_01);
        serihu_waku = GLES20Util.loadBitmap(R.mipmap.serihu_waku);
        namae_waku = GLES20Util.loadBitmap(R.mipmap.namae_waku);
        FaceReader fr = new FaceReader();
        fr.length_x = 384;
        fr.length_y = 192;
        fr.x_count_max = 4;
        fr.y_count_max = 2;
        fr.id = R.mipmap.hero_2;
        fr.name = "田中太郎";
        hero = fr.createFaces();
        nt = new NumberText("custom_font.ttf");
        nt.x = GLES20Util.getWidth_gl()/2f;
        nt.y = GLES20Util.getHeight_gl()/2f;
        nt.textSize = 0.5f;
        text_bitmap = GLES20Util.stringToBitmap(text_string, "custom_font.ttf",100,255,255,255);
        mask = GLES20Util.loadBitmap(R.mipmap.text_mask);

        s_text = OneSentenceStreamText.createStreamText(text_string,mask,25,20,3,"メイリオ",0,255,255,255);
        //s_text.setHeight(0.25f);
        //s_text.setWidth(1.1f);
        //s_text.setX(0.45f);
        //s_text.setY(0.30f);
        //s_text.setHolizontal(UIAlign.Align.LEFT);
        //s_text.setVertical(UIAlign.Align.TOP);

        backButton = new Button(0,0.5f,0.3f,0.4f,"BACK");
        backButton.setCriteria(Button.CRITERIA.HEIGHT);
        backButton.setPadding(0.01f);
        backButton.setButtonListener(new ButtonListener() {
            @Override
            public void touchDown(Button button) {

            }

            @Override
            public void touchHover(Button button) {

            }

            @Override
            public void touchUp(Button button) {
                LoadingTransition lt = LoadingTransition.getInstance();
                lt.initTransition(TownScreen.class);
                GameManager.transition  = lt;
                GameManager.isTransition = true;
            }
        });

        talkBox = new TalkBox(0,0,GLES20Util.getWidth_gl(),0.3f);
        talkBox.setBackground(serihu_waku);
        talkBox.setFace(hero);
        talkBox.setFaceType(FaceType.normal);
        talkBox.setText(s_text);

        event = EventDebuger.createEvent();
        event.preparation();
    }
    protected int count = 0;
    @Override
    public void Proc() {
        if(freeze)
            return;
        event.proc(null);
        nt.setNumber(FpsController.getFps());
        if(count % 10 == 0) {
            //s_text.nextCharX();
            talkBox.proc();
            if(talkBox.isTextEnd()){
                Log.d("TalkBox","stream end");
            }
        }
        backButton.proc();
        count++;
    }

    @Override
    public void Draw(float offsetX, float offsetY) {
        GLES20Util.DrawGraph(GLES20Util.getWidth_gl()/2f,0.5f,GLES20Util.getWidth_gl(),1,image,1, GLES20COMPOSITIONMODE.ALPHA);
        //talkBox.draw();
        event.draw();
        //float a = (float)serihu_waku.getHeight() / (float)serihu_waku.getWidth() * GLES20Util.getWidth_gl();
        //GLES20Util.DrawGraph(GLES20Util.getWidth_gl()/2f,a/2f,GLES20Util.getWidth_gl(),a,serihu_waku,1, GLES20COMPOSITIONMODE.ALPHA);
        ///GLES20Util.DrawGraph(GLES20Util.getWidth_gl()/2f-0.7f,GLES20Util.getHeight_gl()/2-0.04f,0.4f,0.1046f,namae_waku,1,GLES20COMPOSITIONMODE.ALPHA);

        //GLES20Util.DrawGraph(0.28f, a / 2f, a - 0.1f, a - 0.1f, hero.getFace(FaceType.normal), 1f, GLES20COMPOSITIONMODE.ALPHA);
        //GLES20Util.DrawGraph(0.5f,0.33f,0.5f,0.5f,mask,1,GLES20COMPOSITIONMODE.ALPHA);
        //GLES20Util.DrawGraph(0.4f,0.23f,1f,0.4f,text_bitmap,1,GLES20COMPOSITIONMODE.ALPHA);

        //GLES20Util.DrawGraph(0.5f,0.33f,1f,0.4f,text_bitmap,1,GLES20COMPOSITIONMODE.ALPHA);
        //d_text.draw();
        //s_text.draw();
        backButton.draw();
        //nt.draw();
        //GLES20Util.DrawGraph((float)text_bitmap.getWidth(), GLES20Util.getHeight_gl() / 2f, 0.5f, 0.1f, text_bitmap, 1, GLES20COMPOSITIONMODE.ALPHA);
        //nt.draw(FpsController.getFps(), 3, GLES20Util.getWidth_gl() / 2f - 0.3f, GLES20Util.getHeight_gl() / 2f,1,1,GLES20COMPOSITIONMODE.ALPHA);
        //GLES20Util.DrawGraph(GLES20Util.getWidth_gl()/2f+0.4f,GLES20Util.getHeight_gl()/2f,0.8f,0.8f,hero.getFace(0),1f,GLES20COMPOSITIONMODE.ALPHA);
        //GLES20Util.DrawGraph(GLES20Util.getWidth_gl()/2f+0.4f,GLES20Util.getHeight_gl()/2f,0.25f,0.25f,hero.getFace(2),1f,GLES20COMPOSITIONMODE.ALPHA);
        //GLES20Util.DrawGraph(GLES20Util.getWidth_gl()/2f+0.7f,GLES20Util.getHeight_gl()/2f,0.25f,0.25f,hero.getFace(3),1f,GLES20COMPOSITIONMODE.ALPHA);
    }

    @Override
    public void Touch() {
        if(freeze)
            return;
        for(int n = 0;n < Input.getMaxTouch();n++){
            backButton.touch(Input.getTouchArray()[n]);
        }
        event.touch();
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
