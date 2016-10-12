package jp.ac.dendai.c.jtp.Game.BattleSystem;

import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.BattleState;

/**
 * Created by Goto on 2016/10/12.
 */

public abstract class Attackable implements Comparable<Attackable>{
    public enum AttackerType{
        Enemy,
        Friend
    }
    protected int hp;
    protected int atk;
    protected int def;
    protected int agl;
    protected int mp;
    public abstract boolean isDead();
    //public abstract void draw(float ox,float oy,float sx,float sy,float deg);
    public abstract float attackValue(BattleAction battleAction);
    public abstract float damageValue(BattleAction battleAction,float attack);
    public abstract void action(BattleManager bm);
    public abstract float getBaseHp();
    public abstract float getHp();
    public abstract float getBaseAtk();
    public abstract float getAtk();
    public abstract float getBaseDef();
    public abstract float getDef();
    public abstract float getBaseAgl();
    public abstract float getAgl();
    public abstract int getMp();
    public abstract int getBaseMp();
    public abstract float getX();
    public abstract float getY();
    public abstract AttackerType getAttackerType();

    @Override
    public int compareTo(Attackable obj){
        if(obj.getAgl() < getAgl())
            return 1;
        else if(obj.getAgl() > getAgl())
            return -1;
        return 0;
    }
}
