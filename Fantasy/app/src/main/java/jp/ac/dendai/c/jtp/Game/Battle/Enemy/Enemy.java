package jp.ac.dendai.c.jtp.Game.Battle.Enemy;

import jp.ac.dendai.c.jtp.Game.Battle.BattleState.BattleState;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Image.Image;

/**
 * Created by Goto on 2016/09/16.
 */
public class Enemy extends BattleState{
    public Enemy(EnemyTemplate et){
        hp = et.hp;
        atk = et.atk;
        def = et.def;
        image = new Image(et.image);
    }
    public Image getImage(){
        return image;
    }
    public void draw(float offset_x,float offset_y){
        image.draw(offset_x,offset_y);
    }
}
