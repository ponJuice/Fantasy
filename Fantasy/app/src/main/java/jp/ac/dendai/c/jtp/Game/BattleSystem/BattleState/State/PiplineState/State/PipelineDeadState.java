package jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PiplineState.State;

import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleAction;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PiplineState.PipelineState;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Util.Time;

/**
 * Created by Goto on 2016/10/14.
 */

public class PipelineDeadState extends APipelineState {
    protected PipelineState.STATE state;
    protected BattleAction ba;
    protected float timeBuffer;
    public PipelineDeadState(PipelineStatePattern psp) {
        super(psp);
    }

    @Override
    public void proc() {
        if(state == PipelineState.STATE.end){
            psp.changeState(psp.getPipelineEndState());
        }
    }

    @Override
    public void draw(float offsetX, float offsetY) {
        if(state != PipelineState.STATE.effect)
            return;
        if(ba.target.isDead()){
            //死亡エフェクトの表示
            if(timeBuffer >= Constant.dead_effect_time){
                state = PipelineState.STATE.end;
            }
            timeBuffer += Time.getDeltaTime();
        }else{
            state = PipelineState.STATE.end;
        }
    }

    @Override
    public void init() {
        ba = psp.getPipelineState().getBattleState().getBattleManager().getBattleAction();
        state = PipelineState.STATE.effect;
    }
}
