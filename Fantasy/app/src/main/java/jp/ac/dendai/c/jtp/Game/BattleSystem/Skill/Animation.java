package jp.ac.dendai.c.jtp.Game.BattleSystem.Skill;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;

import jp.ac.dendai.c.jtp.Game.DataBase;
import jp.ac.dendai.c.jtp.Game.UIs.Effects.Bitmap.Animator;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;
import jp.ac.dendai.c.jtp.openglesutil.graphic.blending_mode.GLES20COMPOSITIONMODE;

/**
 * Created by テツヤ on 2016/10/15.
 */

public class Animation {
    public final static String tagName ="Animation";
    protected final static String attrib_anim_id = "anim_id";
    protected final static String attrib_start = "start";
    protected final static String attrib_length = "length";
    protected final static String attrib_ox = "ox";
    protected final static String attrib_oy = "oy";
    protected final static String attrib_sx = "sx";
    protected final static String attrib_sy = "sy";
    protected final static String attrib_rot = "rot";

    protected Animator anim;
    protected float start;
    protected float length;
    protected float ox;
    protected float oy;
    protected float sx;
    protected float sy;
    protected float rot;

    public Animation(){
    }

    //戻り値：false まだ再生中 true もう終わった
    public boolean draw(float time,float ox,float oy,float sx,float sy,float deg){
        String str = String.format("[time : %.3f] [start : %.3f] [length : %.3f]",time,start,length);
        //Log.d("Animation",str);
        if(time < start) {
            return false;
        }
        if(time >= start + length){
            return true;
        }
        float timeBuffer = time - start;

        int temp = (int)((float)anim.getAnimationNum() / length * timeBuffer);

        GLES20Util.DrawGraph(this.ox+ox,this.oy+oy,this.sx*sx,this.sy*sy,0,0,1,1,deg+rot,anim.getBitmap(temp),1,GLES20COMPOSITIONMODE.ALPHA);

        return false;
    }

    public void resetAnimation(){
        anim.resetAnimation();
    }

    public static Animation parseCreate(XmlPullParser xpp, DataBase db){
        Animation sk = new Animation();
        String anim_id = xpp.getAttributeValue(null, attrib_anim_id);

        Log.d("Animation","Start parseCreate anim_id : "+anim_id);

        sk.anim = new Animator(db.getAnimation(anim_id));
        sk.start = Float.parseFloat(xpp.getAttributeValue(null, attrib_start));
        sk.length = Float.parseFloat(xpp.getAttributeValue(null, attrib_length));
        sk.ox = Float.parseFloat(xpp.getAttributeValue(null, attrib_ox));
        sk.oy = Float.parseFloat(xpp.getAttributeValue(null, attrib_oy));
        sk.sx = Float.parseFloat(xpp.getAttributeValue(null, attrib_sx));
        sk.sy = Float.parseFloat(xpp.getAttributeValue(null, attrib_sy));
        sk.rot = Float.parseFloat(xpp.getAttributeValue(null, attrib_rot));


        return sk;
    }
}
