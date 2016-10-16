package jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PlayerState.State;

import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PiplineState.State.PipelineStatePattern;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.UIs.Screen.DebugEventSelectScreen;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.Button;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.ButtonListener;
import jp.ac.dendai.c.jtp.Game.UIs.UI.List.List;
import jp.ac.dendai.c.jtp.Game.UIs.UI.List.ListItemButtonListener;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UI;
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

    protected List list;
    public PlayerActionSelectState(final PlayerStatePattern psp) {
        super(psp);
        list = new List(GLES20Util.getWidth_gl()/2f,0);

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
    public void proc() {
        list.touch(Input.getTouchArray()[0]);
        list.proc();
    }

    @Override
    public void draw(float offsetX, float offsetY) {
        list.draw(offsetX,offsetY);
    }

    @Override
    public void init() {

    }
}
