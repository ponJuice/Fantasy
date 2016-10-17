package jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState;

import jp.ac.dendai.c.jtp.Game.BattleSystem.Attackable;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleManager;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.ActorSortState;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.BattleResultState;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PiplineState.PipelineState;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PlayerState.PlayerState;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.State;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Enemy.Enemy;

/**
 * Created by Goto on 2016/09/16.
 */
public class BattleStatePattern {
    protected BattleManager bm;
    protected State state = null;
    protected PlayerState ps;
    protected PipelineState pls;
    protected ActorSortState ass;
    protected BattleResultState brs;

    public BattleStatePattern(BattleManager bm){
        this.bm = bm;
        ps = new PlayerState(this);
        pls = new PipelineState(this);
        ass = new ActorSortState(this);
        brs = new BattleResultState(this);
        //デフォルトステート設定
        state = ass;
    }
    public PlayerState getPlayerState(){
        return ps;
    }
    public PipelineState getPipelineState(){return pls;}
    public ActorSortState getActorSortState(){return ass;}
    public BattleResultState getBattleResultState(){return brs;}

    public void changeState(State state) {
        if (state == null)
            throw new RuntimeException("BattleStateにnullが指定されました。");
        this.state = state;
        this.state.init();
    }
    public Enemy[] getEnemyList(){
        return bm.getEnemyList();
    }
    public Attackable[] getActorList(){
        return bm.getActorList();
    }
    public BattleManager getBattleManager(){
        return bm;
    }

    public void proc(){
        state.actionProcess();
    }

    public void drwa(float offsetX,float offsetY){
        state.draw(offsetX,offsetY);
    }

}
