package jp.ac.dendai.c.jtp.Game.UIs.Screen;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Event.Event;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Flag.FlagManager;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.ADVEventParser;
import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.Game.UIs.Transition.StackLoadingTransition.StackLoadingTransition;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UI;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.TouchUtil.Input;
import jp.ac.dendai.c.jtp.Game.UIs.Transition.LoadingTransition.LoadingTransition;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.Button;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.ButtonListener;

/**
 * Created by Goto on 2016/09/14.
 */
public class TalkScreen implements Screenable {
    protected static FlagManager.ScreenType screenType = FlagManager.ScreenType.talk;
    protected boolean freeze;
    //protected Button backButton;
    protected Event event;

    public TalkScreen(){

        /*backButton = new Button(0,0.5f,0.2f,0.1f,"BACK");
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
        });*/
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
        if(!event.proc()){
            //シーン終了
            StackLoadingTransition slt = StackLoadingTransition.getInstance();
            slt.initStackLoadingTransition();
            GameManager.transition = slt;
            GameManager.isTransition = true;
        }
        //backButton.proc();
    }

    @Override
    public void Draw(float offsetX, float offsetY) {
        event.draw(offsetX,offsetY);
        //backButton.draw(offsetX,offsetY);
    }

    @Override
    public void init() {
        FlagManager.setFlagValue(FlagManager.FlagType.global,1,screenType.getInt());
    }

    @Override
    public void Touch() {
        if(freeze)
            return;
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
