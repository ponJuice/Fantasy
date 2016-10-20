package jp.ac.dendai.c.jtp.Game.GameUI;

import android.graphics.Bitmap;

import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.Button;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.ButtonListener;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.StaticText;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UI;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Util.Time;
import jp.ac.dendai.c.jtp.TouchUtil.Touch;
import jp.ac.dendai.c.jtp.fantasy.R;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Image.Image;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by Goto on 2016/10/20.
 */

public class QuestionBox{
    protected enum Mode{
        in,
        out,
        non
    }
    protected Mode mode = Mode.non;
    protected float animationTime = 0.5f;
    protected float timeBuffer;
    protected float x = 0,y = 0;
    protected float text_pos_y_top = 0.01f;
    protected float button_pos_y_bottom = 0.05f;
    protected float buttonPadding = 0.05f;
    protected float buttonTextPadding = 0.01f;
    protected float background_width_padding_rage = 0.3f;
    protected float height = 0.3f;
    protected float width = 1f;
    protected float text_button_padding_rate = 0.5f;

    protected Image background;
    protected StaticText text;
    protected Button yes,no;

    protected boolean enabled = true;
    protected boolean isTouch = false;

    public QuestionBox(Bitmap background, String text, String yesText, String noText){
        this.background = new Image(background);
        this.text = new StaticText(text,null);
        yes = new Button(0,0,1f,1f,yesText);
        no = new Button(0,0,1,1,noText);

        yes.setBitmap(Constant.getBitmap(Constant.BITMAP.system_button));
        yes.useAspect(true);
        yes.setCriteria(UI.Criteria.Height);
        //yes.setHeight(height * button_height_rate);
        yes.setWidth(width/3f);
        yes.setPadding(buttonTextPadding);
        yes.setHorizontal(UIAlign.Align.RIGHT);
        yes.setVertical(UIAlign.Align.BOTTOM);

        no.setBitmap(Constant.getBitmap(Constant.BITMAP.system_button));
        no.useAspect(true);
        no.setCriteria(UI.Criteria.Height);
        //no.setHeight(height * button_height_rate);
        no.setWidth(width/3f);
        no.setPadding(buttonTextPadding);
        no.setHorizontal(UIAlign.Align.LEFT);
        no.setVertical(UIAlign.Align.BOTTOM);

        this.text.useAspect(true);
        //this.text.setHeight(height * text_height_rate);
        this.text.setWidth(2f*width/3f+2f*buttonPadding);
        this.text.setVertical(UIAlign.Align.TOP);

        height = this.text.getHeight() + yes.getHeight() + this.text.getHeight()*text_button_padding_rate;
        width = this.text.getWidth() + yes.getWidth()*background_width_padding_rage*2f;

        this.background.useAspect(false);
        this.background.setHeight(height);
        this.background.setWidth(width);
    }

    public void setEnabled(boolean enabled){
        this.enabled = enabled;
    }

    public boolean getEnabled(){
        return enabled;
    }

    public void setX(float x){
        this.x = x;
        background.setX(x);
        text.setX(x);
        yes.setX(x - buttonPadding);
        no.setX(x + buttonPadding);
    }

    public void setY(float y){
        this.y = y;
        background.setY(y);
        text.setY(y + background.getHeight()/2 - text_pos_y_top);
        yes.setY(y - background.getHeight()/2f + button_pos_y_bottom);
        no.setY(yes.getY());

    }

    public void setYesButtonListener(ButtonListener bl){
        yes.setButtonListener(bl);
    }

    public void setNoButtonListener(ButtonListener bl){
        no.setButtonListener(bl);
    }

    public void startFadeInAnimation(){
        enabled = true;
        isTouch = false;
        background.setWidth(0);
        background.setHeight(0);
        mode = Mode.in;
    }

    public void startFadeOutAnimation(){
        enabled = true;
        isTouch = false;
        background.setWidth(width);
        background.setHeight(height);
        mode = Mode.out;
    }

    public boolean touch(Touch touch) {
        if(!enabled || !isTouch)
            return true;
        return yes.touch(touch) && no.touch(touch);
    }

    public boolean proc() {
        if(!isTouch) {
            if (timeBuffer >= animationTime) {
                timeBuffer = 0;
                if(mode == Mode.in) {
                    background.setWidth(width);
                    background.setHeight(height);
                }else if(mode == Mode.out){
                    background.setWidth(0);
                    background.setHeight(0);
                    enabled = false;
                }
                isTouch = true;
            } else {
                if(mode == Mode.in) {
                    background.setWidth(width * (timeBuffer / animationTime));
                    background.setHeight(height * (timeBuffer / animationTime));
                }else if(mode == Mode.out){
                    background.setWidth(width * (1f - timeBuffer / animationTime));
                    background.setHeight(height * (1f - timeBuffer / animationTime));
                }
                timeBuffer += Time.getDeltaTime();
            }
        }

        if(!enabled || !isTouch)
            return false;
        if(mode == Mode.in) {
            yes.proc();
            no.proc();
        }
        return true;
    }

    public void draw(float offset_x, float offset_y) {
        if(!enabled)
            return;
        background.draw(offset_x,offset_y);
        if(isTouch) {
            text.draw(offset_x, offset_y);
            yes.draw(offset_x, offset_y);
            no.draw(offset_x, offset_y);
        }
    }
}
