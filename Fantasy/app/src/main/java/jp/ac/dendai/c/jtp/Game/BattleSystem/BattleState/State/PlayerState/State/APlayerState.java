package jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PlayerState.State;

import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PiplineState.State.PipelineStatePattern;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Player.Player;

/**
 * Created by wark on 2016/10/17.
 */

public abstract class APlayerState {
    protected PlayerStatePattern psp;
    public APlayerState(PlayerStatePattern psp){
        this.psp = psp;
    }

    public abstract void proc();
    public abstract void draw(float offsetX,float offsetY);
    public abstract void init();
}
