package jp.ac.dendai.c.jtp.Game.UIs.Screen;

import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.util.Log;

import java.util.ArrayList;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Event.EventManager;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Flag.FlagManager;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Player.PlayerData;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.Game.GameUI.PlayerStatas;
import jp.ac.dendai.c.jtp.Game.GameUI.QuestionBox;
import jp.ac.dendai.c.jtp.Game.Item.Item;
import jp.ac.dendai.c.jtp.Game.MapSystem.CursorAnimator;
import jp.ac.dendai.c.jtp.Game.MapSystem.Dungeon.Dungeon;
import jp.ac.dendai.c.jtp.Game.MapSystem.Dungeon.DungeonNode;
import jp.ac.dendai.c.jtp.Game.MapSystem.Dungeon.DungeonPoint;
import jp.ac.dendai.c.jtp.Game.MapSystem.Node;
import jp.ac.dendai.c.jtp.Game.MapSystem.Town;
import jp.ac.dendai.c.jtp.Game.UIs.Screen.BattleScreen.BattleScreen;
import jp.ac.dendai.c.jtp.Game.UIs.Transition.LoadingTransition.LoadingTransition;
import jp.ac.dendai.c.jtp.Game.UIs.Transition.StackLoadingTransition.StackLoadingTransition;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.Button;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.ButtonListener;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Image.Image;
import jp.ac.dendai.c.jtp.Game.UIs.UI.List.List;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UI;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.TouchUtil.Input;
import jp.ac.dendai.c.jtp.TouchUtil.Touch;
import jp.ac.dendai.c.jtp.fantasy.R;
import jp.ac.dendai.c.jtp.openglesutil.Util.ImageReader;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by Goto on 2016/10/24.
 */

public class DungeonScreen implements Screenable{
    protected enum Mode{
        moveTownEffect,
        arriveTown,
        arriveEncount,
        arriveStart,
        returnScreen,
        menu,
        item_select,
        non
    }
    protected FlagManager.ScreenType screenType = FlagManager.ScreenType.dungeon;
    protected PlayerData playerData;
    protected QuestionBox battleQuestion;
    protected QuestionBox inTownQuestion;
    protected QuestionBox backMapQuestion;
    protected float cursorAnimTime = 0.5f;
    protected DungeonScreen.Mode mode = DungeonScreen.Mode.non;
    protected int encount = 0;
    protected float cursorHeight = 0.1f;
    protected Image cursor;
    protected float height = 1f;
    protected Image background;
    protected Dungeon dungeon;
    protected Button menuButton;
    protected boolean isMenu = false;
    protected DungeonNode toPoint;
    protected CursorAnimator cursorAnimator;
    protected DungeonPoint nowPoint;
    protected ArrayList<DungeonIcon> icons;
    protected boolean freeze = true;
    protected Item selectItem;
    protected MenuDialog md;

    public DungeonScreen(){
        playerData = GameManager.getPlayerData();

        background = new Image();
        menuButton = new Button(0,0,1f,1f,"アイテム");
        menuButton.setBitmap(Constant.getBitmap(Constant.BITMAP.system_button));
        menuButton.useAspect(true);
        menuButton.setCriteria(UI.Criteria.Height);
        menuButton.setPadding(0.05f);
        menuButton.setHeight(0.1f);
        menuButton.setHorizontal(UIAlign.Align.LEFT);
        menuButton.setVertical(UIAlign.Align.TOP);
        menuButton.setButtonListener(new ButtonListener() {
            @Override
            public void touchDown(Button button) {

            }

            @Override
            public void touchHover(Button button) {

            }

            @Override
            public void touchUp(Button button) {
                mode = Mode.menu;
            }
        });

        cursor = new Image();
        cursor.setImage(Constant.getBitmap(Constant.BITMAP.system_cursor));
        cursor.useAspect(true);
        cursor.setVertical(UIAlign.Align.BOTTOM);
        cursor.setHeight(cursorHeight);
        cursor.setX(GameManager.getPlayerData().getTown().getX());
        cursor.setY(GameManager.getPlayerData().getTown().getY());

        cursorAnimator = new CursorAnimator(cursor);

        battleQuestion = new QuestionBox(Constant.getBitmap(Constant.BITMAP.system_message_box),"進みますか？","進む","戻る");
        battleQuestion.setX(GLES20Util.getWidth_gl()/2f + GLES20Util.getCameraPosX());
        battleQuestion.setY(GLES20Util.getHeight_gl()/2f + GLES20Util.getCameraPosY());
        battleQuestion.setYesButtonListener(new DungeonScreen.BattleYesButtonListener());
        battleQuestion.setNoButtonListener(new DungeonScreen.BattleNoButtonListener());
        inTownQuestion = new QuestionBox(Constant.getBitmap(Constant.BITMAP.system_message_box),"戦いますか？","はい","いいえ");
        inTownQuestion.setX(GLES20Util.getWidth_gl()/2f + GLES20Util.getCameraPosX());
        inTownQuestion.setY(GLES20Util.getHeight_gl()/2f + GLES20Util.getCameraPosY());
        inTownQuestion.setNoButtonListener(new DungeonScreen.InTownNoListener());
        inTownQuestion.setYesButtonListener(new DungeonScreen.InTownYesListener());
        backMapQuestion = new QuestionBox(Constant.getBitmap(Constant.BITMAP.system_message_box),"出ますか？","はい","いいえ");
        backMapQuestion.setX(GLES20Util.getWidth_gl()/2f + GLES20Util.getCameraPosX());
        backMapQuestion.setY(GLES20Util.getHeight_gl()/2f + GLES20Util.getCameraPosY());
        backMapQuestion.setNoButtonListener(new BackMapNoButtonListener());
        backMapQuestion.setYesButtonListener(new BackMapYesButtonListener());

        md = new MenuDialog();
    }

    public CursorAnimator getCursorAnimator(){
        return cursorAnimator;
    }

    @Override
    public void constract(Object[] args) {
        String dungeonName = (String)args[1];
        dungeon= GameManager.getDataBase().getDungeon(dungeonName);
        icons = createPointIconArray(dungeon);
        nowPoint = dungeon.getStartPoint();
        cursorAnimator.reset(nowPoint.getX(),nowPoint.getY());
        background.setImage(ImageReader.readImageToAssets(Constant.image_file_directory + dungeon.getBackground()));
        background.useAspect(true);
        background.setHeight(height);
        background.setHorizontal(UIAlign.Align.LEFT);
        background.setVertical(UIAlign.Align.BOTTOM);
        //townIcons = createTownIconArray(GameManager.getDataBase().getTownList().toArray(new Town[0]));
        //nodes = GameManager.getDataBase().getNodeList();
    }

    public ArrayList<DungeonIcon> createPointIconArray(Dungeon _dungeon){
        ArrayList<DungeonIcon> da = new ArrayList<>();
        for(int n = 0;n < _dungeon.getPoints().size();n++){
            da.add(new DungeonIcon(_dungeon.getPoints().get(n)));
        }
        return da;
    }

    @Override
    public void Proc() {
        if(freeze)
            return;
        if(mode == DungeonScreen.Mode.moveTownEffect){
            if(cursorAnimator.proc()){
                encount++;
                if(encount <= 0){
                    encount = 0;
                    if(nowPoint.getEnemies().size() != 0) {
                        mode = Mode.arriveTown;
                        inTownQuestion.setX(GLES20Util.getWidth_gl() / 2f + GLES20Util.getCameraPosX());
                        inTownQuestion.setY(GLES20Util.getHeight_gl() / 2f + GLES20Util.getCameraPosY());
                        inTownQuestion.startFadeInAnimation();
                    }else if(nowPoint.isStart()) {
                        mode = Mode.arriveStart;
                        backMapQuestion.setX(GLES20Util.getWidth_gl() / 2f + GLES20Util.getCameraPosX());
                        backMapQuestion.setY(GLES20Util.getHeight_gl() / 2f + GLES20Util.getCameraPosY());
                        backMapQuestion.startFadeInAnimation();
                    }else{
                        mode = Mode.non;
                    }
                }else if(encount >= (toPoint.getEncount()+1)){
                    //街に着いた
                    //playerData.setTown(toPoint.getOtherPoint(nowPoint));
                    nowPoint = toPoint.getOtherPoint(nowPoint);
                    encount = 0;
                    if(nowPoint.getEnemies().size() != 0) {
                        mode = Mode.arriveTown;
                        inTownQuestion.setX(GLES20Util.getWidth_gl() / 2f + GLES20Util.getCameraPosX());
                        inTownQuestion.setY(GLES20Util.getHeight_gl() / 2f + GLES20Util.getCameraPosY());
                        inTownQuestion.startFadeInAnimation();
                    }else if(nowPoint.isStart()){
                        mode = Mode.arriveStart;
                        backMapQuestion.setX(GLES20Util.getWidth_gl() / 2f + GLES20Util.getCameraPosX());
                        backMapQuestion.setY(GLES20Util.getHeight_gl() / 2f + GLES20Util.getCameraPosY());
                        backMapQuestion.startFadeInAnimation();
                    }else{
                        if(nowPoint.procFlag()) {
                            init();
                        }
                        mode = Mode.non;
                    }
                }else{
                    //エンカウント
                    mode = DungeonScreen.Mode.arriveEncount;
                    battleQuestion.setX(GLES20Util.getWidth_gl()/2f + GLES20Util.getCameraPosX());
                    battleQuestion.setY(GLES20Util.getHeight_gl()/2f + GLES20Util.getCameraPosY());
                    battleQuestion.startFadeInAnimation();
                    GameManager.stack.push(this);
                    GameManager.args = new Object[3];
                    GameManager.args[0] = toPoint.getBackFile();
                    GameManager.args[1] = 0;
                    GameManager.args[2] = toPoint.getRank();
                    LoadingTransition lt = LoadingTransition.getInstance();
                    lt.initTransition(BattleScreen.class);
                    GameManager.transition = lt;
                    GameManager.isTransition = true;

                    mode = Mode.returnScreen;
                }
            }
        }else {
            if(mode == Mode.arriveTown){
                if(inTownQuestion.proc()){
                    if(!inTownQuestion.getEnabled())
                        mode = Mode.non;
                }
            }else if(mode == Mode.arriveEncount){
                //トランジョン表示
            }else if(mode == Mode.returnScreen){
                battleQuestion.proc();
            }else if(mode == Mode.menu){
                md.proc();
            }else if(mode == Mode.arriveStart){
                if(backMapQuestion.proc()){
                    if(!backMapQuestion.getEnabled())
                        mode = Mode.non;
                }
            }else if(mode == Mode.non) {
                menuButton.setX(GLES20Util.getCameraPosX());
                menuButton.setY(GLES20Util.getCameraPosY() + GLES20Util.getHeight_gl());
                menuButton.proc();
            }else if(mode == Mode.item_select){
                selectItem.influence(playerData);
                mode = Mode.menu;
            }
        }
    }

    @Override
    public void Draw(float offsetX, float offsetY) {
        background.draw(offsetX,offsetY);
        for(int n = 0;n < dungeon.getNodes().size();n++){
            dungeon.getNodes().get(n).drawNode(offsetX,offsetY,1);
        }
        for(int n = 0;n < icons.size();n++){
            icons.get(n).draw(offsetX,offsetY);
        }
        //cursor.draw(offsetX,offsetY);
        cursorAnimator.draw(offsetX,offsetY);
        if(mode == Mode.non) {
            menuButton.draw(offsetX, offsetY);
        }
        if(mode == DungeonScreen.Mode.arriveTown){
            inTownQuestion.draw(offsetX, offsetY);
        }else if(mode == DungeonScreen.Mode.returnScreen){
            battleQuestion.draw(offsetX,offsetY);
        }else if(mode == Mode.arriveStart){
            backMapQuestion.draw(offsetX,offsetY);
        }else if(mode == Mode.menu){
            md.draw(offsetX,offsetY);
        }
    }

    @Override
    public void init() {
        FlagManager.setFlagValue(FlagManager.FlagType.global,1,screenType.getInt());
        EventManager eventManager = GameManager.getDataBase().getEventManager();
        String str = eventManager.checkEvent();
        if(str != null){
            eventManager.startEvent(this,str);
            LoadingTransition.getInstance().useFadeIn(false);
        }
        GameManager.startBGM(R.raw.map,true);
    }

    @Override
    public void Touch() {
        if(freeze)
            return;

        if(mode == DungeonScreen.Mode.moveTownEffect)
            return;
        boolean through = true;
        through = through && menuButton.touch(Input.getTouchArray()[0]);
        if(!through)
            return;

        if(mode ==Mode.menu) {
            md.touch();
            return;
        }

        for (int n = 0; n < icons.size(); n++) {
            icons.get(n).touch(Input.getTouchArray()[0]);
            icons.get(n).proc();
        }

        if(mode == DungeonScreen.Mode.arriveTown){
            inTownQuestion.touch(Input.getTouchArray()[0]);
        }else if(mode == DungeonScreen.Mode.arriveEncount) {

        }else if(mode == DungeonScreen.Mode.returnScreen) {
            battleQuestion.touch(Input.getTouchArray()[0]);
        }else if(mode == Mode.arriveStart){
            backMapQuestion.touch(Input.getTouchArray()[0]);
        }else {
/*
            float camX = GLES20Util.getCameraPosX() + Input.getTouchArray()[0].getDelta(Touch.Pos_Flag.X) / 1000f;
            float camY = GLES20Util.getCameraPosY() - Input.getTouchArray()[0].getDelta(Touch.Pos_Flag.Y) / 1000f;
            if (camX < 0)
                camX = 0;
            else if (camX + GLES20Util.getAspect() > background.getWidth())
                camX = background.getWidth() - GLES20Util.getAspect();
            if (camY < 0)
                camY = 0;
            else if (camY + 1f > background.getHeight())
                camY = background.getHeight() - 1f;

            GLES20Util.setCameraPos(camX, camY);*/
        }

        Input.getTouchArray()[0].resetDelta();
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

    protected class InTownYesListener implements ButtonListener {

        @Override
        public void touchDown(Button button) {

        }

        @Override
        public void touchHover(Button button) {

        }

        @Override
        public void touchUp(Button button) {
            mode = DungeonScreen.Mode.non;
            ArrayList<String> enemys = nowPoint.getEnemies();
            if(enemys.size() != 0) {
                GameManager.stack.push(GameManager.nowScreen);
                GameManager.args = new Object[enemys.size() + 2];
                GameManager.args[0] = nowPoint.getBackgroundFile();
                GameManager.args[1] = enemys.size();
                for (int n = 2; n < GameManager.args.length; n++) {
                    GameManager.args[n] = enemys.get(n - 2);
                }
                nowPoint.procFlag();
                LoadingTransition lt = LoadingTransition.getInstance();
                lt.initTransition(BattleScreen.class);
                GameManager.transition = lt;
                GameManager.isTransition = true;
            }
        }
    }

    protected class InTownNoListener implements ButtonListener{

        @Override
        public void touchDown(Button button) {

        }

        @Override
        public void touchHover(Button button) {

        }

        @Override
        public void touchUp(Button button) {
            mode = DungeonScreen.Mode.non;
            inTownQuestion.startFadeOutAnimation();
        }
    }

    protected class BattleNoButtonListener implements ButtonListener{

        @Override
        public void touchDown(Button button) {

        }

        @Override
        public void touchHover(Button button) {

        }

        @Override
        public void touchUp(Button button) {
            mode = DungeonScreen.Mode.moveTownEffect;
            cursorAnimator.reset();
            cursorAnimator.start();
            cursorAnimator.setAnimation(
                    toPoint.getEncountPosX(nowPoint,encount),
                    toPoint.getEncountPosY(nowPoint,encount),
                    nowPoint.getX(),
                    nowPoint.getY(),
                    cursorAnimTime);
            encount = -1;
        }
    }

    protected class BattleYesButtonListener implements ButtonListener{

        @Override
        public void touchDown(Button button) {

        }

        @Override
        public void touchHover(Button button) {

        }

        @Override
        public void touchUp(Button button) {
            mode = DungeonScreen.Mode.moveTownEffect;
            cursorAnimator.reset();
            cursorAnimator.start();
            cursorAnimator.setAnimation(
                    toPoint.getEncountPosX(nowPoint,encount),
                    toPoint.getEncountPosY(nowPoint,encount),
                    toPoint.getEncountPosX(nowPoint,encount+1),
                    toPoint.getEncountPosY(nowPoint,encount+1),
                    cursorAnimTime);
        }
    }

    protected class BackMapNoButtonListener implements ButtonListener{

        @Override
        public void touchDown(Button button) {

        }

        @Override
        public void touchHover(Button button) {

        }

        @Override
        public void touchUp(Button button) {
            mode = DungeonScreen.Mode.non;
            backMapQuestion.startFadeOutAnimation();
        }
    }

    protected class BackMapYesButtonListener implements ButtonListener{
        @Override
        public void touchDown(Button button) {

        }

        @Override
        public void touchHover(Button button) {

        }

        @Override
        public void touchUp(Button button) {
            StackLoadingTransition slt = StackLoadingTransition.getInstance();
            slt.initStackLoadingTransition();
            GameManager.transition = slt;
            GameManager.isTransition = true;
        }
    }

    public class DungeonIcon implements ButtonListener,UI {
        protected boolean boss;
        public DungeonPoint point;
        public DungeonIcon(DungeonPoint point){
            this.point = point;
            this.point.setButtonListener(this);
        }

        @Override
        public boolean touch(Touch touch) {
            return point.touch(touch);
        }

        @Override
        public void proc() {
            point.proc();
        }

        @Override
        public void draw(float offsetX, float offsetY){
            point.drawIcon(offsetX,offsetY);
        }

        @Override
        public void touchDown(Button button) {

        }

        @Override
        public void touchHover(Button button) {
        }

        @Override
        public void touchUp(Button button) {
            if(nowPoint == point){
                //同じ場所をクリック
                encount = -1;
                if(nowPoint.isStart()) {
                    mode = Mode.arriveStart;
                    backMapQuestion.startFadeInAnimation();
                }else if(nowPoint.getEnemies().size() != 0) {
                    mode = Mode.arriveTown;
                    inTownQuestion.startFadeInAnimation();
                }
                return;
            }
            else if(nowPoint.getEnemies().size() != 0){
                //ボスの場所だったらボス戦開始
                return;
            }
            DungeonNode node = nowPoint.searchNode(point);
            if(node == null){
                toPoint = null;
            }else{
                toPoint = node;
                encount = 0;
                mode = DungeonScreen.Mode.moveTownEffect;

                cursorAnimator.reset();
                cursorAnimator.start();
                cursorAnimator.setAnimation(
                        nowPoint.getX(),
                        nowPoint.getY(),
                        toPoint.getEncountPosX(nowPoint,encount+1),
                        toPoint.getEncountPosY(nowPoint,encount+1),
                        cursorAnimTime);
                Log.d("TownIcon","発見しました");
            }
        }
    }
    protected class MenuDialog {
        protected float button_height = 0.2f;
        protected float shop_list_width = 0.5f;
        protected float shop_list_height = GLES20Util.getHeight_gl();
        protected float itemList_y = 0;
        protected float itemList_x = 0;
        protected Button backButton;
        protected List itemList;
        protected PlayerStatas playerStatas;

        public MenuDialog() {
            backButton = new Button(0, 0, 1, 1, "戻る");
            init();
        }

        public void init() {
            playerStatas = new PlayerStatas();
            this.itemList = new List(0, button_height, shop_list_width, shop_list_height);
            this.itemList.setContentHeight(0.1f);
            this.itemList.setTextPaddint(0.03f);
            this.itemList.setHorizontal(UIAlign.Align.LEFT);
            this.itemList.setVertical(UIAlign.Align.BOTTOM);
            this.itemList.setX(itemList_x);
            this.itemList.setY(itemList_y);
            PlayerData pd = GameManager.getPlayerData();
            for (Item item : pd.getItemList()) {
                if (!item.isUseable())
                    continue;
                item.setPadding(0.05f);
                item.setHeight(itemList.getContentHeight() - 0.01f);
                item.setWidth(itemList_x, itemList.getContentWidth());
                Button btn = new Button(0, 0, 1, 1, item);
                btn.setBitmap(Constant.getBitmap(Constant.BITMAP.system_button));
                btn.setPadding(0.01f);
                btn.setCriteria(UI.Criteria.Height);
                btn.setButtonListener(new MenuDialog.ItemListListener(item, btn));
                itemList.addItem(btn);
            }
            backButton.setBitmap(Constant.getBitmap(Constant.BITMAP.system_button));
            backButton.useAspect(true);
            backButton.setPadding(0.05f);
            backButton.setCriteria(UI.Criteria.Height);
            backButton.setHorizontal(UIAlign.Align.RIGHT);
            backButton.setVertical(UIAlign.Align.BOTTOM);
            backButton.setHeight(button_height);
            backButton.setX(GLES20Util.getWidth_gl());
            backButton.setButtonListener(new ButtonListener() {
                @Override
                public void touchDown(Button button) {

                }

                @Override
                public void touchHover(Button button) {

                }

                @Override
                public void touchUp(Button button) {
                    mode = Mode.non;
                }
            });
        }

        public void touch() {
            Touch touch = Input.getTouchArray()[0];
            itemList.touch(touch);
            backButton.touch(touch);
        }

        public void proc() {
            itemList.proc();
            backButton.proc();
            playerStatas.proc();
        }

        public void draw(float offsetX, float offsetY) {
            itemList.draw(offsetX, offsetY);
            playerStatas.draw(offsetX, offsetY);
            backButton.draw(offsetX, offsetY);
        }

        public class ItemListListener implements ButtonListener {
            protected Item item;
            protected Button btn;

            public ItemListListener(Item _item, Button btn) {
                item = _item;
                this.btn = btn;
            }

            @Override
            public void touchDown(Button button) {

            }

            @Override
            public void touchHover(Button button) {

            }

            @Override
            public void touchUp(Button button) {
                selectItem = item;
                if (selectItem.getNumber() - 1 <= 0)
                    itemList.removeItem(btn);
                mode = Mode.item_select;
            }
        }
    }
}
