package jp.ac.dendai.c.jtp.Game.BattleSystem.DrawMachine;

import jp.ac.dendai.c.jtp.Game.BattleSystem.Attackable;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleManager;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.BattleStateMachine;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;
import jp.ac.dendai.c.jtp.openglesutil.graphic.blending_mode.GLES20COMPOSITIONMODE;

/**
 * Created by Goto on 2016/10/12.
 */

public class DefaultDrawMachine implements DrawMachine {
    @Override
    public void draw(BattleManager manager) {
        Attackable[] list = manager.getEnemyList();
        for(int n = 0;n < list.length;n++){
            //敵が死んでいる場合は表示しない
            if(list[n].isDead())
                continue;
            //敵の表示
            GLES20Util.DrawGraph(list[n].getX(),list[n].getY(), Constant.enemy_size_x,Constant.enemy_size_y,list[n].getImage(),1, GLES20COMPOSITIONMODE.ALPHA);
            	//draw(オフセット、オフセット、サイズ、サイズ、回転角度（度）)
        }
    }

    @Override
    public void draw(BattleStateMachine bsm) {

    }
}
