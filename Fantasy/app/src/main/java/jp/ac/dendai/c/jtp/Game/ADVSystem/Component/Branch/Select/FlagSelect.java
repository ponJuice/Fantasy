package jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Branch.Select;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Condition.Conditions;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Event.Event;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.ADVEventParser;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.AssetManager;

/**
 * Created by テツヤ on 2016/10/07.
 */
public class FlagSelect extends Select{
    public final static String tagName = "Select";
    public final static String scenes = "Scenes";

    protected Conditions conditions;

    public boolean evaluation(Event event){
        if(conditions == null)
            return false;
        return conditions.evaluation(event);
    }

    @Override
    public void parseCreate(AssetManager am, XmlPullParser xpp) {
        int eventType = XmlPullParser.END_DOCUMENT;
        try{
            eventType = xpp.getEventType();
        }catch (XmlPullParserException e){
            e.printStackTrace();
        }

        while(eventType != XmlPullParser.END_DOCUMENT){
            if(eventType == XmlPullParser.END_TAG && xpp.getName().equals(tagName)){
                break;
            }
            if(eventType == XmlPullParser.START_TAG){
                if(xpp.getName().equals(Conditions.tagName)){
                    Conditions c = new Conditions();
                    c.parseCreate(am,xpp);
                    conditions = c;
                }else if(xpp.getName().equals(scenes)){
                    next = ADVEventParser._parser(am,xpp,scenes);
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

    }

    @Override
    public String getTagName() {
        return null;
    }
}
