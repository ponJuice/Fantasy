package jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PiplineState.State;

/**
 * Created by Goto on 2016/10/14.
 */

public abstract class APipelineState {
    protected PipelineStatePattern psp;
    public APipelineState(PipelineStatePattern psp){
        this.psp = psp;
    }

    public abstract void proc();
    public abstract void draw(float offsetX,float offsetY);
    public abstract void init();
}
