package jp.ac.dendai.c.jtp.Game;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;

import jp.ac.dendai.c.jtp.Game.BattleSystem.Enemy.EnemyTemplate;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Skill.Skill;
import jp.ac.dendai.c.jtp.Game.Item.Item;
import jp.ac.dendai.c.jtp.Game.UIs.Effects.Bitmap.AnimationBitmap;
import jp.ac.dendai.c.jtp.openglesutil.Util.FileManager;

/**
 * Created by テツヤ on 2016/10/15.
 */

public class DataBase {
    protected final static String animationTag = "Animation";
    protected final static String skillTag = "Skill";

    /* ---------- Animation ------------*/
    protected final static String attrib_anim_id = "id";
    protected final static String attrib_anim_file = "file";
    protected final static String attrib_anim_width = "width";
    protected final static String attrib_anim_height = "height";
    protected final static String attrib_anim_count_x = "count_x";
    protected final static String attrib_anim_count_y = "count_y";
    protected final static String attrib_anim_count_anime = "count_anime";

    /* --------- Battle.Enemy ---------- */
    protected final static String enemyTag = "Enemy";

    protected static HashMap<String,EnemyTemplate> enemyList;
    protected static HashMap<String,AnimationBitmap> animationList;
    protected static HashMap<String,Skill> skillList;
    protected static HashMap<String,Item> itemList;

    public DataBase(){
        initSkillList();
        initEnemyList();
    }

    public AnimationBitmap getAnimation(String key){
        if(animationList.containsKey(key)){
            return animationList.get(key);
        }
        return null;
    }

    public Skill getSkill(String key){
        if(skillList.containsKey(key)){
            return skillList.get(key);
        }
        return null;
    }

    public Item getItem(String key){
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

    protected void initSkillList(){
        Log.d("DataBase","Start initSkillList");

        skillList = new HashMap<>();
        animationList = new HashMap<>();

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
            /*if (eventType == XmlPullParser.END_TAG) {
                if (xpp.getName().equals(animationTag)) {
                    Log.d("DataBase","End initSkillList");
                    return;
                }
            }*/

            if (eventType == XmlPullParser.START_TAG) {
                if (xpp.getName().equals(animationTag)) {
                    //アニメーションタグ
                    String id = xpp.getAttributeValue(null, attrib_anim_id);
                    Log.d("DataBase[initSkillList]", "Load Animation id :" + id);
                    String file = xpp.getAttributeValue(null, attrib_anim_file);
                    int width = Integer.parseInt(xpp.getAttributeValue(null, attrib_anim_width));
                    int height = Integer.parseInt(xpp.getAttributeValue(null, attrib_anim_height));
                    int count_x = Integer.parseInt(xpp.getAttributeValue(null, attrib_anim_count_x));
                    int count_y = Integer.parseInt(xpp.getAttributeValue(null, attrib_anim_count_y));
                    int count_anime = Integer.parseInt(xpp.getAttributeValue(null, attrib_anim_count_anime));

                    AnimationBitmap ab = AnimationBitmap.createAnimation(Constant.skill_image_file_directory + file, width, height, count_x, count_y, count_anime);
                    animationList.put(id, ab);
                } else if (xpp.getName().equals(skillTag)) {
                    Skill skill = Skill.parseCreate(xpp, this);
                    skillList.put(skill.getSkillName(),skill);
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
}
