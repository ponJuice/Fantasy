package jp.ac.dendai.c.jtp.Game.BattleSystem.Enemy;

import org.xmlpull.v1.XmlPullParser;

import jp.ac.dendai.c.jtp.Game.DataBase;
import jp.ac.dendai.c.jtp.Game.Item.Item;
import jp.ac.dendai.c.jtp.Game.Item.ItemTemplate;
import jp.ac.dendai.c.jtp.ParserUtil;

/**
 * Created by wark on 2016/10/23.
 */

public class EnemyItem {
    public final static String tagName = "Item";
    protected static String attrib_name = "name";
    protected static String attrib_pro = "probability";
    public ItemTemplate item;
    public int probability;

    public static EnemyItem parseCreate(XmlPullParser xpp, DataBase db){
        EnemyItem ei = new EnemyItem();
        String name = xpp.getAttributeValue(null,attrib_name);
        ei.item = db.getItem(name);
        ei.probability = ParserUtil.convertInt(xpp,attrib_pro);
        return ei;
    }
}
