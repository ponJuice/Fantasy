package jp.ac.dendai.c.jtp.Game.BattleSystem;

import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.BattleState;

/**
 * Created by Goto on 2016/10/12.
 */

public abstract class Attackable implements Comparable<Attackable>,BattleState{
    protected int hp,atk,def,agl;
    public abstract boolean isDead();
    //public abstract void draw(float ox,float oy,float sx,float sy,float deg);
    public abstract float attackValue(BattleAction battleAction);
    public abstract float damageValue(BattleAction battleAction,float attack);
    public abstract boolean action(BattleAction battleAction,BattleManager battleManager);
    public abstract float getBaseHp();
    public abstract float getHp();
    public abstract float getBaseAtk();
    public abstract float getAtk();
    public abstract float getBaseDef();
    public abstract float getDef();
    public abstract float getBaseAgl();
    public abstract float getAgl();
    @Override
    public int compareTo(Attackable obj){
        if(obj.getAgl() < getAgl())
            return 1;
        else if(obj.getAgl() > getAgl())
            return -1;
        return 0;
    }
}
