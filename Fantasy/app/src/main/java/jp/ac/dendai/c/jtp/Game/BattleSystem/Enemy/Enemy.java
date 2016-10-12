package jp.ac.dendai.c.jtp.Game.BattleSystem.Enemy;

import android.graphics.Bitmap;

import jp.ac.dendai.c.jtp.Game.BattleSystem.Attackable;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleAction;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleManager;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.BattleStateMachine;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Enum.ActionType;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Skill.Skill;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;
import jp.ac.dendai.c.jtp.openglesutil.graphic.blending_mode.GLES20COMPOSITIONMODE;

/**
 * Created by Goto on 2016/09/16.
 */
public class Enemy extends Attackable{
    protected Bitmap image;
    protected float x,y;
    protected Skill[] skills;

    public Enemy(EnemyTemplate et,float x,float y){
        hp = et.hp;
        atk = et.atk;
        def = et.def;
        //agl = et.agl;
        //mp = et.mp;
        this.image = et.image;
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean isDead() {
        return hp <= 0;
    }

    @Override
    public float attackValue(BattleAction battleAction) {
        return atk;
    }

    @Override
    public float damageValue(BattleAction battleAction,float attack) {
        return Math.max(attack - def,Constant.damage_lowest);
    }

    @Override
    public void action(BattleManager bm){
        //ダメージ計算などを行う
        BattleAction action = bm.getBattleAction();
        //敵を選ぶ
    }

    @Override
    public float getBaseHp() {
        return hp;
    }

    @Override
    public float getHp() {
        return hp;
    }

    @Override
    public float getBaseAtk() {
        return atk;
    }

    @Override
    public float getAtk() {
        return atk;
    }

    @Override
    public float getBaseDef() {
        return def;
    }

    @Override
    public float getDef() {
        return def;
    }

    @Override
    public float getBaseAgl() {
        return agl;
    }

    @Override
    public float getAgl() {
        return agl;
    }

    @Override
    public int getMp() {
        return 0;
    }

    @Override
    public int getBaseMp() {
        return 0;
    }

    @Override
    public float getX() {
        return 0;
    }

    @Override
    public float getY() {
        return 0;
    }

    @Override
    public AttackerType getAttackerType(){
        return AttackerType.Enemy;
    }

    /*@Override
    public void proc(BattleStateMachine bsm) {
        BattleAction ba = bsm.getBattleAction();
        ba.actionType = ActionType.Normal;
        ba.owner = this;
        //ターゲットはプレイヤー　（デバッグ）
        ba.target = bsm.getList()[bsm.getList().length-1];
        ba.id = -1;
        ba.enabled = true;
    }

    @Override
    public void draw(float offset_x, float offset_y) {

    }*/
}
