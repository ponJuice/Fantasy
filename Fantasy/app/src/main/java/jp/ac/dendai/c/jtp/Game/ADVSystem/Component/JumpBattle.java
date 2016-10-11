package jp.ac.dendai.c.jtp.Game.ADVSystem.Component;

import org.xmlpull.v1.XmlPullParser;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Event.Event;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.AssetManager;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.Parseable;
import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.Game.UIs.Screen.TalkScreen;
import jp.ac.dendai.c.jtp.Game.UIs.Transition.LoadingTransition.LoadingTransition;

/**
 * Created by Goto on 2016/10/11.
 */

public class JumpBattle extends ADVComponent implements Parseable {
    public final static String tagName = "Battle";

    @Override
    public void draw(float offset_x, float offset_y) {

    }

    @Override
    public ADVComponent proc(Event event) {
        GameManager.stack.push(GameManager.nowScreen);
        GameManager.args = new Object[1];
        GameManager.args[0] = "event_text.event";
        LoadingTransition lt = LoadingTransition.getInstance();
        lt.initTransition(TalkScreen.class);
        GameManager.transition = lt;
        GameManager.isTransition = true;
        return next;
    }

    @Override
    public void init(Event event) {

    }

    @Override
    public void parseCreate(AssetManager am, XmlPullParser xpp) {

    }

    @Override
    public String getTagName() {
        return null;
    }
}
