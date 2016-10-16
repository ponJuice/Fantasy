package jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PiplineState.State;

import jp.ac.dendai.c.jtp.Game.BattleSystem.Attackable;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleAction;
import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.Game.UIs.Screen.DebugEventSelectScreen;
import jp.ac.dendai.c.jtp.Game.UIs.Transition.LoadingTransition.LoadingTransition;

/**
 * Created by Goto on 2016/10/14.
 */

public class PipelineEndState extends APipelineState {
    public BattleAction ba;
    public PipelineEndState(PipelineStatePattern psp) {
        super(psp);
    }

    @Override
    public void proc() {
        if(!ba.target.isDead()) {
            //ターゲットが死んでいなければ少なくともゲームオーバーではない
            //次のステートへ
            psp.changeState(psp.getPipelineAnomalousState());
            return;
        }
        if(ba.target.getAttackerType() == Attackable.AttackerType.Friend){
            //プレイヤー ゲームオーバー画面の表示→タイトルへ
            //デバッグ　タイトルの代わりのDebugEventSelectScreenへ
            psp.changeBattleResult(PipelineStatePattern.BATTLE_RESULT.gameover);
            LoadingTransition lt = LoadingTransition.getInstance();
            lt.initTransition(DebugEventSelectScreen.class);
            GameManager.transition = lt;
            GameManager.isTransition = true;
            return;
        }else{
            //敵の終了条件検索
            Attackable[] enemys = psp.getPipelineState().getBattleState().getBattleManager().getEnemyList();
            for(int n = 0;n < enemys.length;n++){
                if(!enemys[n].isDead()){
                    //敵が一体でも残っていたらクリアではないので次のステートへ
                    psp.changeState(psp.getPipelineAnomalousState());
                    return;
                }
            }
            //全員死んでいたらクリアとなる
            psp.changeBattleResult(PipelineStatePattern.BATTLE_RESULT.clear);
        }
    }

    @Override
    public void draw(float offsetX, float offsetY) {

    }

    @Override
    public void init() {
        ba = psp.getPipelineState().getBattleState().getBattleManager().getBattleAction();
    }
}
