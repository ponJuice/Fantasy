package jp.ac.dendai.c.jtp.Game.ADVSystem.Component;

import android.media.MediaPlayer;

import org.xmlpull.v1.XmlPullParser;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Event.Event;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.AssetManager;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.Parseable;
import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Image.Image;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by wark on 2016/10/25.
 */

public class BGM extends ADVComponent implements Parseable {
    protected int res_id;
    protected boolean isStopTag;
    public final static String tagName = "BGM";
    protected final static String id = "id";

    @Override
    public void draw(float offset_x,float offset_y) {

    }

    @Override
    public ADVComponent proc(Event event) {
        init(event);
        if(next == null)
            return next;
        return next.proc(event);
    }

    @Override
    public void init(Event event) {
        if(isInit)
            return;
        if(!isStopTag) {
            GameManager.startBGM(res_id,true);
        }else{
            GameManager.stopBGM();
        }
        if(next != null)
            next.init(event);
        isInit = true;
    }

    @Override
    public void parseCreate(AssetManager am, XmlPullParser xpp) {
        isStopTag = false;
        String stop = xpp.getAttributeValue(null,"stop");
        if(stop != null){
            isStopTag = Boolean.parseBoolean(stop);
        }
        if(!isStopTag) {
            String fileName = xpp.getAttributeValue(null, "res_id");
            res_id = GameManager.act.getResources().getIdentifier(fileName, "raw", GameManager.act.getPackageName());
        }
    }

    @Override
    public String getTagName() {
        return tagName;
    }
}
