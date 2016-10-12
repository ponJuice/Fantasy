package jp.ac.dendai.c.jtp.Game.BattleSystem;

import jp.ac.dendai.c.jtp.Game.BattleSystem.Enum.ActionType;

/**
 * Created by Goto on 2016/10/12.
 */

public class BattleAction {
    public Attackable owner;
    public Attackable target;
    public ActionType actionType;
    public int id;
    public boolean enabled = false;
    protected int damage;

    public static void calc(BattleAction battleAction){
        float attack = battleAction.owner.attackValue(battleAction);
        float damage = battleAction.target.damageValue(battleAction,attack);
    }

}
