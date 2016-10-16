package jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State;

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
        battleState.changeState(battleState.getPlayerState());
    }

    @Override
    public void init() {

    }

    @Override
    public void draw(float offsetX, float offsetY) {

    }
}
