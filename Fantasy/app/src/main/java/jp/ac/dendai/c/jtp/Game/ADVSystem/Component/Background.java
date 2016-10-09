package jp.ac.dendai.c.jtp.Game.ADVSystem.Component;


import org.xmlpull.v1.XmlPullParser;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Event.Event;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.AssetManager;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.Parseable;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Image.Image;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by wark on 2016/10/09.
 */

public class Background extends ADVComponent implements Parseable {
    protected Image background;
    public final static String tagName = "Back";
    protected final static String id = "id";

    @Override
    public void draw() {
        background.draw();
    }

    @Override
    public ADVComponent proc(Event event) {
        init(event);
        event.setBackGround(this);
        if(next == null)
            return next;
        return next.proc(event);
    }

    @Override
    public void init(Event event) {
        if(isInit)
            return;
        event.setBackGround(this);
        event.setDrawTarget(this);
        event.setComponent(next);
        next.init(event);

        isInit = true;
    }

    @Override
    public void parseCreate(AssetManager am, XmlPullParser xpp) {
        String _id = xpp.getAttributeValue(null,id);
        background = new Image(am.getImage(_id));
        background.setHeight(GLES20Util.getHeight_gl());
        background.setWidth(GLES20Util.getWidth_gl());
        background.setX(GLES20Util.getWidth_gl() / 2f);
        background.setY(GLES20Util.getHeight_gl() / 2f);
    }

    @Override
    public String getTagName() {
        return tagName;
    }
}
