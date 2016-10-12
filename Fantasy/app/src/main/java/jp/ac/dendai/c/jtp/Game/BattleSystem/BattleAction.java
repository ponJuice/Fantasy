package jp.ac.dendai.c.jtp.Game.BattleSystem;

import jp.ac.dendai.c.jtp.Game.BattleSystem.Enum.ActionType;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Skill.Skill;

/**
 * Created by Goto on 2016/10/12.
 */

public class BattleAction {
    public enum ActionType{
        Skill,
        Normal
    }
    public Attackable owner;
    public Attackable target;
    public ActionType type;
    public Skill skill;
    public int id;
    protected int damage;
    protected boolean isEnd = false;

    public boolean isEnd(){
        return isEnd;
    }

    public void resetInfo(){
        isEnd = false;
    }
    public boolean drawEffect(float ox,float oy,float sx,float sy,float deg){
        if(type == ActionType.Skill){
            return skill.draw(ox,oy,sx,sy,deg);
        }else if(type == ActionType.Normal){
            return true;//通常攻撃の表示
        }else{
            return true;//アイテムの表示
        }
    }

}
