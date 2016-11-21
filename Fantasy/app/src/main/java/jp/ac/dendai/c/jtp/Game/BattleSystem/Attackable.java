package jp.ac.dendai.c.jtp.Game.BattleSystem;

import android.graphics.Bitmap;

import jp.ac.dendai.c.jtp.Game.BattleSystem.Skill.Skill;

/**
 * Created by Goto on 2016/10/12.
 */

public abstract class Attackable implements Comparable<Attackable>{
    public enum AttackerType{
        Enemy,
        Friend
    }
    protected int baseHp;
    protected int hp;
    protected int atk;
    protected int def;
    protected int agl;
    protected int baseMp;
    protected int mp;
    protected String name;
    protected float x,y,sx,sy;
    protected float r = 0.5f,g = 0.5f,b = 0.5f;
    protected Skill[] skills;   //一番最後が通常攻撃
    protected Bitmap name_bitmap;
    public abstract boolean isDead();
    //public abstract void draw(float ox,float oy,float sx,float sy,float deg);
    public abstract float damageValue(float attack);
    public abstract void action(BattleManager bm);
    public abstract String getName();
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
    public abstract float getSX();
    public abstract float getSY();
    public abstract AttackerType getAttackerType();
    public abstract Bitmap getImage();
    public abstract boolean isDead(int damage);
    public abstract void proc();
    public abstract void draw(float offsetX,float offsetY);
    public abstract void influenceDamage(float value);
    public abstract void influenceMp(float value);
    public abstract boolean mpDecreaseAnimation(float time,int value);
    public abstract boolean deadAnimation(float time);
    public abstract boolean damageAnimation(float time,BattleAction ba);
    public Bitmap getNameImage(){
        return name_bitmap;
    }
    public void setR(float value){
        r = value;
    }
    public void setG(float value){
        g = value;
    }
    public void setB(float value){
        b = value;
    }
    public float getR(){
        return r;
    }
    public float getG(){
        return g;
    }
    public float getB(){
        return b;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("type : " + getAttackerType() + "\n");
        sb.append("name : " + name + "\n");
        sb.append("hp / baseHp : " + getHp() + "/" + getBaseHp() + "\n");
        sb.append("atk / baseAtk : " + getAtk() + "/" + getBaseAtk() + "\n");
        sb.append("def / baseDef : " + getDef() + "/" + getBaseDef() + "\n");
        sb.append("agl / baseAgl : " + getAgl() + "/" + getBaseAgl() + "\n");
        sb.append("mp / baseMp : " + getMp() + "/" + getBaseMp() + "\n");

        return sb.toString();
    }

    @Override
    public int compareTo(Attackable obj){
        if(obj.getAgl() < getAgl())
            return 1;
        else if(obj.getAgl() > getAgl())
            return -1;
        return 0;
    }
}
