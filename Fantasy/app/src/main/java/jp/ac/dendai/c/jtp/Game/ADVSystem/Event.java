package jp.ac.dendai.c.jtp.Game.ADVSystem;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.ADVComponent;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Condition.Conditions;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Flag.FlagManager;
import jp.ac.dendai.c.jtp.TouchUtil.Input;
import jp.ac.dendai.c.jtp.TouchUtil.Touch;

/**
 * Created by テツヤ on 2016/10/06.
 */
public class Event {
    protected Conditions conditions;
    protected ADVComponent component;
    protected ADVComponent drawTarget;
    protected int[] local_flags;
    protected Touch touch;
    public void draw(){
        drawTarget.draw();
    }
    public void proc(ADVManager manager){
        component.proc(this);
    }
    public void touch(){
        if(touch.getTouchID() == -1)
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
}
