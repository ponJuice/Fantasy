package jp.ac.dendai.c.jtp.Game.Item;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import jp.ac.dendai.c.jtp.Game.BattleSystem.Player.PlayerData;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Skill.Animation;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.DataBase;
import jp.ac.dendai.c.jtp.Game.Item.Effects.HpEffect;
import jp.ac.dendai.c.jtp.Game.Item.Effects.ItemEffect;
import jp.ac.dendai.c.jtp.ParserUtil;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Image.Image;

/**
 * Created by Goto on 2016/09/16.
 */
public class ItemTemplate {
    public final static String tagName = "Item";
    protected final static String animationsTag = "Animations";
    protected final static String children_detail = "Detail";
    protected final static String getChildren_Hp = "HP";
    protected final static String getChildren_Attack = "Attack";
    protected final static String getChildren_Defence = "Defence";
    protected final static String getChildren_Agility = "Agility";
    protected final static String attrib_name = "name";
    protected final static String attrib_price = "price";
    protected final static String attrib_sell = "sell";
    protected final static String attrib_sellable = "sellable";
    protected final static String attrib_useable = "usable";

    protected String name;
    protected Image name_image;
    protected int price;
    protected int sell;
    protected String detail;
    protected boolean sellable = true;
    protected boolean useable = false;
    protected ArrayList<ItemEffect> effects;
    protected ArrayList<Animation> animations;

    public ItemTemplate(){
        effects = new ArrayList<>();
        animations = new ArrayList<>();
    }

    public int getPrice(){
        return price;
    }
    public int getSell(){return sell;}
    public String getName(){
        return name;
    }
    public ArrayList<ItemEffect> getEffects(){
        return effects;
    }

    public Image getNameImage(){
        return name_image;
    }

    public boolean drawEffect(float time,float ox,float oy,float sx,float sy,float deg){
        return true;
    }

    public void influence(PlayerData pd){
        for(int n = 0;n < effects.size();n++){
            effects.get(n).influence(pd);
        }
    }

    public static ItemTemplate parseCreate(XmlPullParser xpp, DataBase db){

        ItemTemplate item = new ItemTemplate();

        item.name = xpp.getAttributeValue(null,attrib_name);
        item.name_image = new Image(GLES20Util.stringToBitmap(item.name,Constant.fontName,25,255,255,255));
        item.name_image.useAspect(true);
        item.price = ParserUtil.convertInt(xpp,attrib_price);
        item.sell = ParserUtil.convertInt(xpp,attrib_sell);
        String bool = xpp.getAttributeValue(null,attrib_sellable);
        if(bool != null)
            item.sellable = Boolean.parseBoolean(bool);
        bool = xpp.getAttributeValue(null,attrib_useable);
        if(bool != null)
            item.useable = Boolean.parseBoolean(bool);

        int eventType = XmlPullParser.END_DOCUMENT;
        try{
            eventType = xpp.getEventType();
        }catch (XmlPullParserException e){
            e.printStackTrace();
        }

        while(eventType != XmlPullParser.END_DOCUMENT){
            if(eventType == XmlPullParser.END_TAG){
                if(xpp.getName().equals(tagName)){
                    //終了
                    break;
                }
            }

            //詳細とアイテムの影響を作成
            if(eventType == XmlPullParser.START_TAG){
                if(xpp.getName().equals(children_detail)) {
                    //詳細タグ
                    item.detail = getDetail(xpp);
                }else if(xpp.getName().equals(getChildren_Hp)){
                    HpEffect he = HpEffect.parseCreate(xpp);
                    item.effects.add(he);
                }else {
                    //詳細タグでない
                    for (int n = 0; n < EffectType.values().length; n++) {
                        if (xpp.getName().equals(EffectType.values()[n].toString())) {
                            item.effects.add(EffectType.values()[n].parseCreate(xpp));
                            break;
                        }
                    }
                }
                if (xpp.getName().equals(animationsTag)) {
                    createAnimations(xpp,item,db);
                }
            }

            try{
                eventType = xpp.next();
            }catch (XmlPullParserException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return item;
    }
    public static String getDetail(XmlPullParser xpp){
        int eventType = XmlPullParser.END_DOCUMENT;
        String detail = "読み込み失敗";
        try{
            eventType = xpp.getEventType();
        }catch(XmlPullParserException e){
            e.printStackTrace();
        }

        while(eventType != XmlPullParser.END_DOCUMENT){
            if(eventType == XmlPullParser.END_TAG){
                if(xpp.getName().equals(ItemTemplate.children_detail)){
                    break;
                }
            }
            if(eventType == XmlPullParser.TEXT){
                detail = xpp.getText();
            }
            try{
                eventType = xpp.next();
            }catch (XmlPullParserException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        return detail;
    }
    public static void createAnimations(XmlPullParser xpp,ItemTemplate item,DataBase db){
        int eventType = XmlPullParser.END_DOCUMENT;
        try{
            eventType = xpp.getEventType();
        }catch (XmlPullParserException e){
            e.printStackTrace();
        }

        while(eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.END_TAG) {
                if (xpp.getName().equals(animationsTag)) {
                    //終了
                    break;
                }
            }
            if(eventType == XmlPullParser.START_TAG) {
                if (xpp.getName().equals(Animation.tagName)) {
                    Animation sa = Animation.parseCreate(xpp, db);
                    item.animations.add(sa);
                }
            }

            try{
                eventType = xpp.next();
            }catch (XmlPullParserException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
