package jp.ac.dendai.c.jtp.Game.BattleSystem.Enemy;

import android.graphics.Bitmap;

import jp.ac.dendai.c.jtp.Game.BattleSystem.Attackable;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleAction;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleManager;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Skill.NormalAttack;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Skill.Skill;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.Button;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.ButtonListener;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Slider.Rect;
import jp.ac.dendai.c.jtp.Game.UIs.UI.TextBox.TalkBox;
import jp.ac.dendai.c.jtp.TouchUtil.Input;
import jp.ac.dendai.c.jtp.TouchUtil.Touch;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;
import jp.ac.dendai.c.jtp.openglesutil.graphic.blending_mode.GLES20COMPOSITIONMODE;

import static jp.ac.dendai.c.jtp.Game.Charactor.FaceManager.list;

/**
 * Created by Goto on 2016/09/16.
 */
public class Enemy extends Attackable{
    protected Bitmap image;
    protected float alpha = 1;
    protected Button btn;

    public Enemy(EnemyTemplate et,float x,float y){
        name = et.name;
        baseHp = et.hp;
        hp = et.hp;
        atk = et.atk;
        def = et.def;
        agl = et.agl;
        mp = et.mp;
        this.name_bitmap = et.name_bitmap;
        this.image = et.image;
        this.x = x;
        this.y = y;
        this.sx = 1;
        this.sy = 1;
        skills = et.skills;

        btn = new Button(x,y,Constant.enemy_size_x,Constant.enemy_size_y,null);
        btn.setBitmap(Constant.getBitmap(Constant.BITMAP.system_selector));
        btn.useAspect(true);
    }

    @Override
    public Bitmap getImage(){
        return image;
    }

    @Override
    public boolean isDead() {
        return getHp() <= 0;
    }

    public boolean isDead(int damage){
        return 0 <= (hp - damage);
    }

    @Override
    public void draw(float offsetX, float offsetY) {
        //if(!isDead())
        GLES20Util.DrawGraph(getX(),getY(), Constant.enemy_size_x,Constant.enemy_size_y,r,g,b,image,alpha, GLES20COMPOSITIONMODE.ALPHA);
    }

    @Override
    public void influenceDamage(float value) {
        hp -= value;
        hp = Math.max(hp,0);
    }

    @Override
    public boolean deadAnimation(float time) {
        if(time >= Constant.dead_effect_time) {
            alpha = 0;
            return true;
        }
        alpha = 1f - 1f / Constant.dead_effect_time * time;
        alpha = Math.min(alpha,1);
        return false;
    }

    @Override
    public boolean damageAnimation(float time, BattleAction ba) {
        if(time >= Constant.damage_gage_time)
            return true;
        return false;
    }

    @Override
    public float damageValue(float attack) {
        return Math.max(attack - def,Constant.damage_lowest);
    }

    @Override
    public void action(BattleManager bm){
        //ダメージ計算などを行う
        BattleAction action = bm.getBattleAction();
        //敵を選ぶ
        //デバッグ、プレイヤーを選ぶ
        action.owner = this;
        action.target = bm.getPlayer();
        action.type = BattleAction.ActionType.Skill;
        action.skill = skills[Constant.getRandom().nextInt(skills.length)];

    }

    public void drawButton(float offsetX,float offsetY){
        if(!isDead())
            btn.draw(offsetX,offsetY);
    }

    public void setButtonListener(ButtonListener bl){
        btn.setButtonListener(bl);
    }

    public void touch(Touch touch){
        if(!isDead())
            btn.touch(touch);
    }

    @Override
    public void proc(){
        if(!isDead())
            btn.proc();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getBaseHp() {
        return baseHp;
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
    public float getSX() {
        return sx;
    }

    @Override
    public float getSY() {
        return sy;
    }

    @Override
    public AttackerType getAttackerType(){
        return AttackerType.Enemy;
    }

    /*@Override
    public void proc(BattleStatePattern bsm) {
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
