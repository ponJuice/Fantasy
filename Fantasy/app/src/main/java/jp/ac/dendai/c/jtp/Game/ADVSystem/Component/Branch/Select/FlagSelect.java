package jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Branch.Select;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Condition.Conditions;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Event;

/**
 * Created by テツヤ on 2016/10/07.
 */
public class FlagSelect extends Select{
    protected Conditions conditions;
    public boolean evaluation(Event event){
        return conditions.evaluation(event);
    }
}
