package jp.ac.dendai.c.jtp.Game.MapSystem;

import org.xmlpull.v1.XmlPullParser;

import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.DataBase;
import jp.ac.dendai.c.jtp.Game.Item.ItemTemplate;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.Button;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.NumberText;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UI;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.TouchUtil.Touch;

/**
 * Created by wark on 2016/10/20.
 */

public class ShopItem implements UI{
    protected final static String attrib_name="name";
    protected final static String attrib_price="price";
    protected final static float padding = 0.05f;
    protected ItemTemplate item;
    protected NumberText priceImage;
    protected float price;
    protected Button id;
    protected boolean buy = true;

    public ShopItem(){
        priceImage = new NumberText(Constant.fontName);
    }

    public void setIdButton(Button btn){
        id = btn;
    }

    public Button getIdButton(){
        return id;
    }

    public ShopItem(ItemTemplate it,float price,boolean buy){
        this.buy = buy;
        this.item = it;
        this.price = price;
        priceImage = new NumberText(Constant.fontName);
    }

    public int getPrice(){
        if(buy) {
            return (int) (item.getPrice() * price);
        }else{
            return (int)(item.getSell() * price);
        }
    }

    public static ShopItem parseCreate(XmlPullParser xpp, DataBase db){
        ShopItem shopItem = new ShopItem();
        shopItem.price = Float.parseFloat(xpp.getAttributeValue(null,attrib_price));
        shopItem.item = db.getItem(xpp.getAttributeValue(null,attrib_name));
        shopItem.priceImage = new NumberText(Constant.fontName);
        shopItem.priceImage.setHorizontal(UIAlign.Align.RIGHT);
        shopItem.item.getNameImage().setHorizontal(UIAlign.Align.LEFT);

        return shopItem;
    }

    public String getName(){
        return item.getName();
    }

    public void setHeight(float height){
        priceImage.useAspect(true);
        priceImage.setHorizontal(UIAlign.Align.RIGHT);
        //priceImage.setHeight(height-padding);
        priceImage.setHeight(height);
        priceImage.setNumber(getPrice());

        item.getNameImage().setHorizontal(UIAlign.Align.LEFT);

        item.getNameImage().setHeight(height);
    }

    public ItemTemplate getItemTemplate(){
        return item;
    }

    public void setLeft(float left){
        item.getNameImage().setX(left + padding);
    }

    public void setRight(float right){
        priceImage.setX(right - padding);
    }

    @Override
    public boolean touch(Touch touch) {
        return false;
    }

    @Override
    public void proc() {

    }

    @Override
    public void draw(float offset_x, float offset_y) {
        //float length = item.getNameImage().getWidth() + priceImage.getWidth();
        //float _x = item.getNameImage().getWidth() - length / 2;
        item.getNameImage().draw(0,offset_y);
        priceImage.draw(0,offset_y);
    }
}
