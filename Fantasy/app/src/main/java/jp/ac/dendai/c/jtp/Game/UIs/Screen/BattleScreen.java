package jp.ac.dendai.c.jtp.Game.UIs.Screen;

import jp.ac.dendai.c.jtp.Game.Battle.BattleManager;
import jp.ac.dendai.c.jtp.Game.Battle.Enemy.Enemy;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.TouchUtil.Input;
import jp.ac.dendai.c.jtp.Game.UIs.Transition.LoadingTransition.LoadingTransition;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.Button;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.ButtonListener;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Image.Image;
import jp.ac.dendai.c.jtp.fantasy.R;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by Goto on 2016/09/16.
 */
public class BattleScreen implements Screenable {
    protected BattleManager battleManager;
    protected boolean freeze = true;
    protected Image background;
    protected Enemy[] enemys;
    protected Button toDungeon;
    public BattleScreen(){
        background = new Image(GLES20Util.loadBitmap(R.mipmap.dungeon_01));
        background.setX(GLES20Util.getWidth_gl()/2f);
        background.setY(GLES20Util.getHeight_gl()/2f);
        background.setHeight(GLES20Util.getHeight_gl());
        battleManager = new BattleManager();

        toDungeon = new Button(0,0.9f,0.8f,0.7f,"町へ");
        toDungeon.setPadding(0.08f);
        toDungeon.setCriteria(Button.CRITERIA.HEIGHT);
        toDungeon.setBackground(Constant.getBitmap(Constant.BITMAP.system_button));
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
                lt.initTransition(TownScreen.class);
                GameManager.transition = lt;
                GameManager.isTransition = true;
            }
        });
    }
    @Override
    public void Proc() {
        if(freeze)
            return;
        battleManager.proc();
        toDungeon.proc();
    }

    @Override
    public void Draw(float offsetX, float offsetY) {
        background.draw();
        battleManager.draw();
        toDungeon.draw();
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
