package jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State;

import android.util.Log;

import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.BattleStatePattern;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.State;
import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.Game.UIs.Transition.StackLoadingTransition;

/**
 * Created by wark on 2016/10/16.
 */

public class BattleResultState extends State {
    public BattleResultState(BattleStatePattern battleState) {
        super(battleState);
    }

    @Override
    public void actionProcess() {
        //Log.d("BattleResultState","BattleClear");
        StackLoadingTransition slt = StackLoadingTransition.getInstance();
        slt.initStackLoadingTransition();
        GameManager.transition = slt;
        GameManager.isTransition = true;
    }

    @Override
    public void init() {

    }

    @Override
    public void draw(float offsetX, float offsetY) {

    }
}
