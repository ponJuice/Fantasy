package jp.ac.dendai.c.jtp.Game.ADVSystem.Component;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Enum.Trans_Mode;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Enum.Trans_Type;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Event;

/**
 * Created by テツヤ on 2016/10/06.
 */
public class Transition extends ADVComponent{
    protected int color;
    protected Trans_Mode mode;
    protected Trans_Type type;
    protected float time;
    protected float timeBuffer;
    protected ADVComponent drawBack;
    @Override
    public void draw() {
        drawBack.draw();
        //トランジョンの表示
    }

    @Override
    public ADVComponent proc(Event event) {
        if(time < timeBuffer)
            return next;
        else{

            return this;
        }
    }

    @Override
    public void init(Event event){
        drawBack = event.getDrawTarget();
        event.setDrawTarget(this);
    }
}
