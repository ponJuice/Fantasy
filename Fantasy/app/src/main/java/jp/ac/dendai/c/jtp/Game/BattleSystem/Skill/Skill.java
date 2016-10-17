package jp.ac.dendai.c.jtp.Game.BattleSystem.Skill;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.DataBase;
import jp.ac.dendai.c.jtp.Game.UIs.Effects.Bitmap.Animator;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Util.Time;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;
import jp.ac.dendai.c.jtp.openglesutil.graphic.blending_mode.GLES20COMPOSITIONMODE;

/**
 * Created by wark on 2016/10/13.
 */

public class Skill {
    public final static String tagName = "Skill";
    protected final static String animationTag = "Animation";
    protected final static String attrib_damage = "damage";
    protected final static String attrib_name = "name";

    protected String skillName;
    protected float damage;
    protected ArrayList<SkillAnimation> skillAnimations;
    protected float timeBuffer;

    public Skill(){
        skillAnimations = new ArrayList<>();
    }

    public void effectInit(){
        //animator.resetAnimation();
        timeBuffer = 0;
    }

    public String getSkillName(){
        return skillName;
    }

    public boolean draw(float ox,float oy,float sx,float sy,float deg){
        boolean flag = true;

        for(int n = 0;n < skillAnimations.size();n++){
            SkillAnimation sa = skillAnimations.get(n);
            flag = sa.draw(timeBuffer,ox,oy,sx,sy,deg) && flag;
        }
        /*if(skillAnimations.size() > 1)
            flag = flag && skillAnimations.get(1).draw(timeBuffer,ox,oy,sx,sy,deg);
        else
            flag = flag && skillAnimations.get(0).draw(timeBuffer,ox,oy,sx,sy,deg);*/
        timeBuffer += Time.getDeltaTime();

        if(flag)
            timeBuffer = 0;
        return flag;
    }

    public int calcDamage(float attackValue){
        return (int)(attackValue * damage);
    }

    public static Skill parseCreate(XmlPullParser xpp, DataBase db){
        Skill sk = new Skill();
        sk.damage = Float.parseFloat(xpp.getAttributeValue(null,attrib_damage));
        sk.skillName = xpp.getAttributeValue(null,attrib_name);

        Log.d("Skill","Start parseCreate name : "+sk.skillName);

        int eventType = XmlPullParser.END_DOCUMENT;
        try{
            eventType = xpp.getEventType();
        }catch (XmlPullParserException e){
            e.printStackTrace();
        }

        while(eventType != XmlPullParser.END_DOCUMENT){
            if(eventType == XmlPullParser.END_TAG){
                if(xpp.getName().equals(tagName)){
                    return sk;
                }
            }

            if(eventType == XmlPullParser.START_TAG) {
                if (xpp.getName().equals(animationTag)) {
                    SkillAnimation sa = SkillAnimation.parseCreate(xpp, db);
                    sk.skillAnimations.add(sa);
                }
            }
            try {
                eventType = xpp.next();
            }catch (XmlPullParserException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return sk;
    }
}
