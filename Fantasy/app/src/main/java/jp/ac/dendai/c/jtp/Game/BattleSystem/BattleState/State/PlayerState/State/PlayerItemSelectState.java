package jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PlayerState.State;

import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PiplineState.State.PipelineStatePattern;
import jp.ac.dendai.c.jtp.Game.UIs.UI.List.PlayerActionList;

/**
 * Created by wark on 2016/10/17.
 */

public class PlayerItemSelectState extends APlayerState {
    public PlayerItemSelectState(PlayerStatePattern psp) {
        super(psp);
    }

    @Override
    public void clean() {

    }

    @Override
    public void proc() {

    }

    @Override
    public void draw(float offsetX, float offsetY) {

    }

    @Override
    public void init(PlayerActionList list) {
        this.list = list;
    }

    @Override
    public PlayerActionList getList() {
        return list;
    }
}
