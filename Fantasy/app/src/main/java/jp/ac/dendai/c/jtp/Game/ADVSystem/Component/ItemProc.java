package jp.ac.dendai.c.jtp.Game.ADVSystem.Component;

import org.xmlpull.v1.XmlPullParser;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Event.Event;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.AssetManager;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.Parseable;
import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.ParserUtil;

/**
 * Created by wark on 2016/10/24.
 */

public class ItemProc extends ADVComponent implements Parseable {
    public final static String tagName = "ItemProc";
    protected static String attrib_name = "name";
    protected static String attrib_num = "num";
    protected String itemName;
    protected int num;
    @Override
    public void draw(float offset_x, float offset_y) {

    }

    @Override
    public ADVComponent proc(Event event) {
        GameManager.getPlayerData().addItem(GameManager.getDataBase().getItem(itemName),num);
        return next;
    }

    @Override
    public void init(Event event) {

    }

    @Override
    public void parseCreate(AssetManager am, XmlPullParser xpp) {
        itemName = xpp.getAttributeValue(null,attrib_name);
        num = ParserUtil.convertInt(xpp,attrib_num);
    }

    @Override
    public String getTagName() {
        return tagName;
    }
}
