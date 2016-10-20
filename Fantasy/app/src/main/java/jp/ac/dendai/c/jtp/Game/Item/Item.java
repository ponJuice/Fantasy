package jp.ac.dendai.c.jtp.Game.Item;

import android.graphics.Bitmap;
import android.graphics.Color;

import jp.ac.dendai.c.jtp.Game.BattleSystem.Skill.Animation;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.NumberText;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UI;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Util.Time;
import jp.ac.dendai.c.jtp.TouchUtil.Touch;

/**
 * Created by Goto on 2016/09/16.
 */
public class Item implements UI {
    protected final static float effectNumberDrawTime = 1f;
    protected static float space_aspect = 0.8f;
    protected static NumberText effectNumber;
    protected float timeBuffer_2 = 0;
    protected int number;
    protected NumberText numberText;
    protected ItemTemplate itemTemplate;
    protected boolean drawNumber = false;
    protected float timeBuffer;
    public Item(int number,ItemTemplate itemTemplate){
        if(effectNumber == null) {
            effectNumber = new NumberText(Constant.fontName);
            effectNumber.useAspect(true);
        }
        this.number = number;
        this.itemTemplate = itemTemplate;
        numberText = new NumberText(Constant.fontName);
        numberText.setNumber(number);
        numberText.useAspect(true);
        numberText.setHorizontal(UIAlign.Align.LEFT);
    }

    public void effectInit(){
        timeBuffer = 0;
        timeBuffer_2 = 0;
    }

    public void setEffectNumber(int num){
        effectNumber.setNumber(num);
    }

    public void setEffectNumberPos(float x,float y){
        effectNumber.setX(x);
        effectNumber.setY(y);
    }
    public void setEffectNumberHeight(float height){
        effectNumber.setHeight(height);
    }
    public void setEffectNumberColor(int color){
        effectNumber.setR(Color.red(color));
        effectNumber.setG(Color.green(color));
        effectNumber.setB(Color.blue(color));
    }

    public int getNumber(){
        return number;
    }

    public void setHeight(float height){
        itemTemplate.getNameImage().setHeight(height);
        numberText.setHeight(height);
    }

    public void setDrawNumber(boolean flag){
        drawNumber = flag;
    }

    public void draw(float offsetX,float offsetY,boolean drawNumber){
        float length = itemTemplate.getNameImage().getWidth();
        float _x = 0;
        if(drawNumber) {
            itemTemplate.getNameImage().setHorizontal(UIAlign.Align.RIGHT);
            length += numberText.getWidth() + space_aspect*itemTemplate.getNameImage().getHeight();
            _x = itemTemplate.getNameImage().getWidth() - length / 2;
        }else{
            itemTemplate.getNameImage().setHorizontal(UIAlign.Align.CENTOR);
        }
        itemTemplate.getNameImage().draw(offsetX+_x,offsetY);
        if(drawNumber)
            numberText.draw(offsetX+_x+space_aspect*itemTemplate.getNameImage().getHeight(),offsetY);
    }

    public Bitmap getNameImage(){
        return itemTemplate.getNameImage().getImage();
    }

    @Override
    public boolean touch(Touch touch) {
        return false;
    }

    @Override
    public void proc() {

    }

    @Override
    public void draw(float offset_x, float offset_y) {
        draw(offset_x,offset_y,drawNumber);
    }

    public boolean drawEffect(float ox,float oy,float sx,float sy,float deg){
        boolean flag = true;
        boolean numFlag = false;

        for(int n = 0;n < itemTemplate.animations.size();n++) {
            Animation sa = itemTemplate.animations.get(n);
            flag = sa.draw(timeBuffer, ox, oy, sx, sy, deg) && flag;
        }
        effectNumber.draw(0,0);
        /*if(skillAnimations.size() > 1)
            flag = flag && skillAnimations.get(1).draw(timeBuffer,ox,oy,sx,sy,deg);
        else
            flag = flag && skillAnimations.get(0).draw(timeBuffer,ox,oy,sx,sy,deg);*/
        timeBuffer += Time.getDeltaTime();

        if(numFlag)
            timeBuffer = 0;
        return numFlag;
    }
}
