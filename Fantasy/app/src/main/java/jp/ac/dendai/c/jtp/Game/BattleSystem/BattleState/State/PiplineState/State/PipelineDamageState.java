package jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PiplineState.State;

import jp.ac.dendai.c.jtp.Game.BattleSystem.Attackable;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleAction;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PiplineState.PipelineState;

/**
 * Created by Goto on 2016/10/14.
 */

public class PipelineDamageState extends APipelineState {
    protected PipelineState.STATE state;
    protected Attackable actor;
    protected BattleAction ba;
    public PipelineDamageState(PipelineStatePattern psp) {
        super(psp);
    }

    @Override
    public void proc() {
        if(state == PipelineState.STATE.end){
            psp.changeState(psp.getPipelineDeadState());
        }
        if(state != PipelineState.STATE.proc)
            return;
        actor.action(psp.getPipelineState().getBattleState().getBattleManager());
        //ダメージ量の計算
        ba = psp.getPipelineState().getBattleState().getBattleManager().getBattleAction();
        ba.calcDamage();

        state = PipelineState.STATE.effect;
    }

    @Override
    public void draw(float offsetX, float offsetY) {
        if(state != PipelineState.STATE.effect)
            return;
        if(ba.drawEffect(offsetX,offsetX,1,1,0)){
            //終了
            state = PipelineState.STATE.end;
        }
    }

    @Override
    public void init() {
        state = PipelineState.STATE.proc;
        actor = psp.getActor();
    }
}
