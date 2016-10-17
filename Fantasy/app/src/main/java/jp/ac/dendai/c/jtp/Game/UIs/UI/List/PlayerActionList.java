package jp.ac.dendai.c.jtp.Game.UIs.UI.List;

import jp.ac.dendai.c.jtp.Game.UIs.UI.Util.Time;

/**
 * Created by Goto on 2016/10/17.
 */

public class PlayerActionList extends List{
    protected enum ListState{
        add,
        remove,
        non
    }
    protected ListState state = ListState.non;
    protected final static float animationTime = 0.1f;
    protected float timeBuffer = 0;
    //protected boolean touchable = true;
    PlayerActionList next;
    float animationOffset = 0;

    public PlayerActionList(float x, float y, float width, float height) {
        super(x, y, width, height);
    }
    public void setNextList(PlayerActionList next){
        PlayerActionList temp = this;
        while(temp.next != null){
            temp = next;
        }
        temp.next = next;
        //backList.next = this;
        setTouchable(false);
        state = ListState.add;
        animationOffset = width;
    }

    @Override
    public void proc(){
        if(state == ListState.add) {
            if (timeBuffer >= animationTime) {
                animationOffset = width;
                timeBuffer = 0;
                state = ListState.non;
            } else {
                animationOffset = width * (1f - timeBuffer / animationTime);
                timeBuffer += Time.getDeltaTime();
            }
        }else if(state == ListState.remove){
                if(timeBuffer >= animationTime){
                    animationOffset = 0;
                    timeBuffer = 0;
                    state = ListState.non;
                }else{
                    animationOffset = width * (timeBuffer / animationTime);
                    timeBuffer += Time.getDeltaTime();
                }
        }else{
            super.proc();
        }
    }

    public void stateBack(){
        state = ListState.remove;
    }
    public void clear(){
        next.clear();
        next = null;
    }
    @Override
    public void draw(float offsetX,float offsetY){
        if(next != null)
            next.draw(offsetX,offsetY);
        super.draw(offsetX-animationOffset,offsetY);
    }
    @Override
    public String toString(){
        String str = "";
        if(next != null)
            str += next.toString();
        return str + " ["+super.toString()+"] ";
    }

}
