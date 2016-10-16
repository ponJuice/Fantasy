package jp.ac.dendai.c.jtp.Game.BattleSystem.Enemy;

import android.graphics.Bitmap;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import jp.ac.dendai.c.jtp.Game.BattleSystem.Skill.Skill;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.DataBase;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Image.Image;
import jp.ac.dendai.c.jtp.ParserUtil;
import jp.ac.dendai.c.jtp.openglesutil.Util.ImageReader;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by Goto on 2016/09/16.
 */
public class EnemyTemplate {
    public final static String tagName = "Enemy";
    protected final static String skillTag ="Skill";
    protected final static String attrib_enemy_id = "id";
    protected final static String attrib_enemy_name = "name";
    protected final static String attrib_enemy_skill_name = "name";
    protected final static String attrib_enemy_file = "file";
    protected final static String attrib_enemy_rank = "rank";
    protected final static String attrib_enemy_hp = "hp";
    protected final static String attrib_enemy_atk = "atk";
    protected final static String attrib_enemy_def = "def";
    protected final static String attrib_enemy_agl = "agl";
    protected final static String attrib_enemy_mp = "mp";

    public String name;
    public Bitmap name_bitmap;
    public String id;
    public int hp,atk,def,agl,rank,mp;
    public Bitmap image;
    public Skill[] skills;
    public EnemyTemplate(String name,int hp,int atk,int def,int agl,Bitmap image,Skill[] magics){
        this.name = name;
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.agl = agl;
        this.image = image;
        this.skills = magics;

        name_bitmap = GLES20Util.stringToBitmap(name,Constant.fontName,25,255,255,255);

    }

    protected EnemyTemplate(){}

    @Override
    public String toString(){
        return String.format("[name : %s] [id : %s] [hp : %d] [atk : %d] [def : %d] [agl : %d] [mp : %d]",name,id,hp,atk,def,agl,mp);
    }

    public static EnemyTemplate parseCreate(XmlPullParser xpp, DataBase db){

        EnemyTemplate et = new EnemyTemplate();

        ArrayList<Skill> skills = new ArrayList<>();
        String name = xpp.getAttributeValue(null,attrib_enemy_name);
        Log.d("EnemyTemplate","Start parseCreate [name:"+name+"]");
        String file = xpp.getAttributeValue(null,attrib_enemy_file);
        et.id = xpp.getAttributeValue(null,attrib_enemy_id);
        et.name = name;
        et.rank = ParserUtil.convertInt(xpp,attrib_enemy_rank);
        et.hp = ParserUtil.convertInt(xpp,attrib_enemy_hp);
        et.atk = ParserUtil.convertInt(xpp,attrib_enemy_atk);
        et.def = ParserUtil.convertInt(xpp,attrib_enemy_def);
        et.agl = ParserUtil.convertInt(xpp,attrib_enemy_agl);
        et.mp = ParserUtil.convertInt(xpp,attrib_enemy_mp);

        et.name_bitmap = GLES20Util.stringToBitmap(name,Constant.fontName,25,255,255,255);

        et.image = ImageReader.readImageToAssets(Constant.enemy_image_file_directory + xpp.getAttributeValue(null,attrib_enemy_file));

        int eventType = XmlPullParser.END_DOCUMENT;
        try{
            eventType = xpp.next();
        }catch (XmlPullParserException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        while(eventType != XmlPullParser.END_DOCUMENT){
            if(eventType == XmlPullParser.END_TAG){
                if(xpp.getName().equals(tagName)){
                    Log.d("EnemyTemplate ","End parseCreate"+et.toString());
                    et.skills = skills.toArray(new Skill[0]);
                    return et;
                }
            }

            if(eventType == XmlPullParser.START_TAG){
                if(xpp.getName().equals(skillTag)){
                    String skillName = xpp.getAttributeValue(null,attrib_enemy_skill_name);
                    skills.add(db.getSkill(skillName));
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

        et.skills = skills.toArray(new Skill[0]);
        //デバッグ表示
        Log.d("EnemyTemplate ","EndDocument parseCreate"+et.toString());
        return et;
    }
}
