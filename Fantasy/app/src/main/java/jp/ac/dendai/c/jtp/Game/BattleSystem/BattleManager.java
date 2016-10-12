package jp.ac.dendai.c.jtp.Game.BattleSystem;

import java.util.Arrays;

import jp.ac.dendai.c.jtp.Game.BattleSystem.Enemy.Enemy;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Enemy.EnemyTemplate;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Enum.BattleStateEnum;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Player.Player;
import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by Goto on 2016/09/16.
 */
public class BattleManager {
    protected Attackable[] list;
    protected Attackable[] enemyList;
    protected BattleStateEnum battleStateEnum = BattleStateEnum.All_Start;
    protected int turnIndex = 0;
    protected BattleAction battleAction;

    public BattleManager(EnemyTemplate[] enemys){
        //敵及びプレイヤーを含むリストと、敵のみのリストを初期化
        enemyList = new Attackable[enemys.length];
        list = new Attackable[enemys.length+1];
        for(int n = 0;n < enemyList.length;n++){
            float ox = GLES20Util.getWidth_gl()/(float)(enemyList.length+1) * (float)(n+1);
            float oy = GLES20Util.getHeight_gl()/3f*2f;
            enemyList[n] = new Enemy(enemys[n],ox,oy);
            list[n] = enemyList[n];
        }
        //リストの一番最後にプレイヤーを挿入
        list[list.length-1] = new Player(GameManager.getPlayerData());
        battleAction = new BattleAction();
    }

    public Attackable[] getEnemyList(){
        return enemyList;
    }

    public void proc(){
        if(battleStateEnum == BattleStateEnum.All_Start){
            //俊敏性でソート
            Arrays.sort(list);
            battleStateEnum = BattleStateEnum.Turn_Start;
        }else if(battleStateEnum == BattleStateEnum.Turn_Start){
            //ターンの開始
            //if(list[turnIndex].action(battleAction,this)){
               // battleStateEnum = BattleStateEnum.Turn_End;
            //}
        }else if(battleStateEnum == BattleStateEnum.Turn_End){

        }
    }
}
