package jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PlayerState.State;

import android.util.Log;

import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PiplineState.State.PipelineStatePattern;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PlayerState.PlayerState;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Player.Player;

/**
 * Created by wark on 2016/10/17.
 */

public class PlayerStatePattern {
    protected APlayerState state;
    protected PlayerState playerState;
    protected PlayerActionSelectState pass;
    protected PlayerEnemySelectState pess;
    protected PlayerItemSelectState piss;
    protected PlayerSkillSelectState psss;

    public PlayerStatePattern(PlayerState playerState){
        this.playerState = playerState;
        pass = new PlayerActionSelectState(this);
        pess = new PlayerEnemySelectState(this);
        piss = new PlayerItemSelectState(this);
        psss = new PlayerSkillSelectState(this);
        changeState(getPlayerActionSelectState());
    }

    public void changeState(APlayerState state){
        this.state = state;
        this.state.init();
    }

    public PlayerActionSelectState getPlayerActionSelectState(){
        return pass;
    }

    public PlayerEnemySelectState getPlayerEnemySelectState(){
        return pess;
    }

    public PlayerItemSelectState getPlayerItemSelectState(){
        return piss;
    }

    public PlayerSkillSelectState getPlayerSkillSelectState(){
        return psss;
    }

    public void init(){
        changeState(getPlayerActionSelectState());
    }

    public boolean proc(){
        state.proc();
        return false;
    }

    public void draw(float offsetX,float offsetY){
        state.draw(offsetX,offsetY);
    }
}
