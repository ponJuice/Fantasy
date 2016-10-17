package jp.ac.dendai.c.jtp.Game.BattleSystem;

import android.util.Log;

import jp.ac.dendai.c.jtp.Game.BattleSystem.Enum.ActionType;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Skill.Skill;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.DataBase;
import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.NumberText;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.StaticText;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Util.Time;

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
    protected static Skill normalSkill;
    protected BattleManager bm;
    protected NumberText damageText;
    protected int damage;
    protected boolean endEffect = false;
    protected float timeBuffer = 0;

    public BattleAction(BattleManager bm){
        if(normalSkill == null){
            normalSkill = GameManager.getDataBase().getSkill(Constant.normal_attack_name);
        }
        damageText = new NumberText(Constant.fontName);
        damageText.useAspect(true);
        damageText.setHorizontal(UIAlign.Align.CENTOR);
        damageText.setVertical(UIAlign.Align.CENTOR);
        damageText.setHeight(damage_text_height);
        damageText.setNumber(0);

        this.bm = bm;

        attackText = new StaticText("の攻撃！！",null);
    }

    public void effectReset(){
        skill.effectInit();
    }

    public boolean drawEffect(float ox,float oy,float sx,float sy,float deg){
        boolean flag = false;
        if(type == ActionType.Skill){
            if(!endEffect)
                endEffect = skill.draw(target.getX() + ox,target.getY() + oy,target.getSX()*sx,target.getSY()*sy,deg);
            else {
                if(target.damageAnimation(timeBuffer,this)){
                    timeBuffer = 0;
                    endEffect = false;
                    flag = true;
                }else {
                    timeBuffer += Time.getDeltaTime();
                }
                damageText.draw(ox, oy);
            }
        }else if(type == ActionType.Normal){
            if(!endEffect)
                endEffect = skill.draw(target.getX() + ox,target.getY() + oy,target.getSX()*sx,target.getSY()*sy,deg);
            else {
                if(target.damageAnimation(timeBuffer,this)){
                    timeBuffer = 0;
                    endEffect = false;
                    flag = true;
                }else {
                    timeBuffer += Time.getDeltaTime();
                    //Log.d("BattleAction","TimeBuffer : "+timeBuffer);
                }
                damageText.draw(ox, oy);
            }
        }else{
            return true;//アイテムの表示
        }
        return flag;
    }

    public int getDamage(){
        return damage;
    }

    public void calcDamage(){
        //ダメージ量の計算

        damage = (int)target.damageValue(skill.calcDamage(owner.getAtk()));
        damageText.setNumber(damage);
        damageText.setX(target.getX());
        damageText.setY(target.getY());
        damageText.setHeight(damage_text_height*target.getSY());
    }

    public void influenceDamage(){
        target.influenceDamage(damage);
    }

    public boolean isTargetDead(){
        return target.isDead(damage);
    }

}
