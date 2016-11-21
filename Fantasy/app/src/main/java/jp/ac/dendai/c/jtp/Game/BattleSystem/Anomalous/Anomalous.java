package jp.ac.dendai.c.jtp.Game.BattleSystem.Anomalous;

import jp.ac.dendai.c.jtp.Game.BattleSystem.Player.Player;

/**
 * Created by wark on 2016/10/22.
 */

public abstract class Anomalous {
    protected int turn;
    public Anomalous(int turn){
        this.turn = turn;
    }
    public abstract void effect(Player player);
    public abstract boolean drawEffect(float offsetX,float offsetY);
}
