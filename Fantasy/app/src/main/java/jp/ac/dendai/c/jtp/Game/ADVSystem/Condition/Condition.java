package jp.ac.dendai.c.jtp.Game.ADVSystem.Condition;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Enum.Mnemonic;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Event;

/**
 * Created by テツヤ on 2016/10/06.
 */
public class Condition implements ICondition {
    protected String type1;
    protected String type2;
    protected int value1;
    protected int value2;
    protected Mnemonic mnemonic;
    @Override
    public boolean evaluation(Event event) {
        return mnemonic.evaluation(event.getFlagValue(type1,value1)
                ,event.getFlagValue(type2,value2));
    }
}
