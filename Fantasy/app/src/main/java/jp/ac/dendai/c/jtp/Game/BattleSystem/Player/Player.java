package jp.ac.dendai.c.jtp.Game.BattleSystem.Player;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import jp.ac.dendai.c.jtp.Game.BattleSystem.Attackable;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleAction;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleManager;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Enchant;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Skill.Skill;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.GameUI.Gage;
import jp.ac.dendai.c.jtp.Game.Item.Item;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.Button;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.ButtonListener;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UI;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by Goto on 2016/09/16.
 */
public class Player extends Attackable{
    protected Button btn;
    protected Gage hpGage;
    protected Gage mpGage;
    protected BattleAction battleAction;
    protected ArrayList<Item> items;
    protected ArrayList<Skill> skills;
    protected Enchant atk_enchant;
    protected Enchant def_enchant;
    protected Enchant agl_enchant;
    protected int money;
    protected boolean actionEnd = false;
    public Player(PlayerData pd){
        baseHp = pd.baseHp;
        hp = pd.hp;
        atk = pd.atk;
        def = pd.def;
        agl = pd.agl;
        baseMp = pd.baseMp;
        mp = pd.mp;

        this.sx = 2f;
        this.sy = 2f;

        this.name_bitmap = pd.name_bitmap;

        skills = pd.getSkill();
        items = new ArrayList<>(Arrays.asList(pd.items.values().toArray(new Item[0])));

        money = pd.getMoney();

        atk_enchant = new Enchant();
        def_enchant = new Enchant();
        agl_enchant = new Enchant();

        /* ------- Debug --------*/
        /*name = "アラン";
        skills = new Skill[1];
        skills[0] = GameManager.getDataBase().getSkill("通常攻撃");*/
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



    public int getMoney(){
        return money;
    }

    public ArrayList<Skill> getSkillList(){
        return skills;
    }

    public ArrayList<Item> getItemList(){return items;}

    public void setHpGage(Gage hp){
        hpGage = hp;
        hpGage.setValue(getHp());
    }

    public void setMpGage(Gage mp){
        mpGage = mp;
        mpGage.setValue(getMp());
    }

    public BattleAction getBattleAction(){
        return battleAction;
    }
    public void setBattleAction(BattleAction battleAction){
        this.battleAction = battleAction;
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
        ba.target = battleAction.target;
        ba.owner = this;
        ba.type = battleAction.type;
        ba.skill = battleAction.skill;
        ba.item = battleAction.item;
        actionEnd = true;
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
        return atk_enchant.getValue(atk);
    }

    @Override
    public float getBaseDef() {
        return def;
    }

    @Override
    public float getDef() {
        return def_enchant.getValue(def);
    }

    @Override
    public float getBaseAgl() {
        return agl;
    }

    @Override
    public float getAgl() {
        return agl_enchant.getValue(agl);
    }

    @Override
    public int getMp() {
        return mp;
    }

    @Override
    public int getBaseMp() {
        return baseMp;
    }

    public void setAtkEnchant(int turn,float par){
        atk_enchant.setPar(par);
        atk_enchant.setTurn(turn);
    }

    public void setDefEnchant(int turn,float par){
        def_enchant.setPar(par);
        def_enchant.setTurn(turn);
    }

    public void setAglEnchant(int turn,float par){
        agl_enchant.setPar(par);
        agl_enchant.setTurn(turn);
    }

    public void procAtkEnchant(){
        atk_enchant.declimentTurn();
    }

    public void procDefEnchant(){
        def_enchant.declimentTurn();
    }

    public void procAglEnchant(){
        agl_enchant.declimentTurn();
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
    public void proc() {

    }

    @Override
    public void draw(float offsetX, float offsetY) {
        //btn.draw(offsetX,offsetY);
    }

    public void setHp(int hp){
        this.hp = hp;
    }

    public void setMp(int mp){
        this.mp = mp;
    }

    @Override
    public void influenceDamage(float value) {
        hp -= value;
        hp = Math.max(hp,0);
    }

    @Override
    public void influenceMp(float value) {
        mp -= value;
        mpGage.setValue(mp);
    }

    @Override
    public boolean mpDecreaseAnimation(float time, int value) {
        return mpGage.animation(time,Constant.damage_gage_time,value);
    }

    @Override
    public boolean deadAnimation(float time) {
        return true;
    }

    @Override
    public boolean damageAnimation(float time,BattleAction ba) {
        return hpGage.animation(time,Constant.damage_gage_time,ba.getDamage());
    }

    public void debugDrawItems(float x,float y,boolean flag){
        for(int n = 0;n < items.size();n++){
            items.get(n).setHeight(0.3f);
            items.get(n).draw(x,y+0.3f*n,flag);
        }
    }
}
