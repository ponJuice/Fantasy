package jp.ac.dendai.c.jtp.Game.ADVSystem.Component;

import org.xmlpull.v1.XmlPullParser;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Enum.Operator;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Event.Event;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.AssetManager;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.Parseable;

/**
 * Created by テツヤ on 2016/10/09.
 */

public class Process extends ADVComponent implements Parseable {
    /* -------- Parseable関連 --------------*/
    public final static String tagName = "Proc";
    public final static String attrib_type1     = "type1";
    public final static String attrib_value1    = "value1";
    public final static String attrib_type2     = "type2";
    public final static String attrib_value2    = "value2";
    public final static String attrib_type3     = "type3";
    public final static String attrib_value3    = "value3";
    public final static String attrib_oparator  = "operator";

    /* ------- Process関連 ----------------*/
    protected String type1;
    protected int value1;
    protected String type2;
    protected int value2;
    protected String type3;
    protected int value3;
    protected Operator op;

    @Override
    public void draw() {

    }

    @Override
    public ADVComponent proc(Event event) {
        init(event);
        event.setFlagValue(type3,value3,op.process(event.getFlagValue(type1,value1),event.getFlagValue(type2,value2)));
        return next.proc(event);
    }

    @Override
    public void init(Event event) {

    }

    @Override
    public void parseCreate(AssetManager am, XmlPullParser xpp) {
        //<Proc type1="local" value1="1" operator="+" type2="local" value2="2" type3="local" value3="3"/>
        type1 = xpp.getAttributeValue(null,attrib_type1);
        value1 = Integer.parseInt(xpp.getAttributeValue(null,attrib_value1));

        type2 = xpp.getAttributeValue(null,attrib_type2);
        value2 = Integer.parseInt(xpp.getAttributeValue(null,attrib_value2));

        type3 = xpp.getAttributeValue(null,attrib_type3);
        value3 = Integer.parseInt(xpp.getAttributeValue(null,attrib_value3));

        op.parse(xpp.getAttributeValue(null,attrib_oparator));
    }

    @Override
    public String getTagName() {
        return null;
    }
}
