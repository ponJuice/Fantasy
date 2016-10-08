package jp.ac.dendai.c.jtp.Game.ADVSystem.Parser;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.ADVComponent;

/**
 * Created by テツヤ on 2016/10/08.
 */

class ADVComponentPack{
    private ADVComponent first;
    private ADVComponent end;
    public void setComponent(ADVComponent comp){
        if(first == null) {
            first = comp;
            end = comp;
            return;
        }
        end.setNext(comp);
        end = comp;
    }
    public ADVComponent getFirst(){
        return first;
    }
}
