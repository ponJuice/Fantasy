package jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Branch;


import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.ADVComponent;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Branch.Select.Select;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Branch.Select.UserSelect;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Event.Event;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.AssetManager;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.Parseable;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.Button;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.ButtonListener;
import jp.ac.dendai.c.jtp.TouchUtil.Touch;

/**
 * Created by テツヤ on 2016/10/06.
 */
public class UserBranch extends Branch implements Parseable{
    /*----------- Parseable関連 -------------*/
    public final static String tagName = "UserBranch";

    /*----------- UserBranch関連 ------------*/
    protected List<UserSelect> selects;
    protected ADVComponent target;
    protected ADVComponent drawBack;

    @Override
    public void draw(float offset_x,float offset_y) {
        drawBack.draw(offset_x,offset_y);
        //分岐の表示
        for(int n = 0;n < selects.size();n++){
            selects.get(n).draw(offset_x,offset_y);
        }
    }

    @Override
    public ADVComponent proc(Event event) {
        init(event);
        if(target != null) {
            target.init(event);
            target = target.proc(event);
            if(target == null)
                return next;
            return this;
        }
        for(int n = 0;n < selects.size();n++){
            selects.get(n).proc();
            if(event.getTouch() != null)
                selects.get(n).touch(event.getTouch());

        }
        return this;
        //return next;
    }

    @Override
    public void init(Event event) {
        if(!isInit) {
            drawBack = event.getDrawTarget();
            event.setDrawTarget(this);
            isInit = true;
        }
    }

    @Override
    public void parseCreate(AssetManager am, XmlPullParser xpp) {
        Log.d(tagName+" Parse","");
        selects = new ArrayList<>();
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
                if(xpp.getName().equals(UserSelect.tagName)){
                    //Selectタグ
                    UserSelect s = new UserSelect();
                    s.parseCreate(am,xpp);
                    s.setButtonListener(new SelectButtonListener(s));
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
        for(int n = 0;n < selects.size();n++){
            selects.get(n).init(n,selects.size());
        }
    }

    @Override
    public String getTagName() {
        return tagName;
    }
    private class SelectButtonListener implements ButtonListener{
        private UserSelect select;
        public SelectButtonListener(UserSelect s){
            select = s;
        }
        @Override
        public void touchDown(Button button) {

        }

        @Override
        public void touchHover(Button button) {

        }

        @Override
        public void touchUp(Button button) {
            target = select.getNext();
        }
    }
}
