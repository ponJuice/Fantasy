package jp.ac.dendai.c.jtp.Game.Item;

import org.xmlpull.v1.XmlPullParser;

import jp.ac.dendai.c.jtp.Game.Item.Effects.AgilityEffect;
import jp.ac.dendai.c.jtp.Game.Item.Effects.ItemEffect;
import jp.ac.dendai.c.jtp.Game.Item.Effects.HpEffect;

/**
 * Created by Goto on 2016/10/19.
 */

public enum EffectType {
    HP{
        @Override
        public ItemEffect parseCreate(XmlPullParser xpp){
            return HpEffect.parseCreate(xpp);
        }
    },
    MP{
        @Override
        public ItemEffect parseCreate(XmlPullParser xpp){
            return null;
        }
    },
    Attack{
        @Override
        public ItemEffect parseCreate(XmlPullParser xpp){
            return null;
        }
    },
    Defence{
        @Override
        public ItemEffect parseCreate(XmlPullParser xpp){
            return null;
        }
    },
    Agility{
        @Override
        public ItemEffect parseCreate(XmlPullParser xpp){
            return AgilityEffect.parseCreate(xpp);
        }
    };

    public abstract ItemEffect parseCreate(XmlPullParser xpp);
}
