package jp.ac.dendai.c.jtp.Game.BattleSystem.Skill;

import android.provider.ContactsContract;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import jp.ac.dendai.c.jtp.Game.DataBase;
import jp.ac.dendai.c.jtp.ParserUtil;

/**
 * Created by wark on 2016/10/23.
 */

public class SkillEvolutions {
    public final static String tagName = "Evolution";
    protected final static String attrib_owner = "name";
    protected final static String attrib_count = "count";
    protected final static String attrib_skill_name = "name";
    protected final static String child_next_tag = "Next";
    public Skill owner;
    public ArrayList<Skill> nextSkill;

    public SkillEvolutions(){
        nextSkill = new ArrayList<>();
    }

    public static SkillEvolutions parseCreate(XmlPullParser xpp, DataBase db){
        SkillEvolutions se = new SkillEvolutions();

        String name = xpp.getAttributeValue(null,attrib_owner);
        int count = ParserUtil.convertInt(xpp,attrib_count);
        se.owner = db.getSkill(name);
        se.owner.count = count;

        int eventType = XmlPullParser.END_DOCUMENT;
        try{
            eventType = xpp.getEventType();
        }catch (XmlPullParserException e){
            e.printStackTrace();
        }

        while(!(eventType == XmlPullParser.END_TAG && xpp.getName().equals(tagName))){
            if(eventType == XmlPullParser.START_TAG){
                if(xpp.getName().equals(child_next_tag)){
                    se.nextSkill.add(getNextName(xpp,db));
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

        return se;
    }

    public static Skill getNextName(XmlPullParser xpp,DataBase db){
        return db.getSkill(xpp.getAttributeValue(null,attrib_skill_name));
    }
}
