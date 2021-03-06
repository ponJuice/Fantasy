package jp.ac.dendai.c.jtp.Game.BattleSystem.DrawMachine;

import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleManager;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.BattleStatePattern;

/**
 * Created by Goto on 2016/10/12.
 */

public interface DrawMachine {
    public void draw(BattleManager manager,float offsetX,float offsetY);
    public void draw(BattleStatePattern bsm);
}
