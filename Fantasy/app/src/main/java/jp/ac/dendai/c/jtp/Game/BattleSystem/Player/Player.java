package jp.ac.dendai.c.jtp.Game.BattleSystem.Player;

import android.graphics.Bitmap;
import android.util.Log;

import jp.ac.dendai.c.jtp.Game.BattleSystem.Attackable;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleAction;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleManager;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.BattleStateMachine;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.Button;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.ButtonListener;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UI;
import jp.ac.dendai.c.jtp.TouchUtil.Input;
import jp.ac.dendai.c.jtp.TouchUtil.Touch;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by Goto on 2016/09/16.
 */
public class Player extends Attackable{
    protected Button btn;
    protected float x,y;
    protected boolean actionEnd = false;
    public Player(PlayerData pd){
        baseHp = pd.hp;
        hp = pd.hp;
        atk = pd.atk;
        def = pd.def;
        //でバグ
        name = "アラン";

        btn = new Button(GLES20Util.getWidth_gl()/2f,GLES20Util.getHeight_gl()/2f,0.5f,0.5f,"攻撃");
        btn.setBitmap(Constant.getBitmap(Constant.BITMAP.system_button));
        btn.setBackImageCriteria(UI.Criteria.Height);
        btn.setHeight(0.2f);
        btn.setCriteria(UI.Criteria.Height);
        btn.setPadding(0.02f);

        btn.setButtonListener(new ButtonListener() {
            @Override
            public void touchDown(Button button) {

            }

            @Override
            public void touchHover(Button button) {

            }

            @Override
            public void touchUp(Button button) {
                actionEnd = true;
            }
        });
    }

    public void setX(float x){
        this.x = x;
    }

    public void setY(float y){
        this.y = y;
    }

    @Override
    public boolean isDead() {
        return hp <= 0;
    }

    @Override
    public float damageValue(float attack) {
        return Math.max(attack - def,Constant.damage_lowest);
    }
    @Override
    public void action(BattleManager bm) {
        //Log.d("Attacable Action","Player Action");
        BattleAction ba = bm.getBattleAction();
        btn.touch(Input.getTouchArray()[0]);
        btn.proc();
        ba.owner = this;
        ba.target = bm.getEnemyList()[0];
        ba.resetInfo(actionEnd);
        actionEnd = false;
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
    public AttackerType getAttackerType() {
        return AttackerType.Friend;
    }

    @Override
    public Bitmap getImage() {
        return null;
    }

    @Override
    public boolean isDead(int damage) {
        return false;
    }

    @Override
    public void draw(float offsetX, float offsetY) {
        btn.draw(offsetX,offsetY);
    }

    @Override
    public void influenceDamage(float value) {
        hp -= value;
        hp = Math.max(hp,0);
    }
}
