package jp.ac.dendai.c.jtp.Game.MapSystem;

import android.graphics.Bitmap;

import org.xmlpull.v1.XmlPullParser;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Condition.Condition;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.DataBase;
import jp.ac.dendai.c.jtp.ParserUtil;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;
import jp.ac.dendai.c.jtp.openglesutil.graphic.blending_mode.GLES20COMPOSITIONMODE;

/**
 * Created by wark on 2016/10/19.
 */

public class Node {
    public final static String tagName = "Node";
    protected final static float line_width = 0.01f;
    protected final static float encount_size = 0.03f;
    protected final static String attrib_encount = "encount";
    protected final static String attrib_enemyRank = "rank";
    protected final static String attrib_town1 = "town1";
    protected final static String attrib_town2 = "town2";
    protected Town town1;
    protected Town town2;
    protected int encount;
    protected int enemyRank;
    protected float degree;
    protected float length;

    protected float deg;

    public void drawNode(float offsetX,float offsetY,float alpha){

        Bitmap line = Constant.getBitmap(Constant.BITMAP.white);
        GLES20Util.DrawGraph(offsetX + town1.getX()+(town2.getX() - town1.getX())/2f,offsetY + town1.getY() + (town2.getY() - town1.getY())/2f
                ,length,line_width,0,0,1,1,degree
                ,line,alpha, GLES20COMPOSITIONMODE.ALPHA);
        for(int n = 0;n < encount;n++){
            GLES20Util.DrawGraph(offsetX + town1.getX()+(town2.getX() - town1.getX())/(float)(encount + 1)*(float)(n+1),offsetY + town1.getY() + (town2.getY() - town1.getY())/(float) (encount + 1)*(float)(n+1)
                    ,encount_size,encount_size
                    ,line,alpha, GLES20COMPOSITIONMODE.ALPHA);
        }
    }

    public Town getOtherTown(Town town){
        if(town != town1 && town != town2)
            return town1;
        if(town == town1)
            return town2;
        else
            return town1;
    }

    public int getEncount(){
        return encount;
    }

    public float getEncountPosX(Town start,int index){
        if(index <= 0)
            return start.getX();
        else if(index > encount)
            return getOtherTown(start).getX();
        float offset = getOtherTown(start).getX() - start.getX();
        offset = offset / (float)(encount+1) * (float)index;
        return start.getX() + offset;
    }

    public float getEncountPosY(Town start,int index){
        if(index <= 0)
            return start.getY();
        else if(index > encount)
            return getOtherTown(start).getY();
        float offset = getOtherTown(start).getY() - start.getY();
        offset = offset / (float)(encount+1) * (float)index;
        return start.getY() + offset;
    }

    public void debugSet(Town t1,Town t2,int encount){
        town1 = t1;
        town2 = t2;
        this.encount = encount;

        degree = (float) Math.toDegrees(Math.atan2(town2.getY() - town1.getY(),town2.getX() - town1.getX()));
        length = (float)Math.hypot(town2.getX() - town1.getX(),town2.getY() - town1.getY());
    }


    public static Node parseCreate(XmlPullParser xpp, DataBase db){
        Node node = new Node();
        node.encount = ParserUtil.convertInt(xpp,attrib_encount);
        node.enemyRank = ParserUtil.convertInt(xpp,attrib_enemyRank);
        node.town1 = db.getTown(xpp.getAttributeValue(null,attrib_town1));
        node.town2 = db.getTown(xpp.getAttributeValue(null,attrib_town2));

        node.town1.addNode(node);
        node.town2.addNode(node);

        node.degree = (float)Math.toDegrees(Math.atan2(node.town2.getY() - node.town1.getY(),node.town2.getX() - node.town1.getX()));
        node.length = (float)Math.hypot(node.town2.getX() - node.town1.getX(),node.town2.getY() - node.town1.getY());

        return node;
    }
}
