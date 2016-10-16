package jp.ac.dendai.c.jtp.Game.UIs.Screen.BattleScreen;

import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleManager;
import jp.ac.dendai.c.jtp.Game.BattleSystem.DrawMachine.DefaultDrawMachine;
import jp.ac.dendai.c.jtp.Game.BattleSystem.DrawMachine.DrawMachine;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Enemy.EnemyTemplate;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.DataBase;
import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.Game.UIs.Screen.DebugEventSelectScreen;
import jp.ac.dendai.c.jtp.Game.UIs.Screen.Screenable;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UI;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.TouchUtil.Input;
import jp.ac.dendai.c.jtp.Game.UIs.Transition.LoadingTransition.LoadingTransition;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.Button;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.ButtonListener;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Image.Image;
import jp.ac.dendai.c.jtp.fantasy.R;
import jp.ac.dendai.c.jtp.openglesutil.Util.ImageReader;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by Goto on 2016/09/16.
 */
public class BattleScreen implements Screenable {
    protected BattleManager battleManager;
    protected boolean freeze = true;
    protected Image background;
    protected Button toDungeon;
    protected DrawMachine defaultMachine;

    public BattleScreen(){
        EnemyTemplate[] et = new EnemyTemplate[2];
        DataBase db = GameManager.getDataBase();
        et[0] = GameManager.getDataBase().getEnemy("blue_slime");
        et[1] = GameManager.getDataBase().getEnemy("orange_slime");

        background = new Image(GLES20Util.loadBitmap(R.mipmap.dungeon_01));
        background.setX(GLES20Util.getWidth_gl()/2f);
        background.setY(GLES20Util.getHeight_gl()/2f);
        background.setHeight(GLES20Util.getHeight_gl());
        battleManager = new BattleManager(et);

        defaultMachine = new DefaultDrawMachine();

        toDungeon = new Button(0,0.9f,0.8f,0.7f,"町へ");
        toDungeon.setBitmap(Constant.getBitmap(Constant.BITMAP.system_button));
        toDungeon.useAspect(true);
        toDungeon.setBackImageCriteria(UI.Criteria.Height);
        toDungeon.setHeight(0.2f);
        toDungeon.setPadding(0.08f);
        toDungeon.setCriteria(UI.Criteria.Height);
        toDungeon.setHorizontal(UIAlign.Align.LEFT);
        toDungeon.setX(0);
        toDungeon.setY(GLES20Util.getHeight_gl());
        toDungeon.setVertical(UIAlign.Align.TOP);
        toDungeon.setButtonListener(new ButtonListener() {
            @Override
            public void touchDown(Button button) {

            }

            @Override
            public void touchHover(Button button) {

            }

            @Override
            public void touchUp(Button button) {
                LoadingTransition lt = LoadingTransition.getInstance();
                lt.initTransition(DebugEventSelectScreen.class);
                GameManager.transition = lt;
                GameManager.isTransition = true;
            }
        });
    }

    @Override
    public void constract(Object[] args) {

    }

    @Override
    public void Proc() {
        if(freeze)
            return;
        toDungeon.proc();

        battleManager.proc();
    }

    @Override
    public void Draw(float offsetX, float offsetY) {
        background.draw(offsetX,offsetY);
        toDungeon.draw(offsetX,offsetY);
        defaultMachine.draw(battleManager);
        battleManager.draw(offsetX,offsetY);
    }

    @Override
    public void Touch() {
        if(freeze)
            return;
        for(int n = 0; n < Input.getMaxTouch(); n++){
            toDungeon.touch(Input.getTouchArray()[n]);
        }
    }

    @Override
    public void death() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void freeze() {
        freeze = true;
    }

    @Override
    public void unFreeze() {
        freeze = false;
    }
}
