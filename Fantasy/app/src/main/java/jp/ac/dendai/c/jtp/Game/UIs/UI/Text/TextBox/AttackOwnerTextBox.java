package jp.ac.dendai.c.jtp.Game.UIs.UI.Text.TextBox;

import android.graphics.Bitmap;
import android.opengl.GLES20;

import jp.ac.dendai.c.jtp.Game.BattleSystem.Skill.Skill;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.GameUI.AttackText;
import jp.ac.dendai.c.jtp.Game.GameUI.PatchingText;
import jp.ac.dendai.c.jtp.Game.GameUI.SkillText;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;
import jp.ac.dendai.c.jtp.openglesutil.graphic.blending_mode.GLES20COMPOSITIONMODE;

/**
 * Created by テツヤ on 2016/10/16.
 */

public class AttackOwnerTextBox{
    protected UIAlign.Align horizontal = UIAlign.Align.CENTOR,vertical = UIAlign.Align.CENTOR;
    protected float x,y;
    protected float width,height;
    protected float back_aspect;

    protected float padding = 0;
    protected AttackText attackText;
    protected SkillText  skillText;
    protected PatchingText patchingText;
    protected Bitmap background;
    public AttackOwnerTextBox(Bitmap back){
        background = back;
        back_aspect = (float)back.getWidth() / (float)back.getHeight();
        height = 1;
        width = back_aspect * height;

        /*escapeTextImage = GLES20Util.stringToBitmap("は逃げた",Constant.fontName,25,255,255,255);
        itemUseTextImage = GLES20Util.stringToBitmap("を使った",Constant.fontName,25,255,255,255);
        conjunction_no = GLES20Util.stringToBitmap("の",Constant.fontName,25,255,255,255);
        conjunction_ha = GLES20Util.stringToBitmap("は",Constant.fontName,25,255,255,255);*/

        /*attackText = GLES20Util.stringToBitmap("の攻撃!!", Constant.fontName,25,255,255,255);
        text_aspect = (float)attackText.getWidth() / (float)attackText.getHeight();
        text_height = height;
        text_width = text_height * text_aspect;*/
        attackText = new AttackText();
        skillText = new SkillText();
    }

    public void setPadding(float p){
        padding = p;
    }

    public void setX(float x){
        this.x = x;
    }
    public void setY(float y){
        this.y = y;
    }
    public void setWidth(float width){
        this.width = width;
        this.height = back_aspect / width;
    }
    public void setHeight(float height){
        this.height = height;
        this.width = back_aspect * height;
    }

    public void setPatchingText(PatchingText text){
        patchingText = text;
        patchingText.setHeight(height-padding);
    }

    public AttackText getAttackText(){
        return attackText;
    }

    public SkillText getSkillText(){
        return skillText;
    }

    public void draw(float offsetX,float offsetY){
        /*
        float length = owner_width + text_width;
        float half_length = length / 2f;
        float owner_offset = owner_width - half_length - owner_width/2f;
        float text_offset = -(half_length - owner_width) + text_width/2f;
        GLES20Util.DrawGraph(_x + owner_offset,_y,owner_width,owner_height,ownerName,1,GLES20COMPOSITIONMODE.ALPHA);
        GLES20Util.DrawGraph(_x + text_offset,_y,text_width,text_height,attackText,1,GLES20COMPOSITIONMODE.ALPHA);*/
        float _x = x + UIAlign.convertAlign(width,horizontal);
        float _y = y + UIAlign.convertAlign(height,vertical);
        GLES20Util.DrawGraph(_x,_y,width,height,background,1,GLES20COMPOSITIONMODE.ALPHA);
        patchingText.draw(offsetX+_x,offsetY+_y);
    }
}
