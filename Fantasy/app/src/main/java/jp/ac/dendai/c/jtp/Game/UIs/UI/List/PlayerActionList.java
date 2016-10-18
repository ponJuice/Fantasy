package jp.ac.dendai.c.jtp.Game.UIs.UI.List;

import jp.ac.dendai.c.jtp.Game.UIs.UI.Util.Time;
import jp.ac.dendai.c.jtp.TouchUtil.Touch;

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
    protected final float animationTime = 0.1f;
    protected float timeBuffer = 0;
    protected int childCount = 0;
    //protected boolean touchable = true;
    PlayerActionList next;
    float animationOffset = 0;

    public PlayerActionList(float x, float y, float width, float height) {
        super(x, y, width, height);
    }
    public void addListEnd(PlayerActionList next){
        if(this.next == null) {
            this.next = next;
            this.next.clear();
        }else{
            this.next.addListEnd(next);
        }
        childCount++;
        setTouchable(false);
        state = ListState.add;
        //animationOffset = width*childCount;
    }
    public void removeListEnd(){
        if(this.next.next == null){
            this.next = null;
            setTouchable(true);
        }else{
            removeListEnd();
        }
        childCount--;
        state = ListState.remove;
    }

    @Override
    public void setDrawable(boolean flag){
        super.setDrawable(flag);
        if(next != null)
            next.setDrawable(flag);
    }

    @Override
    public boolean touch(Touch touch){
        if(state != ListState.non)
            return true;
        boolean flag = true;
        if(touchable)
            flag = flag && super.touch(touch);
        if(next != null)
            flag = flag && next.touch(touch);
        return flag;
    }

    @Override
    public void proc(){
        if(state == ListState.add) {
            if (timeBuffer >= animationTime) {
                animationOffset = width*childCount;
                timeBuffer = 0;
                state = ListState.non;
            } else {
                animationOffset = width * (timeBuffer / animationTime)*childCount;
                timeBuffer += Time.getDeltaTime();
            }
        }else if(state == ListState.remove){
                if(timeBuffer >= animationTime){
                    animationOffset = 0;
                    timeBuffer = 0;
                    state = ListState.non;
                }else{
                    animationOffset = width * (1f - timeBuffer / animationTime)*(childCount+1);
                    timeBuffer += Time.getDeltaTime();
                }
        }else{
            super.proc();
        }
        if(next != null)
            next.proc();
    }

    @Override
    public void init(){
        super.init();
        animationOffset = 0;
    }

    public void stateBack(){
        state = ListState.remove;
    }
    public void clear(){
        if(next != null)
            next.clear();
        next = null;
        childCount = 0;
        state = ListState.non;
        animationOffset = 0;
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
