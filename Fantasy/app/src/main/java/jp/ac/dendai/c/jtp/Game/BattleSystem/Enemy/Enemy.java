package jp.ac.dendai.c.jtp.Game.BattleSystem.Enemy;

import android.graphics.Bitmap;

import jp.ac.dendai.c.jtp.Game.BattleSystem.Attackable;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleAction;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleManager;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.BattleStateMachine;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Enum.ActionType;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Skill.NormalAttack;
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
    protected Skill[] skills;   //一番最後が通常攻撃

    public Enemy(EnemyTemplate et,float x,float y){
        hp = et.hp;
        atk = et.atk;
        def = et.def;
        //agl = et.agl;
        //mp = et.mp;
        this.image = et.image;
        this.x = x;
        this.y = y;
        skills = new Skill[1];
        skills[0] = new NormalAttack();
    }

    @Override
    public Bitmap getImage(){
        return image;
    }

    @Override
    public boolean isDead() {
        return hp <= 0;
    }

    public boolean isDead(int damage){
        return 0 <= (hp - damage);
    }

    @Override
    public void draw(float offsetX, float offsetY) {

    }

    @Override
    public void influenceDamage(float value) {
        hp -= value;
        hp = Math.max(hp,0);
    }

    @Override
    public float damageValue(float attack) {
        return Math.max(attack - def,Constant.damage_lowest);
    }

    @Override
    public void action(BattleManager bm){
        //ダメージ計算などを行う
        BattleAction action = bm.getBattleAction();
        action.resetInfo(true);
        //敵を選ぶ
        //デバッグ、プレイヤーを選ぶ
        action.owner = this;
        action.target = bm.getPlayer();
        action.type = BattleAction.ActionType.Normal;
        action.skill = skills[skills.length - 1];

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
        return x;
    }

    @Override
    public float getY() {
        return y;
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
