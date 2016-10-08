package jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Branch.Select;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.ADVComponent;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.Parseable;

/**
 * Created by テツヤ on 2016/10/06.
 */
public abstract class Select implements Parseable{
    protected ADVComponent next;
    public ADVComponent getNext(){
        return next;
    }
}
