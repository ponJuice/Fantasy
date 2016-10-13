package jp.ac.dendai.c.jtp.Game.BattleSystem.Skill;

import android.view.animation.Animation;

import jp.ac.dendai.c.jtp.Game.UIs.Effects.Bitmap.AnimationBitmap;
import jp.ac.dendai.c.jtp.Game.UIs.Effects.Bitmap.Animator;

/**
 * Created by Goto on 2016/10/13.
 */

public class NormalAttack extends Skill {
    protected final static String imageName = "Battle/Effect/normal_attack.png";
    public NormalAttack(){
        AnimationBitmap ab = AnimationBitmap.createAnimation(imageName,368,147,5,2,6);
        animator = new Animator(ab);
    }
}
