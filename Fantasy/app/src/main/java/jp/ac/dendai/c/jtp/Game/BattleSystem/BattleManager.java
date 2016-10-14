package jp.ac.dendai.c.jtp.Game.BattleSystem;

import java.util.Arrays;

import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.BattleStatePattern;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PiplineState.State.APipelineState;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Enemy.Enemy;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Enemy.EnemyTemplate;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Enum.BattleStateEnum;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Player.Player;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.Game.GameUI.DamageEffect;
import jp.ac.dendai.c.jtp.Game.UIs.Screen.BattleScreen.UserInterface.PlayerUI;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by Goto on 2016/09/16.
 */
public class BattleManager {
    enum BATTLE_STATE{
        complete,		//一巡した
        effect_start,			//エフェクト描画中
        effect_end,             //エフェクト描画終了
        damage_effect_start,
        damage_effect_end,
        status_ailments_start,
        status_ailments_end,
        turn_end,		//行動、エフェクト描画終了
    }

    protected Attackable[] list;
    protected Attackable[] enemyList;
    protected Player player;
    protected int turnIndex = 0;
    protected BattleAction battleAction;
    protected Attackable actor;
    protected PlayerUI p_ui;
    protected DamageEffect de;

    protected BattleStatePattern bsp;

    BATTLE_STATE state; //ゲーム側の状態

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
        player = new Player(GameManager.getPlayerData());
        player.setX(GLES20Util.getWidth_gl()/2f);
        player.setY(GLES20Util.getHeight_gl()/2f);
        list[list.length-1] = player;


        p_ui = new PlayerUI(player);
        //Arrays.sort(list);
        state = BATTLE_STATE.turn_end;
        battleAction = new BattleAction();
        actor =list[turnIndex];

        de = new DamageEffect();

        bsp = new BattleStatePattern(this);
    }

    public BattleAction getBattleAction(){
        return battleAction;
    }

    public Attackable[] getActorList(){return list;}

    public Attackable[] getEnemyList(){
        return enemyList;
    }

    public Player getPlayer(){
        return player;
    }

    public void proc(){
        bsp.proc();
        //プレイヤーの行動選択
        //行動開始


        /*if(turnIndex >= list.length){
            //もしインデックスがリストを超えた場合は一巡したとみなす
            turnIndex = 0;
            state = BATTLE_STATE.complete;
        }

        if(state == BATTLE_STATE.complete){
            //敵のリストをソート
            Arrays.sort(list);
            actor = list[0];
            state = BATTLE_STATE.turn_end;
        }else if(state == BATTLE_STATE.turn_end){	//新たなターン開始
            //行動すべきキャラクターを選ぶ
            actor = list[turnIndex];

            //行動選択
            actor.action(this);

            if(battleAction.isEnd()){	//選択中ならfalseが返る
                //選択終了
                battleAction.calcDamage();            //ダメージ計算
                battleAction.influenceDamage();
                p_ui.damage(battleAction);
                state = BATTLE_STATE.effect_start;	//状態をエフェクト表示状態に
                //effectDrawer.init();			//エフェクトの表示を初期化
                turnIndex++;	//次のキャラクターが行動できるようにインデックスをインクリメント
                return;
            }
        }else if(state == BATTLE_STATE.effect_start){
            //エフェクト描画中は操作を受け付けない
        }else if(state == BATTLE_STATE.effect_end){
            //エフェクトの描画が終わったら、ダメージなどを反映する
        }
        p_ui.proc();*/
    }

    public void draw(float offset_x,float offset_y){
        p_ui.draw(offset_x,offset_y);
        bsp.drwa(offset_x,offset_y);
        /*actor.draw(offset_x,offset_y);
        if(state == BATTLE_STATE.effect_start){
            //エフェクトの表示
            float ox = battleAction.target.getX();
            float oy = battleAction.target.getY();
            float sx = Constant.enemy_damage_size_x;
            float sy = Constant.enemy_damage_size_y;
            if(battleAction.target.getAttackerType() == Attackable.AttackerType.Friend){
                //行動対象がプレイヤーなら位置を中心、サイズを二倍
                ox = GLES20Util.getWidth_gl() / 2f;
                oy = GLES20Util.getHeight_gl() / 2f;
                sx = Constant.friend_damage_size_x;
                sy = Constant.friend_damage_size_y;
            }

            if(battleAction.drawEffect(ox,oy,sx,sy,0)){
                state = BATTLE_STATE.damage_effect_start;
                de.reset(battleAction);
            }
        }else if(state == BATTLE_STATE.damage_effect_start){
            //ダメージの表示
            if(de.draw(offset_x,offset_y) && p_ui.isEndGageEffect()){
                //終了するとtrueが帰る
                state = BATTLE_STATE.turn_end;
            }
        }*/
    }
}
