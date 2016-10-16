package jp.ac.dendai.c.jtp.Game.BattleSystem.Player;

import android.graphics.Bitmap;
import android.util.Log;

import jp.ac.dendai.c.jtp.Game.BattleSystem.Attackable;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleAction;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleManager;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Skill.NormalAttack;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Skill.Skill;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.Button;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.ButtonListener;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UI;
import jp.ac.dendai.c.jtp.TouchUtil.Input;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by Goto on 2016/09/16.
 */
public class Player extends Attackable{
    protected Button btn;
    protected boolean actionEnd = false;
    public Player(PlayerData pd){
        baseHp = pd.hp;
        hp = pd.hp;
        atk = pd.atk;
        def = pd.def;
        agl = pd.agl;

        this.sx = 2f;
        this.sy = 2f;

        this.name_bitmap = pd.name_bitmap;

        /* ------- Debug --------*/
        name = "アラン";
        skills = new Skill[1];
        skills[0] = GameManager.getDataBase().getSkill("通常攻撃");
        /* --------------------- */

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

        String str = String.format("[name : %s] [hp : %d] [atk : %d] [def : %d] [agl : %d] [mp : %d]",name,hp,atk,def,agl,mp);
        Log.d("Player","Player Info"+str);
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
        ba.skill = skills[0];
        ba.type = BattleAction.ActionType.Normal;
        ba.resetInfo(actionEnd);
        actionEnd = false;
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
        return agl;
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
        return mp;
    }

    @Override
    public int getBaseMp() {
        return mp;
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
