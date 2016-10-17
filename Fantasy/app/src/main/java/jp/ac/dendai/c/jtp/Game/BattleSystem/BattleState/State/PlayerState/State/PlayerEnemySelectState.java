package jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PlayerState.State;

import android.util.Log;

import java.util.ArrayDeque;
import java.util.Stack;

import jp.ac.dendai.c.jtp.Game.BattleSystem.Attackable;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleAction;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PiplineState.State.APipelineState;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PiplineState.State.PipelineStatePattern;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Enemy.Enemy;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.Button;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.ButtonListener;
import jp.ac.dendai.c.jtp.Game.UIs.UI.List.PlayerActionList;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UI;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Util.Time;
import jp.ac.dendai.c.jtp.TouchUtil.Input;
import jp.ac.dendai.c.jtp.fantasy.R;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by wark on 2016/10/17.
 */

public class PlayerEnemySelectState extends APlayerState implements ButtonListener{

    protected float freq = 0.5f;
    protected float timeBuffer = 0;
    protected int selectIndex = -1;
    protected boolean isSelect = false;


    public PlayerEnemySelectState(PlayerStatePattern _psp) {
        super(_psp);
    }

    @Override
    public void clean() {
        Enemy[] enemys = psp.getPlayerState().getBattleState().getEnemyList();
        for(int n = 0;n < enemys.length;n++){
            enemys[n].setR(0.5f);
            enemys[n].setG(0.5f);
            enemys[n].setB(0.5f);
        }
    }

    @Override
    public void proc() {
        Enemy[] enemys = psp.getPlayerState().getBattleState().getEnemyList();
        for(selectIndex =0;selectIndex < enemys.length;selectIndex++) {
            enemys[selectIndex].touch(Input.getTouchArray()[0]);
            enemys[selectIndex].proc();
            if(isSelect){
                Log.d("PESS","Select Enemy : "+enemys[selectIndex]);
                BattleAction ba = psp.getPlayerState().getBattleState().getBattleManager().getPlayer().getBattleAction();
                ba.target = enemys[selectIndex];
                psp.endState();
                return;
            }
        }
    }

    @Override
    public void draw(float offsetX, float offsetY) {
        Enemy[] enemies = psp.getPlayerState().getBattleState().getEnemyList();
        for(int n =0;n < enemies.length;n++) {
            float color = (float)(Math.sin(6.28*freq*timeBuffer)/2)+1f;
            enemies[n].setR(color);
            enemies[n].setG(color);
            enemies[n].setB(color);
            enemies[n].drawButton(offsetX,offsetY);
        }
        timeBuffer += Time.getDeltaTime();
    }

    @Override
    public void init(PlayerActionList list) {
        this.list = list;
        isSelect = false;
        timeBuffer = freq * 3f;
        Enemy[] enemys = psp.getPlayerState().getBattleState().getEnemyList();
        for(int n =0;n < enemys.length;n++) {
            enemys[n].setButtonListener(this);
        }
    }

    @Override
    public PlayerActionList getList() {
        return this.list;
    }

    @Override
    public void touchDown(Button button) {

    }

    @Override
    public void touchHover(Button button) {

    }

    @Override
    public void touchUp(Button button) {
        Log.d("PESS ButtonListener","Selected");
        isSelect = true;
    }
}
