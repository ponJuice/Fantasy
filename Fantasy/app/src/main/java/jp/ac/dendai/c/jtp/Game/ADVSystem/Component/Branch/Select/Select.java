package jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Branch.Select;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.ADVComponent;

/**
 * Created by テツヤ on 2016/10/06.
 */
public abstract class Select{
    protected ADVComponent next;
    public ADVComponent getNext(){
        return next;
    }
}
