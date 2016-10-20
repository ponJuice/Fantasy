package jp.ac.dendai.c.jtp.Game.UIs.Screen;

import android.content.res.AssetManager;

import java.io.IOException;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Event.Event;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.Game.GameUI.QuestionBox;
import jp.ac.dendai.c.jtp.Game.MapSystem.Node;
import jp.ac.dendai.c.jtp.Game.UIs.Screen.BattleScreen.BattleScreen;
import jp.ac.dendai.c.jtp.Game.UIs.Transition.LoadingTransition.LoadingTransition;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.Button;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.ButtonListener;
import jp.ac.dendai.c.jtp.Game.UIs.UI.List.List;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.TextBox.AttackOwnerTextBox;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UI;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.TouchUtil.Input;
import jp.ac.dendai.c.jtp.openglesutil.Util.ImageReader;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;
import jp.ac.dendai.c.jtp.Game.MapSystem.Town;

/**
 * Created by テツヤ on 2016/10/10.
 */

public class DebugEventSelectScreen implements Screenable{
    protected boolean freeze;
    protected Event event;
    protected List list;
    protected Button toBattle;
    protected Button toMap;

    protected Node testNode1,testNode2,testNode3;
    protected Town t1,t2,t3;

    protected QuestionBox qb;

    protected float black_x = 0f,black_y = 0f;
    protected float black_lx = 0.4f,black_ly = 0.4f;
    protected float red_x = 0.0f,red_y = 0.0f;
    protected float red_lx = 0.2f,red_ly = 0.2f;
    protected float green_x = 0.4f,green_y = 0.4f;
    protected float green_lx = 0.2f,green_ly = 0.2f;

    public DebugEventSelectScreen(){

        list = new List(GLES20Util.getWidth_gl()/2f,0,Constant.battle_list_width,GLES20Util.getHeight_gl());
        AssetManager assetMgr = GameManager.act.getResources().getAssets();
        try {
            String files[] = assetMgr.list("Event");
            for(int i = 0; i < files.length; i++) {
                Button bt = new Button(0,0,0.5f,0.25f,files[i]);
                bt.setBitmap(Constant.getBitmap(Constant.BITMAP.system_button));
                bt.setButtonListener(new SelectEventButtonListener(files[i]));
                bt.setCriteria(UI.Criteria.Width);
                bt.setPadding(0.05f);
                list.addItem(bt);
            }
        } catch (IOException e) {
        }
        toBattle = new Button(0,0,0.1f,0.1f,"戦闘テスト");
        toBattle.useAspect(true);
        toBattle.setBitmap(Constant.getBitmap(Constant.BITMAP.system_button));
        toBattle.setBackImageCriteria(UI.Criteria.Height);
        toBattle.setHeight(0.1f);
        toBattle.setCriteria(UI.Criteria.Height);
        toBattle.setPadding(0.05f);
        toBattle.setHorizontal(UIAlign.Align.LEFT);
        toBattle.setVertical(UIAlign.Align.BOTTOM);
        toBattle.setButtonListener(new ButtonListener() {
            @Override
            public void touchDown(Button button) {

            }

            @Override
            public void touchHover(Button button) {

            }

            @Override
            public void touchUp(Button button) {
                LoadingTransition lt = LoadingTransition.getInstance();
                lt.initTransition(BattleScreen.class);
                GameManager.transition = lt;
                GameManager.isTransition = true;
            }
        });

        toMap = new Button(0,0.15f,0.1f,0.1f,"マップへ");
        toMap.useAspect(true);
        toMap.setBitmap(Constant.getBitmap(Constant.BITMAP.system_button));
        toMap.setBackImageCriteria(UI.Criteria.Height);
        toMap.setHeight(0.1f);
        toMap.setCriteria(UI.Criteria.Height);
        toMap.setPadding(0.05f);
        toMap.setHorizontal(UIAlign.Align.LEFT);
        toMap.setVertical(UIAlign.Align.BOTTOM);
        toMap.setButtonListener(new ButtonListener() {
            @Override
            public void touchDown(Button button) {

            }

            @Override
            public void touchHover(Button button) {

            }

            @Override
            public void touchUp(Button button) {
                LoadingTransition lt = LoadingTransition.getInstance();
                lt.initTransition(MapScreen.class);
                GameManager.args = new Object[1];
                GameManager.args[0] = ImageReader.readImageToAssets(Constant.mapImageFile);
                GameManager.transition = lt;
                GameManager.isTransition = true;
            }
        });


        testNode1 = new Node();
        testNode2 = new Node();
        testNode3 = new Node();
        t1 = new Town();
        t1.debugSet(0,0);
        t2 = new Town();
        t2.debugSet(1f,1f);
        t3 = new Town();
        t3.debugSet(1.3f,0.2f);
        testNode1.debugSet(t1,t2,3);
        testNode2.debugSet(t2,t3,2);
        testNode3.debugSet(t3,t1,4);

        qb = new QuestionBox(Constant.getBitmap(Constant.BITMAP.system_message_box),"買いますか?","はい","いいえ");
        qb.setX(GLES20Util.getWidth_gl()/2f);
        qb.setY(GLES20Util.getHeight_gl()/2f);
    }

    @Override
    public void constract(Object[] args) {

    }

    @Override
    public void Proc() {
        if(freeze)
            return;
        list.proc();
        toBattle.proc();
        toMap.proc();
        t1.proc();
        t2.proc();
        t3.proc();
        qb.proc();
    }

    @Override
    public void Draw(float offsetX, float offsetY) {
        toBattle.draw(offsetX,offsetY);
        toMap.draw(offsetX,offsetY);

        list.draw(offsetX,offsetY);
        testNode1.drawNode(0,0,1);
        testNode2.drawNode(0,0,1);
        testNode3.drawNode(0,0,1);
        t1.drawIcon(offsetX,offsetY);
        t2.drawIcon(offsetX,offsetY);
        t3.drawIcon(offsetX,offsetY);

        qb.draw(offsetX,offsetY);
    }

    @Override
    public void Touch() {
        if(freeze)
            return;
        list.touch(Input.getTouchArray()[0]);
        toBattle.touch(Input.getTouchArray()[0]);
        toMap.touch(Input.getTouchArray()[0]);
        t1.touch(Input.getTouchArray()[0]);
        t2.touch(Input.getTouchArray()[0]);
        t3.touch(Input.getTouchArray()[0]);

        qb.touch(Input.getTouchArray()[0]);
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

    protected class SelectEventButtonListener implements ButtonListener{
        String fileName;
        public SelectEventButtonListener(String file){
            fileName = file;
        }
        @Override
        public void touchDown(Button button) {

        }

        @Override
        public void touchHover(Button button) {

        }

        @Override
        public void touchUp(Button button) {
            GameManager.stack.push(GameManager.nowScreen);
            LoadingTransition lt = LoadingTransition.getInstance();
            lt.initTransition(TalkScreen.class);
            GameManager.args = new Object[1];
            GameManager.args[0] = fileName;
            GameManager.transition = lt;
            GameManager.isTransition = true;
        }
    }
}
