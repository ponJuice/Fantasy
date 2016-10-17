package jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PlayerState.State;

import android.util.Log;

import java.util.ArrayDeque;

import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PiplineState.State.APipelineState;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PiplineState.State.PipelineStatePattern;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PlayerState.PlayerState;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Player.Player;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.Button;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.ButtonListener;
import jp.ac.dendai.c.jtp.Game.UIs.UI.List.PlayerActionList;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UI;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.TouchUtil.Input;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by wark on 2016/10/17.
 */

public class PlayerStatePattern {
    protected final static String backText = "戻る";
    protected final static float padding = 0.1f;
    protected final static float backButtonHeight = 0.2f;

    protected APlayerState state;
    protected PlayerState playerState;
    protected PlayerActionSelectState pass;
    protected PlayerEnemySelectState pess;
    protected PlayerItemSelectState piss;
    protected PlayerSkillSelectState psss;
    protected ArrayDeque<APlayerState> stateStack;
    protected Button backButton;
    protected PlayerActionList pal;

    public PlayerStatePattern(PlayerState playerState){
        this.playerState = playerState;
        pass = new PlayerActionSelectState(this);
        pess = new PlayerEnemySelectState(this);
        piss = new PlayerItemSelectState(this);
        psss = new PlayerSkillSelectState(this,playerState.getBattleState().getBattleManager().getPlayer());
        stateStack = new ArrayDeque<>();

        backButton = new Button(0,0,1,1,"戻る");
        backButton.setBitmap(Constant.getBitmap(Constant.BITMAP.system_button));
        backButton.useAspect(true);
        backButton.setHeight(backButtonHeight);
        backButton.setCriteria(UI.Criteria.Height);
        backButton.setPadding(padding);
        backButton.setHorizontal(UIAlign.Align.RIGHT);
        backButton.setVertical(UIAlign.Align.BOTTOM);
        backButton.setX(GLES20Util.getWidth_gl());
        backButton.setY(0);
        backButton.setButtonListener(new ButtonListener() {
            @Override
            public void touchDown(Button button) {

            }

            @Override
            public void touchHover(Button button) {

            }

            @Override
            public void touchUp(Button button) {
                backState();
            }
        });

        init();
    }

    public void changeState(APlayerState state, PlayerActionList list){
        if(this.state != null) {
            this.state.clean();
            stateStack.push(this.state);
            Log.d("PlayerStatePattaern","Stack : "+stateStack);
        }
        this.state = state;
        this.state.init(pal);
        //pal = this.state.getList();

        Log.d("PlayerStatePattern cs",pal.toString());
    }

    public void backState(){
        this.state.clean();
        if(stateStack.size() <= 0)
            this.state = getPlayerActionSelectState();
        else
            this.state = stateStack.pop();
        this.state.init(pal);

        Log.d("PlayerStatePattern bs",pal.toString());
    }

    public PlayerState getPlayerState(){
        return playerState;
    }

    public void endState(){
        this.state.clean();
        this.state = null;
        playerState.getBattleState().changeState(playerState.getBattleState().getPipelineState());
    }

    public PlayerActionSelectState getPlayerActionSelectState(){
        Log.d("PalyerStartPattern","Change State pass");
        return pass;
    }

    public PlayerEnemySelectState getPlayerEnemySelectState(){
        Log.d("PlayerStatePattern","Change State pess");
        return pess;
    }

    public PlayerItemSelectState getPlayerItemSelectState(){
        Log.d("PlayerStatePattern","Change State piss");
        return piss;
    }

    public PlayerSkillSelectState getPlayerSkillSelectState(){
        Log.d("PlayerStatePattern","Change State psss");
        return psss;
    }

    public void init(){
        stateStack.clear();
        pal = getPlayerActionSelectState().list;
        changeState(getPlayerActionSelectState(),null);
    }

    public boolean proc(){
        if(stateStack.size() >= 1){
            backButton.touch(Input.getTouchArray()[0]);
            backButton.proc();
        }
        state.proc();
        pal.touch(Input.getTouchArray()[0]);
        pal.proc();
        return false;
    }

    public void draw(float offsetX,float offsetY){
        if(stateStack.size() >= 1){
            backButton.draw(offsetX,offsetY);
        }
        pal.draw(offsetX,offsetY);
        state.draw(offsetX,offsetY);
    }
}
