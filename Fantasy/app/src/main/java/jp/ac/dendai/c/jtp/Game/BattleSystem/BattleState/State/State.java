package jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State;

import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.BattleStatePattern;

/**
 * Created by Goto on 2016/10/14.
 */
public abstract class State {
    protected BattleStatePattern battleState;
    public State(BattleStatePattern battleState){
        this.battleState = battleState;
    }
    //行動選択
    public abstract void actionProcess();
    public abstract void init();
    public abstract void draw(float offsetX,float offsetY);
    public BattleStatePattern getBattleState(){
        return battleState;
    }
}
