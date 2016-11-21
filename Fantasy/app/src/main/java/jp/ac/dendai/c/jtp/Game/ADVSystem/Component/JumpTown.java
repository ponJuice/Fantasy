package jp.ac.dendai.c.jtp.Game.ADVSystem.Component;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Event.Event;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.AssetManager;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.Parseable;
import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.Game.UIs.Screen.BattleScreen.BattleScreen;
import jp.ac.dendai.c.jtp.Game.UIs.Screen.MapScreen;
import jp.ac.dendai.c.jtp.Game.UIs.Screen.TownScreen;
import jp.ac.dendai.c.jtp.Game.UIs.Transition.LoadingTransition.LoadingTransition;

/**
 * Created by Goto on 2016/10/24.
 */

public class JumpTown extends ADVComponent implements Parseable {
    public final static String tagName = "JumpTown";
    protected final static String attrib_target_name = "name";
    protected String townName;

    @Override
    public void draw(float offset_x, float offset_y) {

    }

    @Override
    public ADVComponent proc(Event event) {
        GameManager.getPlayerData().setTown(GameManager.getDataBase().getTown(townName));
        LoadingTransition lt = LoadingTransition.getInstance();
        lt.initTransition(TownScreen.class);
        GameManager.stack.clear();
        GameManager.args = new Object[1];
        GameManager.args[0] = GameManager.getDataBase().getTown(townName);
        GameManager.transition = lt;
        GameManager.isTransition = true;
        if(next != null)
            next.proc(event);
        return next;
    }

    @Override
    public void init(Event event) {

    }

    @Override
    public void parseCreate(AssetManager am, XmlPullParser xpp) {
        townName = xpp.getAttributeValue(null,attrib_target_name);
    }

    @Override
    public String getTagName() {
        return null;
    }
}
