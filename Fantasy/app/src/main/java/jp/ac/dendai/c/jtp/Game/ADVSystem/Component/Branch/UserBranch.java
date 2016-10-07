package jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Branch;


import java.util.List;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.ADVComponent;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Branch.Select.UserSelect;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Event.Event;

/**
 * Created by テツヤ on 2016/10/06.
 */
public class UserBranch extends Branch{
    protected List<UserSelect> selects;
    protected ADVComponent component;
    protected ADVComponent drawBack;

    @Override
    public void draw() {
        drawBack.draw();
        //分岐の表示
    }

    @Override
    public ADVComponent proc(Event event) {
        return next;
    }

    @Override
    public void init(Event event) {
        drawBack = event.getDrawTarget();
        event.setDrawTarget(this);
    }
}
