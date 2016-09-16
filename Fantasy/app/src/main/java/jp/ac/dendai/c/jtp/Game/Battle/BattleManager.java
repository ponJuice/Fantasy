package jp.ac.dendai.c.jtp.Game.Battle;

import jp.ac.dendai.c.jtp.Game.Battle.BattleState.BattleState;
import jp.ac.dendai.c.jtp.Game.Battle.Enemy.Enemy;
import jp.ac.dendai.c.jtp.Game.Battle.Enemy.EnemyTemplate;
import jp.ac.dendai.c.jtp.Game.Battle.Player.Player;
import jp.ac.dendai.c.jtp.Game.Battle.Player.PlayerData;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.UIs.Screen.BattleScreen;
import jp.ac.dendai.c.jtp.UIs.Transition.LoadingTransition.LoadingTransition;
import jp.ac.dendai.c.jtp.UIs.UI.Button.Button;
import jp.ac.dendai.c.jtp.UIs.UI.Button.ButtonListener;
import jp.ac.dendai.c.jtp.UIs.UI.Image.Image;
import jp.ac.dendai.c.jtp.fantasy.R;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by Goto on 2016/09/16.
 */
public class BattleManager {
    protected BattleState[] battleState;
    protected Enemy[] enemys;
    protected Player player;
    public BattleManager(){
        EnemyTemplate e = new EnemyTemplate();
        e.image = new Image(GLES20Util.loadBitmap(R.mipmap.monst_test));
        e.image.setHeight(0.4f);
        battleState = new BattleState[4];
        enemys = new Enemy[3];
        for(int n = 0;n < enemys.length;n++){
            enemys[n] = new Enemy(e);
            battleState[n] = enemys[n];
        }
        PlayerData pd = new PlayerData();
        player = new Player(pd);
        battleState[battleState.length-1] = player;
        for(int n = 0;n < enemys.length;n++){
            int m = 2*n + 1;
            float a = GLES20Util.getWidth_gl() / (float)(enemys.length*2);
            enemys[n].getImage().setX(a*(float)m);
            enemys[n].getImage().setY(GLES20Util.getHeight_gl()/2f+0.1f);
        }

    }
    public void proc(){
    }

    public void draw(){
        for(int n = 0;n < enemys.length;n++){
            enemys[n].getImage().draw();
        }

    }
}
