package jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PiplineState.State;

import android.util.Log;

import jp.ac.dendai.c.jtp.Game.BattleSystem.Attackable;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.BattleStatePattern;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PiplineState.PipelineState;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PiplineState.State.APipelineState;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PiplineState.State.PipelineAnomalousState;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PiplineState.State.PipelineDamageState;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PiplineState.State.PipelineDeadState;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PiplineState.State.PipelineEndState;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PiplineState.State.PipelineTurnEndState;

/**
 * Created by Goto on 2016/10/14.
 */

public class PipelineStatePattern {
    public enum BATTLE_RESULT{
        clear,
        gameover,
        turnend,
        non
    }
    protected BATTLE_RESULT br;
    protected PipelineState ps;
    protected APipelineState state = null;
    protected PipelineDamageState pdas;
    protected PipelineDeadState pdes;
    protected PipelineEndState pes;
    protected PipelineAnomalousState pas;
    protected PipelineTurnEndState ptes;

    public PipelineStatePattern(PipelineState ps){
        this.ps = ps;
        pdas = new PipelineDamageState(this);
        pdes = new PipelineDeadState(this);
        pes = new PipelineEndState(this);
        pas = new PipelineAnomalousState(this);
        ptes = new PipelineTurnEndState(this);
        br = BATTLE_RESULT.non;
    }
    public PipelineState getPipelineState(){
        return ps;
    }
    public APipelineState getPipelineDamageState(){return pdas;}
    public APipelineState getPipelineDeadState(){return pdes;}
    public APipelineState getPipelineEndState(){return pes;}
    public APipelineState getPipelineAnomalousState(){return pas;}
    public APipelineState getPipelineTurnEndState(){return ptes;}
    public void changeBattleResult(BATTLE_RESULT br){
        //戦闘に勝利
        this.br = br;
    }

    public void init(){
        br = BATTLE_RESULT.non;
        changeState(getPipelineDamageState());
    }

    public void changeState(APipelineState state){
        this.state = state;
        this.state.init();
    }

    public Attackable getActor(){return ps.getActor();}

    public boolean proc(){
        if(br == BATTLE_RESULT.turnend)
            return true;
        if(br == BATTLE_RESULT.clear){
            Log.d("PipelineEndState","GameClear");
            return false;
        }
        state.proc();
        return false;
    }

    public void draw(float offsetX,float offsetY){
        state.draw(offsetX,offsetY);
    }
}
