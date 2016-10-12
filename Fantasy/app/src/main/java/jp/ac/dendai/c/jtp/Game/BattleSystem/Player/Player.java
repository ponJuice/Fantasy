package jp.ac.dendai.c.jtp.Game.BattleSystem.Player;

import jp.ac.dendai.c.jtp.Game.BattleSystem.Attackable;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleAction;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleManager;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.BattleStateMachine;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.BattleState;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PlayerActionState;

/**
 * Created by Goto on 2016/09/16.
 */
public class Player extends Attackable{
    public Player(PlayerData pd){
        hp = pd.hp;
        atk = pd.atk;
        def = pd.def;
    }

    @Override
    public boolean isDead() {
        return hp <= 0;
    }

    @Override
    public float attackValue(BattleAction battleAction) {
        return 0;
    }

    @Override
    public float damageValue(BattleAction battleAction, float attack) {
        return 0;
    }

    @Override
    public boolean action(BattleAction battleAction,BattleManager battleManager) {
        return false;
    }

    @Override
    public float getBaseHp() {
        return 0;
    }

    @Override
    public float getHp() {
        return 0;
    }

    @Override
    public float getBaseAtk() {
        return 0;
    }

    @Override
    public float getAtk() {
        return 0;
    }

    @Override
    public float getBaseDef() {
        return 0;
    }

    @Override
    public float getDef() {
        return 0;
    }

    @Override
    public float getBaseAgl() {
        return 0;
    }

    @Override
    public float getAgl() {
        return 0;
    }

    @Override
    public void proc(BattleStateMachine bsm) {

    }

    @Override
    public void draw(float offset_x, float offset_y) {

    }
}
