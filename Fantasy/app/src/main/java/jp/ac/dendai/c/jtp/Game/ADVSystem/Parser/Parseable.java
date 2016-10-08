package jp.ac.dendai.c.jtp.Game.ADVSystem.Parser;

import org.xmlpull.v1.XmlPullParser;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.ADVComponent;

/**
 * Created by テツヤ on 2016/10/07.
 */

public interface Parseable {
    public void parseCreate(AssetManager am,XmlPullParser xpp);
    public String getTagName();
}
