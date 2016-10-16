package jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PiplineState.State;

/**
 * Created by Goto on 2016/10/14.
 */

public class PipelineAnomalousState extends APipelineState {
    public PipelineAnomalousState(PipelineStatePattern psp) {
        super(psp);
    }

    @Override
    public void proc() {
        psp.changeState(psp.getPipelineTurnEndState());
    }

    @Override
    public void draw(float offsetX, float offsetY) {

    }

    @Override
    public void init() {

    }
}
