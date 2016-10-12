package jp.ac.dendai.c.jtp.Game.BattleSystem.DrawMachine;

import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleManager;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.BattleStateMachine;

/**
 * Created by Goto on 2016/10/12.
 */

public interface DrawMachine {
    public void draw(BattleManager manager);
    public void draw(BattleStateMachine bsm);
}
