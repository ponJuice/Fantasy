package jp.ac.dendai.c.jtp.Game.BattleSystem.Player;

import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.BattleState;

/**
 * Created by Goto on 2016/09/16.
 */
public class Player extends BattleState {
    public Player(PlayerData pd){
        hp = pd.hp;
        atk = pd.atk;
        def = pd.def;
        image = pd.image;
    }
}
