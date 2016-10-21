package jp.ac.dendai.c.jtp.Game.ADVSystem.Component;

import android.graphics.Bitmap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Event.Event;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.AssetManager;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.Parseable;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.Game.UIs.Screen.BattleScreen.BattleScreen;
import jp.ac.dendai.c.jtp.Game.UIs.Screen.TalkScreen;
import jp.ac.dendai.c.jtp.Game.UIs.Transition.LoadingTransition.LoadingTransition;
import jp.ac.dendai.c.jtp.openglesutil.Util.ImageReader;

/**
 * Created by Goto on 2016/10/11.
 */

public class JumpBattle extends ADVComponent implements Parseable {
    public final static String tagName = "Battle";
    protected final static String attrib_back_name = "back_file";
    protected final static String child_enemy = "Enemy";
    protected final static String child_enemy_attrib_id = "id";
    protected ArrayList<String> enemyIds;
    protected String background_name;

    @Override
    public void draw(float offset_x, float offset_y) {

    }

    @Override
    public ADVComponent proc(Event event) {
        GameManager.stack.push(GameManager.nowScreen);
        GameManager.args = new Object[enemyIds.size() + 2];
        GameManager.args[0] = background_name;
        GameManager.args[1] = enemyIds.size();
        for(int n = 2;n < GameManager.args.length;n++){
            GameManager.args[n] = enemyIds.get(n - 2);
        }
        LoadingTransition lt = LoadingTransition.getInstance();
        lt.initTransition(BattleScreen.class);
        GameManager.transition = lt;
        GameManager.isTransition = true;
        return next;
    }

    @Override
    public void init(Event event) {

    }

    @Override
    public void parseCreate(AssetManager am, XmlPullParser xpp) {
        enemyIds = new ArrayList<>();
        background_name = xpp.getAttributeValue(null,attrib_back_name);

        int eventType = XmlPullParser.END_DOCUMENT;
        try{
            eventType = xpp.getEventType();
        }catch (XmlPullParserException e){
            e.printStackTrace();
        }

        while(!(eventType == XmlPullParser.END_TAG && xpp.getName().equals(tagName))){
            if(eventType == XmlPullParser.START_TAG){
                if(xpp.getName().equals(child_enemy)){
                    enemyIds.add(xpp.getAttributeValue(null,child_enemy_attrib_id));
                }
            }

            try{
                eventType = xpp.next();
            }catch (XmlPullParserException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public String getTagName() {
        return null;
    }
}
