package jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State;

import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.BattleStateMachine;

/**
 * Created by Goto on 2016/10/12.
 */

public interface BattleState {
    public void proc(BattleStateMachine bsm);
    public void draw(float offset_x,float offset_y);
}