package jp.ac.dendai.c.jtp.Game.MapSystem;

import org.xmlpull.v1.XmlPullParser;

import jp.ac.dendai.c.jtp.Game.DataBase;
import jp.ac.dendai.c.jtp.Game.Item.ItemTemplate;

/**
 * Created by wark on 2016/10/20.
 */

public class ShopItem {
    protected final static String attrib_name="name";
    protected ItemTemplate item;
    protected float price;

    public int getPrice(){
        return (int)price;
    }

    public static ShopItem parseCreate(XmlPullParser xpp, DataBase db){
        ShopItem shopItem = new ShopItem();
        shopItem.item = db.getItem(xpp.getAttributeValue(null,attrib_name));
        return shopItem;
    }
}
