package jp.ac.dendai.c.jtp.Game.GameUI;

import jp.ac.dendai.c.jtp.Game.BattleSystem.Attackable;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleAction;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.NumberText;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Util.Time;

/**
 * Created by Goto on 2016/10/13.
 */

public class DamageEffect {
    protected final static String fontName="メイリオ";
    protected NumberText nt;
    protected float timeBuffer = 0;
    public DamageEffect(){
        nt = new NumberText(fontName);
        nt.setHorizontal(UIAlign.Align.CENTOR);
        nt.setVertical(UIAlign.Align.CENTOR);
    }
    public void setNumber(int number){
        nt.setNumber(number);
    }
    public void reset(BattleAction ba){
        nt.setNumber(ba.getDamage());
        nt.setX(ba.target.getX());
        nt.setY(ba.target.getY());
        if(ba.target.getAttackerType() == Attackable.AttackerType.Enemy){
            nt.setHeight(Constant.damage_number_height_enemy);
        }else {
            nt.setHeight(Constant.damage_number_height_player);
        }
        timeBuffer = 0;
    }
    public boolean draw(float offsetX,float offsetY){
        nt.draw(offsetX,offsetY);
        if(timeBuffer >= Constant.damage_number_time){
            return true;
        }
        timeBuffer += Time.getDeltaTime();
        return false;
    }
}
