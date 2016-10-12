package jp.ac.dendai.c.jtp.Game.BattleSystem.Skill;

import android.graphics.Bitmap;

import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.UIs.Effects.Bitmap.Animator;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Util.Time;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;
import jp.ac.dendai.c.jtp.openglesutil.graphic.blending_mode.GLES20COMPOSITIONMODE;

/**
 * Created by wark on 2016/10/13.
 */

public class Skill {
    protected String skillName;
    protected Animator animator;
    protected float timeBuffer;

    public void effectInit(){
        animator.resetAnimation();
        timeBuffer = 0;
    }
    public boolean draw(float ox,float oy,float sx,float sy,float deg){
        if(timeBuffer >= Constant.skill_effect_time){
            return true;	//終わったらtrue
        }

        int temp = (int)((float)animator.getAnimationNum() / Constant.skill_effect_time * timeBuffer);
        Bitmap bitmap = animator.getBitmap(temp);

        GLES20Util.DrawGraph(ox,oy,sx,sy,bitmap,1, GLES20COMPOSITIONMODE.ALPHA);

        timeBuffer += Time.getDeltaTime();
        return false;
    }
}
