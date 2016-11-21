package jp.ac.dendai.c.jtp.Game.BattleSystem.Anomalous;

import jp.ac.dendai.c.jtp.Game.BattleSystem.Player.Player;

/**
 * Created by wark on 2016/10/22.
 */

public class Poison extends Anomalous {
    public Poison(int turn){
        super(turn);
    }
    @Override
    public void effect(Player player) {

    }

    @Override
    public boolean drawEffect(float offsetX, float offsetY) {
        return false;
    }
}
