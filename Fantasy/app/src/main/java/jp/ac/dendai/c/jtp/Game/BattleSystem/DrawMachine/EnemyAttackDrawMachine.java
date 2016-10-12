package jp.ac.dendai.c.jtp.Game.BattleSystem.DrawMachine;

import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleAction;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleManager;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.BattleStateMachine;

/**
 * Created by Goto on 2016/10/12.
 */

public class EnemyAttackDrawMachine implements DrawMachine {
    @Override
    public void draw(BattleManager manager) {

    }

    @Override
    public void draw(BattleStateMachine bsm) {
        BattleAction ba = bsm.getBattleAction();
    }
}
