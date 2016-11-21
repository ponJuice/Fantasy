package jp.ac.dendai.c.jtp.Game.MapSystem.Dungeon;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import jp.ac.dendai.c.jtp.Game.DataBase;

/**
 * Created by Goto on 2016/10/24.
 */

public class Dungeon {
    public final static String tagName = "Dungeon";
    protected final static String attrib_name = "name";
    protected final static String attrib_id = "id";
    protected final static String attrib_back = "image";
    protected String name;
    protected String background;
    protected String id;
    protected ArrayList<DungeonPoint> points;
    protected DungeonPoint startPoint;
    protected HashMap<String,DungeonPoint> hashPoints;
    protected ArrayList<DungeonNode> nodes;
    public ArrayList<DungeonPoint> getPoints(){
        return points;
    }
    public ArrayList<DungeonNode> getNodes(){
        return nodes;
    }
    public String getName(){
        return name;
    }
    public DungeonPoint getStartPoint(){
        return startPoint;
    }
    public String getBackground(){
        return background;
    }
    public static Dungeon parseCreate(XmlPullParser xpp, DataBase db){
        Dungeon dungeon = new Dungeon();
        dungeon.points = new ArrayList<>();
        dungeon.hashPoints = new HashMap<>();
        dungeon.nodes = new ArrayList<>();
        dungeon.name = xpp.getAttributeValue(null,attrib_name);
        dungeon.id = xpp.getAttributeValue(null,attrib_id);
        dungeon.background = xpp.getAttributeValue(null,attrib_back);
        int eventType = XmlPullParser.END_DOCUMENT;
        try{
            eventType = xpp.getEventType();
        }catch (XmlPullParserException e){
            e.printStackTrace();
        }
        while(!(eventType == XmlPullParser.END_TAG && xpp.getName().equals(tagName))){
            if(eventType == XmlPullParser.START_TAG){
                if(xpp.getName().equals(DungeonPoint.tagName)){
                    DungeonPoint point = DungeonPoint.parseCreate(xpp,db);
                    dungeon.points.add(point);
                    dungeon.hashPoints.put(point.getId(),point);
                    if(point.isStart()){
                        dungeon.startPoint = point;
                    }
                }else if(xpp.getName().equals(DungeonNode.tagName)){
                    DungeonNode dn = DungeonNode.parseCreate(xpp,db,dungeon.hashPoints);
                    dungeon.nodes.add(dn);
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
        return dungeon;
    }
}
