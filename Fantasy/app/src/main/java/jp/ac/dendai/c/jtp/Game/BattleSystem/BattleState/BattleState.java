package jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState;

import jp.ac.dendai.c.jtp.Game.UIs.UI.Image.Image;

/**
 * Created by Goto on 2016/09/16.
 */
public class BattleState {
    protected enum TYPE{
        ENEMY,
        PLAYER
    }
    protected TYPE type;
    protected int hp,atk,def;
    protected Image image;
    public void draw(float offset_x,float offset_y){
        image.draw(offset_x,offset_y);
    }
}
