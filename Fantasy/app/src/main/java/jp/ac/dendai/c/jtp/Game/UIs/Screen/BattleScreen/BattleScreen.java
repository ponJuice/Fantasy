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
    protected int enemyMax = 3;
    protected BattleManager battleManager;
    protected boolean freeze = true;
    protected Image background;
    protected Button toDungeon;
    protected DrawMachine defaultMachine;

    public BattleScreen(){

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
        //args
        // 0:背景画像
        // 1:個別指定する敵の数（0の場合はランクで抽選）
        // 2:敵のID　(args[1]が0の場合のみ、ランクの値)
        background = new Image(ImageReader.readImageToAssets(Constant.image_file_directory + (String)args[0]));
        background.useAspect(true);
        background.setX(GLES20Util.getWidth_gl()/2f);
        background.setY(GLES20Util.getHeight_gl()/2f);
        background.setHeight(GLES20Util.getHeight_gl());

        EnemyTemplate[] et;
        DataBase db = GameManager.getDataBase();
        int args1 = (int)args[1];
        if(args1 > 0){
            //0より大きいの場合個別指定した敵を設定　args[1]の値は敵の数
            et = new EnemyTemplate[args1];
            for(int n = 0;n < args1;n++){
                et[n] = db.getEnemy((String)args[n+2]);
            }

        }else {
            //そうでない場合はランクによってランダム抽選
            et = new EnemyTemplate[Constant.getRandom().nextInt(enemyMax) + 1];
            for (int n = 0; n < et.length; n++) {
                et[n] = db.getEnemy((int)args[2]);
            }
        }
        battleManager = new BattleManager(et);
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
        defaultMachine.draw(battleManager,offsetX,offsetY);
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
