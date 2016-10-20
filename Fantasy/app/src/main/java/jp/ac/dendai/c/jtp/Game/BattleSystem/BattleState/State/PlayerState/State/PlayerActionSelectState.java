package jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PlayerState.State;

import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleAction;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Player.Player;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.Button;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.ButtonListener;
import jp.ac.dendai.c.jtp.Game.UIs.UI.List.List;
import jp.ac.dendai.c.jtp.Game.UIs.UI.List.PlayerActionList;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UI;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.TouchUtil.Input;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by wark on 2016/10/17.
 */

public class PlayerActionSelectState extends APlayerState{
    protected final static String attackBtnString = "攻撃";
    protected final static String skillBtnString ="スキル";
    protected final static String itemBtnString = "アイテム";
    protected final static String escapeBtnString = "逃げる";
    protected final static float padding = 0.1f;

    public PlayerActionSelectState(final PlayerStatePattern psp) {
        super(psp);
        list = new PlayerActionList(GLES20Util.getWidth_gl()/2f,0,Constant.battle_list_width,Constant.battle_list_height);
        list.setContentWidth(Constant.battle_list_content_width);
        list.setContentHeight(Constant.battle_list_content_height);
        list.setItemPadding(Constant.battle_list_item_padding);
        list.setTextPaddint(Constant.battle_list_text_padding);
        list.setHorizontal(UIAlign.Align.CENTOR);
        list.setVertical(UIAlign.Align.CENTOR);
        list.setScrollable(true);

        Button btn = new Button(0,0,0.5f,0.25f,attackBtnString);
        btn.useAspect(true);
        btn.setBitmap(Constant.getBitmap(Constant.BITMAP.system_button));
        btn.setCriteria(UI.Criteria.Height);
        btn.setPadding(padding);
        btn.setButtonListener(new ButtonListener() {
            @Override
            public void touchDown(Button button) {

            }

            @Override
            public void touchHover(Button button) {

            }

            @Override
            public void touchUp(Button button) {
                //攻撃ボタン　敵選択へ
                Player p = psp.getPlayerState().getBattleState().getBattleManager().getPlayer();
                p.getBattleAction().type = BattleAction.ActionType.Normal;
                p.getBattleAction().skill = GameManager.getDataBase().getSkill(Constant.normal_attack_name);
                psp.getList().setDrawable(false);
                psp.getList().setTouchable(false);
                psp.changeState(psp.getPlayerEnemySelectState());
            }
        });
        list.addItem(btn);

        btn = new Button(0,0,0.5f,0.25f,skillBtnString);
        btn.useAspect(true);
        btn.setBitmap(Constant.getBitmap(Constant.BITMAP.system_button));
        btn.setCriteria(UI.Criteria.Height);
        btn.setPadding(padding);
        btn.setButtonListener(new ButtonListener() {
            @Override
            public void touchDown(Button button) {

            }

            @Override
            public void touchHover(Button button) {

            }

            @Override
            public void touchUp(Button button) {
                //スキル スキル選択へ
                Player p = psp.getPlayerState().getBattleState().getBattleManager().getPlayer();
                p.getBattleAction().type = BattleAction.ActionType.Skill;
                psp.changeState(psp.getPlayerSkillSelectState());
            }
        });
        list.addItem(btn);

        btn = new Button(0,0,0.5f,0.25f,itemBtnString);
        btn.useAspect(true);
        btn.setBitmap(Constant.getBitmap(Constant.BITMAP.system_button));
        btn.setCriteria(UI.Criteria.Height);
        btn.setPadding(padding);
        btn.setButtonListener(new ButtonListener() {
            @Override
            public void touchDown(Button button) {

            }

            @Override
            public void touchHover(Button button) {

            }

            @Override
            public void touchUp(Button button) {
                //アイテム選択へ
                Player p = psp.getPlayerState().getBattleState().getBattleManager().getPlayer();
                p.getBattleAction().type = BattleAction.ActionType.Item;
                psp.changeState(psp.getPlayerItemSelectState());
            }
        });
        list.addItem(btn);

        btn = new Button(0,0,0.5f,0.25f,escapeBtnString);
        btn.useAspect(true);
        btn.setBitmap(Constant.getBitmap(Constant.BITMAP.system_button));
        btn.setCriteria(UI.Criteria.Height);
        btn.setPadding(padding);
        btn.setButtonListener(new ButtonListener() {
            @Override
            public void touchDown(Button button) {

            }

            @Override
            public void touchHover(Button button) {

            }

            @Override
            public void touchUp(Button button) {
                //逃走

            }
        });
        list.addItem(btn);
    }

    @Override
    public void clean() {

    }

    @Override
    public void proc() {
        //list.touch(Input.getTouchArray()[0]);
        //list.proc();
    }

    @Override
    public void draw(float offsetX, float offsetY) {
        //list.draw(offsetX,offsetY);
    }

    @Override
    public void init(PlayerActionList list) {
        this.list.init();
    }

    @Override
    public PlayerActionList getList() {
        return list;
    }
}
