package jp.ac.dendai.c.jtp.Game.Item;

import android.graphics.Bitmap;

import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleAction;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Player.PlayerData;
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
    protected float timeBuffer_2 = 0;
    protected int number;
    protected NumberText numberText;
    protected ItemTemplate itemTemplate;
    protected boolean drawNumber = true;
    protected float timeBuffer;
    protected float padding;
    public Item(int number,ItemTemplate itemTemplate){
        this.number = number;
        this.itemTemplate = itemTemplate;
        numberText = new NumberText(Constant.fontName);
        numberText.setNumber(number);
        numberText.useAspect(true);
        numberText.setHorizontal(UIAlign.Align.LEFT);
    }

    public boolean isSellable(){
        return itemTemplate.sellable;
    }

    public boolean isUseable(){
        return itemTemplate.useable;
    }

    public int getPrice(){
        return itemTemplate.price;
    }

    public void addItem(int num){
        number += num;
        refreshNumberText();
    }


    public void subItem(int num){
        number -= num;
        number = Math.max(number,0);
        refreshNumberText();
    }

    public void effectInit(){
        timeBuffer = 0;
        timeBuffer_2 = 0;
    }

    protected void refreshNumberText(){
        numberText.setNumber(number);
    }

    public int getNumber(){
        return number;
    }

    public void setHeight(float height) {
        itemTemplate.getNameImage().setHorizontal(UIAlign.Align.LEFT);
        itemTemplate.getNameImage().setHeight(height);
        numberText.setHorizontal(UIAlign.Align.RIGHT);
        numberText.setHeight(height);
    }

    public void influence(PlayerData pd){
        if(number > 0) {
            itemTemplate.influence(pd);
            number--;
        }
    }

    public void setWidth(float x,float width){
        itemTemplate.getNameImage().setX(x - width/2f + padding);
        numberText.setX(x + width - width/2f - padding);
    }

    public void setPadding(float padding){
        this.padding = padding;
    }

    public void setDrawNumber(boolean flag){
        drawNumber = flag;
    }

    public void draw(float offsetX,float offsetY,boolean drawNumber){
        float length = itemTemplate.getNameImage().getWidth();
        float _x = 0;
        if(drawNumber) {
            /*numberText.setNumber(number);
            itemTemplate.getNameImage().setHorizontal(UIAlign.Align.LEFT);
            length += numberText.getWidth() + space_aspect*itemTemplate.getNameImage().getHeight();
            _x = itemTemplate.getNameImage().getWidth() - length / 2;
            itemTemplate.getNameImage().setX(_x);*/
        }else{
            itemTemplate.getNameImage().setHorizontal(UIAlign.Align.CENTOR);
        }
        itemTemplate.getNameImage().draw(offsetX,offsetY);
        if(drawNumber) {
            numberText.setNumber(number);
            numberText.draw(offsetX, offsetY);//offsetX+_x+space_aspect*itemTemplate.getNameImage().getHeight(),offsetY);
        }
    }

    public Bitmap getNameImage(){
        return itemTemplate.getNameImage().getImage();
    }

    public String getName(){return itemTemplate.getName();}

    public ItemTemplate getItemTemplate(){
        return itemTemplate;
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

    public void resetEffect(BattleAction ba){
        for(int n = 0; n < itemTemplate.effects.size();n++) {
            itemTemplate.effects.get(n).effectInit(this, ba);
        }
    }

    public void calcDamage(BattleAction ba){
        for(int n = 0; n < itemTemplate.effects.size();n++) {
            itemTemplate.effects.get(n).calcDamage(ba);
        }
    }

    public boolean drawEffect(float ox,float oy,float sx,float sy,float deg){
        boolean flag = true;
        boolean numFlag = false;

        for(int n = 0;n < itemTemplate.animations.size();n++) {
            Animation sa = itemTemplate.animations.get(n);
            flag = sa.draw(timeBuffer, ox, oy, sx, sy, deg) && flag;
        }

        for(int n = 0; n < itemTemplate.effects.size();n++) {
            itemTemplate.effects.get(n).drawEffect(ox, oy);
        }
        /*if(skillAnimations.size() > 1)
            flag = flag && skillAnimations.get(1).draw(timeBuffer,ox,oy,sx,sy,deg);
        else
            flag = flag && skillAnimations.get(0).draw(timeBuffer,ox,oy,sx,sy,deg);*/
        timeBuffer += Time.getDeltaTime();

        if(flag)
            timeBuffer = 0;
        return flag;
    }
}
