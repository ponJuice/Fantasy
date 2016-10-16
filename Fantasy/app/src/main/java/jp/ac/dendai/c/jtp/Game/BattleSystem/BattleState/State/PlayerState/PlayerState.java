package jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PlayerState;

import android.util.Log;

import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.BattleStatePattern;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PlayerState.State.PlayerStatePattern;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.State;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.Button;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.ButtonListener;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UI;
import jp.ac.dendai.c.jtp.TouchUtil.Input;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by Goto on 2016/10/14.
 */
public class PlayerState extends State {
    protected PlayerStatePattern psp;
    protected Button btn;
    public PlayerState(final BattleStatePattern battleState) {
        super(battleState);
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
                battleState.changeState(battleState.getPipelineState());
            }
        });

        psp = new PlayerStatePattern(this);
    }

    public void nextState(){
        battleState.changeState(battleState.getPipelineState());
    }

    @Override
    public void actionProcess() {
        //btn.touch(Input.getTouchArray()[0]);
        //btn.proc();
        psp.proc();
    }

    @Override
    public void init() {
        Log.d("PlayerState","init");

    }

    @Override
    public void draw(float offsetX, float offsetY) {
        //btn.draw(offsetX,offsetY);
        psp.draw(offsetX,offsetY);
    }
}
