package jp.ac.dendai.c.jtp.Game.ADVSystem.Event;

import android.util.Log;

import jp.ac.dendai.c.jtp.Game.ADVSystem.ADVManager;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.ADVComponent;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Background;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Condition.Conditions;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Flag.FlagManager;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.AssetManager;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.UIs.UI.TextBox.TalkBox;
import jp.ac.dendai.c.jtp.TouchUtil.Input;
import jp.ac.dendai.c.jtp.TouchUtil.Touch;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by テツヤ on 2016/10/06.
 */
public class Event {
    private final static int local_flag_max = 32;
    protected Conditions conditions;
    protected ADVComponent component;
    protected ADVComponent drawTarget;
    protected AssetManager assetManager;
    protected TalkBox talkBox;
    protected int[] local_flags = new int[local_flag_max];
    protected Touch touch;
    protected Background back;
    public TalkBox getTalkBox(){
        return talkBox;
    }
    public void preparation(){
        talkBox = new TalkBox(Constant.talk_textbox_x,Constant.talk_textbox_y, GLES20Util.getWidth_gl(),0.3f);
        component.init(this);
    }
    public void draw(float offset_x,float offset_y){
        if(back != null)
            back.draw(offset_x,offset_y);
        drawTarget.draw(offset_x,offset_y);
    }
    public boolean proc(ADVManager manager){
        if(component == null){
            Log.d("Event","event end");
            return false;
        }else {
            component = component.proc(this);
            return true;
        }
    }
    public void touch(){
        /*if(touch != null && touch.getTouchID() == -1)
            touch = null;*/
        for(int n = 0;n < Input.getTouchArray().length;n++){
            if(Input.getTouchArray()[n].getTouchID() != -1){
                touch = Input.getTouchArray()[n];
                break;
            }
        }
    }
    public void setBackGround(Background b){
        back = b;
    }
    public Touch getTouch(){
        return touch;
    }
    public boolean evaluation(){
        return conditions.evaluation(this);
    }
    public int getFlagValue(String type,int value){
        if(type.equals("local")){
            return local_flags[value];
        }
        else{
            return FlagManager.getFlagValue(type,value);
        }
    }
    public void setFlagValue(String type,int index,int value){
        if(type.equals("local"))
            local_flags[index] = value;
        else{
            FlagManager.setFlagValue(type,index,value);
        }
    }
    public void outputDebugInfo(){
        StringBuilder sb = new StringBuilder();
        sb.append("Local Flag = ");
        for(int n = 0;n < local_flags.length;n++){
            sb.append("["+n+" : "+local_flags[n]+"]");
        }
        Log.d("Event Info",sb.toString());
        FlagManager.outputDebugInfo();
    }
    public void setDrawTarget(ADVComponent comp){
        drawTarget = comp;
    }
    public ADVComponent getDrawTarget(){
        return drawTarget;
    }
    public void setComponent(ADVComponent component){
        this.component = component;
    }
    public void setAssetManager(AssetManager am){
        assetManager = am;
    }
}
