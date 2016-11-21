package jp.ac.dendai.c.jtp.Game.MapSystem;

import android.graphics.Bitmap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Condition.Condition;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.DataBase;
import jp.ac.dendai.c.jtp.Game.Item.ItemTemplate;
import jp.ac.dendai.c.jtp.Game.UIs.Screen.MapScreen;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.Button;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.ButtonListener;
import jp.ac.dendai.c.jtp.ParserUtil;
import jp.ac.dendai.c.jtp.TouchUtil.Touch;
import jp.ac.dendai.c.jtp.openglesutil.Util.ImageReader;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;
import jp.ac.dendai.c.jtp.openglesutil.graphic.blending_mode.GLES20COMPOSITIONMODE;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Image.Image;

/**
 * Created by wark on 2016/10/19.
 */

public class Town {
    public final static String tagName="Town";
    protected final static String child_tag_shop = "Shop";
    protected final static String child_tag_hotel = "Hotel";
    protected final static String child_shop_item = "Item";
    protected final static String attrib_image = "image";
    protected final static String attrib_hotel = "price";
    protected final static String attrib_name = "name";
    protected final static String attrib_dungeon = "dungeon";
    protected final static String attrib_id = "id";
    protected final static String attrib_x = "x";
    protected final static String attrib_y = "y";
    protected final static float icon_width = 0.05f;
    protected final static float icon_height = 0.08f;
    protected float icon_alpha = 1f;
    protected String name;
    protected Bitmap nameImage;
    protected Image background;
    protected Button btnIcon;
    protected int hotelPrice;
    protected ArrayList<ShopItem> shopItem;
    protected ArrayList<Node> nodes;
    protected boolean isDungeon;
    protected int id;

    public Town(){
        btnIcon = new Button(0,0,1,1);
        shopItem = new ArrayList<>();
        nodes = new ArrayList<>();
    }

    public void setButtonListener(ButtonListener bl){
        btnIcon.setButtonListener(bl);
    }

    public Node searchNode(Town next){
        for(int n = 0;n < nodes.size();n++){
            if((nodes.get(n).town1 == this && nodes.get(n).town2 == next)
                || (nodes.get(n).town1 == next && nodes.get(n).town2 == this)){
                return nodes.get(n);
            }
        }
        return null;
    }

    public boolean isDungeon(){
        return isDungeon;
    }

    public int getHotelPrice(){
        return hotelPrice;
    }

    public ArrayList<ShopItem> getShopItem(){
        return shopItem;
    }

    public Image getBackground(){
        return background;
    }

    public void addNode(Node node){
        nodes.add(node);
    }

    public float getX(){
        return btnIcon.getX();
    }

    public float getY(){
        return btnIcon.getY();
    }

    public Bitmap getNameImage(){
        return nameImage;
    }

    public String getName(){
        return name;
    }

    public boolean touch(Touch touch){
        return btnIcon.touch(touch);
    }

    public void proc(){
        btnIcon.proc();
    }

    public void drawIcon(float offsetX,float offsetY){
        btnIcon.draw(offsetX,offsetY);
        //GLES20Util.DrawGraph(x,y,icon_width,icon_height,b,icon_alpha, GLES20COMPOSITIONMODE.ALPHA);
    }

    public int getId(){
        return id;
    }

    public void debugSet(float x,float y){
        btnIcon.setX(x);
        btnIcon.setY(y);
    }

    public static Town parseCreate(XmlPullParser xpp,DataBase db){
        Town town = new Town();
        town.name = xpp.getAttributeValue(null,attrib_name);
        town.nameImage = GLES20Util.stringToBitmap(town.name, Constant.fontName,25,255,255,255);
        town.btnIcon.setX(Float.parseFloat(xpp.getAttributeValue(null,attrib_x)));
        town.btnIcon.setY(Float.parseFloat(xpp.getAttributeValue(null,attrib_y)));
        String backFile = xpp.getAttributeValue(null,attrib_image);
        if(backFile == null)
            town.background = new Image(Constant.getBitmap(Constant.BITMAP.white));
        else
            town.background = new Image(ImageReader.readImageToAssets(Constant.image_file_directory + backFile));
        town.id = ParserUtil.convertInt(xpp,attrib_id);
        town.isDungeon = false;
        String bool = xpp.getAttributeValue(null,attrib_dungeon);
        if(bool != null){
            town.isDungeon = Boolean.parseBoolean(bool);
            town.btnIcon.setBitmap(Constant.getBitmap(Constant.BITMAP.dungeon_icon));
        }else{
            town.btnIcon.setBitmap(Constant.getBitmap(Constant.BITMAP.town_icon));
        }
        town.btnIcon.useAspect(true);
        town.btnIcon.setHeight(icon_height);

        int eventType = XmlPullParser.END_DOCUMENT;
        try{
            eventType = xpp.getEventType();
        }catch (XmlPullParserException e){
            e.printStackTrace();
        }

        while(!(eventType == XmlPullParser.END_TAG && xpp.getName().equals(tagName))){

            if(eventType == XmlPullParser.START_TAG){
                if(xpp.getName().equals(child_tag_shop)){
                    createShop(xpp,db,town.shopItem);
                }else if(xpp.getName().equals(child_tag_hotel)){
                    town.hotelPrice = ParserUtil.convertInt(xpp,attrib_hotel);
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

        return town;
    }
    public static void createShop(XmlPullParser xpp,DataBase db,ArrayList<ShopItem> shop){
        int eventType = XmlPullParser.END_DOCUMENT;
        try{
            eventType = xpp.getEventType();
        }catch (XmlPullParserException e){
            e.printStackTrace();
        }

        while(!(eventType == XmlPullParser.END_TAG && xpp.getName().equals(child_tag_shop))){

            if(eventType == XmlPullParser.START_TAG){
                if(xpp.getName().equals(child_shop_item)){
                    shop.add(ShopItem.parseCreate(xpp,db));
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
