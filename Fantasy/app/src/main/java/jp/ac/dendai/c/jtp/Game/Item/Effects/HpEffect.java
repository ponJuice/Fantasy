package jp.ac.dendai.c.jtp.Game.Item.Effects;

import android.graphics.Color;

import org.xmlpull.v1.XmlPullParser;

import jp.ac.dendai.c.jtp.Game.BattleSystem.Attackable;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleAction;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Player.PlayerData;
import jp.ac.dendai.c.jtp.Game.Item.Item;
import jp.ac.dendai.c.jtp.ParserUtil;

/**
 * Created by Goto on 2016/10/19.
 */

public class HpEffect extends ItemEffect {
    protected final static String attrib_value = "value";
    protected int value;

    public HpEffect(){
        super();
    }
    @Override
    public void calcDamage(BattleAction ba) {
        ba.damage = -(int)Math.min(ba.owner.getBaseHp() - ba.owner.getHp(),value);
    }

    @Override
    public void effectInit(Item item,BattleAction ba){
        setEffectNumber((int)Math.min(ba.target.getBaseHp() - ba.target.getHp(),value));
        setEffectNumberColor(Color.argb(255,0,255,0));
        setEffectNumberPos(ba.owner.getX(),ba.owner.getY());
        setEffectNumberHeight(ba.owner.getSY()*effect_text_height);
    }



    public void calcAction(BattleAction ba){

    }
    public static HpEffect parseCreate(XmlPullParser xpp){
        HpEffect hpEffect = new HpEffect();
        hpEffect.value = ParserUtil.convertInt(xpp,attrib_value);
        return hpEffect;
    }

    @Override
    public boolean drawEffect(float offsetX,float offsetY) {
        effectNumber.draw(0,0);
        return true;
    }

    @Override
    public void influence(PlayerData pd) {
        pd.setHp(Math.min(pd.getHp() + value,pd.getBaseHp()));
    }
}
