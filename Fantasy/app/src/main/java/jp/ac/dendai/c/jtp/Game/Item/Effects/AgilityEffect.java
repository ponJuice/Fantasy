package jp.ac.dendai.c.jtp.Game.Item.Effects;

import org.xmlpull.v1.XmlPullParser;

import jp.ac.dendai.c.jtp.ParserUtil;

/**
 * Created by Goto on 2016/10/19.
 */

public class AgilityEffect extends Effect {
    protected final static String attrib_value = "value";
    protected final static String attribg_time = "time";
    protected int value;
    protected int time;

    public static AgilityEffect parseCreate(XmlPullParser xpp){
        AgilityEffect ae = new AgilityEffect();
        ae.value = ParserUtil.convertInt(xpp,attrib_value);
        ae.time = ParserUtil.convertInt(xpp,attribg_time);

        return ae;
    }
}
