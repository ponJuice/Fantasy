package jp.ac.dendai.c.jtp.Game.ADVSystem.Event;

import jp.ac.dendai.c.jtp.Game.ADVSystem.ADVManager;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.ADVComponent;
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
    protected Conditions conditions;
    protected ADVComponent component;
    protected ADVComponent drawTarget;
    protected AssetManager assetManager;
    protected TalkBox talkBox;
    protected int[] local_flags;
    protected Touch touch;
    public TalkBox getTalkBox(){
        return talkBox;
    }
    public void preparation(){
        talkBox = new TalkBox(Constant.talk_textbox_x,Constant.talk_textbox_y, GLES20Util.getWidth_gl(),0.3f);
        component.init(this);
    }
    public void draw(){
        drawTarget.draw();
    }
    public void proc(ADVManager manager){
        ADVComponent temp = component.proc(this);
        if(temp != component){
            //シーンが進んだ
            temp.init(this);
        }
        component = temp;
    }
    public void touch(){
        if(touch != null && touch.getTouchID() == -1)
            touch = null;
        for(int n = 0;n < Input.getTouchArray().length;n++){
            if(Input.getTouchArray()[n].getTouchID() != -1){
                touch = Input.getTouchArray()[n];
                break;
            }
        }
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
