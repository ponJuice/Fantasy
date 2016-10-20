package jp.ac.dendai.c.jtp.Game.Item.Effects;

import org.xmlpull.v1.XmlPullParser;

import jp.ac.dendai.c.jtp.ParserUtil;

/**
 * Created by Goto on 2016/10/19.
 */

public class AttackEffect extends Effect {
    protected final static String attrib_value = "value";
    protected final static String attrib_time = "time";
    protected int value;
    protected int time;

    public static AttackEffect parseCreate(XmlPullParser xpp){
        AttackEffect ae = new AttackEffect();
        ae.value = ParserUtil.convertInt(xpp,attrib_value);
        ae.time = ParserUtil.convertInt(xpp,attrib_time);

        return ae;
    }
}
