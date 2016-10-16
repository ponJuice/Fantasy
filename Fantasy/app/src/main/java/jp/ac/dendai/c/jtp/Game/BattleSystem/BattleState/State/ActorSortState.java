package jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State;

import android.util.Log;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import jp.ac.dendai.c.jtp.Game.BattleSystem.Attackable;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.BattleStatePattern;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Player.Player;

/**
 * Created by Goto on 2016/10/14.
 */

public class ActorSortState extends State {
    public ActorSortState(BattleStatePattern battleState) {
        super(battleState);
    }

    @Override
    public void actionProcess() {
        //キャラクターリストをAGLで昇順でソート
        //Collections.sort(battleState.getActorList(),Collections.reverseOrder());
        Arrays.sort(battleState.getActorList());
        /* ----- Debug ----- */
        for(int n = 0;n < battleState.getActorList().length;n++){
            Log.d("ActorSortState",battleState.getActorList()[n].toString());
        }

        battleState.changeState(battleState.getPlayerState());
    }

    @Override
    public void init() {
        Log.d("ActorSortState","init");
    }

    @Override
    public void draw(float offsetX, float offsetY) {

    }
}
