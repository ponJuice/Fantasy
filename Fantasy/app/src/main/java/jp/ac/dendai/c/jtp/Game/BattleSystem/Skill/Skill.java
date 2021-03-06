package jp.ac.dendai.c.jtp.Game.BattleSystem.Skill;

import android.graphics.Bitmap;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.CheckedOutputStream;

import jp.ac.dendai.c.jtp.Game.BattleSystem.Attackable;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.DataBase;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.NumberText;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.StaticText;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UI;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Util.Time;
import jp.ac.dendai.c.jtp.ParserUtil;
import jp.ac.dendai.c.jtp.TouchUtil.Touch;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by wark on 2016/10/13.
 */

public class Skill implements UI{
    public final static String tagName = "Skill";
    protected final static String animationTag = "Animation";
    protected final static String attrib_damage = "damage";
    protected final static String attrib_name = "name";
    protected final static String attrib_mp = "mp";
    protected final static String attrib_rank = "rank";

    protected String skillName;
    protected StaticText nameImage;
    protected NumberText skillCost;
    protected float damage;
    protected int mp;
    protected int count,countBuff= 0;
    protected int rank;
    protected ArrayList<Animation> skillAnimations;
    protected float timeBuffer;

    public Skill(){
        skillAnimations = new ArrayList<>();
    }

    public int getRank(){
        return rank;
    }

    public void setEvolutionCount(int count){this.count = count;}

    public boolean addCount(){
        countBuff++;
        if(count == countBuff){
            return true;
        }
        return false;
    }

    public int getCount(){
        return countBuff;
    }

    public void setCountBuff(int n){
        countBuff = n;
    }

    public Bitmap getNameImage(){
        return nameImage.getImage();
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
            Animation sa = skillAnimations.get(n);
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


    public void setWidth(float width,float padding){
        nameImage.setX(-width/2f + padding);
        skillCost.setX(width/2f - padding);
    }

    public void setHeight(float height,float padding){
        skillCost.setHeight(height-padding);
        nameImage.setHeight(height-padding);
    }

    public int calcDamage(float attackValue){
        return (int)(attackValue * damage);
    }

    public int calcMpValue(Attackable a){
        return mp;
    }

    public static Skill parseCreate(XmlPullParser xpp, DataBase db){
        Skill sk = new Skill();
        sk.damage = Float.parseFloat(xpp.getAttributeValue(null,attrib_damage));
        sk.skillName = xpp.getAttributeValue(null,attrib_name);
        sk.nameImage = new StaticText(sk.skillName,null);
        sk.mp = Integer.parseInt(xpp.getAttributeValue(null,attrib_mp));
        sk.rank = ParserUtil.convertInt(xpp,attrib_rank);
        sk.skillCost = new NumberText(Constant.fontName);
        sk.skillCost.setNumber(sk.mp);
        sk.nameImage.setHorizontal(UIAlign.Align.LEFT);
        sk.skillCost.setHorizontal(UIAlign.Align.RIGHT);

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
                    Animation sa = Animation.parseCreate(xpp, db);
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

    @Override
    public boolean touch(Touch touch) {
        return false;
    }

    @Override
    public void proc() {

    }

    @Override
    public void draw(float offset_x, float offset_y) {
        nameImage.draw(offset_x,offset_y);
        skillCost.draw(offset_x,offset_y);
    }
}
