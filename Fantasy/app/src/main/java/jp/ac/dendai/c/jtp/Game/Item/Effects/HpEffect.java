package jp.ac.dendai.c.jtp.Game.Item.Effects;

import org.xmlpull.v1.XmlPullParser;

import jp.ac.dendai.c.jtp.ParserUtil;

/**
 * Created by Goto on 2016/10/19.
 */

public class HpEffect extends Effect {
    protected final static String attrib_value = "value";
    protected int value;
    public static HpEffect parseCreate(XmlPullParser xpp){
        HpEffect hpEffect = new HpEffect();
        hpEffect.value = ParserUtil.convertInt(xpp,attrib_value);
        return hpEffect;
    }
}
