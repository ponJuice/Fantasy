package jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Branch;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.ADVComponent;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Branch.Select.FlagSelect;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Branch.Select.UserSelect;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Flag;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Event.Event;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.AssetManager;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.Parseable;

/**
 * Created by テツヤ on 2016/10/07.
 */
public class FlagBranch extends Branch implements Parseable{
    public final static String tagName="FlagBranch";
    protected final static String attrib_default = "default";

    protected List<FlagSelect> selects;
    protected ADVComponent component;
    @Override
    public void draw(float offset_x,float offset_y) {
        component.draw(offset_x,offset_y);
    }

    @Override
    public ADVComponent proc(Event event) {
        init(event);
        if(component == null)
            return next;
        component = component.proc(event);
        return this;
    }

    @Override
    public void init(Event event) {
        if(isInit)
            return;
        for (int n = 0; n < selects.size(); n++) {
            if (selects.get(n).evaluation(event)) {
                component = selects.get(n).getNext();
                break;
            }
        }
        if (component == null && existDefault) {
            //デフォルトが存在する場合はリストの末尾
            component = selects.get(selects.size() - 1).getNext();
        }
        isInit = true;
    }

    @Override
    public void parseCreate(AssetManager am, XmlPullParser xpp) {
        Log.d(tagName+" Parse","");
        selects = new ArrayList<>();
        existDefault = Boolean.parseBoolean(xpp.getAttributeValue(null,attrib_default));
        int eventType = XmlPullParser.END_DOCUMENT;
        try{
            eventType = xpp.getEventType();
        } catch (XmlPullParserException e){
            e.printStackTrace();
        }
        while(eventType != XmlPullParser.END_DOCUMENT){
            if(eventType == XmlPullParser.END_TAG && xpp.getName().equals(tagName)){
                break;
            }
            if(eventType == XmlPullParser.START_TAG){
                if(xpp.getName().equals(FlagSelect.tagName)){
                    //Selectタグ
                    FlagSelect s = new FlagSelect();
                    s.parseCreate(am,xpp);
                    selects.add(s);
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
