package jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State;

import android.graphics.Bitmap;

import javax.microedition.khronos.opengles.GL;

import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleAction;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.BattleStateMachine;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Enum.ActionType;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Util.Time;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;
import jp.ac.dendai.c.jtp.openglesutil.graphic.blending_mode.GLES20COMPOSITIONMODE;

/**
 * Created by Goto on 2016/10/12.
 */

public class EnemyDrawState implements BattleState{
    protected final static float normal_damage_effect_time = 1f;
    protected float timeBuffer = 0;
    protected Bitmap testImage;
    protected float alpha = 0;
    public EnemyDrawState(){
        testImage = GLES20Util.createBitmap(255,255,0,255);
    }

    @Override
    public void proc(BattleStateMachine bsm) {
        BattleAction ba = bsm.getBattleAction();
        if(ba.actionType == ActionType.Normal){
            //通常攻撃
            if(normal_damage_effect_time > timeBuffer){
                alpha = (float)Math.sin(2*3.14*2*timeBuffer);
                timeBuffer += Time.getDeltaTime();
            }else{
                timeBuffer = 0;
                bsm.setDrawState(BattleStateMachine.DrawState.Enemy);
            }

        }
    }

    @Override
    public void draw(float offset_x, float offset_y) {
        GLES20Util.DrawGraph(GLES20Util.getWidth_gl()/2f,GLES20Util.getHeight_gl()/2f,0.5f,0.5f,testImage,alpha, GLES20COMPOSITIONMODE.ALPHA);
    }
}
