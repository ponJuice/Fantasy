package jp.ac.dendai.c.jtp.Game.MapSystem.Dungeon;

import android.graphics.Bitmap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Flag;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Enemy.Enemy;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Enemy.EnemyTemplate;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.DataBase;
import jp.ac.dendai.c.jtp.Game.MapSystem.Node;
import jp.ac.dendai.c.jtp.Game.MapSystem.ShopItem;
import jp.ac.dendai.c.jtp.Game.MapSystem.Town;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.Button;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.ButtonListener;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Image.Image;
import jp.ac.dendai.c.jtp.ParserUtil;
import jp.ac.dendai.c.jtp.TouchUtil.Touch;
import jp.ac.dendai.c.jtp.openglesutil.Util.ImageReader;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by Goto on 2016/10/24.
 */

public class DungeonPoint {
    public final static String tagName="Point";
    protected final static String child_shop_item = "Item";
    protected final static String attrib_id = "id";
    protected final static String attrib_x = "x";
    protected final static String attrib_y = "y";
    protected final static String attrib_back = "back";
    protected final static String child_tag_enemy = "Enemy";
    protected final static String child_enemy_attrib_name = "name";
    protected final static String child_flag_tag = "Flag";
    protected final static String attrib_start = "start";
    protected final static float icon_width = 0.1f;
    protected final static float icon_height = 0.1f;
    protected float icon_alpha = 1f;
    protected Flag flagProc;
    protected Button btnIcon;
    protected ArrayList<DungeonNode> nodes;
    protected ArrayList<String> enemies;
    protected boolean isStartPoint;
    protected String backgroundFile;
    protected String id;

    public DungeonPoint(){
        btnIcon = new Button(0,0,1,1);
        btnIcon.setBitmap(Constant.getBitmap(Constant.BITMAP.white));
        btnIcon.useAspect(true);
        btnIcon.setHeight(icon_height);
        nodes = new ArrayList<>();
    }

    public ArrayList<String> getEnemies(){
        return enemies;
    }

    public void setButtonListener(ButtonListener bl){
        btnIcon.setButtonListener(bl);
    }

    public DungeonNode searchNode(DungeonPoint next){
        for(int n = 0;n < nodes.size();n++){
            if((nodes.get(n).dungeon1 == this && nodes.get(n).dungeon2 == next)
                    || (nodes.get(n).dungeon1 == next && nodes.get(n).dungeon2 == this)){
                return nodes.get(n);
            }
        }
        return null;
    }

    public void addNode(DungeonNode node){
        nodes.add(node);
    }

    public float getX(){
        return btnIcon.getX();
    }

    public float getY(){
        return btnIcon.getY();
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

    public String getId(){
        return id;
    }

    public void debugSet(float x,float y){
        btnIcon.setX(x);
        btnIcon.setY(y);
    }

    public boolean procFlag(){
        if(flagProc != null) {
            flagProc.proc();
            return true;
        }
        return false;
    }

    public boolean isStart(){
        return isStartPoint;
    }

    public String getBackgroundFile(){
        return backgroundFile;
    }

    public static DungeonPoint parseCreate(XmlPullParser xpp, DataBase db){
        DungeonPoint town = new DungeonPoint();
        town.btnIcon.setX(Float.parseFloat(xpp.getAttributeValue(null,attrib_x)));
        town.btnIcon.setY(Float.parseFloat(xpp.getAttributeValue(null,attrib_y)));
        town.id = xpp.getAttributeValue(null,attrib_id);
        town.enemies = new ArrayList<>();
        town.isStartPoint = false;
        town.isStartPoint = Boolean.parseBoolean(xpp.getAttributeValue(null,attrib_start));
        town.backgroundFile = xpp.getAttributeValue(null,attrib_back);

        if(town.isStartPoint){
            town.btnIcon.setBitmap(Constant.getBitmap(Constant.BITMAP.dungeon_icon));
        }

        int eventType = XmlPullParser.END_DOCUMENT;
        try{
            eventType = xpp.getEventType();
        }catch (XmlPullParserException e){
            e.printStackTrace();
        }

        while(!(eventType == XmlPullParser.END_TAG && xpp.getName().equals(tagName))){

            if(eventType == XmlPullParser.START_TAG){
                if(xpp.getName().equals(child_tag_enemy)){
                    town.enemies.add(xpp.getAttributeValue(null,child_enemy_attrib_name));
                }else if(xpp.getName().equals(child_flag_tag)){
                    Flag flagProc = new Flag();
                    flagProc.parseCreate(null,xpp);
                    town.flagProc = flagProc;
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
        if(town.enemies.size() != 0){
            town.btnIcon.setBitmap(Constant.getBitmap(Constant.BITMAP.boss_icon));
        }
        return town;
    }
}
