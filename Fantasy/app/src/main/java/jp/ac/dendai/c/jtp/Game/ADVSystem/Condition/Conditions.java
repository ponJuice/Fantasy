package jp.ac.dendai.c.jtp.Game.ADVSystem.Condition;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Enum.Condition_Mode;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Event.Event;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.AssetManager;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.Parseable;

/**
 * Created by テツヤ on 2016/10/06.
 */
public class Conditions implements ICondition,Parseable{
    public final static String tagName = "Conditions";
    public final static String attrib_mode = "mode";

    protected List<ICondition> conditions;
    protected Condition_Mode mode;
    @Override
    public boolean evaluation(Event event) {
        boolean temp = conditions.get(0).evaluation(event);
        for(int n = 1;n < conditions.size();n++){
            temp = mode.evaluation(temp,conditions.get(n).evaluation(event));
        }
        return temp;
    }

    @Override
    public boolean evaluation(){
        boolean temp = conditions.get(0).evaluation();
        for(int n = 1;n < conditions.size();n++){
            temp = mode.evaluation(temp,conditions.get(n).evaluation());
        }
        return temp;
    }

    @Override
    public void parseCreate(AssetManager am, XmlPullParser xpp) {
        conditions = new ArrayList<>();
        String c_mode = xpp.getAttributeValue(null,attrib_mode);
        mode = Condition_Mode.valueOf(c_mode);

        int eventType = XmlPullParser.END_DOCUMENT;
        try{
            eventType = xpp.next();
        }catch(XmlPullParserException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        while(eventType != XmlPullParser.END_DOCUMENT){
            if(eventType == XmlPullParser.END_TAG && xpp.getName().equals(tagName))
                break;
            if(eventType == XmlPullParser.START_TAG){
                if(xpp.getName().equals(Condition.tagName)){
                    Condition c = new Condition();
                    c.parseCreate(am,xpp);
                    conditions.add(c);
                }else if(xpp.getName().equals(Conditions.tagName)){
                    Conditions c = new Conditions();
                    c.parseCreate(am,xpp);
                    conditions.add(c);
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
        return tagName;
    }
}
