package jp.ac.dendai.c.jtp.Game.ADVSystem.Condition;

import java.util.List;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Enum.Condition_Mode;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Event;

/**
 * Created by テツヤ on 2016/10/06.
 */
public class Conditions implements ICondition{
    protected List<ICondition> conditions;
    protected Condition_Mode mode;
    @Override
    public boolean evaluation(Event event) {
        boolean temp = conditions.get(0).evaluation(event);
        for(int n = 1;n < conditions.size();n++){
            temp = mode.evaluation(temp,conditions.get(n).evaluation(event));
        }
        return temp;
    }
}
