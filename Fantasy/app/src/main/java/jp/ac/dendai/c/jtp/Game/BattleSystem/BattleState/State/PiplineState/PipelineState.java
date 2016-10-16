package jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PiplineState;

import jp.ac.dendai.c.jtp.Game.BattleSystem.Attackable;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleAction;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.BattleStatePattern;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PiplineState.State.PipelineStatePattern;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.State;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.TextBox.TextBox;

/**
 * Created by Goto on 2016/10/14.
 */
//行動順にエフェクトを表示していく
public class PipelineState extends State {
    public enum STATE{
        proc,
        effect,
        end,
    }
    protected STATE state;
    protected int index = 0;
    protected Attackable actor = null;
    protected BattleAction battleAction = null;
    protected PipelineStatePattern psp;
    protected TextBox textBox;

    public PipelineState(BattleStatePattern battleState) {
        super(battleState);
        psp = new PipelineStatePattern(this);
    }

    @Override
    public void actionProcess() {
        if(psp.proc()){
            index--;
            if(index < 0){
                //一巡したらプレイヤーの行動選択へ戻る
                battleState.changeState(battleState.getPlayerState());
                return;
            }
            actor = battleState.getActorList()[index];
            psp.init();
            //psp.proc();
        }
    }

    public Attackable getActor(){
        return actor;
    }

    @Override
    public void init() {
        index = battleState.getActorList().length-1;
        actor = battleState.getActorList()[index];
        battleAction = battleState.getBattleManager().getBattleAction();
        psp.init();
    }

    @Override
    public void draw(float offsetX, float offsetY) {
        psp.draw(offsetX,offsetY);
    }
}
