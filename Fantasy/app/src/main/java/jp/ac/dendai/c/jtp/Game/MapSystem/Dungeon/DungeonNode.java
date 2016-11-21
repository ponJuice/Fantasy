package jp.ac.dendai.c.jtp.Game.MapSystem.Dungeon;

import android.graphics.Bitmap;

import org.xmlpull.v1.XmlPullParser;

import java.util.HashMap;

import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.DataBase;
import jp.ac.dendai.c.jtp.Game.MapSystem.Node;
import jp.ac.dendai.c.jtp.Game.MapSystem.Town;
import jp.ac.dendai.c.jtp.ParserUtil;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;
import jp.ac.dendai.c.jtp.openglesutil.graphic.blending_mode.GLES20COMPOSITIONMODE;

/**
 * Created by Goto on 2016/10/24.
 */

public class DungeonNode{
    public final static String tagName = "Node";
    protected final static float line_width = 0.01f;
    protected final static float encount_size = 0.03f;
    protected final static String attrib_encount = "encount";
    protected final static String attrib_enemyRank = "rank";
    protected final static String attrib_town1 = "point1";
    protected final static String attrib_town2 = "point2";
    protected final static String attrib_back_file = "back";
    protected DungeonPoint dungeon1;
    protected DungeonPoint dungeon2;
    protected int encount;
    protected int enemyRank;
    protected float degree;
    protected float length;
    protected String fileName;
    protected HashMap<String,DungeonPoint> points;

    protected float deg;

    public void drawNode(float offsetX,float offsetY,float alpha){

        Bitmap line = Constant.getBitmap(Constant.BITMAP.white);
        GLES20Util.DrawGraph(offsetX + dungeon1.getX()+(dungeon2.getX() - dungeon1.getX())/2f,offsetY + dungeon1.getY() + (dungeon2.getY() - dungeon1.getY())/2f
                ,length,line_width,0,0,1,1,degree
                ,line,alpha, GLES20COMPOSITIONMODE.ALPHA);
        for(int n = 0;n < encount;n++){
            GLES20Util.DrawGraph(offsetX + dungeon1.getX()+(dungeon2.getX() - dungeon1.getX())/(float)(encount + 1)*(float)(n+1),offsetY + dungeon1.getY() + (dungeon2.getY() - dungeon1.getY())/(float) (encount + 1)*(float)(n+1)
                    ,encount_size,encount_size
                    ,line,alpha, GLES20COMPOSITIONMODE.ALPHA);
        }
    }

    public DungeonPoint getOtherPoint(DungeonPoint point){
        if(point != dungeon1 && point != dungeon2)
            return dungeon1;
        if(point == dungeon1)
            return dungeon2;
        else
            return dungeon1;
    }

    public int getEncount(){
        return encount;
    }

    public float getEncountPosX(DungeonPoint start,int index){
        if(index <= 0)
            return start.getX();
        else if(index > encount)
            return getOtherPoint(start).getX();
        float offset = getOtherPoint(start).getX() - start.getX();
        offset = offset / (float)(encount+1) * (float)index;
        return start.getX() + offset;
    }

    public float getEncountPosY(DungeonPoint start,int index){
        if(index <= 0)
            return start.getY();
        else if(index > encount)
            return getOtherPoint(start).getY();
        float offset = getOtherPoint(start).getY() - start.getY();
        offset = offset / (float)(encount+1) * (float)index;
        return start.getY() + offset;
    }

    public int getRank(){
        return enemyRank;
    }

    public String getBackFile(){
        return fileName;
    }

    public void debugSet(DungeonPoint t1,DungeonPoint t2,int encount){
        dungeon1 = t1;
        dungeon2 = t2;
        this.encount = encount;

        degree = (float) Math.toDegrees(Math.atan2(dungeon2.getY() - dungeon1.getY(), dungeon2.getX() - dungeon1.getX()));
        length = (float)Math.hypot(dungeon2.getX() - dungeon1.getX(), dungeon2.getY() - dungeon1.getY());
    }


    public static DungeonNode parseCreate(XmlPullParser xpp, DataBase db,HashMap<String,DungeonPoint> points){
        DungeonNode node = new DungeonNode();
        node.encount = ParserUtil.convertInt(xpp,attrib_encount);
        node.enemyRank = ParserUtil.convertInt(xpp,attrib_enemyRank);
        node.dungeon1 = points.get(xpp.getAttributeValue(null,attrib_town1));
        node.dungeon2 = points.get(xpp.getAttributeValue(null,attrib_town2));

        node.fileName = xpp.getAttributeValue(null,attrib_back_file);

        node.dungeon1.addNode(node);
        node.dungeon2.addNode(node);

        node.degree = (float)Math.toDegrees(Math.atan2(node.dungeon2.getY() - node.dungeon1.getY(),node.dungeon2.getX() - node.dungeon1.getX()));
        node.length = (float)Math.hypot(node.dungeon2.getX() - node.dungeon1.getX(),node.dungeon2.getY() - node.dungeon1.getY());

        return node;
    }
}
