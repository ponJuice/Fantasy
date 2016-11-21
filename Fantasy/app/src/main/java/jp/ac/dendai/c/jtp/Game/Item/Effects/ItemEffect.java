package jp.ac.dendai.c.jtp.Game.Item.Effects;

import android.graphics.Color;

import org.xmlpull.v1.XmlPullParser;

import jp.ac.dendai.c.jtp.Game.BattleSystem.Attackable;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleAction;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Enemy.Enemy;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Player.PlayerData;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.Item.Item;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.NumberText;

/**
 * Created by Goto on 2016/10/19.
 */

public abstract class ItemEffect {
    protected final static float effect_text_height = 0.1f;
    protected static NumberText effectNumber;
    public ItemEffect() {
        if (effectNumber == null) {
            effectNumber = new NumberText(Constant.fontName);
            effectNumber.useAspect(true);
        }
    }

    public void setEffectNumber(int num){
        effectNumber.setNumber(num);
    }

    public void setEffectNumberPos(float x,float y){
        effectNumber.setX(x);
        effectNumber.setY(y);
    }
    public void setEffectNumberHeight(float height){
        effectNumber.setHeight(height);
    }
    public void setEffectNumberColor(int color){
        effectNumber.setR(Color.red(color));
        effectNumber.setG(Color.green(color));
        effectNumber.setB(Color.blue(color));
    }
    public abstract void calcDamage(BattleAction ba);
    public abstract void effectInit(Item item, BattleAction ba);
    public abstract boolean drawEffect(float offsetX,float offsetY);
    public abstract void influence(PlayerData pd);
}
