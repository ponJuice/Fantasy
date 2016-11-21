package jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State;

import android.util.Log;

import java.util.ArrayList;

import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.BattleStatePattern;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Enemy.Enemy;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Player.PlayerData;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Skill.Skill;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Skill.SkillEvolutions;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.Game.Item.ItemTemplate;
import jp.ac.dendai.c.jtp.Game.UIs.Transition.StackLoadingTransition.StackLoadingTransition;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.Button;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.ButtonListener;
import jp.ac.dendai.c.jtp.Game.UIs.UI.List.List;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.StaticText;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UI;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.TouchUtil.Input;
import jp.ac.dendai.c.jtp.TouchUtil.Touch;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Image.Image;

/**
 * Created by wark on 2016/10/16.
 */

public class BattleResultState extends State {
    public enum ResultMode{
        Item,
        Skill,
        End
    }
    protected ResultDialog rd;

    public BattleResultState(BattleStatePattern battleState) {
        super(battleState);
    }

    @Override
    public void actionProcess() {
        //Log.d("BattleResultState","BattleClear");
        /**/
        rd.touch(Input.getTouchArray()[0]);
        rd.proc();
    }

    @Override
    public void init() {
        ArrayList<ItemTemplate> it = new ArrayList<>();
        //it.add(GameManager.getDataBase().getItem("薬草"));
        //it.add(GameManager.getDataBase().getItem("薬草"));
        Enemy[] enemies = battleState.getBattleManager().getEnemyList();
        for(int n = 0;n < enemies.length;n++){
            enemies[n].getItem(it);
        }

        rd = new ResultDialog(it,battleState.getBattleManager().getCountMaxSkillList());
        if(battleState.getBattleManager().getCountMaxSkillList().size() > 0){
            ArrayList<Skill> list = battleState.getBattleManager().getCountMaxSkillList();
            for(int n = 0; n < list.size();n++){
                ArrayList<Skill> nexts = GameManager.getDataBase().getSkillEvolutions(list.get(n).getSkillName()).nextSkill;
                for(int m = 0;m < nexts.size();m++){
                    Log.d("BattleResultState","owner:"+list.get(n).getSkillName() + " next:"+nexts.get(m).getSkillName());
                }
            }
        }
    }

    @Override
    public void draw(float offsetX, float offsetY) {
        rd.draw(offsetX,offsetY);
    }

    protected class ResultDialog{
        protected ResultMode rm = ResultMode.Item;
        protected float statasTextHeight = 0.1f;
        protected float statasTextX = GLES20Util.getWidth_gl()/2f - 0.3f;
        protected float statasTextY = GLES20Util.getHeight_gl()/2f + 0.2f;
        protected float statasTextContentX = GLES20Util.getWidth_gl()/2f -0.45f;
        protected float statasTextContentY = GLES20Util.getHeight_gl()/2f + 0.15f;
        protected float list_width = 0.7f;
        protected float list_height = 0.35f;
        protected float list_content_height = 0.1f;
        protected float list_text_padding = 0.01f;
        protected float list_x = GLES20Util.getWidth_gl()/2f;
        protected float list_y = GLES20Util.getHeight_gl()/2f - 0.03f;
        protected float skill_list_height = 0.34f;
        protected float skill_list_width = 0.5f;
        protected float skill_list_x = GLES20Util.getWidth_gl()/2f;
        protected float skill_list_y = statasTextContentY - skill_list_height;//GLES20Util.getHeight_gl()/2 - skill_list_height/2f;
        protected float button_height = 0.1f;
        protected float button_y = 0.25f;
        protected float backGround_width = 1.5f;
        protected float backGround_height = 0.8f;
        protected float backGround_y = GLES20Util.getHeight_gl()/2f+0.05f;
        protected Image backGround;
        protected StaticText statasText;
        protected StaticText victoryText;
        protected float victoryTextHeight = 0.2f;
        protected float victoryTextY = 0.85f;
        protected StaticText getItemText;
        protected float getItemTextHeight = 0.1f;
        protected float getItemTextY = 0.7f;
        protected StaticText skillText;
        protected float skillTextHeight = 0.1f;
        protected float skillTextY = 0.7f;
        protected float skillTextX = GLES20Util.getWidth_gl()/2f + 0.3f;
        protected List itemList;
        protected List skillList;
        protected Button okButton;
        protected float hp,mp,atk,def,agl;
        protected StaticText  hp_text,mp_text,atk_text,def_text,agl_text;
        protected float numberHeight = 0.07f;
        protected ArrayList<Skill> _newSkill;
        protected ArrayList<ItemTemplate> _items;
        public ResultDialog(ArrayList<ItemTemplate> item, final ArrayList<Skill> newSkill){
            _newSkill = newSkill;
            backGround = new Image();
            backGround.setImage(Constant.getBitmap(Constant.BITMAP.system_message_box));
            backGround.useAspect(false);
            backGround.setWidth(backGround_width);
            backGround.setHeight(backGround_height);
            backGround.setX(GLES20Util.getWidth_gl()/2f);
            backGround.setY(backGround_y);
            victoryText = new StaticText("リザルト",null);
            victoryText.setHeight(victoryTextHeight);
            victoryText.setY(victoryTextY);
            victoryText.setX(GLES20Util.getWidth_gl()/2f);
            getItemText = new StaticText("取得アイテム",null);
            getItemText.setHeight(getItemTextHeight);
            getItemText.setY(getItemTextY);
            getItemText.setX(GLES20Util.getWidth_gl()/2f);
            skillText = new StaticText("習得技",null);
            skillText.setHeight(skillTextHeight);
            skillText.setY(skillTextY);
            skillText.setX(skillTextX);
            statasText = new StaticText("ステータス",null);
            statasText.useAspect(true);
            statasText.setHeight(statasTextHeight);
            statasText.setX(statasTextX);
            statasText.setY(statasTextY);
            itemList = new List(list_x - list_width/2f,list_y - list_height/2f,list_width,list_height);
            itemList.setContentHeight(list_content_height);
            itemList.setTextPaddint(list_text_padding);
            _items = item;
            for(int n = 0;n < item.size();n++){
                Log.d("BattleResultState","Get Item:"+item.get(n).getName() + "max:"+item.size());
                Button btn = new Button(0,0,1,1,item.get(n).getNameImage().getImage(),item.get(n).getName());
                btn.setBitmap(Constant.getBitmap(Constant.BITMAP.system_button));
                btn.setCriteria(UI.Criteria.Height);
                itemList.addItem(btn);
            }
            if(newSkill.size() != 0) {
                skillList = new List(skill_list_x,skill_list_y,skill_list_width,skill_list_height);
                skillList.setContentHeight(list_content_height);
                skillList.setTextPaddint(list_text_padding);
                for (int n = 0; n < newSkill.size(); n++) {
                    SkillEvolutions skillEvo = GameManager.getDataBase().getSkillEvolutions(newSkill.get(n).getSkillName());
                    for(int m = 0;m < skillEvo.nextSkill.size();m++) {
                        Button btn = new Button(0, 0, 1, 1, skillEvo.nextSkill.get(m).getNameImage(),skillEvo.nextSkill.get(m).getSkillName());
                        btn.setBitmap(Constant.getBitmap(Constant.BITMAP.system_button));
                        btn.setCriteria(UI.Criteria.Height);
                        skillList.addItem(btn);
                    }
                }
                hp = battleState.getBattleManager().getPlayer().getBaseHp();
                mp = battleState.getBattleManager().getPlayer().getBaseMp();
                atk = battleState.getBattleManager().getPlayer().getBaseAtk();
                def = battleState.getBattleManager().getPlayer().getBaseDef();
                agl = battleState.getBattleManager().getPlayer().getBaseAgl();
                PlayerData pd = GameManager.getPlayerData();
                for (int n = 0; n < _newSkill.size(); n++) {
                    pd.masterSkill(_newSkill.get(n));
                }

                hp_text = new StaticText("HP "+(int)hp + "→" + pd.getHp(), null);
                hp_text.useAspect(true);
                hp_text.setHorizontal(UIAlign.Align.LEFT);
                hp_text.setVertical(UIAlign.Align.TOP);
                hp_text.setHeight(numberHeight);
                mp_text = new StaticText("MP "+(int)mp + "→" + pd.getMp(), null);
                mp_text.useAspect(true);
                mp_text.setHorizontal(UIAlign.Align.LEFT);
                mp_text.setVertical(UIAlign.Align.TOP);
                mp_text.setHeight(numberHeight);
                atk_text = new StaticText("ATK "+(int)atk + "→" + pd.getAtk(), null);
                atk_text.useAspect(true);
                atk_text.setHorizontal(UIAlign.Align.LEFT);
                atk_text.setVertical(UIAlign.Align.TOP);
                atk_text.setHeight(numberHeight);
                def_text = new StaticText("DEF "+(int)def + "→" + pd.getDef(), null);
                def_text.useAspect(true);
                def_text.setHorizontal(UIAlign.Align.LEFT);
                def_text.setVertical(UIAlign.Align.TOP);
                def_text.setHeight(numberHeight);
                agl_text = new StaticText("AGL "+(int)agl + "→" + pd.getAgl(), null);
                agl_text.useAspect(true);
                agl_text.setHorizontal(UIAlign.Align.LEFT);
                agl_text.setVertical(UIAlign.Align.TOP);
                agl_text.setHeight(numberHeight);

                hp_text.setX(statasTextContentX);
                hp_text.setY(statasTextContentY);
                mp_text.setX(hp_text.getX());
                mp_text.setY(hp_text.getY() - hp_text.getHeight());
                atk_text.setX(mp_text.getX());
                atk_text.setY(mp_text.getY() - mp_text.getHeight());
                def_text.setX(atk_text.getX());
                def_text.setY(atk_text.getY() - atk_text.getHeight());
                agl_text.setX(def_text.getX());
                agl_text.setY(def_text.getY() - def_text.getHeight());

            }

            okButton = new Button(0,0,1,1,"次へ");
            okButton.setBitmap(Constant.getBitmap(Constant.BITMAP.system_button));
            okButton.useAspect(true);
            okButton.setCriteria(UI.Criteria.Height);
            okButton.setHeight(button_height);
            okButton.setX(GLES20Util.getWidth_gl()/2f);
            okButton.setY(button_y);
            okButton.setButtonListener(new ButtonListener() {
                @Override
                public void touchDown(Button button) {

                }

                @Override
                public void touchHover(Button button) {

                }

                @Override
                public void touchUp(Button button) {
                    if(rm == ResultMode.Item) {
                        if (_newSkill.size() == 0) {
                            rm = ResultMode.End;
                            GameManager.getPlayerData().setData(battleState.getBattleManager().getPlayer());
                            for(int n = 0;n < _items.size();n++){
                                GameManager.getPlayerData().addItem(_items.get(n),1);
                            }
                            StackLoadingTransition slt = StackLoadingTransition.getInstance();
                            slt.initStackLoadingTransition();
                            GameManager.transition = slt;
                            GameManager.isTransition = true;
                        } else {
                            okButton.setText("閉じる");
                            rm = ResultMode.Skill;
                        }
                    }else if(rm == ResultMode.Skill){
                        rm = ResultMode.End;
                        GameManager.getPlayerData().setData(battleState.getBattleManager().getPlayer());
                        for(int n = 0;n < _items.size();n++){
                            GameManager.getPlayerData().addItem(_items.get(n),1);
                        }
                        StackLoadingTransition slt = StackLoadingTransition.getInstance();
                        slt.initStackLoadingTransition();
                        GameManager.transition = slt;
                        GameManager.isTransition = true;
                    }
                }
            });
        }
        public void proc(){
            okButton.proc();
            if(rm == ResultMode.Item)
                itemList.proc();
            else if(rm == ResultMode.Skill)
                skillList.proc();

        }
        public void draw(float offsetX,float offsetY){
            backGround.draw(offsetX,offsetY);
            victoryText.draw(offsetX,offsetY);
            if(rm == ResultMode.Item) {
                getItemText.draw(offsetX,offsetY);
                itemList.draw(offsetX, offsetY);
            }else if(rm == ResultMode.Skill){
                skillText.draw(offsetX,offsetY);
                statasText.draw(offsetX,offsetY);
                hp_text.draw(offsetX,offsetY);
                mp_text.draw(offsetX,offsetY);
                atk_text.draw(offsetX,offsetY);
                def_text.draw(offsetX,offsetY);
                agl_text.draw(offsetX,offsetY);
                skillList.draw(offsetX,offsetY);
            }
            okButton.draw(offsetX,offsetY);
        }
        public boolean touch(Touch touch){
            boolean flag = true;
            flag = flag && okButton.touch(touch);
            if(rm == ResultMode.Item) {
                flag = flag && itemList.touch(touch);
            }else{
                flag = flag && skillList.touch(touch);
            }
            return flag;
        }
    }
}
