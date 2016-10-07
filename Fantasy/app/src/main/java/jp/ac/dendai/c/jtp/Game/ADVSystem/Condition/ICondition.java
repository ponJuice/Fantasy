package jp.ac.dendai.c.jtp.Game.ADVSystem.Condition;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Event.Event;

/**
 * Created by テツヤ on 2016/10/06.
 */
public interface ICondition {
    public boolean evaluation(Event event);
}
