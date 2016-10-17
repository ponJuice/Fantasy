package jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PiplineState.State;

import android.util.Log;

import jp.ac.dendai.c.jtp.Game.BattleSystem.Attackable;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleAction;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PiplineState.PipelineState;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.TextBox.AttackOwnerTextBox;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Util.Time;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by Goto on 2016/10/14.
 */

public class PipelineDamageState extends APipelineState {
    protected float timeBuffer = 0;
    protected PipelineState.STATE state;
    protected Attackable actor;
    protected BattleAction ba;
    protected AttackOwnerTextBox aotb;
    protected boolean effectEnd = false;
    public PipelineDamageState(PipelineStatePattern psp) {
        super(psp);
        aotb = new AttackOwnerTextBox(Constant.getBitmap(Constant.BITMAP.system_message_box));
        //aotb.setOwner(GLES20Util.stringToBitmap("クシャルダオラ",Constant.fontName,25,255,255,255));
        aotb.setX(GLES20Util.getWidth_gl()/2f);
        aotb.setY(GLES20Util.getHeight_gl()-Constant.action_textbox_y_offset);
        aotb.setHeight(0.2f);
        aotb.setPadding(0.15f);
    }

    @Override
    public void proc() {
        if(state == PipelineState.STATE.end){
            ba.influenceDamage();
            psp.changeState(psp.getPipelineDeadState());
        }

        if(state != PipelineState.STATE.proc)
            return;

        actor.action(psp.getPipelineState().getBattleState().getBattleManager());

        //ダメージ量の計算
        ba = psp.getPipelineState().getBattleState().getBattleManager().getBattleAction();
        ba.calcDamage();

        ba.effectReset();

        state = PipelineState.STATE.effect;

        //表示
        aotb.setOwner(ba.owner.getNameImage());

        Log.d("DamageState",ba.owner.getName()+"の攻撃!!");
        if(ba.type == BattleAction.ActionType.Normal)
            Log.d("DamageState","通常攻撃!!");
        else
            Log.d("DamageState",ba.skill.getSkillName());
    }

    @Override
    public void draw(float offsetX, float offsetY) {
        if(state != PipelineState.STATE.effect)
            return;
        aotb.draw(offsetX,offsetY);
        if(!effectEnd) {
            effectEnd = ba.drawEffect(offsetX, offsetX, 1, 1, 0);
        }else {
            if(timeBuffer >= Constant.battle_state_interval) {
                //終了
                timeBuffer = 0;
                effectEnd = false;
                state = PipelineState.STATE.end;
                return;
            }
            timeBuffer += Time.getDeltaTime();
            //Log.d("PDS","timeBuffer : "+timeBuffer);
        }
    }

    @Override
    public void init() {
        state = PipelineState.STATE.proc;
        actor = psp.getActor();
    }
}
