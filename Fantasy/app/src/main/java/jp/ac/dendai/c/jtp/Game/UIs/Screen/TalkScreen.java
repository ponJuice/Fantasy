package jp.ac.dendai.c.jtp.Game.UIs.Screen;

import android.graphics.Bitmap;
import android.util.DebugUtils;
import android.util.Log;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Enum.FaceType;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Event.Event;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Event.EventDebuger;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.ADVEventParser;
import jp.ac.dendai.c.jtp.Game.Charactor.Face;
import jp.ac.dendai.c.jtp.Game.Charactor.FaceReader;
import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.StreamText.OneSentenceStreamText;
import jp.ac.dendai.c.jtp.Game.UIs.UI.TextBox.TalkBox;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UI;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UIAlign;
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
    protected boolean freeze;
    protected Button backButton;
    protected Event event;

    public TalkScreen(){

        backButton = new Button(0,0.5f,0.2f,0.1f,"BACK");
        backButton.setHorizontal(UIAlign.Align.LEFT);
        backButton.setVertical(UIAlign.Align.BOTTOM);
        backButton.setCriteria(UI.Criteria.Height);
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
                lt.initTransition(DebugEventSelectScreen.class);
                GameManager.transition  = lt;
                GameManager.isTransition = true;
            }
        });
    }

    @Override
    public void constract(Object[] args) {
        if(args == null)
            return;
        if(args.length != 1)
            return;
        event = ADVEventParser.createEvent((String)args[0]);
        event.preparation();
    }

    @Override
    public void Proc() {
        if(freeze)
            return;
        event.proc(null);
        backButton.proc();
    }

    @Override
    public void Draw(float offsetX, float offsetY) {
        event.draw(offsetX,offsetY);
        backButton.draw(offsetX,offsetY);
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
