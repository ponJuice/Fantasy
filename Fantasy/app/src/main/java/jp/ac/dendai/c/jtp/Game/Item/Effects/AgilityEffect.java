package jp.ac.dendai.c.jtp.Game.Item.Effects;

import org.xmlpull.v1.XmlPullParser;

import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleAction;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Player.PlayerData;
import jp.ac.dendai.c.jtp.Game.Item.Item;
import jp.ac.dendai.c.jtp.ParserUtil;

/**
 * Created by Goto on 2016/10/19.
 */

public class AgilityEffect extends ItemEffect {
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

    @Override
    public void calcDamage(BattleAction ba) {

    }

    @Override
    public void effectInit(Item item, BattleAction ba) {

    }

    @Override
    public boolean drawEffect(float offsetX, float offsetY) {
        return false;
    }

    @Override
    public void influence(PlayerData pd) {
        pd.setAgl(pd.getAgl() + value);
    }
}
