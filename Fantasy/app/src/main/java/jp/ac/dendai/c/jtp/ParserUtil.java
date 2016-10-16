package jp.ac.dendai.c.jtp;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;

/**
 * Created by テツヤ on 2016/10/16.
 */

public class ParserUtil {
    public static int convertInt(XmlPullParser xpp, String attrib_name){
        int i = 0;
        try{
            i = Integer.parseInt(xpp.getAttributeValue(null,attrib_name));
        }catch(NumberFormatException e){
            Log.e("convert integer",attrib_name + " : " + xpp.getPositionDescription());
            e.printStackTrace();
        }
        return i;
    }
}
