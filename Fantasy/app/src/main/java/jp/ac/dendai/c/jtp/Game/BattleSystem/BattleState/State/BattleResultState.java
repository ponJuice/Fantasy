package jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State;

import android.util.Log;

import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.BattleStatePattern;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.State;

/**
 * Created by wark on 2016/10/16.
 */

public class BattleResultState extends State {
    public BattleResultState(BattleStatePattern battleState) {
        super(battleState);
    }

    @Override
    public void actionProcess() {
        Log.d("BattleResultState","BattleClear");
    }

    @Override
    public void init() {

    }

    @Override
    public void draw(float offsetX, float offsetY) {

    }
}
