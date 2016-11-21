package jp.ac.dendai.c.jtp.Game.ADVSystem.Event;

import android.content.res.AssetManager;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Condition.Conditions;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.ADVEventParser;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.Game.UIs.Screen.DebugEventSelectScreen;
import jp.ac.dendai.c.jtp.Game.UIs.Screen.Screenable;
import jp.ac.dendai.c.jtp.Game.UIs.Screen.TalkScreen;
import jp.ac.dendai.c.jtp.Game.UIs.Transition.LoadingTransition.LoadingTransition;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.Button;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UI;
import jp.ac.dendai.c.jtp.openglesutil.Util.FileManager;

/**
 * Created by wark on 2016/10/24.
 */

public class EventManager {
    protected static String event_tag = "Event";
    protected static String event_attrib_event_name = "name";
    protected static String event_condition_tag = "EventCondition";
    protected static String child_event_condition_tag = "Conditions";
    protected ArrayList<EventPackage> events;
    public EventManager(){
        events = new ArrayList<>();
        AssetManager assetMgr = GameManager.act.getResources().getAssets();
        try {
            String files[] = assetMgr.list("Event");
            for(int i = 0; i < files.length; i++) {
                EventPackage ep = new EventPackage(files[i]);
                events.add(ep);
            }
        } catch (IOException e) {
        }
    }
    public String checkEvent(){
        for(int n = 0;n < events.size();n++){
            if(events.get(n).conditions.evaluation()){
                return events.get(n).getFileName();
            }
        }
        return null;
    }

    public void startEvent(Screenable screen,String fileName){
        GameManager.stack.push(screen);
        LoadingTransition lt = LoadingTransition.getInstance();
        lt.initTransition(TalkScreen.class);
        GameManager.args = new Object[1];
        GameManager.args[0] = fileName;
        GameManager.transition = lt;
        GameManager.isTransition = true;
    }

    protected class EventPackage{
        protected String eventName;
        protected String fileName;
        protected Conditions conditions;
        public EventPackage(String fileName){
            this.fileName = fileName;
            XmlPullParser xpp = null;
            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                xpp = factory.newPullParser();
                xpp.setInput(new StringReader(FileManager.readTextFile(Constant.EVENT_DIRECTORY + fileName)));
            }catch (Exception e){
                e.printStackTrace();
                ADVEventParser.debugOutputString("error end");
            }
            
            
            int eventType = XmlPullParser.END_DOCUMENT;
            try{
                eventType = xpp.getEventType();
            }catch (XmlPullParserException e){
                e.printStackTrace();
            }
            
            while(eventType != XmlPullParser.END_DOCUMENT){
                if(eventType == XmlPullParser.END_TAG){
                    if(xpp.getName().equals(event_condition_tag)) {
                        break;
                    }
                }
                
                if(eventType == XmlPullParser.START_TAG){
                    if(xpp.getName().equals(event_condition_tag)){
                        parserCondition(xpp,event_condition_tag);
                        return;
                    }else if(xpp.getName().equals(event_tag)){
                        eventName = xpp.getAttributeValue(null,event_attrib_event_name);
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
        protected void parserCondition(XmlPullParser xpp,String endTag){
            int eventType = XmlPullParser.END_DOCUMENT;
            try{
                eventType = xpp.getEventType();
            }catch (XmlPullParserException e){
                e.printStackTrace();
            }

            while(!(eventType == XmlPullParser.END_TAG && xpp.getName().equals(endTag))){
                if(eventType == XmlPullParser.START_TAG){
                    if(xpp.getName().equals(child_event_condition_tag)){
                        conditions = new Conditions();
                        conditions.parseCreate(null,xpp);
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
        public String getEventName(){
            return eventName;
        }
        public String getFileName(){
            return fileName;
        }
    }
}
