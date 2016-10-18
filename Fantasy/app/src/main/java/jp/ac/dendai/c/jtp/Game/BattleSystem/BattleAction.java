package jp.ac.dendai.c.jtp.Game.BattleSystem;

import android.graphics.Bitmap;
import android.util.Log;

import jp.ac.dendai.c.jtp.Game.BattleSystem.Enum.ActionType;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Skill.Skill;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.DataBase;
import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.Game.GameUI.AttackText;
import jp.ac.dendai.c.jtp.Game.GameUI.SkillText;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.NumberText;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.StaticText;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.TextBox.AttackOwnerTextBox;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Util.Time;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by Goto on 2016/10/12.
 */

public class BattleAction {
    public enum ActionType{
        Skill,
        Normal,
        Escape
    }
    public final static float damage_text_height = 0.1f;
    public Attackable owner;
    public Attackable target;
    public ActionType type;
    public Skill skill;
    public int id;
    protected static Skill normalSkill;
    protected BattleManager bm;
    protected NumberText damageText;
    protected int damage;
    protected int mp;
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

    }

    public void effectReset(){
        skill.effectInit();
    }

    public boolean drawEffect(float ox,float oy,float sx,float sy,float deg){
        boolean flag = false;
        if(type == ActionType.Skill){
            if(!endEffect) {
                endEffect = skill.draw(target.getX() + ox, target.getY() + oy, target.getSX() * sx, target.getSY() * sy, deg);
            }else {
                flag = target.damageAnimation(timeBuffer,this);
                flag = owner.mpDecreaseAnimation(timeBuffer,mp) && flag;
                if(flag){
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

        mp = skill.calcMpValue(owner);
    }

    public void setAttackTextBox(AttackOwnerTextBox aotb){
        if(type == ActionType.Normal){
            AttackText at = aotb.getAttackText();
            at.setOwner(owner.getNameImage());
            aotb.setPatchingText(at);
        }else if(type == ActionType.Skill){
            SkillText st = aotb.getSkillText();
            st.setOwner(owner.getNameImage());
            st.setSkill(skill.getNameImage());
            aotb.setPatchingText(st);
        }
    }

    public void influenceDamage(){
        target.influenceDamage(damage);
        owner.influenceMp(mp);
    }



    public boolean isTargetDead(){
        return target.isDead(damage);
    }

}
