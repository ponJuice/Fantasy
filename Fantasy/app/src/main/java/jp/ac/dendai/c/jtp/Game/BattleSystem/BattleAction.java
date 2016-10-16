package jp.ac.dendai.c.jtp.Game.BattleSystem;

import jp.ac.dendai.c.jtp.Game.BattleSystem.Enum.ActionType;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Skill.Skill;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.NumberText;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.StaticText;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UIAlign;

/**
 * Created by Goto on 2016/10/12.
 */

public class BattleAction {
    public enum ActionType{
        Skill,
        Normal
    }
    public final static float damage_text_height = 0.1f;
    public StaticText attackText;
    public Attackable owner;
    public Attackable target;
    public ActionType type;
    public Skill skill;
    public int id;
    protected NumberText damageText;
    protected int damage;
    protected boolean isEnd = false;

    public BattleAction(){
        damageText = new NumberText(Constant.fontName);
        damageText.useAspect(true);
        damageText.setHorizontal(UIAlign.Align.CENTOR);
        damageText.setVertical(UIAlign.Align.CENTOR);
        damageText.setHeight(damage_text_height);
        damageText.setNumber(0);

        attackText = new StaticText("の攻撃！！",null);
    }

    public boolean isEnd(){
        return isEnd;
    }

    public void resetInfo(boolean isEnd){
        this.isEnd = isEnd;
    }

    public void effectReset(){
        skill.effectInit();
    }

    public boolean drawEffect(float ox,float oy,float sx,float sy,float deg){
        boolean flag;
        if(type == ActionType.Skill){
            flag = skill.draw(target.getX() + ox,target.getY() + oy,target.getSX()*sx,target.getSY()*sy,deg);
            damageText.draw(ox,oy);
            return flag;
        }else if(type == ActionType.Normal){
            flag = skill.draw(target.getX() + ox,target.getY() + oy,target.getSX()*sx,target.getSY()*sy,deg);
            damageText.draw(ox,oy);
            return flag;
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
        damageText.setNumber(damage);
        damageText.setX(target.getX());
        damageText.setY(target.getY());
        damageText.setHeight(damage_text_height*target.getSY());
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
