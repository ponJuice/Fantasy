package jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState;

import jp.ac.dendai.c.jtp.Game.BattleSystem.Attackable;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleAction;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleManager;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.BattleState;
import jp.ac.dendai.c.jtp.Game.BattleSystem.DrawMachine.DefaultDrawMachine;

/**
 * Created by Goto on 2016/09/16.
 */
public class BattleStateMachine {
    public enum DrawState{
        Enemy,
        Player
    }
    protected BattleAction battleAction;
    protected BattleManager battleManager;
    protected DefaultDrawMachine defaultDrawMachine;
    protected EnemyDrawState eds;
    protected Attackable[] list;
    protected BattleState now;
    protected int turnIndex = 0;

    public BattleStateMachine(BattleManager battleManager){
        this.battleManager = battleManager;
        battleAction = new BattleAction();
        eds = new EnemyDrawState();
    }

    public BattleAction getBattleAction(){
        return battleAction;
    }

    public Attackable[] getList(){
        return getList();
    }

    public void nextState(){
        turnIndex++;
        turnIndex = turnIndex % list.length;
    }

    public void setDrawState(DrawState ds){
        if(ds == DrawState.Enemy)
            now = eds;
        else
            now = eds;
    }

    public void draw(float offset_x,float offset_y){
        now.draw(offset_x,offset_y);
    }

    public void proc(){
        now.proc(this);
    }
}
