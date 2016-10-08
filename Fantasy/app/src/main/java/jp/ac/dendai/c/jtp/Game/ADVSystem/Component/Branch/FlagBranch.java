package jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Branch;

import java.util.List;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.ADVComponent;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Branch.Select.FlagSelect;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Event.Event;

/**
 * Created by テツヤ on 2016/10/07.
 */
public class FlagBranch extends Branch{
    protected List<FlagSelect> selects;
    protected ADVComponent component;
    @Override
    public void draw() {
        component.draw();
    }

    @Override
    public ADVComponent proc(Event event) {
        init(event);
        if(component == null){
            return next;
        }
        component = component.proc(event);
        if(component == null){
            return next;
        }
        return this;
    }

    @Override
    public void init(Event event) {
        if(isInit)
            return;
        event.setDrawTarget(this);
        for (int n = 0; n < selects.size(); n++) {
            if (selects.get(n).evaluation(event)) {
                component = selects.get(n).getNext();
                return;
            }
        }
        if (existDefault) {
            //デフォルトが存在する場合はリストの末尾
            component = selects.get(selects.size() - 1).getNext();
        }

        isInit = true;
    }
}
