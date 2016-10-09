package jp.ac.dendai.c.jtp.Game.ADVSystem.Condition;

import org.xmlpull.v1.XmlPullParser;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Enum.Mnemonic;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Event.Event;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.AssetManager;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.Parseable;

/**
 * Created by テツヤ on 2016/10/06.
 */
public class Condition implements ICondition,Parseable{
    public final static String tagName = "Condition";
    public final static String attrib_type1 = "type1";
    public final static String attrib_value1 = "value1";
    public final static String attrib_type2 = "type2";
    public final static String attrib_value2 = "value2";
    public final static String attrib_mnemonic = "mnemonic";

    protected String type1;
    protected String type2;
    protected int value1;
    protected int value2;
    protected Mnemonic mnemonic;
    @Override
    public boolean evaluation(Event event) {
        return mnemonic.evaluation(event.getFlagValue(type1,value1)
                ,event.getFlagValue(type2,value2));
    }

    @Override
    public void parseCreate(AssetManager am, XmlPullParser xpp) {
        //<Condition  type1="local" value1="0" mnemonic="==" type2="const" value2="1"/>
        type1 = xpp.getAttributeValue(null,attrib_type1);
        type2 = xpp.getAttributeValue(null,attrib_type2);
        value1 = Integer.parseInt(xpp.getAttributeValue(null,attrib_value1));
        value2 = Integer.parseInt(xpp.getAttributeValue(null,attrib_value2));
        String m = xpp.getAttributeValue(null,attrib_mnemonic);
        mnemonic = Mnemonic.parse(m);
    }

    @Override
    public String getTagName() {
        return null;
    }
}
