package jp.ac.dendai.c.jtp.Game.BattleSystem;

import android.media.MediaPlayer;

import java.util.ArrayList;
import java.util.Arrays;

import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.BattleStatePattern;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PiplineState.State.APipelineState;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Enemy.Enemy;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Enemy.EnemyTemplate;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Enum.BattleStateEnum;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Player.Player;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Skill.Skill;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.Game.GameUI.DamageEffect;
import jp.ac.dendai.c.jtp.Game.GameUI.Gage;
import jp.ac.dendai.c.jtp.Game.UIs.Screen.BattleScreen.UserInterface.PlayerUI;
import jp.ac.dendai.c.jtp.fantasy.R;
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
    protected MediaPlayer mp;
    protected MediaPlayer clearMp;

    protected Attackable[] list;
    protected Enemy[] enemyList;
    protected Player player;
    protected int turnIndex = 0;
    protected BattleAction battleAction;
    protected Attackable actor;
    protected PlayerUI p_ui;
    protected DamageEffect de;
    protected Gage hpGage;
    protected ArrayList<Skill> countMaxSkill;

    protected BattleStatePattern bsp;

    BATTLE_STATE state; //ゲーム側の状態

    public BattleManager(EnemyTemplate[] enemys){
        countMaxSkill = new ArrayList<>();

        //敵及びプレイヤーを含むリストと、敵のみのリストを初期化
        enemyList = new Enemy[enemys.length];
        list = new Attackable[enemys.length+1];
        for(int n = 0;n < enemyList.length;n++){
            float ox = GLES20Util.getWidth_gl()/(float)(enemyList.length+1) * (float)(n+1);
            float oy = GLES20Util.getHeight_gl()/3f*2f;
            enemyList[n] = new Enemy(enemys[n],ox,oy);
            list[n] = enemyList[n];
        }
        //リストの一番最後にプレイヤーを挿入
        player = new Player(GameManager.getPlayerData());
        player.setBattleAction(new BattleAction(this));
        player.setX(GLES20Util.getWidth_gl()/2f);
        player.setY(GLES20Util.getHeight_gl()/2f);
        list[list.length-1] = player;

        p_ui = new PlayerUI(player);

        player.setHpGage(p_ui.getHpGage());
        player.setMpGage(p_ui.getMpGage());
        //Arrays.sort(list);
        state = BATTLE_STATE.turn_end;
        battleAction = new BattleAction(this);
        actor =list[turnIndex];

        de = new DamageEffect();

        bsp = new BattleStatePattern(this);


        GameManager.startBGM(R.raw.kaisen,true);

    }

    public MediaPlayer getMediaPlayer(){
        return mp;
    }

    public void startClearMp(){
        GameManager.startBGM(R.raw.victory,false);
    }

    public void addCountMaxSkill(Skill skill){
        countMaxSkill.add(skill);
    }

    public ArrayList<Skill> getCountMaxSkillList(){
        return countMaxSkill;
    }

    public BattleAction getBattleAction(){
        return battleAction;
    }

    public Attackable[] getActorList(){return list;}

    public Enemy[] getEnemyList(){
        return enemyList;
    }

    public Player getPlayer(){
        return player;
    }

    public void proc(){
        bsp.proc();
    }

    public void draw(float offset_x,float offset_y){
        p_ui.draw(offset_x,offset_y);
        bsp.drwa(offset_x,offset_y);
    }
}
