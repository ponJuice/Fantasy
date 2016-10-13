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

    public void resetInfo(boolean isEnd){
        this.isEnd = isEnd;
    }
    public boolean drawEffect(float ox,float oy,float sx,float sy,float deg){
        if(type == ActionType.Skill){
            return skill.draw(ox,oy,sx,sy,deg);
        }else if(type == ActionType.Normal){
            return skill.draw(ox,oy,sx,sy,deg);
        }else{
            return true;//アイテムの表示
        }
    }

    public int getDamage(){
        return damage;
    }

    public void calcDamage(){
        //ダメージ量の計算
        damage = (int)target.damageValue(owner.getAtk());
    }

    public void influenceDamage(){
        if(!isEnd)
            return;
        target.influenceDamage(damage);
    }

    public boolean isTargetDead(){
        return target.isDead(damage);
    }

}
