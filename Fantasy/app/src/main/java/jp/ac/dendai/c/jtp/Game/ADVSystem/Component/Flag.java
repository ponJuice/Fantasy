package jp.ac.dendai.c.jtp.Game.ADVSystem.Component;

import org.xmlpull.v1.XmlPullParser;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Event.Event;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Flag.FlagManager;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.AssetManager;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.Parseable;

/**
 * Created by テツヤ on 2016/10/09.
 */

public class Flag extends ADVComponent implements Parseable {
    public final static String tagName = "Flag";
    public final static String s_type1 = "type1";
    public final static String s_mode1 = "mode1";
    public final static String s_value1 = "value1";
    public final static String s_type2 = "type2";
    public final static String s_mode2 = "mode2";
    public final static String s_value2 = "value2";

    protected FlagManager.FlagType type1;
    protected FlagManager.FlagType mode1;
    protected int value1;
    protected FlagManager.FlagType type2;
    protected FlagManager.FlagType mode2;
    protected int value2;

    @Override
    public void draw(float offset_x,float offset_y) {

    }

    @Override
    public ADVComponent proc(Event event) {
        init(event);
        event.setFlagValue(type1,event.getFlagValue(mode1,value1),event.getFlagValue(type2,event.getFlagValue(mode2,value2)));
        event.outputDebugInfo();
        if(next == null)
            return next;
        return next.proc(event);
    }

    public void proc(){
        FlagManager.setFlagValue(type1,FlagManager.getFlagValue(mode1,value1),FlagManager.getFlagValue(type2,FlagManager.getFlagValue(mode2,value2)));
        FlagManager.outputDebugInfo();
    }

    @Override
    public void init(Event event) {

    }

    @Override
    public void parseCreate(AssetManager am, XmlPullParser xpp) {
        //<Flag type1="local" mode1="const" value1="1" type2="global" mode2="local" value2="1"/>
        type1 = FlagManager.FlagType.parse(xpp.getAttributeValue(null,s_type1));
        mode1 = FlagManager.FlagType.parse(xpp.getAttributeValue(null,s_mode1));
        type2 = FlagManager.FlagType.parse(xpp.getAttributeValue(null,s_type2));
        mode2 = FlagManager.FlagType.parse(xpp.getAttributeValue(null,s_mode2));

        String _value = xpp.getAttributeValue(null,s_value1);
        value1 = Integer.parseInt(_value);

        _value = xpp.getAttributeValue(null,s_value2);
        value2 = Integer.parseInt(_value);
    }

    @Override
    public String getTagName() {
        return tagName;
    }
}
