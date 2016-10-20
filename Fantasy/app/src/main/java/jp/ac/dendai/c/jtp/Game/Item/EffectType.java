package jp.ac.dendai.c.jtp.Game.Item;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import jp.ac.dendai.c.jtp.Game.Item.Effects.AgilityEffect;
import jp.ac.dendai.c.jtp.Game.Item.Effects.Effect;
import jp.ac.dendai.c.jtp.Game.Item.Effects.HpEffect;
import jp.ac.dendai.c.jtp.ParserUtil;

/**
 * Created by Goto on 2016/10/19.
 */

public enum EffectType {
    HP{
        @Override
        public Effect parseCreate(XmlPullParser xpp){
            return HpEffect.parseCreate(xpp);
        }
    },
    MP{
        @Override
        public Effect parseCreate(XmlPullParser xpp){
            return null;
        }
    },
    Attack{
        @Override
        public Effect parseCreate(XmlPullParser xpp){
            return null;
        }
    },
    Defence{
        @Override
        public Effect parseCreate(XmlPullParser xpp){
            return null;
        }
    },
    Agility{
        @Override
        public Effect parseCreate(XmlPullParser xpp){
            return AgilityEffect.parseCreate(xpp);
        }
    };

    public abstract Effect parseCreate(XmlPullParser xpp);
}
