package jp.ac.dendai.c.jtp.Game.Charactor;

import java.util.HashMap;

/**
 * Created by テツヤ on 2016/10/07.
 */

public class FaceManager {
    public static HashMap<String,Face> list;
    public static Face getFace(String name){
        if(list.containsKey(name))
            return list.get(name);
        return null;
    }
    public static void setFace(Face face){
        list.put(face.getName(),face);
    }
}
