package jp.ac.dendai.c.jtp.Game;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import jp.ac.dendai.c.jtp.Game.BattleSystem.Enemy.Enemy;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Enemy.EnemyTemplate;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Skill.Animation;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Skill.Skill;
import jp.ac.dendai.c.jtp.Game.Item.Item;
import jp.ac.dendai.c.jtp.Game.Item.ItemTemplate;
import jp.ac.dendai.c.jtp.Game.MapSystem.Node;
import jp.ac.dendai.c.jtp.Game.UIs.Effects.Bitmap.AnimationBitmap;
import jp.ac.dendai.c.jtp.openglesutil.Util.FileManager;
import jp.ac.dendai.c.jtp.Game.MapSystem.Town;

/**
 * Created by テツヤ on 2016/10/15.
 */

public class DataBase {
    protected final static String animationTag = "Animations";
    protected final static String skillTag = "Skill";
    protected final static String mapTag = "Map";
    protected final static String nodesTag = "Nodes";

    /* ---------- Animation.dat ------------*/
    protected final static String attrib_anim_id = "id";
    protected final static String attrib_anim_file = "file";
    protected final static String attrib_anim_width = "width";
    protected final static String attrib_anim_height = "height";
    protected final static String attrib_anim_count_x = "count_x";
    protected final static String attrib_anim_count_y = "count_y";
    protected final static String attrib_anim_count_anime = "count_anime";

    /* --------- Battle.Enemy ---------- */
    protected final static String enemyTag = "Enemy";
    protected final static String itemsTag = "Items";

    protected static HashMap<Integer,ArrayList<EnemyTemplate>> rankEnemy;
    protected static HashMap<String,EnemyTemplate> enemyList;
    protected static HashMap<String,AnimationBitmap> animationList;
    protected static HashMap<String,Skill> skillList;
    protected static HashMap<String,ItemTemplate> itemList;
    protected static HashMap<String,Town> townList;
    protected static ArrayList<Node> nodeList;

    public DataBase(){
        initAnimationList();
        initSkillList();
        initEnemyList();
        initItemList();
        initTownList();
    }

    public AnimationBitmap getAnimation(String key){
        if(animationList.containsKey(key)){
            return animationList.get(key);
        }
        return null;
    }

    public Town getTown(String key){
        if(townList.containsKey(key))
            return townList.get(key);
        return null;
    }

    public Collection<Town> getTownList(){
        return townList.values();
    }

    public ArrayList<Node> getNodeList(){
        return nodeList;
    }

    public Skill getSkill(String key){
        if(skillList.containsKey(key)){
            return skillList.get(key);
        }
        return null;
    }

    public ItemTemplate getItem(String key){
        if(itemList.containsKey(key)){
            return itemList.get(key);
        }
        return null;
    }

    public EnemyTemplate getEnemy(String key){
        if(enemyList.containsKey(key)){
            return enemyList.get(key);
        }
        return null;
    }

    public EnemyTemplate getEnemy(int rank){
        if(rankEnemy.containsKey(rank)){
            ArrayList<EnemyTemplate> ea = rankEnemy.get(rank);
            return ea.get(Constant.getRandom().nextInt(ea.size()));
        }
        return null;
    }

    public void initAnimationList(){
        Log.d("DataBase","Start initAnimationList");
        animationList = new HashMap<>();
        XmlPullParser xpp = null;
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            xpp = factory.newPullParser();
            xpp.setInput(new StringReader(FileManager.readTextFile(Constant.animationFile)));
        }catch (Exception e){
            e.printStackTrace();
            //debugOutputString("error end");
        }

        int eventType = XmlPullParser.END_DOCUMENT;
        try{
            eventType = xpp.getEventType();
        }catch (XmlPullParserException e){
            e.printStackTrace();
        }

        while(eventType != XmlPullParser.END_DOCUMENT) {
            /*if (eventType == XmlPullParser.END_TAG) {
                if (xpp.getName().equals(animationTag)) {
                    Log.d("DataBase","End initSkillList");
                    return;
                }
            }*/

            if (eventType == XmlPullParser.START_TAG) {
                if (xpp.getName().equals(Animation.tagName)) {
                    //アニメーションタグ
                    String id = xpp.getAttributeValue(null, attrib_anim_id);
                    Log.d("DataBase[initSkillList]", "Load Animation.dat id :" + id);
                    String file = xpp.getAttributeValue(null, attrib_anim_file);
                    int width = Integer.parseInt(xpp.getAttributeValue(null, attrib_anim_width));
                    int height = Integer.parseInt(xpp.getAttributeValue(null, attrib_anim_height));
                    int count_x = Integer.parseInt(xpp.getAttributeValue(null, attrib_anim_count_x));
                    int count_y = Integer.parseInt(xpp.getAttributeValue(null, attrib_anim_count_y));
                    int count_anime = Integer.parseInt(xpp.getAttributeValue(null, attrib_anim_count_anime));

                    AnimationBitmap ab = AnimationBitmap.createAnimation(Constant.animation_image_file_directory + file, width, height, count_x, count_y, count_anime);
                    animationList.put(id, ab);
                }

            }

            try {
                eventType = xpp.next();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
        }

    }

    protected void initSkillList(){
        Log.d("DataBase","Start initSkillList");

        skillList = new HashMap<>();

        XmlPullParser xpp = null;
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            xpp = factory.newPullParser();
            xpp.setInput(new StringReader(FileManager.readTextFile(Constant.skillFile)));
        }catch (Exception e){
            e.printStackTrace();
            //debugOutputString("error end");
        }

        int eventType = XmlPullParser.END_DOCUMENT;
        try{
            eventType = xpp.getEventType();
        }catch (XmlPullParserException e){
            e.printStackTrace();
        }

        while(eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.START_TAG) {
                if (xpp.getName().equals(skillTag)) {
                    Skill skill = Skill.parseCreate(xpp, this);
                    skillList.put(skill.getSkillName(), skill);
                }
            }

            try {
                eventType = xpp.next();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
        }
    }

    protected void initEnemyList(){
        Log.d("DataBase","Start initEnemyList");

        enemyList = new HashMap<>();
        rankEnemy = new HashMap<>();

        XmlPullParser xpp = null;
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            xpp = factory.newPullParser();
            xpp.setInput(new StringReader(FileManager.readTextFile(Constant.enemyFile)));
        }catch (Exception e){
            e.printStackTrace();
            //debugOutputString("error end");
        }

        int eventType = XmlPullParser.END_DOCUMENT;
        try{
            eventType = xpp.getEventType();
        }catch (XmlPullParserException e){
            e.printStackTrace();
        }

        while(eventType != XmlPullParser.END_DOCUMENT) {
            /*if(eventType == XmlPullParser.END_TAG){
                if(xpp.getName().equals(enemyTag)){
                    Log.d("DataBase","End initEnemyList");
                    return;
                }
            }*/

            if(eventType == XmlPullParser.START_TAG){
                if(xpp.getName().equals(enemyTag)){
                    EnemyTemplate et = EnemyTemplate.parseCreate(xpp,this);
                    if(rankEnemy.containsKey(et.rank)){
                        rankEnemy.get(et.rank).add(et);
                    }else{
                        ArrayList<EnemyTemplate> ea = new ArrayList<>();
                        ea.add(et);
                        rankEnemy.put(et.rank,ea);
                    }
                    enemyList.put(et.id,et);
                }
            }

            try{
                eventType = xpp.next();
            }catch (IOException e){
                e.printStackTrace();
            }catch (XmlPullParserException e){
                e.printStackTrace();
            }
        }
    }

    protected void initItemList(){
        Log.d("DataBase","Start initItemList");

        itemList = new HashMap<>();

        XmlPullParser xpp = null;
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            xpp = factory.newPullParser();
            xpp.setInput(new StringReader(FileManager.readTextFile(Constant.itemFile)));
        }catch (Exception e){
            e.printStackTrace();
            //debugOutputString("error end");
        }

        int eventType = XmlPullParser.END_DOCUMENT;
        try{
            eventType = xpp.getEventType();
        }catch (XmlPullParserException e){
            e.printStackTrace();
        }

        while(eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.END_TAG){
                if(xpp.getName().equals(itemsTag))
                    break;
            }
            if(eventType == XmlPullParser.START_TAG){
                if(xpp.getName().equals(ItemTemplate.tagName)){
                    ItemTemplate item = ItemTemplate.parseCreate(xpp,this);
                    itemList.put(item.getName(),item);
                }
            }

            if(eventType == XmlPullParser.END_DOCUMENT)
                break;

            try{
                eventType = xpp.next();
            }catch (IOException e){
                e.printStackTrace();
            }catch (XmlPullParserException e){
                e.printStackTrace();
            }
        }
    }

    protected void initTownList(){
        townList = new HashMap<>();
        nodeList = new ArrayList<>();
        XmlPullParser xpp = null;
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            xpp = factory.newPullParser();
            xpp.setInput(new StringReader(FileManager.readTextFile(Constant.townFile)));
        }catch (Exception e){
            e.printStackTrace();
            //debugOutputString("error end");
        }

        int eventType = XmlPullParser.END_DOCUMENT;
        try{
            eventType = xpp.getEventType();
        }catch (XmlPullParserException e){
            e.printStackTrace();
        }

        while(eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.END_TAG){
                if(xpp.getName().equals(mapTag))
                    break;
            }
            if(eventType == XmlPullParser.START_TAG){
                if(xpp.getName().equals(Town.tagName)){
                    Town town = Town.parseCreate(xpp,this);
                    townList.put(town.getName(),town);
                }else if(xpp.getName().equals(nodesTag)){
                    parseNodes(xpp);
                }
            }

            try{
                eventType = xpp.getEventType();
            }catch (XmlPullParserException e){
                e.printStackTrace();
            }

            if(eventType == XmlPullParser.END_DOCUMENT)
                break;

            try{
                eventType = xpp.next();
            }catch (IOException e){
                e.printStackTrace();
            }catch (XmlPullParserException e){
                e.printStackTrace();
            }
        }
    }

    protected void parseNodes(XmlPullParser xpp){
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            xpp = factory.newPullParser();
            xpp.setInput(new StringReader(FileManager.readTextFile(Constant.townFile)));
        }catch (Exception e){
            e.printStackTrace();
            //debugOutputString("error end");
        }

        int eventType = XmlPullParser.END_DOCUMENT;
        try{
            eventType = xpp.getEventType();
        }catch (XmlPullParserException e){
            e.printStackTrace();
        }

        while(!(eventType == XmlPullParser.END_TAG && xpp.getName().equals(nodesTag))) {
            if(eventType == XmlPullParser.START_TAG){
                if(xpp.getName().equals(Node.tagName)){
                    nodeList.add(Node.parseCreate(xpp,this));
                }
            }

            try{
                eventType = xpp.getEventType();
            }catch (XmlPullParserException e){
                e.printStackTrace();
            }

            if(eventType == XmlPullParser.END_DOCUMENT)
                break;

            try{
                eventType = xpp.next();
            }catch (IOException e){
                e.printStackTrace();
            }catch (XmlPullParserException e){
                e.printStackTrace();
            }
        }
    }
}
