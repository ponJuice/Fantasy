package jp.ac.dendai.c.jtp.Game.BattleSystem;

/**
 * Created by wark on 2016/10/22.
 */

public class Enchant {
    protected float par = 1;
    protected int turn = 0;
    public void setTurn(int t){
        turn = t;
    }
    public void setPar(float par){
        this.par = par;
    }
    public float getValue(float value){
        if(turn <= 0)
            return value;
        return value * (par / 100);
    }
    public void declimentTurn(){
        if(turn > 0)
            turn--;
        else
            par = 1;
    }
}
