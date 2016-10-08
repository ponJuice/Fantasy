package jp.ac.dendai.c.jtp.Game.ADVSystem.Texts;

import java.util.HashMap;

/**
 * Created by テツヤ on 2016/10/07.
 */

public class TextManager {
    public static HashMap<String,String> list = new HashMap<>();
    public static String getString(String key){
        if(list.containsKey(key))
            return list.get(key);
        return null;
    }
    public static void setString(String key,String text){
        list.put(key,text);
    }
}
